package in.vdeliverzvendor.addvariants.mvp;


import android.os.Handler;
import org.jetbrains.annotations.NotNull;
import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.utils.GeneralResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddStockIntract implements  AddStockContract.GetaddstockIntractor {

    String TAG= AddStockIntract.class.getSimpleName();
    String strPId,stractualprice,strsellingprice,stravailable,strsize,strunit,strvariant;

    interface OnaddstockListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(String strPId,String strActualPrice,String strSellingPrice,String strAvailable,String strSize,String strUnit,String strVariant,final OnaddstockListener listener) {
        this.strPId=strPId;
        this.stractualprice=strActualPrice;
        this.strsellingprice=strSellingPrice;
        this.stravailable=strAvailable;
        this.strsize=strSize;
        this.strunit=strUnit;
        this.strvariant=strVariant;

        new Handler().postDelayed(() -> {

            listener.onSuccess();
        }, 500);
    }

    @Override
    public void addstockAPICall(OnFinishedListener onFinishedListener) {

        VDeliverzApiVendor.getClient().addproductstock(strPId,stractualprice,strsellingprice,stravailable,strsize,strunit,strvariant).enqueue(new Callback<GeneralResponse>(){
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
