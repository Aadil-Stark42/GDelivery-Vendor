package in.vdeliverzvendor.updatetstock.mvp_getstock;


import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.updatetstock.model_getstock.StockDetailResponse;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GetStockIntract implements  GetStockContract.GetstockIntractor {

    String TAG= GetStockIntract.class.getSimpleName();
    String strstockid;

    interface OnStockListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(String strstockid, final OnStockListener listener) {
        this.strstockid=strstockid;
        new Handler().postDelayed(() -> {
            if(strstockid == null || strstockid.isEmpty()){
                listener.onError("Stock ID Error");
            }
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void getstockAPICall(OnFinishedListener onFinishedListener) {

        VDeliverzApiVendor.getClient().getstockdetail(strstockid).enqueue(new Callback<StockDetailResponse>(){
            @Override
            public void onResponse(@NotNull Call<StockDetailResponse> call, @NotNull Response<StockDetailResponse> response) {
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
            public void onFailure(@NotNull Call<StockDetailResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });


    }
}
