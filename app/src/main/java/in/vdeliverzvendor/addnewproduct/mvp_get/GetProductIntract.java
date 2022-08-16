package in.vdeliverzvendor.addnewproduct.mvp_get;


import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import in.vdeliverzvendor.addnewproduct.model_get.GetProductResponse;
import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GetProductIntract implements  GetProductContract.GetproductIntractor{

    String TAG= GetProductIntract.class.getSimpleName();

    interface OnGetProductListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(OnGetProductListener listener) {
        new Handler().postDelayed(() -> {
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void getproductAPICall(OnFinishedListener onFinishedListener) {
        VDeliverzApiVendor.getClient().getproduct().enqueue(new Callback<GetProductResponse>(){
            @Override
            public void onResponse(@NotNull Call<GetProductResponse> call, @NotNull Response<GetProductResponse> response) {
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
            public void onFailure(@NotNull Call<GetProductResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });

    }
}
