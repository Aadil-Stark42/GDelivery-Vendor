package in.vdeliverzvendor.signup;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.signup.model.SignupResponse;
import in.vdeliverzvendor.signup.mvp.SignupContract;
import in.vdeliverzvendor.signup.mvp.SignupIntract;
import in.vdeliverzvendor.signup.mvp.SignupPresenter;
import in.vdeliverzvendor.utils.BaseActivity;
import in.vdeliverzvendor.utils.MnxConstant;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import in.vdeliverzvendor.getotoformobilenum.GetOtoforMobileNumActivity;

public class SignupActivity extends BaseActivity implements SignupContract {

    TextView tv_continue;
    ImageView iv_back,iv_eyepassowrd,iv_eyeconfirmpassword;
    EditText ed_name,ed_emailid,ed_phonenum;
    SignupPresenter signupPresenter;
    String name,email,mobile,password,confirm_password;
    TextInputEditText ed_password,ed_confirm_password;
    TextView tv_status_pass,tv_status_confirm_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        MnxPreferenceManager.setString(MnxConstant.USERSTATE,"Signup");
        tv_continue=findViewById(R.id.tv_continue);
        iv_back=findViewById(R.id.iv_back);
        ed_name=findViewById(R.id.ed_name);
        ed_emailid=findViewById(R.id.ed_emailid);
        ed_phonenum=findViewById(R.id.ed_phonenum);
        ed_password=findViewById(R.id.ed_password);
        ed_confirm_password=findViewById(R.id.ed_confirm_password);
        tv_status_pass=findViewById(R.id.tv_status_pass);
        tv_status_confirm_pass=findViewById(R.id.tv_status_confirm_pass);
        iv_eyeconfirmpassword=findViewById(R.id.iv_eyeconfirm);
        iv_eyepassowrd=findViewById(R.id.iv_eyepassowrd);

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
                    ed_confirm_password.setSelection(ed_confirm_password.getText().length());
                }
                else{
                    tv_status_confirm_pass.setText("hide");
                    ed_confirm_password.setTransformationMethod(new PasswordTransformationMethod());
                    iv_eyeconfirmpassword.setBackground(getResources().getDrawable(R.drawable.ic_eye));
                    ed_confirm_password.setSelection(ed_confirm_password.getText().length());
                }
            }
        });



        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }

        });

        tv_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=ed_name.getText().toString().trim();
                email=ed_emailid.getText().toString().trim();
                mobile=ed_phonenum.getText().toString().trim();
                password= Objects.requireNonNull(ed_password.getText()).toString();
                confirm_password= Objects.requireNonNull(ed_confirm_password.getText()).toString().trim();

                if(name!=null && name.length()>0){
                    if(email !=null && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        if(mobile!=null && mobile.length()==10){
                            mobile="+91"+mobile;
                            if(password!= null && (!(password.isEmpty()))){
                                if(confirm_password!=null && (!(confirm_password.isEmpty()))){
                                    if(password.equals(confirm_password)){
                                        do_signup_apicall();
                                    }else{
                                        Toast.makeText(SignupActivity.this,"Password and Confirm Password are Mismatch", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(SignupActivity.this,"Enter Confirm Password", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(SignupActivity.this,"Enter Password", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(SignupActivity.this,"Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(SignupActivity.this,"Enter Valid Email", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(SignupActivity.this,"Enter Name", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    public void do_signup_apicall(){
        showLoading();
        signupPresenter=new SignupPresenter(this,new SignupIntract());
        signupPresenter.validateDetails(name,email,mobile,password,confirm_password);
    }

    @Override
    public void signup_success(SignupResponse signupResponse) {
        hideLoading();
        if(signupResponse!=null){
            if(signupResponse.getStatus()){
                MnxPreferenceManager.setString(MnxConstant.REG_MOBILE,mobile);
                MnxPreferenceManager.setString(MnxConstant.REG_EMAIL,email);
                MnxPreferenceManager.setString(MnxConstant.USERSTATE,"Started");
                Intent myintent=new Intent(SignupActivity.this, GetOtoforMobileNumActivity.class);
                myintent.putExtra("mobile",mobile);
                startActivity(myintent);
            }else{
                Toast.makeText(this, signupResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void signup_failure(String msg) {
        hideLoading();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
    }
}
