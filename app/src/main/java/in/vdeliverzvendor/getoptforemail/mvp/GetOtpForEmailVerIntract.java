package in.vdeliverzvendor.getoptforemail.mvp;

import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.signup.model.SignupResponse;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GetOtpForEmailVerIntract  implements  GetOtpForEmailVerContract.GetEmailotpIntractor{

    String TAG= GetOtpForEmailVerIntract.class.getSimpleName();
    String stremail;


    interface OnGetotpListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(String stremail,final OnGetotpListener listener) {

        this.stremail=stremail;

        new Handler().postDelayed(() -> {
            if(stremail.isEmpty()){
                listener.onError("Email Error");
            }
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void getemailotpAPICall(OnFinishedListener onFinishedListener) {
        VDeliverzApiVendor.getClient().getotoforemail(stremail).enqueue(new Callback<SignupResponse>(){
            @Override
            public void onResponse(@NotNull Call<SignupResponse> call, @NotNull Response<SignupResponse> response) {
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
            public void onFailure(@NotNull Call<SignupResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });


    }
}
