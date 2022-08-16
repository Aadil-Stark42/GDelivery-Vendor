package in.vdeliverzvendor.resetpassword;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.Objects;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.getotoformobilenum.mvp.GetOtpformobileVerContract;
import in.vdeliverzvendor.getotoformobilenum.mvp.GetOtpformobileVerIntract;
import in.vdeliverzvendor.getotoformobilenum.mvp.GetOtpformobileVerPresenter;
import in.vdeliverzvendor.resetpasswordwithotp.ResetPasswordwithOTPActivity;
import in.vdeliverzvendor.signup.model.SignupResponse;
import in.vdeliverzvendor.utils.BaseActivity;
import in.vdeliverzvendor.utils.MnxConstant;
import in.vdeliverzvendor.utils.MnxPreferenceManager;

public class ResetPasswordActivity extends BaseActivity implements GetOtpformobileVerContract {

    TextView tv_continue;
    ImageView iv_back;
    EditText ed_phonenum;
    String mobile;
    GetOtpformobileVerPresenter sendOtpPresenter;
    String TAG=ResetPasswordActivity.class.getSimpleName();

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        try{

       initcall();
       initclick();

        if (MnxPreferenceManager.getString(MnxConstant.REG_MOBILE,null)!=null)
            mobile=MnxPreferenceManager.getString(MnxConstant.REG_MOBILE,null);

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "dsadasdasdasd df: errorr rr + "+e.getMessage());
        }

    }

    private  void initcall(){
        tv_continue=findViewById(R.id.tv_continue);
        iv_back=findViewById(R.id.iv_back);
        ed_phonenum=findViewById(R.id.ed_phonenum);
    }

    private  void initclick(){

        try{
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        tv_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              send_otp_api_call();
            }
        });

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "hsdjidsusdds df: errorr rr + "+e.getMessage());
        }
    }

    public void send_otp_api_call(){
        try{
        mobile=ed_phonenum.getText().toString();
        MnxPreferenceManager.setString(MnxConstant.REG_MOBILE,mobile);
        if (mobile!=null && (!(mobile.isEmpty())) && mobile.length()==10) {
            showLoading();
            sendOtpPresenter = new GetOtpformobileVerPresenter(this, new GetOtpformobileVerIntract());
            sendOtpPresenter.validateDetails(mobile);
        }
        else
            Toast.makeText(this,"Enter Valid Mobile Number",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "api calllcall df: errorr rr + "+e.getMessage());
        }
    }
    @Override
    public void sendotp_success(SignupResponse signupResponse) {
        try{
        hideLoading();
        if(signupResponse!=null){
            if(signupResponse.getStatus()){
                Toast.makeText(this, signupResponse.getOtp(), Toast.LENGTH_SHORT).show();
                Intent myintent=new Intent(ResetPasswordActivity.this,
                        ResetPasswordwithOTPActivity.class);
                myintent.putExtra("mobile",mobile);
                startActivity(myintent);
            }else{
                Toast.makeText(this, signupResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "suuceess df: errorr rr + "+e.getMessage());
        }
    }

    @Override
    public void sendotp_failure(String msg) {
        hideLoading();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
    }

}
