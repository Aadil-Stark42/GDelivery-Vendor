package in.vdeliverzvendor.productcategory.mvp_catlist;


import android.os.Handler;
import org.jetbrains.annotations.NotNull;
import in.vdeliverzvendor.productcategory.model_catlist.ProductCatListResponse;
import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductCatListIntract implements  ProductCatListContract.GetproductcatlistIntractor {

    String TAG= ProductCatListIntract.class.getSimpleName();
    String strShopid,strpage;

    interface OnproductcatlistListener {
        void onSuccess();
        void onError(String msg);
    }

    @Override
    public void productcatlistAPICall(OnFinishedListener onFinishedListener) {

        VDeliverzApiVendor.getClient().getproductcat(strShopid,strpage).enqueue(new Callback<ProductCatListResponse>(){
            @Override
            public void onResponse(@NotNull Call<ProductCatListResponse> call, @NotNull Response<ProductCatListResponse> response) {
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
            public void onFailure(@NotNull Call<ProductCatListResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });

    }

    public void directValidation(String strShopId,String strpage,final OnproductcatlistListener listener) {

        new Handler().postDelayed(() -> {
            this.strShopid=strShopId;
            this.strpage=strpage;

            listener.onSuccess();
        }, 500);
    }
}
