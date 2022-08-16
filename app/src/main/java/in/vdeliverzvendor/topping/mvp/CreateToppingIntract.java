package in.vdeliverzvendor.topping.mvp;


import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.utils.GeneralResponse;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateToppingIntract implements  CreateToppingContract.Getcreatetopping_Intractor {
    String TAG= CreateToppingIntract.class.getSimpleName();
    String strname;

    interface OncreatetoppingListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(String strname, final OncreatetoppingListener listener) {
        this.strname=strname;
        new Handler().postDelayed(() -> {

            listener.onSuccess();
        }, 500);
    }

    @Override
    public void createtoppingAPICall(OnFinishedListener onFinishedListener) {
        VDeliverzApiVendor.getClient().addtitle(strname).enqueue(new Callback<GeneralResponse>(){
            @Override
            public void onResponse(@NotNull Call<GeneralResponse> call, @NotNull Response<GeneralResponse> response) {
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
