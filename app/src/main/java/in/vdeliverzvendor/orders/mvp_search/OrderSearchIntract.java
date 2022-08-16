package in.vdeliverzvendor.orders.mvp_search;

import android.os.Handler;
import org.jetbrains.annotations.NotNull;
import in.vdeliverzvendor.orders.model.OrdersListResponse;
import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderSearchIntract implements  OrderSearchContract.GetOrderListsearchIntractor{

    String TAG= OrderSearchIntract.class.getSimpleName();
    String strpage,strsearch;

    interface OnorderlistsearchListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(String strpage,String strsearch,final OnorderlistsearchListener listener) {
        new Handler().postDelayed(() -> {
            this.strpage=strpage;
            this.strsearch=strsearch;
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void orderListsearchAPICall(OnFinishedListener onFinishedListener) {

        VDeliverzApiVendor.getClient().getordersearchlist(strpage,strsearch).enqueue(new Callback<OrdersListResponse>(){
            @Override
            public void onResponse(@NotNull Call<OrdersListResponse> call, @NotNull Response<OrdersListResponse> response) {
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
            public void onFailure(@NotNull Call<OrdersListResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });
    }
}
