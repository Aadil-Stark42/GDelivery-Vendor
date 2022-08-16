package in.vdeliverzvendor.userdetail.mvp_update;

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


public class UserUpdateIntract  implements  UserUpdateContract.GetuserupdateIntractor{

    String strName,strEmailid,strPhoneNum;
    File file;
    MultipartBody.Part fileToUpload=null;

    String TAG= UserUpdateIntract.class.getSimpleName();

    interface OnuserupdateListener {
        void onSuccess();
        void onError(String msg);
    }

    public void directValidation(String strname, String strphonenum, String ed_email, File file,  final OnuserupdateListener listener) {
        new Handler().postDelayed(() -> {
            this.strName=strname;
            this.strPhoneNum=strphonenum;
            this.strEmailid=ed_email;
            this.file=file;
            listener.onSuccess();
        }, 500);
    }

    @Override
    public void userupdateAPICall(OnFinishedListener onFinishedListener) {

        if (file!=null){
            RequestBody requestBody = RequestBody.create(file, MediaType.parse("*/*"));
            fileToUpload = MultipartBody.Part.createFormData("profile_image", file.getName(), requestBody);
        }

        MultipartBody.Part phonenum = MultipartBody.Part.createFormData("mobile", strPhoneNum);
        MultipartBody.Part emailid = MultipartBody.Part.createFormData("email", strEmailid);
        MultipartBody.Part name = MultipartBody.Part.createFormData("name", strName);
        VDeliverzApiVendor.getClient().userupdate(phonenum,name,emailid,fileToUpload).enqueue(new Callback<GeneralResponse>(){
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

