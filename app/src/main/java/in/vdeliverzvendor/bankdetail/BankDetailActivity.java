package in.vdeliverzvendor.bankdetail;

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
import in.vdeliverzvendor.bankdetail.mvp_bankcreate.BankCreateContract;
import in.vdeliverzvendor.bankdetail.mvp_bankcreate.BankCreateIntract;
import in.vdeliverzvendor.bankdetail.mvp_bankcreate.BankCreatePresenter;
import in.vdeliverzvendor.utils.BaseActivity;
import in.vdeliverzvendor.utils.GeneralResponse;

public class BankDetailActivity extends BaseActivity implements BankCreateContract {

    TextView tv_save;
    ImageView iv_back;
    String strShopId,strBankName,strHolderName,strBrach,strCity,strifsc,strAccountNum;
    EditText ed_holdername,ed_bankname,ed_accountnumb,ed_branchname,ed_city,ed_ifsccode;
    public  static  boolean isUpdated=false;
    String TAG=BankDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bankdetails);

        try{

        if (getIntent().getStringExtra("shopid")!=null)
            strShopId=getIntent().getStringExtra("shopid");

        initcall();

        initclick();

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "edasdsdsadfghjk df: errorr rr + "+e.getMessage());
        }

    }

    private  void initcall(){

        try{
        tv_save=findViewById(R.id.tv_save);
        iv_back=findViewById(R.id.iv_back);
        ed_holdername=findViewById(R.id.ed_holdername);
        ed_bankname=findViewById(R.id.ed_bankname);
        ed_accountnumb=findViewById(R.id.ed_accountnumb);
        ed_branchname=findViewById(R.id.ed_branchname);
        ed_city=findViewById(R.id.ed_city);
        ed_ifsccode=findViewById(R.id.ed_ifsccode);
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "initntitn df: errorr rr + "+e.getMessage());
        }

    }



    private  void initclick(){
        try{
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent myintent=new Intent(BankDetailActivity.this,SuccessActivity.class);
//                startActivity(myintent);
                doapicall();
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "asdgsdhgjsds df: errorr rr + "+e.getMessage());
        }
    }

    private  void  doapicall(){
        try{
        strHolderName=ed_holdername.getText().toString();
        strAccountNum=ed_accountnumb.getText().toString();
        strBankName=ed_bankname.getText().toString();
        strBrach=ed_branchname.getText().toString();
        strCity=ed_city.getText().toString();
        strifsc=ed_ifsccode.getText().toString();
        if (strShopId!=null && (!(strShopId.isEmpty()))){
            if (strHolderName!=null && (!(strHolderName.isEmpty()))){
                if (strBankName!=null && (!(strBankName.isEmpty()))){
                    if (strAccountNum!=null && (!(strAccountNum.isEmpty()))){
                        if (strBrach!=null && (!(strBrach.isEmpty()))){
                            if (strCity!=null && (!(strCity.isEmpty()))){
                                if (strifsc!=null && (!(strifsc.isEmpty()))){
                                    showLoading();
                                    BankCreatePresenter bankCreatePresenter=new BankCreatePresenter(this,new BankCreateIntract());
                                    bankCreatePresenter.validateDetails(strShopId,strHolderName,strAccountNum,strCity,strifsc,strBrach,strBankName);
                                }
                                else
                                    Toast.makeText(this,"Enter IFSC Code",Toast.LENGTH_LONG).show();
                            }
                            else
                                Toast.makeText(this,"Enter City",Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(this,"Enter Branch Name",Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(this,"Enter Account Number",Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(this,"Enter Bank Name",Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(this,"Enter Holder Name",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,"Shop Id Error",Toast.LENGTH_LONG).show();
        }

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "succeessss df: errorr rr + "+e.getMessage());
        }
    }

    @Override
    public void cratebank_success(GeneralResponse generalResponse) {
        hideLoading();
        if (generalResponse!=null){
            if (generalResponse.getStatus()){
                isUpdated=true;
                Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
                finish();
            }
            else
                Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void cratebank_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
    }
}



