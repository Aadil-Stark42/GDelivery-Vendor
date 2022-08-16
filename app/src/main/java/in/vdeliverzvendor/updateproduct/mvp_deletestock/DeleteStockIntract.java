package in.vdeliverzvendor.updateproduct.mvp_deletestock;


import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.utils.GeneralResponse;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DeleteStockIntract implements  DeleteStockContract.GetDeleteStockIntractor {

    String TAG= DeleteStockIntract.class.getSimpleName();
    String strStockId;

    interface OnDeleteStockListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(String strstockid, final OnDeleteStockListener listener) {
        this.strStockId=strstockid;
        new Handler().postDelayed(() -> {
            if(strStockId == null || strStockId.isEmpty()){
                listener.onError("Stock ID Error");
            }
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void deletestockAPICall(OnFinishedListener onFinishedListener) {

        VDeliverzApiVendor.getClient().deletestock(strStockId).enqueue(new Callback<GeneralResponse>(){
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
