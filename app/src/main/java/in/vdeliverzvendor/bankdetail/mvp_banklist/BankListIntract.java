package in.vdeliverzvendor.bankdetail.mvp_banklist;


import android.os.Handler;

import org.jetbrains.annotations.NotNull;
import in.vdeliverzvendor.bankdetail.model_banklist.BankListResponse;
import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BankListIntract implements  BankListContract.GetbankListIntractor {

    String TAG= BankListIntract.class.getSimpleName();
    String strShopId;

    interface OnbanklistListener {
        void onSuccess();
        void onError(String msg);
    }


    public void directValidation(String strShopId,final OnbanklistListener listener) {
        this.strShopId=strShopId;
        new Handler().postDelayed(() -> {

            listener.onSuccess();
        }, 500);
    }

    @Override
    public void banklistAPICall(OnFinishedListener onFinishedListener) {
        VDeliverzApiVendor.getClient().getbanks(strShopId).enqueue(new Callback<BankListResponse>(){
            @Override
            public void onResponse(@NotNull Call<BankListResponse> call, @NotNull Response<BankListResponse> response) {
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
            public void onFailure(@NotNull Call<BankListResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });
    }
}
