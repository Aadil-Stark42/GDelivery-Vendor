package in.vdeliverzvendor.updatetopping.mvp_gettopping;

import android.os.Handler;
import org.jetbrains.annotations.NotNull;
import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.updatetopping.model_gettopping.ToppingDetailResponse;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetToppingIntract implements  GetToppingContract.GettoppingIntractor {

    String TAG= GetToppingIntract.class.getSimpleName();
    String strtoppingid;

    interface OnToppingListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(String strtoppingid, final OnToppingListener listener) {
        this.strtoppingid=strtoppingid;
        new Handler().postDelayed(() -> {
            if(strtoppingid == null || strtoppingid.isEmpty()){
                listener.onError("Topping ID Error");
            }
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void getstockAPICall(OnFinishedListener onFinishedListener) {

        VDeliverzApiVendor.getClient().gettoppingdetail(strtoppingid).enqueue(new Callback<ToppingDetailResponse>(){
            @Override
            public void onResponse(@NotNull Call<ToppingDetailResponse> call, @NotNull Response<ToppingDetailResponse> response) {
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
            public void onFailure(@NotNull Call<ToppingDetailResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });

    }
}
