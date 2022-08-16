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
import in.vdeliverzvendor.getoptforemail.mvp.GetOtpForEmailVerContract;
import in.vdeliverzvendor.getoptforemail.mvp.GetOtpForEmailVerIntract;
import in.vdeliverzvendor.getoptforemail.mvp.GetOtpForEmailVerPresenter;
import in.vdeliverzvendor.login.LoginActivity;
import in.vdeliverzvendor.otp_Verification.mvp_emailid.VerifyEmailContract;
import in.vdeliverzvendor.otp_Verification.mvp_emailid.VerifyEmailIntract;
import in.vdeliverzvendor.otp_Verification.mvp_emailid.VerifyEmailPresenter;
import in.vdeliverzvendor.signup.model.SignupResponse;
import in.vdeliverzvendor.utils.BaseActivity;
import in.vdeliverzvendor.utils.GeneralResponse;
import in.vdeliverzvendor.utils.MnxConstant;
import in.vdeliverzvendor.utils.MnxPreferenceManager;

public class OTPEmailVerificationActivity extends BaseActivity implements VerifyEmailContract, GetOtpForEmailVerContract {

    TextView tv_verifyandproceed,tv_hint,tv_resend;
    PinView pinview;
    String TAG=OTPEmailVerificationActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        try{
        MnxPreferenceManager.setString(MnxConstant.USERSTATE,"GetEmailOTP");
        tv_verifyandproceed=findViewById(R.id.tv_verifyandproceed);
        tv_hint=findViewById(R.id.tv_hint);
        pinview=findViewById(R.id.pinview);
        tv_resend=findViewById(R.id.tv_resend);

        if (MnxPreferenceManager.getString(MnxConstant.REG_EMAIL,null)!=null)
        tv_hint.setText("Enter the OTP sent to "+MnxPreferenceManager.getString(MnxConstant.REG_EMAIL,null));

        tv_verifyandproceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                docallapi();
            }
        });

        tv_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doapicall();

            }
        });
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "asdsdsd df: errorr rr + "+e.getMessage());
        }
    }

    private  void docallapi(){
        try {
            String stremail,strotp;
            stremail=MnxPreferenceManager.getString(MnxConstant.REG_EMAIL,null);
            strotp=pinview.getText().toString();

            if (strotp!=null && (!(strotp.isEmpty()))){
                if (stremail!=null){
                    showLoading();
                    VerifyEmailPresenter verifyEmailPresenter=new VerifyEmailPresenter(this,new VerifyEmailIntract());
                    verifyEmailPresenter.validateDetails(stremail,strotp);
                }
            }
            else
                Toast.makeText(this,"Enter OTP",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "adafgh h hh df: errorr rr + "+e.getMessage());
        }

    }

    @Override
    public void verifyemailotp_success(GeneralResponse generalResponse) {
        try{
        hideLoading();
        if (generalResponse!=null){
            if (generalResponse.getStatus()){
                Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG);
                MnxPreferenceManager.setString(MnxConstant.USERSTATE,"Completed");
                MnxPreferenceManager.setString(MnxConstant.ISFROM,"EmailVeri");

                Intent myintent=new Intent(OTPEmailVerificationActivity.this, LoginActivity.class);
                myintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(myintent);
                finish();

            }
        }
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "edfghjk df: errorr rr + "+e.getMessage());
        }

    }

    @Override
    public void verifyemailotp_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG);
    }

    private  void doapicall(){
        try {
            String strEmail= MnxPreferenceManager.getString(MnxConstant.REG_EMAIL,null);
            if (strEmail!=null){
                showLoading();
                GetOtpForEmailVerPresenter getOtpForEmailVerPresenter=new GetOtpForEmailVerPresenter(this,new GetOtpForEmailVerIntract());
                getOtpForEmailVerPresenter.validateDetails(strEmail);
            }
            else
                Toast.makeText(this,"Request Data Issue",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "sdghsdghjsdgjhsd df: errorr rr + "+e.getMessage());
        }
    }

    @Override
    public void getemailotp_success(SignupResponse signupResponse) {
        try{
        hideLoading();
        if (signupResponse!=null){
            if (signupResponse.getStatus()){
                Toast.makeText(this,signupResponse.getOtp(),Toast.LENGTH_LONG).show();
            }
        }
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "asdahdjksadhksa df: errorr rr + "+e.getMessage());
        }
    }

    @Override
    public void getemailotp_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
    }
}
