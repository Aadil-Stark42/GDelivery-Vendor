package in.vdeliverzvendor.addtoppings;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.addtoppings.model_getcat.Title;
import in.vdeliverzvendor.addtoppings.model_getcat.ToppingCatResponse;
import in.vdeliverzvendor.addtoppings.mvp.AddToppingContract;
import in.vdeliverzvendor.addtoppings.mvp.AddToppingIntract;
import in.vdeliverzvendor.addtoppings.mvp.AddToppingPresenter;
import in.vdeliverzvendor.addtoppings.mvp_getcat.GetToppingCatContract;
import in.vdeliverzvendor.addtoppings.mvp_getcat.GetToppingCatIntract;
import in.vdeliverzvendor.addtoppings.mvp_getcat.GetToppingCatPresenter;
import in.vdeliverzvendor.utils.BaseActivity;
import in.vdeliverzvendor.utils.GeneralResponse;

public class AddToppingsActivity extends BaseActivity implements AddToppingContract, GetToppingCatContract {

    TextView tv_submit;
    ImageView iv_back;
    EditText ed_name,ed_price,ed_availablecount;
    CheckBox checkbox_veg,checkbox_noonveg;
    Spinner catspinner;
    String strvariantname,strProductid,strtitleid,strvar,stravailable,strprice;
    public  static  boolean isUpdated=false;
    String TAG=AddToppingsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityaddtopping);

        try {

            if (getIntent().getStringExtra("ProductId") != null)
                strProductid = getIntent().getStringExtra("ProductId");

            initcall();

            intclick();
            gettoppingcat();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "asdghjsdgjsadjsad df: errorr rr + "+e.getMessage());
        }

    }

    private void initcall(){
        try{
        tv_submit=findViewById(R.id.tv_submit);
        iv_back=findViewById(R.id.iv_back);
        ed_name=findViewById(R.id.ed_variants);
        ed_price=findViewById(R.id.ed_price);
        ed_availablecount=findViewById(R.id.ed_availablecount);
        checkbox_veg=findViewById(R.id.checkbox);
        checkbox_noonveg=findViewById(R.id.checkboxnonveg);
        catspinner=findViewById(R.id.spinner);
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "cafdsfdf df: errorr rr + "+e.getMessage());
        }
    }

    private  void intclick(){

        try{

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doapicall();
//                Intent myintent=new Intent(AddToppingsActivity.this, DashboardActivity.class);
//                startActivity(myintent);
            }
        });

        checkbox_veg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkbox_veg.isChecked()){
                    strvar="1";
                    checkbox_veg.setChecked(true);
                    checkbox_noonveg.setChecked(false);
                }
                else{
                    strvar="1";
                    checkbox_veg.setChecked(true);
                    checkbox_noonveg.setChecked(false);
                }
            }
        });

        checkbox_noonveg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkbox_noonveg.isChecked()){
                    strvar="0";
                    checkbox_noonveg.setChecked(true);
                    checkbox_veg.setChecked(false);
                }
                else{
                    strvar="0";
                    checkbox_noonveg.setChecked(true);
                    checkbox_veg.setChecked(false);
                }
            }
        });

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "sdsdsd df: errorr rr + "+e.getMessage());
        }

    }

    private  void loadspinner(List<Title> titleList){

        try{

        ArrayList<String> names= new ArrayList<>();
        names.add("Select Category");

        for (int i=0; i<titleList.size();i++){
            names.add(titleList.get(i).getName());
        }

        ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinneritem, names);
        aa.setDropDownViewResource(R.layout.spinneritem);
        catspinner.setAdapter(aa);

        catspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String  Namehint =  String.valueOf(names.get(position));
                if (Namehint.equals("Select Category"))
                    strtitleid=null;
                else
                    strtitleid = String.valueOf(titleList.get((int) parent.getItemIdAtPosition(position)-1).getTitleId());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "asdsdsdhds df: errorr rr + "+e.getMessage());
        }

    }
    private  void gettoppingcat(){
        showLoading();
        GetToppingCatPresenter getToppingCatPresenter=new GetToppingCatPresenter(this,new GetToppingCatIntract());
        getToppingCatPresenter.validateDetails();
    }

    private  void doapicall(){
        try{
        stravailable=ed_availablecount.getText().toString();
        strvariantname=ed_name.getText().toString();
        strprice=ed_price.getText().toString();
        if (strProductid!=null){
            if (strvariantname!=null && (!(strvariantname.isEmpty()))){
                if (strtitleid!=null && (!(strtitleid.isEmpty()))){
                    if (strprice!=null && (!(strprice.isEmpty()))){
                        if (stravailable!=null && (!(stravailable.isEmpty()))){
                            if (strvar!=null && (!(strvar.isEmpty()))) {
                                showLoading();
                                AddToppingPresenter addToppingPresenter = new AddToppingPresenter(this, new AddToppingIntract());
                                addToppingPresenter.validateDetails(strProductid, strprice, stravailable, strtitleid, strvariantname, strvar);
                            }
                            else
                                Toast.makeText(this,"Select the variety",Toast.LENGTH_LONG).show();

                        }
                        else
                            Toast.makeText(this,"Enter the Num of Availables",Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(this,"Enter the price",Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(this,"Select  the category",Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(this,"Enter the title",Toast.LENGTH_LONG).show();

        }
        else
            Toast.makeText(this,"Product Id Missing",Toast.LENGTH_LONG).show();

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "cafdsfdf df: errorr rr + "+e.getMessage());
        }

    }

    @Override
    public void addtopping_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();

    }

    @Override
    public void addtopping_success(GeneralResponse generalResponse) {
        try{
        hideLoading();
        if (generalResponse!=null){
            if (generalResponse.getStatus()){
                Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
                isUpdated=true;
                finish();
            }
            else
                Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "sdgsdhsd df: errorr rr + "+e.getMessage());
        }

    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
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
                        loadspinner(toppingCatResponse.getTitles());
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
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "asdsadgjsdh df: errorr rr + "+e.getMessage());
        }
    }

}
