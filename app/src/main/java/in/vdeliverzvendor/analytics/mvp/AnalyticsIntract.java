package in.vdeliverzvendor.analytics.mvp;


import android.os.Handler;

import org.jetbrains.annotations.NotNull;
import in.vdeliverzvendor.analytics.model.AnalyticsResponse;
import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnalyticsIntract implements AnalyticsContract.GetanalyticsIntractor {

    String TAG= AnalyticsIntract.class.getSimpleName();
    String stryear,strmonth,strshop;

    interface OnanalyticsListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(String stryear,String strmonth,String strshop, final OnanalyticsListener listener) {
        this.stryear=stryear;
        this.strmonth=strmonth;
        this.strshop=strshop;
        new Handler().postDelayed(() -> {
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void analyticsAPICall(OnFinishedListener onFinishedListener) {
        VDeliverzApiVendor.getClient().getanalyticsdata(stryear,strmonth,strshop).enqueue(new Callback<AnalyticsResponse>(){
            @Override
            public void onResponse(@NotNull Call<AnalyticsResponse> call, @NotNull Response<AnalyticsResponse> response) {
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
            public void onFailure(@NotNull Call<AnalyticsResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });

    }
}
