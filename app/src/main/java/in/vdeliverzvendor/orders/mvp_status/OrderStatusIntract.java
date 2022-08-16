package in.vdeliverzvendor.orders.mvp_status;


import android.os.Handler;

import org.jetbrains.annotations.NotNull;
import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.utils.GeneralResponse;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderStatusIntract implements OrderStatucContract.GetorderstatusIntractor {

    String TAG= OrderStatusIntract.class.getSimpleName();
    String strOrderId,strAction;

    interface OnOrderStatusListener {
        void onSuccess();
        void onError(String msg);
    }


    public void directValidation(String strOrderId,String straction, final OnOrderStatusListener listener) {
        this.strOrderId=strOrderId;
        this.strAction=straction;
        new Handler().postDelayed(() -> {
            if(strOrderId == null || strOrderId.isEmpty()){
                listener.onError("Order Id ID Error");
            }
            if(strAction == null || strAction.isEmpty()){
                listener.onError("Action Error");
            }
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void orderstatusAPICall(OnFinishedListener onFinishedListener) {

        VDeliverzApiVendor.getClient().updateorderstatus(strOrderId,strAction).enqueue(new Callback<GeneralResponse>(){
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
