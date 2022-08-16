package in.vdeliverzvendor.addnewproduct.mvp_add;

import android.os.Handler;
import org.jetbrains.annotations.NotNull;

import java.io.File;

import in.vdeliverzvendor.addnewproduct.model_add.AddProductResponse;
import in.vdeliverzvendor.retrofit.VDeliverzApiVendor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddProductIntract implements  AddProductContract.GetAddproductIntractor{

    String TAG= AddProductIntract.class.getSimpleName();
    String strShopId,strCatId,strPdtId,strName,strDesc,strCuId,strVId;
    File fileserver;
    MultipartBody.Part fileToUpload = null;

    interface OnAddProductListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(String strShopId, String strShopCatId, String strProductId, String strName, String strDesc,
                                 String strCuId, String strVarId, File file, OnAddProductListener listener) {
        new Handler().postDelayed(() -> {
            this.strShopId=strShopId;
            this.strCatId=strShopCatId;
            this.strPdtId=strProductId;
            this.strName=strName;
            this.strDesc=strDesc;
            this.strCuId=strCuId;
            this.strVId=strVarId;
            this.fileserver=file;

            listener.onSuccess();
        }, 500);
    }


    @Override
    public void getAddproductAPICall(OnFinishedListener onFinishedListener) {

        if (fileserver != null) {
            RequestBody requestBody = RequestBody.create(fileserver, MediaType.parse("*/*"));
            fileToUpload = MultipartBody.Part.createFormData("image", fileserver.getName(), requestBody);
        }

        RequestBody shopid = RequestBody.create(strShopId,MediaType.parse("multipart/form-data"));
        RequestBody cuiId = RequestBody.create(strCuId,MediaType.parse("multipart/form-data"));
        RequestBody productid = RequestBody.create(strPdtId,MediaType.parse("multipart/form-data"));
        RequestBody varId = RequestBody.create(strVId,MediaType.parse("multipart/form-data"));
        RequestBody catId = RequestBody.create(strCatId,MediaType.parse("multipart/form-data"));
        RequestBody desc = RequestBody.create(strDesc,MediaType.parse("multipart/form-data"));
        RequestBody name = RequestBody.create(strName,MediaType.parse("multipart/form-data"));

        VDeliverzApiVendor.getClient().addproduct(shopid,catId,productid,varId,name,fileToUpload,desc,cuiId).enqueue(new Callback<AddProductResponse>(){
            @Override
            public void onResponse(@NotNull Call<AddProductResponse> call, @NotNull Response<AddProductResponse> response) {
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
            public void onFailure(@NotNull Call<AddProductResponse> call, @NotNull Throwable t) {
                if(t !=null) {
                    onFinishedListener.onFailure(t.getMessage());
                }
            }
        });

    }
}
