package in.vdeliverzvendor.bankdetail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.Objects;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.bankdetail.model_banklist.BankListResponse;
import in.vdeliverzvendor.bankdetail.model_withdraw.WithDrawResponse;
import in.vdeliverzvendor.bankdetail.mvp_banklist.BankListContract;
import in.vdeliverzvendor.bankdetail.mvp_banklist.BankListIntract;
import in.vdeliverzvendor.bankdetail.mvp_banklist.BankListPresenter;
import in.vdeliverzvendor.bankdetail.mvp_withdraw.WithDrawIntract;
import in.vdeliverzvendor.bankdetail.mvp_withdraw.WithDrawPresenter;
import in.vdeliverzvendor.bankdetail.mvp_withdraw.WithdrawContract;
import in.vdeliverzvendor.myearnings.MyEarningsActivity;
import in.vdeliverzvendor.utils.BaseActivity;
import in.vdeliverzvendor.utils.MnxConstant;
import in.vdeliverzvendor.utils.MnxPreferenceManager;


public class AccountSelectionActivity  extends BaseActivity implements BankListContract , WithdrawContract {

    RecyclerView rv_banklist;
    TextView tv_addnewaccount,tv_save;
    String strShopId;
    ImageView iv_back;
    BankListItemAdapter bankListItemAdapter;
    public static  boolean isUpdated=false;
    String TAG=AccountSelectionActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_list);

        try{
        if (getIntent().getStringExtra("ShopId")!=null)
            strShopId=getIntent().getStringExtra("ShopId");

        initcall();
        initclick();
        doapicall();

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "ashsadghsdsdg df: errorr rr + "+e.getMessage());
        }

    }

    private  void initcall(){
        rv_banklist=findViewById(R.id.rv_banklist);
        tv_addnewaccount=findViewById(R.id.tv_addnewaccount);
        tv_save=findViewById(R.id.tv_save);
        iv_back=findViewById(R.id.iv_back);
    }

    private  void initclick(){
        tv_addnewaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent=new Intent(AccountSelectionActivity.this, BankDetailActivity.class);
                myintent.putExtra("shopid",strShopId);
                startActivity(myintent);
            }
        });

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doapicallforwithdraw();
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callnextscreen();

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        callnextscreen();
    }

    private  void callnextscreen(){
        Intent myintent=new Intent(this,MyEarningsActivity.class);
        startActivity(myintent);
        finish();
    }

    private  void doapicall(){
        if (strShopId!=null && (!(strShopId.isEmpty()))){
            showLoading();
            BankListPresenter bankListPresenter=new BankListPresenter(this,new BankListIntract());
            bankListPresenter.validateDetails(strShopId);
        }
        else
            Toast.makeText(this,"Shop Id ",Toast.LENGTH_LONG).show();

    }

    @Override
    public void banklist_success(BankListResponse bankListResponse) {
        try{
        hideLoading();
        if (bankListResponse!=null){
            if (bankListResponse.getStatus()){
                if (bankListResponse.getBanks()!=null && (bankListResponse.getBanks().size()>0)){

                     bankListItemAdapter = new BankListItemAdapter(this,bankListResponse.getBanks());
                    rv_banklist.setHasFixedSize(true);
                    rv_banklist.setLayoutManager(new LinearLayoutManager(this));
                    rv_banklist.setAdapter(bankListItemAdapter);
                }
                else
                    Toast.makeText(this,"No banks found",Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(this,bankListResponse.getMessage(),Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,bankListResponse.getMessage(),Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "successasasas df: errorr rr + "+e.getMessage());
        }
    }

    @Override
    public void banklist_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (BankDetailActivity.isUpdated){
            doapicall();
            BankDetailActivity.isUpdated=false;
        }
    }


    public void selectedbank(String id) {
        try {
            if (id != null) {
                MnxPreferenceManager.setString(MnxConstant.SELECTEDID,id);
            }
            else{
                MnxPreferenceManager.setString(MnxConstant.SELECTEDID,null);
            }
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "sdsdsdsadsad df: errorr rr + "+e.getMessage());
        }

    }


    public void notfication() {
        try {
            if (bankListItemAdapter != null)
                bankListItemAdapter.notifyDataSetChanged();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "sadafgefqtgrgr rwgrg df: errorr rr + "+e.getMessage());
        }
    }

    private  void doapicallforwithdraw(){
        try{
        String strBankid=MnxPreferenceManager.getString(MnxConstant.SELECTEDID,null);
        if (strBankid!=null && (!(strBankid.isEmpty())) && strShopId!=null){
            showLoading();
            WithDrawPresenter withDrawPresenter=new WithDrawPresenter(this,new WithDrawIntract());
            withDrawPresenter.validateDetails(strShopId,strBankid);

        }
        else
            Toast.makeText(this,"Request data issue",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "rdftgvyhuj df: errorr rr + "+e.getMessage());
        }

    }

    @Override
    public void withdraw_success(WithDrawResponse withDrawResponse) {
        hideLoading();
        if (withDrawResponse!=null){
            if (withDrawResponse.getStatus()){
                Toast.makeText(this,withDrawResponse.getMessage(),Toast.LENGTH_LONG).show();
                isUpdated=true;
               callnextscreen();
            }
            else
                Toast.makeText(this,withDrawResponse.getMessage(),Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,withDrawResponse.getMessage(),Toast.LENGTH_LONG).show();

    }

    @Override
    public void withdraw_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();

    }
}
