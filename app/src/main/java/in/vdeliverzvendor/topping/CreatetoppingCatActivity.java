package in.vdeliverzvendor.topping;

import android.content.Intent;
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
import in.vdeliverzvendor.topping.mvp.CreateToppingContract;
import in.vdeliverzvendor.topping.mvp.CreateToppingIntract;
import in.vdeliverzvendor.topping.mvp.CreateToppingPresenter;
import in.vdeliverzvendor.utils.BaseActivity;
import in.vdeliverzvendor.utils.GeneralResponse;

public class CreatetoppingCatActivity  extends BaseActivity implements CreateToppingContract {

    ImageView iv_back;
    TextView tv_create;
    String strname;
    EditText ed_catname;
    String TAG=CreatetoppingCatActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_topping_cat);

        try{

        initcall();

        initclick();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "cafdsfdf df: errorr rr + "+e.getMessage());
        }

    }

    private  void initcall(){
        iv_back=findViewById(R.id.iv_back);
        tv_create=findViewById(R.id.tv_create);
        ed_catname=findViewById(R.id.ed_catname);

    }

    private  void initclick(){

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tv_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doapicall();

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent myintent=new Intent(CreatetoppingCatActivity.this,ToppingListActivity.class);
        startActivity(myintent);
        finish();
    }

    private  void doapicall(){
        strname=ed_catname.getText().toString();
        if (strname!=null && (!(strname.isEmpty()))) {
            showLoading();
            CreateToppingPresenter createToppingPresenter = new CreateToppingPresenter(this, new CreateToppingIntract());
            createToppingPresenter.validateDetails(strname);

        }
        else
            Toast.makeText(this,"Enter Name",Toast.LENGTH_LONG).show();

    }

    @Override
    public void createtopping_success(GeneralResponse generalResponse) {
        hideLoading();
        if (generalResponse!=null){
            if (generalResponse.getStatus()){
                Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        }


    }

    @Override
    public void createtopping_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
    }
}
