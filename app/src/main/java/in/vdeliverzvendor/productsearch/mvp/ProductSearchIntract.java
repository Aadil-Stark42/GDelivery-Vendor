package in.vdeliverzvendor.productsearch.mvp;

import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import in.vdeliverzvendor.productlist.model.ProductListResponse;
import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductSearchIntract implements  ProductSearchContract.GetproductsearchIntractor {

    String TAG= ProductSearchIntract.class.getSimpleName();
    String strSearch,strpage;

    interface onProductsearchlistener {
        void onSuccess();
        void onError(String msg);
    }
    public void directValidation(String strsearch, String strpage, final onProductsearchlistener listener) {
        this.strSearch=strsearch;
        this.strpage=strpage;
        new Handler().postDelayed(() -> {
            if(strSearch == null || strSearch.isEmpty()){
                listener.onError("Search text Error");
            }
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void productsearchAPICall(OnFinishedListener onFinishedListener) {
        VDeliverzApiVendor.getClient().getproductsearchlist(strSearch,strpage).enqueue(new Callback<ProductListResponse>(){
            @Override
            public void onResponse(@NotNull Call<ProductListResponse> call, @NotNull Response<ProductListResponse> response) {
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
            public void onFailure(@NotNull Call<ProductListResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });
    }
}
