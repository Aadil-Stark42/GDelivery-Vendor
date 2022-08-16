package in.vdeliverzvendor.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Objects;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.dashboard.DashboardActivity;
import in.vdeliverzvendor.login.model.LoginResponse;
import in.vdeliverzvendor.login.mvp.LoginContractor;
import in.vdeliverzvendor.login.mvp.LoginIntractor;
import in.vdeliverzvendor.login.mvp.LoginPresenter;
import in.vdeliverzvendor.resetpassword.ResetPasswordActivity;
import in.vdeliverzvendor.signup.SignupActivity;
import in.vdeliverzvendor.utils.BaseActivity;
import in.vdeliverzvendor.utils.MnxConstant;
import in.vdeliverzvendor.utils.MnxPreferenceManager;

public class LoginActivity extends BaseActivity implements LoginContractor {

    TextView tv_login,tv_register,tv_forgotpassword,tv_status;
    TextInputEditText ed_password;
    EditText ed_emailid;
    ImageView iv_eye,imgLoginBg;
    String TAG=LoginActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {

            fcmtoken();

            imgLoginBg = findViewById(R.id.imgLoginBg);
             Glide.with(this).load(R.drawable.login_background).into(imgLoginBg);
            tv_login = findViewById(R.id.tv_login);
            tv_register = findViewById(R.id.tv_register);
            tv_forgotpassword = findViewById(R.id.tv_forgotpassword);
            ed_password = findViewById(R.id.ed_password);
            tv_status = findViewById(R.id.tv_status);
            ed_emailid = findViewById(R.id.ed_emailid);
            iv_eye = findViewById(R.id.iv_eye);
            iv_eye.setBackground(getResources().getDrawable(R.drawable.ic_eye));
            iv_eye.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // throw new RuntimeException("Test Crash"); // Force a crash

                    if (tv_status.getText().toString().equals("hide")) {
                        tv_status.setText("show");
                        ed_password.setTransformationMethod(null);
                        iv_eye.setBackground(getResources().getDrawable(R.drawable.ic_eyeopen));
                        ed_password.setSelection(ed_password.getText().length());
                    } else {
                        tv_status.setText("hide");
                        TextView textView = (TextView) findViewById(R.id.ed_password);
                        ed_password.setTransformationMethod(new PasswordTransformationMethod());
                        iv_eye.setBackground(getResources().getDrawable(R.drawable.ic_eye));
                        ed_password.setSelection(ed_password.getText().length());
                    }
                }
            });

            tv_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callloginapi();
                }
            });

            tv_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myintent = new Intent(LoginActivity.this, SignupActivity.class);
                    startActivity(myintent);
                }
            });

            tv_forgotpassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myintent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                    startActivity(myintent);
                }
            });
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "onCreate: errorr rr + "+e.getMessage());
        }

    }

    private  void callloginapi(){
        try {
            String stremail,strpassword,strfcm;
            stremail=ed_emailid.getText().toString();
            strpassword=ed_password.getText().toString();
            strfcm= MnxPreferenceManager.getString(MnxConstant.FCM_TOKEN,"");

            if(stremail !=null && Patterns.EMAIL_ADDRESS.matcher(stremail).matches()){
                if (strpassword!=null && (!(strpassword.isEmpty()))){
                    if (strfcm!=null){
                        showLoading();
                        LoginPresenter loginPresenter=new LoginPresenter(this,new LoginIntractor());
                        loginPresenter.validateDetails(stremail,strpassword,strfcm);
                    }
                }
                else
                    Toast.makeText(this,"Enter Password",Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(this,"Enter Valid Username",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "onCreate: errorr 1213213 + "+e.getMessage());
        }
    }

    private void fcmtoken() {
        try {
            if (!MnxPreferenceManager.getString(MnxConstant.FCM_TOKEN, "").equals("")) {
                Log.d(TAG, "fcmtoken: token is already");
                Log.d(TAG, "fcmtoken: token"+MnxPreferenceManager.getString(MnxConstant.FCM_TOKEN,""));
            } else {
                FirebaseMessaging.getInstance().getToken()
                        .addOnCompleteListener(new OnCompleteListener<String>() {
                            @Override
                            public void onComplete(@NonNull Task<String> task) {
                                if (!task.isSuccessful()) {
                                    Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                                    return;
                                }
                                // Get new FCM registration token
                                String token = task.getResult();

                                // Log and toas
                                Log.i("TESTTOKEN","TESTOKEN"+token);
                                MnxPreferenceManager.setString(MnxConstant.FCM_TOKEN, token);
                            }
                        });
            }

        }catch (Exception e){
            Log.e(TAG, "fcmtoken: "+e.getMessage() );
        }
    }

    @Override
    public void login_success(LoginResponse loginResponse) {
        try {
            hideLoading();
            if (loginResponse != null) {
                if (loginResponse.getStatus()) {
                    if (loginResponse.getToken() != null)
                        MnxPreferenceManager.setString(MnxConstant.TOKEN, loginResponse.getToken());
                    if (loginResponse.getUser().getName() != null)
                        MnxPreferenceManager.setString(MnxConstant.USER_NAME, loginResponse.getUser().getName());
                    if (loginResponse.getUser().getEmail() != null)
                        MnxPreferenceManager.setString(MnxConstant.USER_EMAIL, loginResponse.getUser().getEmail());
                    if (loginResponse.getUser().getMobile() != null)
                        MnxPreferenceManager.getString(MnxConstant.USER_MOBILE, loginResponse.getUser().getMobile());

                    MnxPreferenceManager.setBoolean(MnxConstant.USER_LOGIN, true);

                    if (loginResponse.getUser().getImage() != null)
                        MnxPreferenceManager.setString(MnxConstant.USERIMAGE, loginResponse.getUser().getImage());

//                Toast.makeText(this,"successfully logged in",Toast.LENGTH_LONG).show();
                    Intent myintent = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(myintent);
                    finish();
                }
            }
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "onCreate: 1231324564 rr + "+e.getMessage());
        }
    }

    @Override
    public void login_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();

    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
    }
}
