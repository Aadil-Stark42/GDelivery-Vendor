package in.vdeliverzvendor.productview.mvp_delete;


import android.os.Handler;

import org.jetbrains.annotations.NotNull;
import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.utils.GeneralResponse;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDeleteIntract implements  ProductDeleteContract.GetProductdeleteIntractor{

    String TAG= ProductDeleteIntract.class.getSimpleName();
    String strproductid;

    interface OnProductDeleteListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(String strproductid, final OnProductDeleteListener listener) {
        this.strproductid=strproductid;
        new Handler().postDelayed(() -> {
            if(strproductid == null || strproductid.isEmpty()){
                listener.onError("Product ID Error");
            }
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void productdeleteAPICall(OnFinishedListener onFinishedListener) {

        VDeliverzApiVendor.getClient().deleteproduct(strproductid).enqueue(new Callback<GeneralResponse>(){
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
