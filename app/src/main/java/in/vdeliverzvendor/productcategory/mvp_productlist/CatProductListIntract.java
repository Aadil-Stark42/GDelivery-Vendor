package in.vdeliverzvendor.productcategory.mvp_productlist;


import android.os.Handler;
import org.jetbrains.annotations.NotNull;

import in.vdeliverzvendor.productlist.model.ProductListResponse;
import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CatProductListIntract implements CatProductListContract.GetcatproductlistIntractor {

    String TAG= CatProductListIntract.class.getSimpleName();
    String strId,strShopId,strpage;

    interface OncatproductlistListener {
        void onSuccess();
        void onError(String msg);

    }

    public void directValidation(String strId,String strShopId,String strpage,final OncatproductlistListener listener) {

        new Handler().postDelayed(() -> {
            this.strId=strId;
            this.strShopId=strShopId;
            this.strpage=strpage;

            listener.onSuccess();
        }, 500);
    }

    @Override
    public void catproductlistAPICall(OnFinishedListener onFinishedListener) {

        VDeliverzApiVendor.getClient().getcatproductlist(strId,strShopId,strpage).enqueue(new Callback<ProductListResponse>(){
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
                    if(response.code()==401){
                        MnxPreferenceManager.clearAllPreferences();
                        onFinishedListener.do_logout();
                    }else{
                        onFinishedListener.onFailure("Server Error");
                    }
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
