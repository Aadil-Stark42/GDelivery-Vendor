package in.vdeliverzvendor.editprofile;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.Objects;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.userdetail.model.UserDetailResponse;
import in.vdeliverzvendor.userdetail.mvp.UserDetailContract;
import in.vdeliverzvendor.userdetail.mvp.UserDetailIntract;
import in.vdeliverzvendor.userdetail.mvp.UserDetailPresenter;
import in.vdeliverzvendor.userdetail.mvp_update.UserUpdateContract;
import in.vdeliverzvendor.userdetail.mvp_update.UserUpdateIntract;
import in.vdeliverzvendor.userdetail.mvp_update.UserUpdatePresenter;
import in.vdeliverzvendor.utils.BaseActivity;
import in.vdeliverzvendor.utils.GeneralResponse;
import in.vdeliverzvendor.utils.MnxConstant;
import in.vdeliverzvendor.utils.MnxPreferenceManager;

public class EditProfileActivity extends BaseActivity implements UserDetailContract, UserUpdateContract {

    TextView tv_update;
    ImageView iv_back;
    ShapeableImageView iv_img;
    EditText ed_name,ed_emailid,ed_phonenum;
    ConstraintLayout consmain;
    File file;
    boolean isImageAva;
    String strpath;
    String TAG=EditProfileActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        try{
        initcall();

        initclick();

        doapicall();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "dcaddadasd: errorr rr + "+e.getMessage());
        }

    }


    private  void initcall(){
        try{
        tv_update=findViewById(R.id.tv_update);
        iv_back=findViewById(R.id.iv_back);
        ed_name=findViewById(R.id.ed_name);
        ed_emailid=findViewById(R.id.ed_emailid);
        ed_phonenum=findViewById(R.id.ed_phonenum);
        consmain=findViewById(R.id.consmain);
        consmain.setVisibility(View.GONE);
        iv_img=findViewById(R.id.iv_img);
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "intiacalll: errorr rr + "+e.getMessage());
        }
    }

    private  void initclick(){
        try{
        tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doupdateapicall();
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        iv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setActivityTitle("Crop image")
                        .setCropShape(CropImageView.CropShape.RECTANGLE)
                        .setCropMenuCropButtonTitle("Done")
                        .setRequestedSize(800, 400)
                        .setCropMenuCropButtonIcon(R.drawable.save)
                        .start(EditProfileActivity.this);
            }
        });

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "onclcickc: errorr rr + "+e.getMessage());
        }

    }

    private  void doapicall(){
        showLoading();
        UserDetailPresenter userDetailPresenter=new UserDetailPresenter(this,new UserDetailIntract());
        userDetailPresenter.validateDetails();

    }

    private  void doupdateapicall(){
        try{
        if (ed_name.getText().toString()!=null && (!(ed_name.getText().toString().isEmpty()))){

            if (ed_emailid.getText().toString()!=null && (!(ed_emailid.getText().toString().isEmpty())) &&  Patterns.EMAIL_ADDRESS.matcher(ed_emailid.getText().toString()).matches()){

                if (ed_phonenum.getText().toString()!=null && (!(ed_phonenum.getText().toString().isEmpty())) && ed_phonenum.getText().toString().length()==10){

                    if (isImageAva){
                        showLoading();
                        UserUpdatePresenter userUpdatePresenter=new UserUpdatePresenter(this,new UserUpdateIntract());
                        userUpdatePresenter.validateDetails(ed_name.getText().toString(),ed_phonenum.getText().toString(),ed_emailid.getText().toString(),file);
                    }
                    else
                        Toast.makeText(this,"Choose Image",Toast.LENGTH_LONG).show();

                }
                else if(ed_phonenum.getText().toString().length()>=10 & ed_phonenum.getText().toString().length()==13){
                       if (isImageAva){
                           showLoading();
                           UserUpdatePresenter userUpdatePresenter=new UserUpdatePresenter(this,new UserUpdateIntract());
                           userUpdatePresenter.validateDetails(ed_name.getText().toString(),ed_phonenum.getText().toString(),ed_emailid.getText().toString(),file);
                       }
                       else
                           Toast.makeText(this,"Choose Image",Toast.LENGTH_LONG).show();
                   }else{
                    Toast.makeText(this,"Enter Valid Phone Number",Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(this,"Enter Email Id",Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(this,"Enter Name",Toast.LENGTH_LONG).show();
        }
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "ccdcdvfvd vdvd vfvf: errorr rr + "+e.getMessage());
        }
    }



    @Override
    public void userdetail_success(UserDetailResponse userDetailResponse) {
        try{
        hideLoading();
        if (userDetailResponse!=null){
            if (userDetailResponse.getStatus()){
                consmain.setVisibility(View.VISIBLE);
                if (userDetailResponse.getUser().getEmail()!=null)
                    ed_emailid.setText(userDetailResponse.getUser().getEmail());

                if (userDetailResponse.getUser().getMobile()!=null)
                    ed_phonenum.setText(userDetailResponse.getUser().getMobile());

                if (userDetailResponse.getUser().getName()!=null)
                    ed_name.setText(userDetailResponse.getUser().getName());

                if (userDetailResponse.getUser().getImage()!=null){
                    isImageAva=true;
                    Glide.with(this)
                            .load(userDetailResponse.getUser().getImage())
                            .fitCenter()
                            .placeholder(R.drawable.user_placeholder_sidenav)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(iv_img);
                }
            }
        }

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "catcjc d dfbf: errorr rr + "+e.getMessage());
        }
    }

    @Override
    public void userdetail_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
    }

    @Override
    public void userupdate_success(GeneralResponse generalResponse) {
        try{
        hideLoading();
        if (generalResponse!=null){
            if (generalResponse.getStatus()){
                if (strpath!=null)
                MnxPreferenceManager.setString(MnxConstant.USERIMAGE,strpath);
                if (ed_name.getText().toString()!=null)
                    MnxPreferenceManager.setString(MnxConstant.USER_NAME,ed_name.getText().toString());
                Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "afghdfghdgf: errorr rr + "+e.getMessage());
        }

    }

    @Override
    public void userupdate_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                    Uri path =result.getUri();
                strpath=getRealPathFromURI(path);
                    file = new File(getRealPathFromURI(path));

                    if (path!=null){
                        isImageAva=true;
                        Glide.with(this)
                                .load(file)
                                .fitCenter()
                                .placeholder(R.drawable.user_placeholder_sidenav)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(iv_img);
                    }
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }

    private String getRealPathFromURI(Uri contentURI) {
        Cursor cursor = getContentResolver().query(contentURI, null, null,
                null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

}
