package in.vdeliverzvendor.myearnings.mvp_withdrawdet;

import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import in.vdeliverzvendor.myearnings.model_withdrawdet.WithDrawDetailResponse;
import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WithDrawDetIntract implements  WithDrawDetContract.GetwithdrawdetIntractor {

    String TAG= WithDrawDetIntract.class.getSimpleName();
    String strWithdrawid;

    interface OnWithDrawListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(String strWithdrawid, final OnWithDrawListener listener) {
        this.strWithdrawid=strWithdrawid;
        new Handler().postDelayed(() -> {
            if(strWithdrawid == null || strWithdrawid.isEmpty()){
                listener.onError("WithDraw ID Error");
            }
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void withdrawdetAPICall(OnFinishedListener onFinishedListener) {

        VDeliverzApiVendor.getClient().getwithdrawdet(strWithdrawid).enqueue(new Callback<WithDrawDetailResponse>(){
            @Override
            public void onResponse(@NotNull Call<WithDrawDetailResponse> call, @NotNull Response<WithDrawDetailResponse> response) {
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
            public void onFailure(@NotNull Call<WithDrawDetailResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });

    }
    }
