package in.vdeliverzvendor.editoutlet.mvp;


import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.utils.GeneralResponse;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditOutletIntract  implements  EditOutletContract.GetUpdateoutletIntractor{

    String TAG= EditOutletIntract.class.getSimpleName();
    String  strRadius,strDesc,strOpeningTime,strEndTime,strAssign,strminimumamount,strshopid;
    File fileshop,filebanner;
    List<String> weekdayslist = new ArrayList();
    MultipartBody.Part fileToUpload = null,fileToUploadbanner=null;

    interface OnEditoutletListener {
        void onSuccess();
        void onError(String msg);
    }


    public void directValidation(String strdhopid,String strminamount, String strradius, String strdesc, String strassign,
                                String stropentime, String strendtime, List<String> weekdaylist, File fileshop,
                                 File filebanne,final OnEditoutletListener listener) {
        new Handler().postDelayed(() -> {
            this.strshopid=strdhopid;
            this.strminimumamount=strminamount;
            this.strRadius=strradius;
            this.strDesc=strdesc;
            this.strAssign=strassign;
            this.strOpeningTime=stropentime;
            this.strEndTime=strendtime;
            this.weekdayslist=weekdaylist;
            this.fileshop=fileshop;
            this.filebanner=filebanne;
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void eupdateoutletAPICall(OnFinishedListener onFinishedListener) {

        if (fileshop != null) {
            RequestBody requestBody = RequestBody.create(fileshop, MediaType.parse("*/*"));
            fileToUpload = MultipartBody.Part.createFormData("shop_image", fileshop.getName(), requestBody);
        }
        if (filebanner!=null){
            RequestBody requestBody = RequestBody.create(filebanner, MediaType.parse("*/*"));
            fileToUploadbanner = MultipartBody.Part.createFormData("banner_image", filebanner.getName(), requestBody);
        }

        MultipartBody.Part shopid = MultipartBody.Part.createFormData("shop_id", strshopid);
        MultipartBody.Part assign = MultipartBody.Part.createFormData("assign", strAssign);
        MultipartBody.Part opening_time = MultipartBody.Part.createFormData("opening_time", strOpeningTime);
        MultipartBody.Part closing_time = MultipartBody.Part.createFormData("closing_time", strEndTime);
        MultipartBody.Part min_amount = MultipartBody.Part.createFormData("min_amount", strminimumamount);
        MultipartBody.Part radius = MultipartBody.Part.createFormData("radius", strRadius);
        MultipartBody.Part description = MultipartBody.Part.createFormData("description", strDesc);

        VDeliverzApiVendor.getClient().updateshop(shopid,assign, opening_time,closing_time,description,min_amount,
                radius,weekdayslist, fileToUpload,fileToUploadbanner).enqueue(new Callback<GeneralResponse>(){
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
