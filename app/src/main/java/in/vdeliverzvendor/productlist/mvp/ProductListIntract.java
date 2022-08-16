package in.vdeliverzvendor.productlist.mvp;

import android.os.Handler;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import in.vdeliverzvendor.productlist.model.ProductListResponse;
import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListIntract implements ProductListContract.GetProductlistIntractor {

    String TAG= ProductListIntract.class.getSimpleName();
    String stremail,strpassword,strPage;

    interface OnLoginListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(String strpage,OnLoginListener listener) {
        new Handler().postDelayed(() -> {
            this.strPage=strpage;

            listener.onSuccess();
        }, 500);
    }

    @Override
    public void productlistAPICall(OnFinishedListener onFinishedListener) {

        VDeliverzApiVendor.getClient().getproductlist(strPage).enqueue(new Callback<ProductListResponse>(){
            @Override
            public void onResponse(@NotNull Call<ProductListResponse> call, @NotNull Response<ProductListResponse> response) {
                Log.d(TAG, "onResponse: 1");
                if(response.isSuccessful()) {
                    Log.d(TAG, "onResponse: 2");
                    if (response.code() == 200) {
                        Log.d(TAG, "onResponse: 3");
                        if (response.body() != null) {
                            Log.d(TAG, "onResponse: 4");
                            if(response.body().getStatus()){
                                Log.d(TAG, "onResponse: 5");
                                onFinishedListener.onFinished(response.body());
                            }else {
                                Log.d(TAG, "onResponse: 6");
                                onFinishedListener.onFailure(response.body().getMessage());
                            }
                        }else{
                            Log.d(TAG, "onResponse: 7");
                            onFinishedListener.onFailure(response.message());
                        }
                    }else {
                        Log.d(TAG, "onResponse: 8");
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
                    Log.d(TAG, "onResponse: 9");
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });

    }
}
