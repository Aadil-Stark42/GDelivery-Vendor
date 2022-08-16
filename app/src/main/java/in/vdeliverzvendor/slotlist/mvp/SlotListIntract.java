package in.vdeliverzvendor.slotlist.mvp;


import android.os.Handler;
import org.jetbrains.annotations.NotNull;
import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.slotlist.model.SlotListResponse;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SlotListIntract  implements  SlotListContract.GetslotlistIntractor{

    String TAG= SlotListIntract.class.getSimpleName();
    String strShopId;

    interface OnSlotListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(String strShopId, final OnSlotListener listener) {

        new Handler().postDelayed(() -> {
            this.strShopId=strShopId;
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void slotlistAPICall(OnFinishedListener onFinishedListener) {

        VDeliverzApiVendor.getClient().getslotlist(strShopId).enqueue(new Callback<SlotListResponse>(){
            @Override
            public void onResponse(@NotNull Call<SlotListResponse> call, @NotNull Response<SlotListResponse> response) {
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
            public void onFailure(@NotNull Call<SlotListResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });


    }
}
