package in.vdeliverzvendor.bankdetail;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.Objects;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.myearnings.model_withdrawdet.WithDrawDetailResponse;
import in.vdeliverzvendor.myearnings.mvp_withdrawdet.WithDrawDetContract;
import in.vdeliverzvendor.myearnings.mvp_withdrawdet.WithDrawDetIntract;
import in.vdeliverzvendor.myearnings.mvp_withdrawdet.WithDrawPresenter;
import in.vdeliverzvendor.utils.BaseActivity;

public class SuccessActivity extends BaseActivity implements WithDrawDetContract {

    TextView tv_refernum,tv_date,tv_amount,tv_bankname,tv_holdername,tv_hint,tv_hints;
    String strWithDrawId;
    ImageView iv_img,iv_back;
    View viewone;
    ConstraintLayout consmain;
    String TAG=SuccessActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountcreate_success);

        try{

        if (getIntent().getStringExtra("withdrawid")!=null){
            strWithDrawId=getIntent().getStringExtra("withdrawid");
        }

        initcall();

        initclick();

        doapicall();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "asdgshdghsdgs df: errorr rr + "+e.getMessage());
        }
    }

    private  void initcall(){
        try{
        tv_refernum=findViewById(R.id.tv_refernum);
        tv_date=findViewById(R.id.tv_date);
        tv_amount=findViewById(R.id.tv_amount);
        tv_bankname=findViewById(R.id.tv_bankname);
        tv_holdername=findViewById(R.id.tv_holdername);
        viewone=findViewById(R.id.viewone);
        iv_img=findViewById(R.id.iv_img);
        tv_hint=findViewById(R.id.tv_hint);
        tv_hints=findViewById(R.id.tv_hints);
        consmain=findViewById(R.id.consmain);
        consmain.setVisibility(View.GONE);
        iv_back=findViewById(R.id.iv_back);
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "inintitnit df: errorr rr + "+e.getMessage());
        }
    }

    private  void initclick(){

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private  void doapicall(){
        if (strWithDrawId!=null){
            showLoading();
            WithDrawPresenter withDrawPresenter=new WithDrawPresenter(this,new WithDrawDetIntract());
            withDrawPresenter.validateDetails(strWithDrawId);
        }
    }



    @Override
    public void withdrawdet_success(WithDrawDetailResponse withDrawDetailResponse) {
        try{
        hideLoading();
        if (withDrawDetailResponse!=null){
            if (withDrawDetailResponse.getStatus()){
                consmain.setVisibility(View.VISIBLE);
                if (withDrawDetailResponse.getWithdrawalDetail().getPaymentState().equals("Accepted")){
                    iv_img.setVisibility(View.GONE);
                    viewone.setVisibility(View.GONE);
                    tv_hints.setVisibility(View.GONE);
                    tv_hint.setVisibility(View.GONE);
                }
                if (withDrawDetailResponse.getWithdrawalDetail().getWithdrawalReferral()!=null)
                    tv_refernum.setText(withDrawDetailResponse.getWithdrawalDetail().getWithdrawalReferral());

                if (withDrawDetailResponse.getWithdrawalDetail().getAmount()!=null)
                    tv_amount.setText(withDrawDetailResponse.getWithdrawalDetail().getAmount());

                if (withDrawDetailResponse.getWithdrawalDetail().getDate()!=null)
                    tv_date.setText(withDrawDetailResponse.getWithdrawalDetail().getDate());

                if (withDrawDetailResponse.getWithdrawalDetail().getAccName()!=null)
                    tv_holdername.setText(withDrawDetailResponse.getWithdrawalDetail().getAccName());

                if (withDrawDetailResponse.getWithdrawalDetail().getBankName()!=null)
                    tv_bankname.setText(withDrawDetailResponse.getWithdrawalDetail().getBankName());

            }
        }
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "scuesssasas df: errorr rr + "+e.getMessage());
        }

    }

    @Override
    public void withdrawdet_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();

    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
    }
}
