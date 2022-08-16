package in.vdeliverzvendor.login.mvp;

import android.os.Handler;

import org.jetbrains.annotations.NotNull;
import in.vdeliverzvendor.login.model.LoginResponse;
import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginIntractor implements  LoginContractor.GetLoginIntractor {

    String TAG= LoginIntractor.class.getSimpleName();
    String stremail,strpassword,strfcm;

    interface OnLoginListener {
        void onSuccess();
        void onError(String msg);
    }

    @Override
    public void loginAPICall(OnFinishedListener onFinishedListener) {
        VDeliverzApiVendor.getClient().getloginApi(stremail,strpassword,strfcm).enqueue(new Callback<LoginResponse>(){
            @Override
            public void onResponse(@NotNull Call<LoginResponse> call, @NotNull Response<LoginResponse> response) {
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
            public void onFailure(@NotNull Call<LoginResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });


    }

    public void directValidation(String stremail,String strpassword,String strfcm, final OnLoginListener listener) {
        this.stremail=stremail;
        this.strpassword=strpassword;
        this.strfcm=strfcm;
        new Handler().postDelayed(() -> {
            if(stremail == null || stremail.isEmpty()){
                listener.onError("Email ID Error");
            }
            if(strpassword == null || strpassword.isEmpty()){
                listener.onError("Password ID Error");
            }
            if(strfcm == null || strfcm.isEmpty()){
                listener.onError("fcm Error");
            }
            listener.onSuccess();
        }, 500);
    }

}
