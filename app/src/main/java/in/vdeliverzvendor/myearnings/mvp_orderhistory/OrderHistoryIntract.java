package in.vdeliverzvendor.myearnings.mvp_orderhistory;


import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import in.vdeliverzvendor.myearnings.model_orderhistory.OrderHistroyResponse;
import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderHistoryIntract implements  OrderHistoryContract.GetorderhistoryIntractor {

    String TAG= OrderHistoryIntract.class.getSimpleName();
    String strShopId;

    interface OnOrderHistoryListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(String strShopId, final OnOrderHistoryListener listener) {
        this.strShopId=strShopId;
        new Handler().postDelayed(() -> {
            if(strShopId == null || strShopId.isEmpty()){
                listener.onError("Shop ID Error");
            }
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void orderhistoryAPICall(OnFinishedListener onFinishedListener) {

        VDeliverzApiVendor.getClient().getorderhistory(strShopId).enqueue(new Callback<OrderHistroyResponse>(){
            @Override
            public void onResponse(@NotNull Call<OrderHistroyResponse> call, @NotNull Response<OrderHistroyResponse> response) {
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
            public void onFailure(@NotNull Call<OrderHistroyResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });

    }

}
