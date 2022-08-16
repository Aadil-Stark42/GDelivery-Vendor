package in.vdeliverzvendor.otp_Verification;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gne.www.lib.PinView;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.Objects;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.getotoformobilenum.mvp.GetOtpformobileVerContract;
import in.vdeliverzvendor.getotoformobilenum.mvp.GetOtpformobileVerIntract;
import in.vdeliverzvendor.getotoformobilenum.mvp.GetOtpformobileVerPresenter;
import in.vdeliverzvendor.signup.model.SignupResponse;
import in.vdeliverzvendor.utils.BaseActivity;
import in.vdeliverzvendor.utils.GeneralResponse;
import in.vdeliverzvendor.utils.MnxConstant;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import in.vdeliverzvendor.getoptforemail.GetOtpforEmailActivity;
import in.vdeliverzvendor.otp_Verification.mvp_mobilenum.VerifyMobileOtpContract;
import in.vdeliverzvendor.otp_Verification.mvp_mobilenum.VerifyMobileOtpIntract;
import in.vdeliverzvendor.otp_Verification.mvp_mobilenum.VerifyMobileOtpPresenter;

public class OTPMobilenumVerificationActivity extends BaseActivity implements VerifyMobileOtpContract , GetOtpformobileVerContract {

    TextView tv_verifyandproceed,tv_hint,tv_resend;
    String mobile;
    PinView pinview;
    String TAG=OTPMobilenumVerificationActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_mobilenumverification);

        try{

        MnxPreferenceManager.setString(MnxConstant.USERSTATE,"GetMobileNumOTP");

        tv_verifyandproceed=findViewById(R.id.tv_verifyandproceed);
        tv_hint=findViewById(R.id.tv_hint);
        pinview=findViewById(R.id.pinview);
        tv_resend=findViewById(R.id.tv_resend);

        Intent intent=getIntent();
        if (MnxPreferenceManager.getString(MnxConstant.REG_MOBILE,null)!=null)
            mobile=MnxPreferenceManager.getString(MnxConstant.REG_MOBILE,null);

        if(mobile!=null){
            tv_hint.setText("Enter the OTP sent to "+mobile);
        }else{
            tv_hint.setText("Enter the OTP sent to ");
        }

        tv_verifyandproceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doapicall();
            }
        });

        tv_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_otp_api_call();
            }
        });
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "create  as: errorr rr + "+e.getMessage());
        }
    }

    private  void doapicall(){
        try {
            String stropt,strmobilenum;
            stropt=pinview.getText().toString();
            strmobilenum= MnxPreferenceManager.getString(MnxConstant.REG_MOBILE,null);
            if (stropt!=null && (!(stropt.isEmpty()))){
                if (strmobilenum!=null && (!(strmobilenum.isEmpty()))) {
                    showLoading();
                    VerifyMobileOtpPresenter verifyMobileOtpPresenter = new VerifyMobileOtpPresenter(this, new VerifyMobileOtpIntract());
                    verifyMobileOtpPresenter.validateDetails(strmobilenum, stropt);
                }
                else
                    Toast.makeText(this,"Mobile Number Error",Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(this,"Enter OTP",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "casf asd sd: errorr rr + "+e.getMessage());
        }
    }

    @Override
    public void verifymobileotp_success(GeneralResponse generalResponse) {
        try{
        hideLoading();
        if (generalResponse.getStatus()){
             Intent myintent=new Intent(OTPMobilenumVerificationActivity.this, GetOtpforEmailActivity.class);
             startActivity(myintent);
             finish();
        }
        else
            Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "fad asdsgd : errorr rr + "+e.getMessage());
        }

    }

    @Override
    public void verifymobileotp_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();

    }
    public void send_otp_api_call(){
        try{
        String strmobile=MnxPreferenceManager.getString(MnxConstant.REG_MOBILE,null);
        if (strmobile!=null && (!(strmobile.isEmpty()))) {
            showLoading();
            GetOtpformobileVerPresenter  sendOtpPresenter = new GetOtpformobileVerPresenter(this, new GetOtpformobileVerIntract());
            sendOtpPresenter.validateDetails(mobile);
        }
        else
            Toast.makeText(this, "Mobile Number Error", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "cdf dgfg : errorr rr + "+e.getMessage());
        }

    }

    @Override
    public void sendotp_success(SignupResponse signupResponse) {
        hideLoading();
        if(signupResponse!=null){
            if(signupResponse.getStatus()){
                Toast.makeText(this, signupResponse.getOtp(), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, signupResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
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
