package in.vdeliverzvendor.settings;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.Objects;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.editprofile.EditProfileActivity;
import in.vdeliverzvendor.login.LoginActivity;
import in.vdeliverzvendor.privacypolicy.PrivacyPolicyActivity;
import in.vdeliverzvendor.settings.logout_mvp.LogoutContract;
import in.vdeliverzvendor.settings.logout_mvp.LogoutIntract;
import in.vdeliverzvendor.settings.logout_mvp.LogoutPresenter;
import in.vdeliverzvendor.termsandcondition.TermsAndConditionActivity;
import in.vdeliverzvendor.utils.BaseActivity;
import in.vdeliverzvendor.utils.GeneralResponse;
import in.vdeliverzvendor.utils.MnxConstant;
import in.vdeliverzvendor.utils.MnxPreferenceManager;

public class SettingsActivity extends BaseActivity implements LogoutContract {

    ImageView iv_back;
    TextView tv_editprofile, tv_bankaccount, tv_terms, tv_privacypolicy;
    ConstraintLayout conslogout;
    Switch btn_switch;
    String TAG = SettingsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        try {

            iv_back = findViewById(R.id.iv_back);
            tv_editprofile = findViewById(R.id.tv_editprofile);
            tv_bankaccount = findViewById(R.id.tv_bankaccount);
            conslogout = findViewById(R.id.conslogout);
            btn_switch = findViewById(R.id.switcher);
            tv_terms = findViewById(R.id.tv_terms);
            tv_privacypolicy = findViewById(R.id.tv_privacypolicy);

            initclick();
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().log(TAG + " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "dcaddadasd: errorr rr + " + e.getMessage());
        }

    }

    private void initclick() {

        try {
            btn_switch.setChecked((MnxPreferenceManager.getBoolean(MnxConstant.NOTIFICATION_MODE, true)));
            btn_switch.setOnCheckedChangeListener((buttonView, isChecked) -> {

                Log.i("TESTCHECK", "TESTCHECK" + isChecked);
                MnxPreferenceManager.setBoolean(MnxConstant.NOTIFICATION_MODE, isChecked);
                // do something, the isChecked will be
                // true if the switch is in the On position
            });

            conslogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doapicall();
                }
            });

            iv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

            tv_editprofile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myintent = new Intent(SettingsActivity.this, EditProfileActivity.class);
                    startActivity(myintent);
                }
            });
            tv_privacypolicy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myintent = new Intent(SettingsActivity.this, PrivacyPolicyActivity.class);
                    startActivity(myintent);
                }
            });

            tv_terms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myintent = new Intent(SettingsActivity.this, TermsAndConditionActivity.class);
                    startActivity(myintent);
                }
            });

        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().log(TAG + " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "asdvgdv dgffu e : errorr rr + " + e.getMessage());
        }
    }

    private void doapicall() {
        showLoading();
        LogoutPresenter logoutPresenter = new LogoutPresenter(this, new LogoutIntract());
        logoutPresenter.validateDetails();
    }

    @Override
    public void logout_success(GeneralResponse generalResponse) {
        try {
            hideLoading();
            if (generalResponse != null) {
                if (generalResponse.getStatus()) {
                    Toast.makeText(this, generalResponse.getMessage(), Toast.LENGTH_LONG).show();

                    MnxPreferenceManager.setString(MnxConstant.TOKEN, "");
                    MnxPreferenceManager.getString(MnxConstant.USER_NAME, "");
                    MnxPreferenceManager.getString(MnxConstant.USER_EMAIL, "");
                    MnxPreferenceManager.getString(MnxConstant.USER_MOBILE, "");
                    MnxPreferenceManager.setBoolean(MnxConstant.USER_LOGIN, false);

                    Intent myintent = new Intent(SettingsActivity.this, LoginActivity.class);
                    myintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(myintent);
                    finish();

                }
            }
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().log(TAG + " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "dcaddadasd: dsadsad rr + " + e.getMessage());
        }

    }

    @Override
    public void logout_failure(String msg) {
        hideLoading();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
    }
}
