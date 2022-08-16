package in.vdeliverzvendor.productcategory;

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
import in.vdeliverzvendor.productcategory.mvp_create.CreateCategoryContract;
import in.vdeliverzvendor.productcategory.mvp_create.CreateCategoryIntract;
import in.vdeliverzvendor.productcategory.mvp_create.CreateCategoryPresenter;
import in.vdeliverzvendor.utils.BaseActivity;
import in.vdeliverzvendor.utils.GeneralResponse;

public class CreateProductCategoryActivity extends BaseActivity implements CreateCategoryContract {

    TextView tv_title,tv_submit;
    ImageView iv_search,iv_back;
    EditText ed_name;
    String TAG=CreateProductCategoryActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createcategoryforproduct);

        try{
        initcall();

        initclick();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "asfdafadfdafdf df: errorr rr + "+e.getMessage());
        }

    }

    private  void initcall(){
        tv_title=findViewById(R.id.tv_name);
        tv_title.setText("Create Category");
        iv_search=findViewById(R.id.iv_search);
        iv_search.setVisibility(View.GONE);
        ed_name=findViewById(R.id.ed_name);
        tv_submit=findViewById(R.id.tv_submit);
        iv_back=findViewById(R.id.iv_back);
    }

    private  void initclick(){
        tv_submit.setOnClickListener(new View.OnClickListener() {
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

    }

    private  void doapicall(){
        String strname=ed_name.getText().toString();
        if (strname!=null && !(strname.isEmpty())){
            showLoading();
            CreateCategoryPresenter createCategoryPresenter=new CreateCategoryPresenter(this,new CreateCategoryIntract());
            createCategoryPresenter.validateDetails(strname);
        }
        else
            Toast.makeText(this,"Enter Category Name",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        callnextscreen();
    }

    private  void callnextscreen(){
        Intent myintent=new Intent(CreateProductCategoryActivity.this,ProductCatListActivity.class);
        startActivity(myintent);
        finish();
    }

    @Override
    public void createcategory_success(GeneralResponse generalResponse) {
        hideLoading();
        if (generalResponse!=null){
            if (generalResponse.getStatus()){
                Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
                callnextscreen();
            }
        }

    }

    @Override
    public void createcategory_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();

    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
    }
}
