package in.vdeliverzvendor.deliveryboy_rating.mvp;

import android.os.Handler;
import android.util.Log;


import org.jetbrains.annotations.NotNull;

import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.utils.GeneralResponse;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DBoyRatingIntract implements DBoyRatingContract.GetDBoyrateIntractor {

    String TAG= DBoyRatingIntract.class.getSimpleName();
    String order_id,rating,comment;

    interface OnNotifyListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(String order_id, String rating,String comment, final OnNotifyListener listener) {
        this.order_id=order_id;
        this.rating=rating;
        this.comment=comment;

        new Handler().postDelayed(() -> {
            if(order_id.isEmpty()){
                listener.onError("Order ID Issue");
            }if(rating.isEmpty()){
                listener.onError("Rating number issue");
            }
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void dboy_rate_APICall(OnFinishedListener onFinishedListener) {

        VDeliverzApiVendor.getClient().give_deliveryboy_rating(order_id,rating,comment).enqueue(new Callback<GeneralResponse>(){
            @Override
            public void onResponse(@NotNull Call<GeneralResponse> call, @NotNull Response<GeneralResponse> response) {
                if(response.isSuccessful()) {
                    Log.d(TAG, "onResponse: 1");
                    if (response.code() == 200) {
                        Log.d(TAG, "onResponse: 2");
                        if (response.body() != null) {
                            Log.d(TAG, "onResponse: 3");
                            if(response.body().getStatus()){
                                Log.d(TAG, "onResponse: 4");
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
