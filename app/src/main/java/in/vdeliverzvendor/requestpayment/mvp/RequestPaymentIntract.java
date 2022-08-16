package in.vdeliverzvendor.requestpayment.mvp;


import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import in.vdeliverzvendor.requestpayment.model.PaymentRequestResponse;
import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestPaymentIntract implements  RequestPaymentContract.GetrequestpaymentIntractor {

    String TAG= RequestPaymentIntract.class.getSimpleName();
    String strShopId,strPage;

    interface OnRequestpaymentListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(String strShopId,String strpage, final OnRequestpaymentListener listener) {
        this.strShopId=strShopId;
        this.strPage=strpage;
        new Handler().postDelayed(() -> {
            if(strShopId == null || strShopId.isEmpty()){
                listener.onError("Shop Id Error");
            }
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void requestpaymentAPICall(OnFinishedListener onFinishedListener) {
        VDeliverzApiVendor.getClient().getpayemetrequest(strShopId,strPage).enqueue(new Callback<PaymentRequestResponse>(){
            @Override
            public void onResponse(@NotNull Call<PaymentRequestResponse> call, @NotNull Response<PaymentRequestResponse> response) {
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
            public void onFailure(@NotNull Call<PaymentRequestResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });

    }
}
