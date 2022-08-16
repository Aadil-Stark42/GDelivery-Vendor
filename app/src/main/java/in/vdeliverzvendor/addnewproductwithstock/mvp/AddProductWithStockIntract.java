package in.vdeliverzvendor.addnewproductwithstock.mvp;


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

public class AddProductWithStockIntract implements  AddProductwithStockContract.Getaddstockproduct_Intractor {

    String TAG= AddProductWithStockIntract.class.getSimpleName();
    String strShopId,strCatId,strPdCatId,strCuisineId,strVarId,strname,strdesc;
    String stractualprice,strsellingprice,stravailable,strsize,strunit,strvariant;
    String stractualpricetop,strtitle,stravailabletop,strnametop,strvarianttop;
    File fileserver;
    MultipartBody.Part fileToUpload = null;

    interface OnAddProductWithStockListener {
        void onSuccess();
        void onError(String msg);
    }



    public void directValidation(String strShopId,String strCatId,String strPdCatId,String strCuisineId,String strVarId,String strname,String strDesc,File file,
                                 String strActualPrice,String strSellingPrice,String strAvailable,String strSize,String strUnit,String strVariant,
                                 String strActualPricetop,String stravailabletop,String strtitleid,String strnametop,String strVarianttop,
                                 final OnAddProductWithStockListener listener) {
        this.strShopId=strShopId;
        this.strCatId=strCatId;
        this.strPdCatId=strPdCatId;
        this.strname=strname;
        this.strdesc=strDesc;
        this.strCuisineId=strCuisineId;
        this.strVarId=strVarId;
        this.fileserver=file;

        this.stractualprice=strActualPrice;
        this.strsellingprice=strSellingPrice;
        this.stravailable=strAvailable;
        this.strsize=strSize;
        this.strunit=strUnit;
        this.strvariant=strVariant;


        this.stractualpricetop=strActualPricetop;
        this.strtitle=strtitleid;
        this.stravailabletop=stravailabletop;
        this.strnametop=strnametop;
        this.strvarianttop=strVarianttop;


        new Handler().postDelayed(() -> {
            listener.onSuccess();
        }, 500);
    }


    @Override
    public void addstockproduct_APICall(OnFinishedListener onFinishedListener) {


        if (fileserver != null) {
            RequestBody requestBody = RequestBody.create(fileserver, MediaType.parse("*/*"));
            fileToUpload = MultipartBody.Part.createFormData("image", fileserver.getName(), requestBody);
        }

        RequestBody shopid = RequestBody.create(strShopId,MediaType.parse("multipart/form-data"));
        RequestBody cuiId = RequestBody.create(strCuisineId,MediaType.parse("multipart/form-data"));
        RequestBody productid = RequestBody.create(strPdCatId,MediaType.parse("multipart/form-data"));
        RequestBody varId = RequestBody.create(strVarId,MediaType.parse("multipart/form-data"));
        RequestBody catId = RequestBody.create(strCatId,MediaType.parse("multipart/form-data"));
        RequestBody desc = RequestBody.create(strdesc,MediaType.parse("multipart/form-data"));
        RequestBody name = RequestBody.create(strname,MediaType.parse("multipart/form-data"));


        RequestBody st_actual_price = RequestBody.create(stractualprice,MediaType.parse("multipart/form-data"));
        RequestBody st_selling_price = RequestBody.create(strsellingprice,MediaType.parse("multipart/form-data"));
        RequestBody st_available = RequestBody.create(stravailable,MediaType.parse("multipart/form-data"));
        RequestBody st_size = RequestBody.create(strsize,MediaType.parse("multipart/form-data"));
        RequestBody st_unit = RequestBody.create(strunit,MediaType.parse("multipart/form-data"));
        RequestBody st_variant = RequestBody.create(strvariant,MediaType.parse("multipart/form-data"));


        RequestBody title_id = RequestBody.create(strtitle,MediaType.parse("multipart/form-data"));
        RequestBody top_name = RequestBody.create(strnametop,MediaType.parse("multipart/form-data"));
        RequestBody top_variety = RequestBody.create(strvarianttop,MediaType.parse("multipart/form-data"));
        RequestBody top_available = RequestBody.create(stravailabletop,MediaType.parse("multipart/form-data"));
        RequestBody top_price = RequestBody.create(stractualpricetop,MediaType.parse("multipart/form-data"));

        VDeliverzApiVendor.getClient().addproductwithstock(shopid,catId,productid,varId,name,fileToUpload,desc,cuiId,
                st_actual_price,st_selling_price,st_available,st_size,st_unit,st_variant,
                title_id,top_name,top_variety,top_available,top_price).enqueue(new Callback<GeneralResponse>(){
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
