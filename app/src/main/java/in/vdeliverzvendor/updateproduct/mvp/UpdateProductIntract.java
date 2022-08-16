package in.vdeliverzvendor.updateproduct.mvp;


import android.os.Handler;

import org.jetbrains.annotations.NotNull;

import java.io.File;

import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import in.vdeliverzvendor.utils.GeneralResponse;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProductIntract implements  UpdateProductContract.GetUpdateProductIntractor {

    String TAG= UpdateProductIntract.class.getSimpleName();
    String stremail,strpassword,strfcm;
    String strShopId,strCatId,strPdCattId,strName,strDesc,strCuId,strVId,strpdtid;
    File fileserver;
    MultipartBody.Part fileToUpload = null;

    interface OnupdateproductListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(String strShopId,String strpid, String strShopCatId, String strProductId, String strName, String strDesc,
                                 String strCuId, String strVarId, File file, OnupdateproductListener listener) {
        new Handler().postDelayed(() -> {
            this.strShopId=strShopId;
            this.strpdtid=strpid;
            this.strCatId=strShopCatId;
            this.strPdCattId=strProductId;
            this.strName=strName;
            this.strDesc=strDesc;
            this.strCuId=strCuId;
            this.strVId=strVarId;
            this.fileserver=file;

            listener.onSuccess();
        }, 500);
    }


    @Override
    public void updateproductAPICall(UpdateProductIntract.OnFinishedListener onFinishedListener) {

        if (fileserver != null) {
            RequestBody requestBody = RequestBody.create(fileserver, MediaType.parse("*/*"));
            fileToUpload = MultipartBody.Part.createFormData("image", fileserver.getName(), requestBody);
        }

        RequestBody shopid = RequestBody.create(strShopId,MediaType.parse("multipart/form-data"));
        RequestBody pid = RequestBody.create(strpdtid,MediaType.parse("multipart/form-data"));
        RequestBody cuiId = RequestBody.create(strCuId,MediaType.parse("multipart/form-data"));
        RequestBody productid = RequestBody.create(strPdCattId,MediaType.parse("multipart/form-data"));
        RequestBody varId = RequestBody.create(strVId,MediaType.parse("multipart/form-data"));
        RequestBody catId = RequestBody.create(strCatId,MediaType.parse("multipart/form-data"));
        RequestBody desc = RequestBody.create(strDesc,MediaType.parse("multipart/form-data"));
        RequestBody name = RequestBody.create(strName,MediaType.parse("multipart/form-data"));

        VDeliverzApiVendor.getClient().updateprodcut(shopid,pid,catId,productid,varId,name,fileToUpload,desc,cuiId).enqueue(new Callback<GeneralResponse>(){
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
