package in.vdeliverzvendor.shoplist.mvp;


import android.os.Handler;
import org.jetbrains.annotations.NotNull;

import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.shoplist.model.ShopListResponse;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopListIntract implements ShopListContract.GetShopListIntractor {

    String TAG= ShopListIntract.class.getSimpleName();
    String strPage;

    interface OShopListener {
        void onSuccess();
        void onError(String msg);
    }

    @Override
    public void shoplistAPICall(OnFinishedListener onFinishedListener) {

        VDeliverzApiVendor.getClient().getshopList(strPage).enqueue(new Callback<ShopListResponse>(){
            @Override
            public void onResponse(@NotNull Call<ShopListResponse> call, @NotNull Response<ShopListResponse> response) {
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
            public void onFailure(@NotNull Call<ShopListResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });

    }

    public void directValidation(String strpage,final OShopListener listener) {
        this.strPage=strpage;
        new Handler().postDelayed(() -> {

            listener.onSuccess();
        }, 500);
    }
}
