package in.vdeliverzvendor.bankdetail.mvp_bankcreate;

import android.os.Handler;

import org.jetbrains.annotations.NotNull;
import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.utils.GeneralResponse;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BankCreateIntract implements  BankCreateContract.GetcratebankIntractor {

    String TAG= BankCreateIntract.class.getSimpleName();
    String strShopId,strBankName,strHolderName,strBrach,strCity,strifsc,strAccountNum;

    interface OnbankCreateListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(String strShopId,String strHolderName, String  strAccountNum, String strCity,
                                 String strifsc, String strBranch, String strBankName, final OnbankCreateListener listener) {
        this.strShopId=strShopId;
        this.strHolderName=strHolderName;
        this.strAccountNum=strAccountNum;
        this.strBankName=strBankName;
        this.strBrach=strBranch;
        this.strCity=strCity;
        this.strifsc=strifsc;
        new Handler().postDelayed(() -> {

            listener.onSuccess();
        }, 500);
    }

    @Override
    public void cratebankAPICall(OnFinishedListener onFinishedListener) {

        VDeliverzApiVendor.getClient().createbank(strShopId,strHolderName,strAccountNum,strCity,strifsc,strBrach,strBankName).enqueue(new Callback<GeneralResponse>(){
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
