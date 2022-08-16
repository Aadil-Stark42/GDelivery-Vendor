package in.vdeliverzvendor.createoutlet.mvp;


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


public class CreateOutletIntract  implements CreateOutletContract.GetcreateoutletIntractor{

    String TAG= CreateOutletIntract.class.getSimpleName();
        String strShopName,strEmail,strPhoneNum,strStreet,strCity,strArea,strLat,strLong,strPrice,strComission,strminamount,
            strRadius,strDesc,strOpeningTime,strEndTime,strAssign,strminimumamount;
    File fileshop,filebanner;
    List<String> weekdayslist = new ArrayList();
    List<Integer> cuisinelist = new ArrayList();
    MultipartBody.Part fileToUpload = null,fileToUploadbanner=null;

    interface OnCreateOutletListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(File fileshop,File filebanner,String strShopName,String strStreet,String strArea,String strCity,
                                 String strLat,String strLong,String strPhoneNum,String strEmail,String strComission,String strPrice,
                                 String strRadius,String strDesc,String strAssign,String strOpeningTime,String strEndTime,List<String> weekdayslist,List<Integer> cuisinelistfinal,String strminimumorderamount, OnCreateOutletListener listener) {
        new Handler().postDelayed(() -> {
            this.fileshop=fileshop;
            this.filebanner=filebanner;
            this.strShopName=strShopName;
            this.strStreet=strStreet;
            this.strArea=strArea;
            this.strCity=strCity;
            this.strLat=strLat;
            this.strLong=strLong;
            this.strPhoneNum=strPhoneNum;
            this.strEmail=strEmail;
            this.strComission=strComission;
            this.strPrice=strPrice;
            this.strRadius=strRadius;
            this.strDesc=strDesc;
            this.strAssign=strAssign;
            this.strOpeningTime=strOpeningTime;
            this.strEndTime=strEndTime;
            this.weekdayslist=weekdayslist;
            this.cuisinelist=cuisinelistfinal;
            this.strminimumamount=strminimumorderamount;
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void createoutletAPICall(OnFinishedListener onFinishedListener) {

        if (fileshop != null) {
            RequestBody requestBody = RequestBody.create(fileshop, MediaType.parse("*/*"));
            fileToUpload = MultipartBody.Part.createFormData("shop_image", fileshop.getName(), requestBody);
        }
        if (filebanner!=null){
            RequestBody requestBody = RequestBody.create(filebanner, MediaType.parse("*/*"));
            fileToUploadbanner = MultipartBody.Part.createFormData("banner_image", filebanner.getName(), requestBody);
        }


        MultipartBody.Part shopname = MultipartBody.Part.createFormData("shop_name", strShopName);
        MultipartBody.Part shop_email = MultipartBody.Part.createFormData("shop_email", strEmail);
        MultipartBody.Part shop_mobile = MultipartBody.Part.createFormData("shop_mobile", strPhoneNum);
        MultipartBody.Part latitude = MultipartBody.Part.createFormData("latitude", strLat);
        MultipartBody.Part longitude = MultipartBody.Part.createFormData("longitude", strLong);
        MultipartBody.Part street = MultipartBody.Part.createFormData("street", strStreet);
        MultipartBody.Part city = MultipartBody.Part.createFormData("city", strCity);
        MultipartBody.Part area = MultipartBody.Part.createFormData("area", strArea);
        MultipartBody.Part price = MultipartBody.Part.createFormData("price", strPrice);
        MultipartBody.Part assign = MultipartBody.Part.createFormData("assign", strAssign);
        MultipartBody.Part opening_time = MultipartBody.Part.createFormData("opening_time", strOpeningTime);
        MultipartBody.Part closing_time = MultipartBody.Part.createFormData("closing_time", strEndTime);
        MultipartBody.Part comission = MultipartBody.Part.createFormData("comission", strComission);
        MultipartBody.Part min_amount = MultipartBody.Part.createFormData("min_amount", strminimumamount);
        MultipartBody.Part radius = MultipartBody.Part.createFormData("radius", strRadius);
        MultipartBody.Part description = MultipartBody.Part.createFormData("description", strDesc);

        VDeliverzApiVendor.getClient().createoutlet(shopname,shop_email,shop_mobile,latitude,longitude,street,city,area,price,
                assign, opening_time,closing_time,comission,description,min_amount,radius,weekdayslist,cuisinelist ,
                fileToUpload,fileToUploadbanner
                ).enqueue(new Callback<GeneralResponse>(){
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
                        onFinishedListener.onFailure(response.code()+" "+ response.message());
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
