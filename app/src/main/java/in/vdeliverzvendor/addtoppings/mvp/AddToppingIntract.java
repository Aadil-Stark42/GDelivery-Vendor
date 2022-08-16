package in.vdeliverzvendor.addtoppings.mvp;

import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.utils.GeneralResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddToppingIntract implements  AddToppingContract.GetaddtoppingIntractor {

    String TAG= AddToppingIntract.class.getSimpleName();
    String strPId,stractualprice,strtitle,stravailable,strname,strunit,strvariant;

    interface OnaddtoppingListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(String strPId,String strActualPrice,String stravailable,String strtitleid,String strname,String strVariant,final OnaddtoppingListener listener) {
        this.strPId=strPId;
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
    public void addtoppingAPICall(OnFinishedListener onFinishedListener) {
        VDeliverzApiVendor.getClient().addtopping(strPId,stractualprice,stravailable,strtitle,strname,strvariant).enqueue(new Callback<GeneralResponse>(){
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
                    onFinishedListener.onFailure("Server Error");
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
