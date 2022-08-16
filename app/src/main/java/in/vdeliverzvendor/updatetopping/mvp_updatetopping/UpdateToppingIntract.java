package in.vdeliverzvendor.updatetopping.mvp_updatetopping;

import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.utils.GeneralResponse;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpdateToppingIntract implements  UpdateToppingContract.updatetoppingProductIntractor{

    String TAG= UpdateToppingIntract.class.getSimpleName();
    String strPId,stractualprice,strtitle,stravailable,strname,strvariant,strtoppingid;

    interface OnupdatetoppingListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(String strPId,String strToppingId,String strActualPrice,String stravailable,String
            strtitleid,String strname,String strVariant,final OnupdatetoppingListener listener) {
        this.strPId=strPId;
        this.strtoppingid=strToppingId;
        this.stractualprice=strActualPrice;
        this.strtitle=strtitleid;
        this.stravailable=stravailable;
        this.strname=strname;
        this.strvariant=strVariant;

        new Handler().postDelayed(() -> {

            listener.onSuccess();
        }, 500);
    }

    @Override
    public void updatetoppingAPICall(OnFinishedListener onFinishedListener) {

        VDeliverzApiVendor.getClient().updatetopping(strPId,strtoppingid,stractualprice,stravailable,strtitle,strname,strvariant).enqueue(new Callback<GeneralResponse>(){
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
