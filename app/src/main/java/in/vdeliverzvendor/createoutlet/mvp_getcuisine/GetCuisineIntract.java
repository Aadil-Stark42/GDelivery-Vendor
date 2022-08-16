package in.vdeliverzvendor.createoutlet.mvp_getcuisine;


import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import in.vdeliverzvendor.createoutlet.model_getcuisine.CuisineListResponse;
import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GetCuisineIntract implements  GetCuisineContract.GetcuisineIntractor {

    String TAG= GetCuisineIntract.class.getSimpleName();

    interface OnCuisineListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(final OnCuisineListener listener) {
        new Handler().postDelayed(() -> {
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void getcuisineCall(OnFinishedListener onFinishedListener) {

        VDeliverzApiVendor.getClient().getcuisinelist().enqueue(new Callback<CuisineListResponse>(){
            @Override
            public void onResponse(@NotNull Call<CuisineListResponse> call, @NotNull Response<CuisineListResponse> response) {
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
            public void onFailure(@NotNull Call<CuisineListResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });

    }
}
