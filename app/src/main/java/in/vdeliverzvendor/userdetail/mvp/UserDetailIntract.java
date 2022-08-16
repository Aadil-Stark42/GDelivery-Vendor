package in.vdeliverzvendor.userdetail.mvp;


import android.os.Handler;

import org.jetbrains.annotations.NotNull;
import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.userdetail.model.UserDetailResponse;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserDetailIntract implements  UserDetailContract.GetuserdetailIntractor {

    String TAG= UserDetailIntract.class.getSimpleName();

    interface OnuserdetailListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation( final OnuserdetailListener listener) {
        new Handler().postDelayed(() -> {
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void userdetailAPICall(OnFinishedListener onFinishedListener) {
        VDeliverzApiVendor.getClient().getuserdetail().enqueue(new Callback<UserDetailResponse>(){
            @Override
            public void onResponse(@NotNull Call<UserDetailResponse> call, @NotNull Response<UserDetailResponse> response) {
                if(response.isSuccessful()) {
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            if(response.body().getStatus()){
                                onFinishedListener.onFinished(response.body());
                            }else {
                                onFinishedListener.onFailure(response.body().getMessage());
                            }
                        }else{
                            onFinishedListener.onFailure(response.message());
                        }
                    }else {
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
            public void onFailure(@NotNull Call<UserDetailResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });

    }
}
