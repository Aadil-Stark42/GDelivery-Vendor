package in.vdeliverzvendor.orders.mvp_orderdetails;


import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import in.vdeliverzvendor.orders.model_ordredetails.OrderDetailResponse;
import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailIntract implements  OrderDetailContract.GetorderdetailIntractor {

    String TAG= OrderDetailIntract.class.getSimpleName();
    String strorderid;

    interface OnOrderDetailListener {
        void onSuccess();
        void onError(String msg);
    }

    @Override
    public void orderdetailAPICall(OnFinishedListener onFinishedListener) {

        VDeliverzApiVendor.getClient().getorderdetails(strorderid).enqueue(new Callback<OrderDetailResponse>(){
            @Override
            public void onResponse(@NotNull Call<OrderDetailResponse> call, @NotNull Response<OrderDetailResponse> response) {
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
            public void onFailure(@NotNull Call<OrderDetailResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });



    }

    public void directValidation(String strOrderid, final OnOrderDetailListener listener) {
        this.strorderid=strOrderid;
        new Handler().postDelayed(() -> {
            if(strorderid == null || strorderid.isEmpty()){
                listener.onError("Order ID Error");
            }
            listener.onSuccess();
        }, 500);
    }

}
