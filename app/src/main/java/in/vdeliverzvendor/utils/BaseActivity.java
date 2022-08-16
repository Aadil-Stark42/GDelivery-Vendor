package in.vdeliverzvendor.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.login.LoginActivity;


public class BaseActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    String TAG= BaseActivity.class.getSimpleName();
    private Dialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(BaseActivity.this);
    }


    public void showProgress(String title, String message) {
        showCustomdialog_progress(title, message);
    }

    public void dismissProgress(){
        Log.d(TAG, "dismissProgress: ");
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void showCustomdialog_progress(String title, String message) {

        if (progressDialog != null) {
            progressDialog.setTitle(title);
            progressDialog.setMessage(message);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            progressDialog.setCancelable(false);
        }
    }
    public void showPopup(String title, String exit){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BaseActivity.this);
        alertDialogBuilder.setMessage(title);
        alertDialogBuilder.setPositiveButton(exit,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        dialog.dismiss();
                        finishActivity(1);
                        dismissProgress();
                    }
                }).create();

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public  void commonToast(String message, Activity activity){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_activity,
                activity.findViewById(R.id.toast_constraint_layout));
        TextView toastTextView = (TextView) layout.findViewById(R.id.toast_tv);
        toastTextView.setText(message);

        // create a new Toast using context
        Toast toast = new Toast(activity);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
    public boolean isNetworkConnected()
    {
        return NetworkConnectivity.checkNow(BaseActivity.this);
    }


    public void showLoading()
    {
        Log.d(TAG, "showLoading: ");
        if ( (mProgressDialog == null || !mProgressDialog.isShowing()))
            mProgressDialog = PackZoProgressDialog.showLoader(this, false);
    }


    public void hideLoading()
    {
        PackZoProgressDialog.dismissLoader(mProgressDialog);
    }


    @Override
    protected void onDestroy()
    {
        if(mProgressDialog!=null)
            PackZoProgressDialog.dismissLoader(mProgressDialog);
        super.onDestroy();
    }

    public void do_logout_and_login_redirect(){
        MnxPreferenceManager.clearAllPreferences();
        Intent myintent=new Intent(getApplicationContext(), LoginActivity.class);
        myintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(myintent);
        finish();
    }
}
