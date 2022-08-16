package in.vdeliverzvendor.dashboard.mvp;


import android.content.Intent;
import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import in.vdeliverzvendor.dashboard.model_dashboard.DashBoardResponse;
import in.vdeliverzvendor.login.LoginActivity;
import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashboardIntract implements  DashboardContract.GetDashboardIntractor {

    String TAG= DashboardIntract.class.getSimpleName();

    interface OndashboardListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(final DashboardIntract.OndashboardListener listener) {
        new Handler().postDelayed(() -> {
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void getdashboardAPICall(OnFinishedListener onFinishedListener) {

        VDeliverzApiVendor.getClient().getdashboarddet().enqueue(new Callback<DashBoardResponse>(){
            @Override
            public void onResponse(@NotNull Call<DashBoardResponse> call, @NotNull Response<DashBoardResponse> response) {
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
            public void onFailure(@NotNull Call<DashBoardResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });
    }
}
