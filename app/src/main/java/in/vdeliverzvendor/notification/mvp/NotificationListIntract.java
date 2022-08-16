package in.vdeliverzvendor.notification.mvp;

import android.os.Handler;
import android.util.Log;
import org.jetbrains.annotations.NotNull;

import in.vdeliverzvendor.notification.model.NotificationListResponse;
import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationListIntract implements  NotificationListContract.GetnotificationlistIntractor{

    String TAG=NotificationListIntract.class.getSimpleName();
    String strpage;

    interface OnnotificationlistListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(String strpage,OnnotificationlistListener listener) {
        new Handler().postDelayed(() -> {
            this.strpage=strpage;

            listener.onSuccess();
        }, 500);
    }

    @Override
    public void notificationlistAPICall(OnFinishedListener onFinishedListener) {

        VDeliverzApiVendor.getClient().getnotificationlist(strpage).enqueue(
                new Callback<NotificationListResponse>(){
            @Override
            public void onResponse(@NotNull Call<NotificationListResponse> call,
                                   @NotNull Response<NotificationListResponse> response) {
                Log.d(TAG, "onResponse: 1");
                if(response.isSuccessful()) {
                    Log.d(TAG, "onResponse: 2");
                    if (response.code() == 200) {
                        Log.d(TAG, "onResponse: 3");
                        if (response.body() != null) {
                            Log.d(TAG, "onResponse: 4");
                            if(response.body().getStatus()){
                                Log.d(TAG, "onResponse: 5");
                                onFinishedListener.onFinished(response.body());
                            }else {
                                Log.d(TAG, "onResponse: 6");
                                onFinishedListener.onFailure(response.body().getMessage());
                            }
                        }else{
                            Log.d(TAG, "onResponse: 7");
                            onFinishedListener.onFailure(response.message());
                        }
                    }else {
                        Log.d(TAG, "onResponse: 8");
                        onFinishedListener.onFailure(response.message());
                    }
                }else {
                    if(response.code()==401){
                        MnxPreferenceManager.clearAllPreferences();
                        onFinishedListener.do_logout();
                    }else{
                        onFinishedListener.onFailure("Server Error");
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<NotificationListResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    Log.d(TAG, "onResponse: 9");
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });

    }
}
