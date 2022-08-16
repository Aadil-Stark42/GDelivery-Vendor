package in.vdeliverzvendor.otp_Verification.mvp_mobilenum;

import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.utils.GeneralResponse;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyMobileOtpIntract implements VerifyMobileOtpContract.GetVerifyOtpIntractor  {

    String TAG= VerifyMobileOtpIntract.class.getSimpleName();
    String mobile,otp;


    interface OnLoginListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(String mobile,String otp,final OnLoginListener listener) {

        this.mobile=mobile;
        this.otp=otp;

        new Handler().postDelayed(() -> {
            if(mobile.isEmpty()){
                listener.onError("Mobile Error");
            }if(otp.isEmpty()){
                listener.onError("Otp Error");
            }
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void verifymobileotpAPICall(OnFinishedListener onFinishedListener) {

        VDeliverzApiVendor.getClient().verifymobnumotp_api_call(mobile,otp).enqueue(new Callback<GeneralResponse>(){
            @Override
            public void onResponse(@NotNull Call<GeneralResponse> call,
                                   @NotNull Response<GeneralResponse> response) {
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
            public void onFailure(@NotNull Call<GeneralResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });

    }
}
