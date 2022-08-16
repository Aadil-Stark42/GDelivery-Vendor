package in.vdeliverzvendor.productview.mvp;


import android.os.Handler;
import org.jetbrains.annotations.NotNull;

import in.vdeliverzvendor.productview.model.ProductDetailResponse;
import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductViewIntractor implements  ProductViewContract.GetProductviewIntractor {

    String TAG= ProductViewIntractor.class.getSimpleName();
    String strProductId;

    interface OnProductviewListener {
        void onSuccess();
        void onError(String msg);
    }

    @Override
    public void productviewAPICall(OnFinishedListener onFinishedListener) {

        VDeliverzApiVendor.getClient().getproductdetail(strProductId).enqueue(new Callback<ProductDetailResponse>(){
            @Override
            public void onResponse(@NotNull Call<ProductDetailResponse> call, @NotNull Response<ProductDetailResponse> response) {
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
            public void onFailure(@NotNull Call<ProductDetailResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });

    }

    public void directValidation(String strproductid, OnProductviewListener listener) {
        this.strProductId=strproductid;
        new Handler().postDelayed(() -> {
            if(strProductId == null || strProductId.isEmpty()){
                listener.onError("Product ID Error");
            }
            listener.onSuccess();
        }, 500);
    }
}
