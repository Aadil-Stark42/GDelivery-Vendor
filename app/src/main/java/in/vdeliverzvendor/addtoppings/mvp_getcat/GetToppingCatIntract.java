package in.vdeliverzvendor.addtoppings.mvp_getcat;

import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import in.vdeliverzvendor.addtoppings.model_getcat.ToppingCatResponse;
import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GetToppingCatIntract implements  GetToppingCatContract.GettoppingCatIntractor{

    String TAG= GetToppingCatIntract.class.getSimpleName();

    interface OntoppingcatListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(final OntoppingcatListener listener) {

        new Handler().postDelayed(() -> {

            listener.onSuccess();
        }, 500);
    }

    @Override
    public void toppingcatAPICall(OnFinishedListener onFinishedListener) {
        VDeliverzApiVendor.getClient().gettoppingcat().enqueue(new Callback<ToppingCatResponse>(){
            @Override
            public void onResponse(@NotNull Call<ToppingCatResponse> call, @NotNull Response<ToppingCatResponse> response) {
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
            public void onFailure(@NotNull Call<ToppingCatResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });

    }
}
