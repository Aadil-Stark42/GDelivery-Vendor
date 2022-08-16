package in.vdeliverzvendor.bankdetail.mvp_withdraw;

import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import in.vdeliverzvendor.bankdetail.model_withdraw.WithDrawResponse;
import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WithDrawIntract implements  WithdrawContract.GetwithdrawIntractor {

    String TAG= WithDrawIntract.class.getSimpleName();
    String strShopId,strbankid;

    interface OnbanklistListener {
        void onSuccess();
        void onError(String msg);
    }


    public void directValidation(String strShopId,String strbankid,final OnbanklistListener listener) {
        this.strShopId=strShopId;
        this.strbankid=strbankid;
        new Handler().postDelayed(() -> {

            listener.onSuccess();
        }, 500);
    }

    @Override
    public void withdrawAPICall(OnFinishedListener onFinishedListener) {
        VDeliverzApiVendor.getClient().requestwithdrw(strShopId,strbankid).enqueue(new Callback<WithDrawResponse>(){
            @Override
            public void onResponse(@NotNull Call<WithDrawResponse> call, @NotNull Response<WithDrawResponse> response) {
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
            public void onFailure(@NotNull Call<WithDrawResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });

    }
}
