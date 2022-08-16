package in.vdeliverzvendor.shopoverview.mvp;

import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import in.vdeliverzvendor.shopoverview.model.ShopDetailResponse;
import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopDetailIntract implements  ShopDetailContract.GetshopdetailIntractor {


    String TAG= ShopDetailIntract.class.getSimpleName();
    String strshopId;

    interface onShopDetaillistener {
        void onSuccess();
        void onError(String msg);
    }
    public void directValidation(String strShopId, final onShopDetaillistener listener) {
        this.strshopId=strShopId;
        new Handler().postDelayed(() -> {
            if(strshopId == null || strshopId.isEmpty()){
                listener.onError("Shop ID Error");
            }
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void shopdetailPICall(OnFinishedListener onFinishedListener) {

        VDeliverzApiVendor.getClient().getshopdetail(strshopId).enqueue(new Callback<ShopDetailResponse>(){
            @Override
            public void onResponse(@NotNull Call<ShopDetailResponse> call, @NotNull Response<ShopDetailResponse> response) {
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
            public void onFailure(@NotNull Call<ShopDetailResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });

    }
}
