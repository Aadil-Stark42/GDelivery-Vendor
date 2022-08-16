package in.vdeliverzvendor.shopsearch.mvp;

import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.shoplist.model.ShopListResponse;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShopSearchIntract implements  ShopSearchContract.GetshopsearchIntractor {

    String TAG= ShopSearchIntract.class.getSimpleName();
    String strSearch,strpage;

    interface onShopsearchlistener {
        void onSuccess();
        void onError(String msg);
    }
    public void directValidation(String strsearch, String strpage, final onShopsearchlistener listener) {
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
    public void shopsearchAPICall(OnFinishedListener onFinishedListener) {
        VDeliverzApiVendor.getClient().getsearchlist(strSearch,strpage).enqueue(new Callback<ShopListResponse>(){
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
}
