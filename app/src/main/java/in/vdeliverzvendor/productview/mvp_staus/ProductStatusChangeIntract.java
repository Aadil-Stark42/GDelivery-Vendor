package in.vdeliverzvendor.productview.mvp_staus;

import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.utils.GeneralResponse;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductStatusChangeIntract implements ProductStausChangeContract.GetProductStatusIntractor {

    String TAG= ProductStatusChangeIntract.class.getSimpleName();
    String strproductid,strname,strstatus;

    interface OnProductStatusListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(String strproductid,String strStatus,String strname, final OnProductStatusListener listener) {
        this.strproductid=strproductid;
        this.strstatus=strStatus;
        this.strname=strname;
        new Handler().postDelayed(() -> {
            if(strproductid == null || strproductid.isEmpty()){
                listener.onError("Product ID Error");
            }
            if(strstatus == null || strstatus.isEmpty()){
                listener.onError("Status Error");
            }
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void productstatusAPICall(OnFinishedListener onFinishedListener) {


        VDeliverzApiVendor.getClient().updateproductstatus(strproductid,strname,strstatus).enqueue(new Callback<GeneralResponse>(){
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
