package in.vdeliverzvendor.slot.mvp_slotupdate;


import android.os.Handler;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.utils.GeneralResponse;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SlotUpdateIntract implements SlotUpdateContract.GetslotupdateIntractor {

    String TAG= SlotUpdateIntract.class.getSimpleName();
    String strFrom,strTo,strShopid,strslotid;
    List<String> weekdays;


    interface OnslotsupdateListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(String strFrom, String strTo, List<String> weekdays, String strshopid,String strslotd, final OnslotsupdateListener listener) {
        this.strFrom=strFrom;
        this.strTo=strTo;
        this.strShopid=strshopid;
        this.weekdays=weekdays;
        this.strslotid=strslotd;
        new Handler().postDelayed(() -> {
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void slotupdateAPICall(OnFinishedListener onFinishedListener) {

        MultipartBody.Part from = MultipartBody.Part.createFormData("from", strFrom);
        MultipartBody.Part to = MultipartBody.Part.createFormData("to", strTo);
        MultipartBody.Part shopid = MultipartBody.Part.createFormData("shop_id", strShopid);
        MultipartBody.Part slotid = MultipartBody.Part.createFormData("slot_id", strslotid);

        VDeliverzApiVendor.getClient().updateslot(from,to,shopid,slotid,weekdays).enqueue(new Callback<GeneralResponse>(){
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
