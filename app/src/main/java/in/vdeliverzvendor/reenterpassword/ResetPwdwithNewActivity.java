package in.vdeliverzvendor.reenterpassword;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.Objects;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.login.LoginActivity;
import in.vdeliverzvendor.resetpassword.mvp.ResetPasswordContract;
import in.vdeliverzvendor.resetpassword.mvp.ResetPasswordIntract;
import in.vdeliverzvendor.resetpassword.mvp.ResetpasswordPresenter;
import in.vdeliverzvendor.utils.BaseActivity;
import in.vdeliverzvendor.utils.GeneralResponse;
import in.vdeliverzvendor.utils.MnxConstant;
import in.vdeliverzvendor.utils.MnxPreferenceManager;

public class ResetPwdwithNewActivity extends BaseActivity  implements ResetPasswordContract {

    TextView tv_status_pass,tv_status_confirm_pass,tv_reset;
    ImageView iv_back,iv_eyepassowrd,iv_eyeconfirmpassword;
    TextInputEditText ed_password,ed_confirm_password;
    String strmobile;

    String TAG=ResetPwdwithNewActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_with_newpassword);

        try {

            initcall();

            initclick();
            if (MnxPreferenceManager.getString(MnxConstant.REG_MOBILE, null) != null)
                strmobile = MnxPreferenceManager.getString(MnxConstant.REG_MOBILE, null);

        }catch (Exception e){
        FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
        Log.d(TAG, "cafdsfdf df: errorr rr + "+e.getMessage());
    }

    }



    private  void initcall(){
        try{
        tv_reset=findViewById(R.id.tv_reset);
        iv_back=findViewById(R.id.iv_back);
        ed_password=findViewById(R.id.ed_password);
        ed_confirm_password=findViewById(R.id.ed_confirm_password);
        tv_status_pass=findViewById(R.id.tv_status_pass);
        tv_status_confirm_pass=findViewById(R.id.tv_status_confirm_pass);
        iv_eyeconfirmpassword=findViewById(R.id.iv_eyeconfirm);
        iv_eyepassowrd=findViewById(R.id.iv_eyepassowrd);
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "asdgyasdghjsdgs df: errorr rr + "+e.getMessage());
        }
    }

    private  void initclick(){

        try{
        iv_eyeconfirmpassword.setBackground(getResources().getDrawable(R.drawable.ic_eye));
        iv_eyepassowrd.setBackground(getResources().getDrawable(R.drawable.ic_eye));

        iv_eyepassowrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_status_pass.getText().toString().equals("hide")){
                    tv_status_pass.setText("show");
                    ed_password.setTransformationMethod(null);
                    iv_eyepassowrd.setBackground(getResources().getDrawable(R.drawable.ic_eyeopen));
                    ed_password.setSelection(ed_password.getText().length());
                }
                else{
                    tv_status_pass.setText("hide");
                    TextView textView = (TextView) findViewById(R.id.ed_password);
                    ed_password.setTransformationMethod(new PasswordTransformationMethod());
                    iv_eyepassowrd.setBackground(getResources().getDrawable(R.drawable.ic_eye));
                    ed_password.setSelection(ed_password.getText().length());
                }

            }
        });

        iv_eyeconfirmpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_status_confirm_pass.getText().toString().equals("hide")){
                    tv_status_confirm_pass.setText("show");
                    ed_confirm_password.setTransformationMethod(null);
                    iv_eyeconfirmpassword.setBackground(getResources().getDrawable(R.drawable.ic_eyeopen));
                    ed_confirm_password.setSelection(ed_password.getText().length());
                }
                else{
                    tv_status_confirm_pass.setText("hide");
                    TextView textView = (TextView) findViewById(R.id.ed_password);
                    ed_confirm_password.setTransformationMethod(new PasswordTransformationMethod());
                    iv_eyeconfirmpassword.setBackground(getResources().getDrawable(R.drawable.ic_eye));
                    ed_confirm_password.setSelection(ed_password.getText().length());
                }

            }
        });


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        tv_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doapicall();
            }
        });

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "adfvfvb vbvbc df: errorr rr + "+e.getMessage());
        }
    }

    private  void doapicall(){
        try{
        String strnewpass=ed_password.getText().toString();
        String strconfirmpass=ed_confirm_password.getText().toString();
        if (strnewpass!=null && (!strnewpass.isEmpty())){
            if (strconfirmpass!=null && (!(strconfirmpass.isEmpty()))){
                if(strnewpass.equals(strconfirmpass)){
                    showLoading();
                    ResetpasswordPresenter resetpasswordPresenter=new ResetpasswordPresenter(this,new ResetPasswordIntract());
                    resetpasswordPresenter.validateDetails(strmobile,strnewpass,strconfirmpass);
                }
                else
                    Toast.makeText(this,"Password and Confirm Password are Mismatch", Toast.LENGTH_SHORT).show();

            }
            else
                 Toast.makeText(this,"Enter Confirm Password",Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,"Enter New Password",Toast.LENGTH_LONG).show();

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "asvhdvhgdvd g fg df: errorr rr + "+e.getMessage());
        }
    }

    @Override
    public void resetpassword_success(GeneralResponse generalResponse) {
        try{
        hideLoading();
        if (generalResponse!=null){
            if (generalResponse.getStatus()){
                Toast.makeText(this,"Changed Successfully",Toast.LENGTH_LONG).show();
                Intent myIntent=new Intent(ResetPwdwithNewActivity.this,LoginActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(myIntent);
                finish();
            }
        }
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "adghsgdhsgdhs df: errorr rr + "+e.getMessage());
        }

    }

    @Override
    public void resetpassword_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
    }
}
