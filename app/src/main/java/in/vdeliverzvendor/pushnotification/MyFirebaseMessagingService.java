package in.vdeliverzvendor.pushnotification;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import in.vdeliverzvendor.dashboard.DashboardActivity;
import in.vdeliverzvendor.utils.MnxConstant;
import in.vdeliverzvendor.utils.MnxPreferenceManager;

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static Handler handler = new Handler();
    String TAG = MyFirebaseMessagingService.class.getSimpleName();
    String title = "", message = "", imageUrl = "", timestamp = "", type;
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";
    private NotificationUtils notificationUtils;
    boolean isBackground = false;

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);

        Log.d(TAG, "onNewToken: " + s);

        MnxPreferenceManager.setString(MnxConstant.FCM_TOKEN, s);

    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "onMessageReceived: " + remoteMessage);
        Log.i("TESTPUSHMESSAGEVEN", "TESTPUSHMESSAGEVEN" + MnxPreferenceManager.getBoolean(MnxConstant.NOTIFICATION_MODE, false));

        if ((MnxPreferenceManager.getBoolean(MnxConstant.NOTIFICATION_MODE, true))) {
            String dataString = remoteMessage.getData().get("data");

            JSONObject data = null;
            if (dataString != null) {
                try {
                    data = new JSONObject(dataString);
                    Log.d(TAG, "onMessageReceived:datajosn " + data);
                    handleDataMessage(data);

                } catch (JSONException e) {
                    Log.e(TAG, "Ignoring push because of JSON exception while processing: " + dataString, e);
                    return;
                }
            }
        }

    }


    private void showNotificationMessage(Context context, String title, String message, String imageUrl, Intent resultIntent, boolean isSound) {

        Log.d(TAG, "showNotificationMessage: ");
        notificationUtils = new NotificationUtils(context);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, imageUrl, resultIntent, isSound);

    }


    private void handleDataMessage(JSONObject json) {


        Log.e(TAG, "push json: " + json.toString());
        try {
            title = json.getString("title");
            message = json.getString("message");
            imageUrl = json.getString("image");
            type = json.getString("type");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
            showNotificationMessage(getApplicationContext(), title, message, imageUrl, pushNotification, false);

        } else {
            // app is in background, show the notification in notification tray
            Intent resultIntent = new Intent(getApplicationContext(), DashboardActivity.class);
            resultIntent.putExtra("isevent", 1);
            resultIntent.putExtra("message", message);
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
            showNotificationMessage(getApplicationContext(), title, message, imageUrl, resultIntent, true);
        }
    }
}
