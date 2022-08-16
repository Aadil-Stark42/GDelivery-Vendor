package in.vdeliverzvendor.shopoverview.mvp_product;


import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import in.vdeliverzvendor.shopoverview.model_product.ShopProductListResponse;
import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShopProductIntract  implements ShopProductContract.GetshopproductlistIntractor{

    String TAG= ShopProductIntract.class.getSimpleName();
    String strShopId;

    interface OnShopProdcutListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(String strShopId, final OnShopProdcutListener listener) {
        this.strShopId=strShopId;
        new Handler().postDelayed(() -> {
            if(strShopId == null || strShopId.isEmpty()){
                listener.onError("Shop ID Error");
            }
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void shopproductlistAPICall(OnFinishedListener onFinishedListener) {

        VDeliverzApiVendor.getClient().getshopproduct(strShopId).enqueue(new Callback<ShopProductListResponse>(){
            @Override
            public void onResponse(@NotNull Call<ShopProductListResponse> call, @NotNull Response<ShopProductListResponse> response) {
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
                    onFinishedListener.onFailure("Server Error");
                }
            }

            @Override
            public void onFailure(@NotNull Call<ShopProductListResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });


    }
}
