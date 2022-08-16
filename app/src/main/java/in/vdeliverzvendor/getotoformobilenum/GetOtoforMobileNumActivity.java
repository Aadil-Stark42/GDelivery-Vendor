package in.vdeliverzvendor.getotoformobilenum;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.Objects;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.otp_Verification.OTPMobilenumVerificationActivity;
import in.vdeliverzvendor.getotoformobilenum.mvp.GetOtpformobileVerContract;
import in.vdeliverzvendor.getotoformobilenum.mvp.GetOtpformobileVerIntract;
import in.vdeliverzvendor.getotoformobilenum.mvp.GetOtpformobileVerPresenter;
import in.vdeliverzvendor.signup.model.SignupResponse;
import in.vdeliverzvendor.utils.BaseActivity;
import in.vdeliverzvendor.utils.MnxConstant;
import in.vdeliverzvendor.utils.MnxPreferenceManager;

public class GetOtoforMobileNumActivity extends BaseActivity implements GetOtpformobileVerContract {

    ImageView iv_back;
    TextView tv_verify,tv_num;

    GetOtpformobileVerPresenter sendOtpPresenter;
    String mobile;
    String TAG=GetOtoforMobileNumActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_mobile_num);
        try{
        MnxPreferenceManager.setString(MnxConstant.USERSTATE,"GetMobileNum");

        if (MnxPreferenceManager.getString(MnxConstant.REG_MOBILE,null)!=null)
            mobile=MnxPreferenceManager.getString(MnxConstant.REG_MOBILE,null);

        iv_back=findViewById(R.id.iv_back);
        tv_num=findViewById(R.id.tv_num);
        tv_verify=findViewById(R.id.tv_verify);


        if(mobile!=null){
            tv_num.setText(mobile);
        }else{
            tv_num.setText("");
        }

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tv_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mobile!=null){
                    send_otp_api_call();
                }
            }
        });
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "sdf gfdshfhdsfg: errorr rr + "+e.getMessage());
        }
    }


    public void send_otp_api_call(){
        if (mobile!=null && (!(mobile.isEmpty()))) {
            showLoading();
            sendOtpPresenter = new GetOtpformobileVerPresenter(this, new GetOtpformobileVerIntract());
            sendOtpPresenter.validateDetails(mobile);
        }
    }

    @Override
    public void sendotp_success(SignupResponse signupResponse) {
        try{
        hideLoading();
        if(signupResponse!=null){
            if(signupResponse.getStatus()){
                Toast.makeText(this, signupResponse.getOtp(), Toast.LENGTH_SHORT).show();
                Intent myintent=new Intent(GetOtoforMobileNumActivity.this,
                        OTPMobilenumVerificationActivity.class);
                myintent.putExtra("mobile",mobile);
                startActivity(myintent);
            }else{
                Toast.makeText(this, signupResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "success: errorr rr + "+e.getMessage());
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
