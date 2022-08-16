package in.vdeliverzvendor.updateproduct.mvp_deletetopping;

import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.utils.GeneralResponse;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DeleteToppingIntract implements  DeleteToppingContract.GetDeleteStockIntractor {

    String TAG= DeleteToppingIntract.class.getSimpleName();
    String strTppingId;

    interface OnDeleteToppingListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(String strtoppingid, final OnDeleteToppingListener listener) {
        this.strTppingId=strtoppingid;
        new Handler().postDelayed(() -> {
            if(strTppingId == null || strTppingId.isEmpty()){
                listener.onError("Topping ID Error");
            }
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void deletetoppingAPICall(OnFinishedListener onFinishedListener) {

        VDeliverzApiVendor.getClient().deletetopping(strTppingId).enqueue(new Callback<GeneralResponse>(){
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
