package in.vdeliverzvendor.pushnotification;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import androidx.core.app.NotificationCompat;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.dashboard.DashboardActivity;

import static android.content.Context.NOTIFICATION_SERVICE;
import static android.content.Context.VIBRATOR_SERVICE;

public class NotificationUtils {

    private static String TAG = NotificationUtils.class.getSimpleName();
    private Context mContext;
    MediaPlayer mediaPlayer;

    public NotificationUtils(Context mContext) {
        this.mContext = mContext;
    }


    public void showNotificationMessage(final String title, final String message, final String imageUrl, Intent intent, boolean isSound) {
        if (TextUtils.isEmpty(message))
            return;


        // notification icon
        final int icon = R.drawable.notification_icon;

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        mContext,
                        0,
                        intent,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );

        Notification.Builder notification = new Notification.Builder(mContext);
        if (!TextUtils.isEmpty(imageUrl)) {
            if (imageUrl != null && imageUrl.length() > 4 && Patterns.WEB_URL.matcher(imageUrl).matches()) {
                Bitmap bitmap = getBitmapFromURL(imageUrl);
                if (bitmap != null) {
                    showBigNotification(bitmap, notification, icon, title, message, imageUrl, resultPendingIntent);
                } else {
                    showSmallNotification(notification, icon, title, message, imageUrl, resultPendingIntent);
                }
            }
        } else {
            showSmallNotification(notification, icon, title, message, imageUrl, resultPendingIntent);
            if (isSound) {
                play_ringtone();
            }
        }
    }


    private void showSmallNotification(Notification.Builder mBuilder, int icon, String title, String message, String timeStamp, PendingIntent resultPendingIntent) {
         Intent intent = new Intent(mContext, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = "Default";
        play_ringtone();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, channelId)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                 .setContentText(message).setAutoCancel(true).setContentIntent(pendingIntent);

        builder.setVibrate(new long[]{500, 500});
        builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        NotificationManager manager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{500, 500});
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());
    }

    private void showBigNotification(Bitmap bitmap, Notification.Builder mBuilder, int icon, String title, String message, String imageurl, PendingIntent resultPendingIntent) {
         NotificationCompat.BigPictureStyle bpStyle = new NotificationCompat.BigPictureStyle();
        bpStyle.bigPicture(bitmap);

        Intent intent = new Intent(mContext, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = "Default";
        play_ringtone();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, channelId);
        builder.setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(icon)
                 .setWhen(System.currentTimeMillis())
                .setStyle(bpStyle);
        builder.setVibrate(new long[]{500, 500});
        builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        NotificationManager manager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[] { 500, 500 });

            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());

    }

    /**
     * Downloading push notification image before displaying it in
     * the notification tray
     */


    public Bitmap getBitmapFromURL(String strURL) {
        try {
            if (!strURL.equals("sample.png")) {
                URL url = new URL(strURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public void play_ringtone() {
        Vibrator vibrator;
        vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        Log.e("vibratorvibrator", "vibratorvibrator ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(1500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(1500);
        }
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        mediaPlayer = MediaPlayer.create(mContext.getApplicationContext(), R.raw.notification_sound);
        mediaPlayer.start();


        /*  try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            mediaPlayer = MediaPlayer.create(mContext.getApplicationContext(), R.raw.preview);
            mediaPlayer.start();
            mediaPlayer.setLooping(true);

            MyFirebaseMessagingService.handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.e("handler.postDelayed", "handler.postDelayed ");
                    mediaPlayer.setLooping(false);
                    mediaPlayer = null;
                }
            }, 7000);
        } catch (Exception e) {
            Log.d(TAG, "play_ringtone: errororr " + e.getMessage());
        }*/
    }

    /**
     * Method checks if the app is in background or not
     */
    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }
        return isInBackground;
    }

    // Clears notification tray messages
    public static void clearNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
}
