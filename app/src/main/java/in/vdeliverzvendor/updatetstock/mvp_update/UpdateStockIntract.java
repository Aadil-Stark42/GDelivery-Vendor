package in.vdeliverzvendor.updatetstock.mvp_update;


import android.os.Handler;
import org.jetbrains.annotations.NotNull;
import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.utils.GeneralResponse;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpdateStockIntract implements  UpdateStockContract.updatestockProductIntractor {

    String TAG= UpdateStockIntract.class.getSimpleName();
    String strPId,stractualprice,strsellingprice,stravailable,strsize,strunit,strvariant,strstockid;

    interface OnUpdateStockListener {
        void onSuccess();
        void onError(String msg);
    }
    public void directValidation(String strPId,String strstockid,String strActualPrice,String strSellingPrice,String strAvailable,
                                 String strSize,String strUnit,String strVariant,final OnUpdateStockListener listener) {
        this.strPId=strPId;
        this.stractualprice=strActualPrice;
        this.strsellingprice=strSellingPrice;
        this.stravailable=strAvailable;
        this.strsize=strSize;
        this.strunit=strUnit;
        this.strstockid=strstockid;
        this.strvariant=strVariant;

        new Handler().postDelayed(() -> {

            listener.onSuccess();
        }, 500);
    }


    @Override
    public void updatestockAPICall(OnFinishedListener onFinishedListener) {

        VDeliverzApiVendor.getClient().updatestcok(strPId,strstockid,stractualprice,strsellingprice,stravailable,strsize,strunit,strvariant).enqueue(new Callback<GeneralResponse>(){
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
