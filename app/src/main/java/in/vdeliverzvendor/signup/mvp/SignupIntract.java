package in.vdeliverzvendor.signup.mvp;

import android.os.Handler;


import org.jetbrains.annotations.NotNull;

import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.signup.model.SignupResponse;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupIntract implements SignupContract.GetSignupIntractor{

    String TAG= SignupIntract.class.getSimpleName();
    String name,email,mobile,password,confirm_password;


    interface OnLoginListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(String name,String email,String mobile,String password,String confirm_password, final OnLoginListener listener) {
        this.name=name;
        this.email=email;
        this.mobile=mobile;
        this.password=password;
        this.confirm_password=confirm_password;

        new Handler().postDelayed(() -> {
            if(name.isEmpty()){
                listener.onError("Name Error");
            }if(email.isEmpty()){
                listener.onError("Email Error");
            }if(mobile.isEmpty()){
                listener.onError("Mobile Error");
            }if(password.isEmpty()){
                listener.onError("Password Error");
            }if(confirm_password.isEmpty()){
                listener.onError("Confirm Password Error");
            }
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void signupAPICall(OnFinishedListener onFinishedListener) {

        VDeliverzApiVendor.getClient().signup_api_call(name,email,mobile,password,confirm_password).enqueue(new Callback<SignupResponse>(){
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
