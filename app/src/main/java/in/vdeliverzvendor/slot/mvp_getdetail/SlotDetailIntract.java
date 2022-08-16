package in.vdeliverzvendor.slot.mvp_getdetail;


import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.slot.model_getdetail.SlotDetailResponse;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SlotDetailIntract implements  SlotDeatilContract.GetslotdetailIntractor {

    String TAG= SlotDetailIntract.class.getSimpleName();
    String strSlotId;

    interface OnSlotDetListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(String strSlotid, final OnSlotDetListener listener) {
        this.strSlotId=strSlotid;

        new Handler().postDelayed(() -> {
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void slotdetailAPICall(OnFinishedListener onFinishedListener) {

        VDeliverzApiVendor.getClient().getslotdetail(strSlotId).enqueue(new Callback<SlotDetailResponse>(){
            @Override
            public void onResponse(@NotNull Call<SlotDetailResponse> call, @NotNull Response<SlotDetailResponse> response) {
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
            public void onFailure(@NotNull Call<SlotDetailResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });


    }
}
