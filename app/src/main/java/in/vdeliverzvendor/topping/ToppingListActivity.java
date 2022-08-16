package in.vdeliverzvendor.topping;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.Objects;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.addtoppings.model_getcat.ToppingCatResponse;
import in.vdeliverzvendor.addtoppings.mvp_getcat.GetToppingCatContract;
import in.vdeliverzvendor.addtoppings.mvp_getcat.GetToppingCatIntract;
import in.vdeliverzvendor.addtoppings.mvp_getcat.GetToppingCatPresenter;
import in.vdeliverzvendor.utils.BaseActivity;

public class ToppingListActivity  extends BaseActivity  implements GetToppingCatContract {

    RecyclerView rv_list;
    TextView tv_title,tv_no_of_shops;
    ImageView iv_back,iv_add,iv_search;
    ConstraintLayout consmain;
    String TAG=ToppingListActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_toppingscat);

        try{

        initcall();

        initclick();

        doapicall();

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "sdsad asdg gads: errorr rr + "+e.getMessage());
        }

    }

    private  void initcall(){
        try{
        rv_list=findViewById(R.id.rv_toppinglist);
        tv_title=findViewById(R.id.tv_name);
        tv_title.setText("Topping Categories");
        iv_back=findViewById(R.id.iv_back);
        iv_add=findViewById(R.id.iv_add);
        tv_no_of_shops=findViewById(R.id.tv_no_of_shops);
        consmain=findViewById(R.id.consmain);
        consmain.setVisibility(View.GONE);
        iv_search=findViewById(R.id.iv_search);
        iv_search.setVisibility(View.GONE);
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "fdfgdfdf df: errorr rr + "+e.getMessage());
        }
    }

    private  void initclick(){

        try{
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent=new Intent(ToppingListActivity.this,CreatetoppingCatActivity.class);
                startActivity(myintent);
                finish();
            }
        });
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "sdsdas sad sd: errorr rr + "+e.getMessage());
        }
    }

    private  void doapicall(){
        showLoading();
        GetToppingCatPresenter getToppingCatPresenter=new GetToppingCatPresenter(this,new GetToppingCatIntract());
        getToppingCatPresenter.validateDetails();
    }

    @Override
    public void toppingcat_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();

    }

    @Override
    public void toppingcat_success(ToppingCatResponse toppingCatResponse) {
        try{
        hideLoading();
        if (toppingCatResponse!=null){
            if (toppingCatResponse.getStatus()){
                if(toppingCatResponse.getTitles()!=null){
                    if (toppingCatResponse.getTitles().size()>0){
                        consmain.setVisibility(View.VISIBLE);
                        tv_no_of_shops.setText(toppingCatResponse.getTitles().size()+"  Topping");
                        ToppingListAdapter toppingListAdapter = new ToppingListAdapter(this,toppingCatResponse.getTitles());
                        rv_list.setHasFixedSize(true);
                        rv_list.setLayoutManager(new LinearLayoutManager(this));
                        rv_list.setAdapter(toppingListAdapter);
                    }
                }
                else
                    Toast.makeText(this,toppingCatResponse.getMessage(),Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(this,toppingCatResponse.getMessage(),Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,toppingCatResponse.getMessage(),Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "fdsf jdhfdsf: errorr rr + "+e.getMessage());
        }
    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
    }
}
