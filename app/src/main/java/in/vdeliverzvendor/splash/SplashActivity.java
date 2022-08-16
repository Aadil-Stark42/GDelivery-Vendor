package in.vdeliverzvendor.splash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.dashboard.DashboardActivity;
import in.vdeliverzvendor.getoptforemail.GetOtpforEmailActivity;
import in.vdeliverzvendor.getotoformobilenum.GetOtoforMobileNumActivity;
import in.vdeliverzvendor.login.LoginActivity;
import in.vdeliverzvendor.otp_Verification.OTPEmailVerificationActivity;
import in.vdeliverzvendor.otp_Verification.OTPMobilenumVerificationActivity;
import in.vdeliverzvendor.utils.MnxConstant;
import in.vdeliverzvendor.utils.MnxPreferenceManager;

import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnFailureListener;
import com.google.firebase.messaging.FirebaseMessaging;

import static com.google.android.play.core.install.model.AppUpdateType.FLEXIBLE;

public class SplashActivity extends AppCompatActivity {

    String TAG= SplashActivity.class.getSimpleName();
    ImageView imgSplash;
    private static final int REQUEST_APP_UPDATE = 200;
    private static final int REQUEST_APP_UPDATEINPROGESS = 201;
    AppUpdateManager    mAppUpdateManager;
    InstallStateUpdatedListener listener;
    boolean isfirrsttime=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        try {
            imgSplash = findViewById(R.id.imgSplash);
            Glide.with(this).load(R.drawable.gdelivery_splash).into(imgSplash);
            isfirrsttime=true;
            mAppUpdateManager = AppUpdateManagerFactory.create(this);

            listener = new InstallStateUpdatedListener() {
                @Override
                public void onStateUpdate(InstallState state) {
                    Log.d(TAG, "onStateUpdate: " + state.toString());

                    if (state.installStatus() == InstallStatus.INSTALLED) {
                        Log.d(TAG, "onStateUpdate: after Install the update");
                    }
                }
            };

            mAppUpdateManager.registerListener(listener);

            mAppUpdateManager
                    .getAppUpdateInfo()
                    .addOnSuccessListener(
                            appUpdateInfo -> {
                                Log.d(TAG, "onCreate: on scucess listner");

                                if (appUpdateInfo.installStatus() == InstallStatus.INSTALLED) {

                                    splashTask();

                                    Log.d(TAG, "onCreate: app is install");
                                }
                                if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                                    Log.d(TAG, "onCreate:  app is downloaded");

                                }

                                if ((appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE)
                                        && appUpdateInfo.isUpdateTypeAllowed(FLEXIBLE )) {

                                    Log.d(TAG, "onCreate: update is avaliable");
                                    // Request the update.
                                    try {
                                        mAppUpdateManager.startUpdateFlowForResult(
                                                appUpdateInfo,
                                                FLEXIBLE,
                                                this,
                                                REQUEST_APP_UPDATE);
                                    } catch (IntentSender.SendIntentException e) {

                                        Log.e(TAG, "onCreate update error: " + e.getMessage());
                                        //e.printStackTrace();
                                    }
                                } else {
                                    Log.e(TAG, "onCreate:  is update is not  avaliable ");

                                    splashTask();

                                }
                            }
                    )
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Log.e(TAG, "onFailure: listener" + e.getMessage());

                            splashTask();
                        }
                    });


            //device ID
            @SuppressLint("HardwareIds") String android_id = Settings.Secure.getString(SplashActivity.this.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            Log.d(TAG, "onCreate: device ID " + android_id);

            MnxPreferenceManager.setString(MnxConstant.DEVICE_ID, android_id);
            Log.d(TAG, "fcmtoken: token" + MnxPreferenceManager.getString(MnxConstant.FCM_TOKEN, ""));
            //splashTask();


        } catch (Exception e) {

            Log.d(TAG, "onCreate: " + e.getMessage());
        }

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
            String version = pInfo.versionName;
            Log.d(TAG, "onCreate: version  " + version);
            MnxPreferenceManager.setString(MnxConstant.APP_VERSION, version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void splashTask() {
        try {

            fcmtoken();

            splashHandler();
        } catch (Exception e) {
            Log.d(TAG, "splashTask: " + e.getMessage());
        }


    }
    @Override
    protected void onResume() {
        super.onResume();
/*        if (!(isfirrsttime)) {
                mAppUpdateManager
                        .getAppUpdateInfo()
                        .addOnSuccessListener(
                                appUpdateInfo -> {
                                    Log.d(TAG, "onResume: appa update");
                                    if (appUpdateInfo.updateAvailability()

                                            == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                                        // If an in-app update is already running, resume the update.
                                        try {
                                            mAppUpdateManager.startUpdateFlowForResult(
                                                    appUpdateInfo,
                                                    FLEXIBLE,
                                                    this,
                                                    REQUEST_APP_UPDATEINPROGESS);
                                        } catch (IntentSender.SendIntentException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Log.e(TAG, "Resume onFailure: listener" + e.getMessage());
                        splashTask();
                    }
                });
        }*/
    }

    private void splashHandler() {
        new Handler().postDelayed(() -> {
            try {
                Log.i("TESTCALLCHECK","TESTCALLCHECK"+"1");
                if (MnxPreferenceManager.getBoolean(MnxConstant.USER_LOGIN,false)){
                    Log.i("TESTCALLCHECK","TESTCALLCHECK"+"3");
                    Intent myintent = new Intent(this, DashboardActivity.class);
                    startActivity(myintent);
                    finish();
                }
                else if ((MnxPreferenceManager.getString(MnxConstant.USERSTATE,null)!=null) && MnxPreferenceManager.getString(MnxConstant.USERSTATE,null).equals("Started")){
                    Intent myintent=new Intent(SplashActivity.this, GetOtoforMobileNumActivity.class);
                    myintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(myintent);
                }
                else if ((MnxPreferenceManager.getString(MnxConstant.USERSTATE,null)!=null) && MnxPreferenceManager.getString(MnxConstant.USERSTATE,null).equals("GetMobileNum")){
                    Intent myintent=new Intent(SplashActivity.this, GetOtoforMobileNumActivity.class);
                    myintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(myintent);
                }
                else if ((MnxPreferenceManager.getString(MnxConstant.USERSTATE,null)!=null) && MnxPreferenceManager.getString(MnxConstant.USERSTATE,null).equals("GetMobileNumOTP")){
                    Intent myintent=new Intent(SplashActivity.this, OTPMobilenumVerificationActivity.class);
                    myintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(myintent);
                }
                else if ((MnxPreferenceManager.getString(MnxConstant.USERSTATE,null)!=null) && MnxPreferenceManager.getString(MnxConstant.USERSTATE,null).equals("GetEmail")){
                    Intent myintent=new Intent(SplashActivity.this, GetOtpforEmailActivity.class);
                    myintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(myintent);
                }
                else if ((MnxPreferenceManager.getString(MnxConstant.USERSTATE,null)!=null) && MnxPreferenceManager.getString(MnxConstant.USERSTATE,null).equals("GetEmailOTP")){
                    Intent myintent=new Intent(SplashActivity.this, OTPEmailVerificationActivity.class);
                    myintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(myintent);
                }
                else{
                    Intent myintent = new Intent(this, LoginActivity.class);
                    startActivity(myintent);
                    finish();
                }
                isfirrsttime=false;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 2000);

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
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (mAppUpdateManager != null) {
                mAppUpdateManager.unregisterListener(listener);
            }
        }catch (Exception e){
            Log.d(TAG, "onDestroy: "+e.getMessage());
        }
    }


}