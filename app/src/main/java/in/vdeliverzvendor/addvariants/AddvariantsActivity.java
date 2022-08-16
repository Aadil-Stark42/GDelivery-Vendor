package in.vdeliverzvendor.addvariants;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.Objects;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.addvariants.mvp.AddStockContract;
import in.vdeliverzvendor.addvariants.mvp.AddStockIntract;
import in.vdeliverzvendor.addvariants.mvp.AddStockPresenter;
import in.vdeliverzvendor.utils.BaseActivity;
import in.vdeliverzvendor.utils.GeneralResponse;

public class AddvariantsActivity extends BaseActivity implements AddStockContract {

    TextView tv_addtoppings;
    ImageView iv_back;
    EditText ed_variants,ed_size,ed_actualprice,ed_sellingprice,ed_availablecount;
    Spinner spinnerunit,spinnersize;
    String strProductId,StrActPrice,strSellingPrice,strAva,strVar,strUnit,strSize;
    public  static  boolean isUpdated=false;
    String TAG=AddvariantsActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityaddvariants);

        try{


        if (getIntent().getStringExtra("ProductId")!=null)
            strProductId=getIntent().getStringExtra("ProductId");

        initcall();

        inticlick();

        loadspinner();

        loadspinnersize();

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "sdbhjsdhjsdhjsd df: errorr rr + "+e.getMessage());
        }

    }

    private  void initcall(){
        try{
        ed_variants=findViewById(R.id.ed_variants);
        spinnerunit=findViewById(R.id.spinnerunit);
//        ed_size=findViewById(R.id.ed_size);
        spinnersize=findViewById(R.id.spinnersize);
        ed_actualprice=findViewById(R.id.ed_price);
        ed_sellingprice=findViewById(R.id.ed_sellingprice);
        ed_availablecount=findViewById(R.id.ed_availablecount);
        tv_addtoppings=findViewById(R.id.tv_addtoppings);
        iv_back=findViewById(R.id.iv_back);
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "asdshdgshdghsd df: errorr rr + "+e.getMessage());
        }
    }

    private  void inticlick(){

        try{
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });
        tv_addtoppings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doapicall();

            }
        });

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "asdsgdjhsd df: errorr rr + "+e.getMessage());
        }

    }

    private  void loadspinner(){

        try{

        String[] country = {"Select unit", "gms","ml","pcs","kgs","litre"};

        ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinneritem, country);
        aa.setDropDownViewResource(R.layout.spinneritem);
        spinnerunit.setAdapter(aa);

        spinnerunit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strUnit = String.valueOf(country[position]);
                if (strUnit.equals("Select unit"))
                    strUnit=null;
                ((TextView) view).setTextColor(getResources().getColor(R.color.apptextcoloor)); //Change selected text color
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "load spinnererer df: errorr rr + "+e.getMessage());
        }

    }


    private  void loadspinnersize(){
        try{

        String[] country = {"Select Size", "Small","Medium","Large"};

        ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinneritem, country);
        aa.setDropDownViewResource(R.layout.spinneritem);
        spinnersize.setAdapter(aa);

        spinnersize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strSize = String.valueOf(country[position]);
                if (strSize.equals("Select Size"))
                    strSize=null;
                ((TextView) view).setTextColor(getResources().getColor(R.color.apptextcoloor)); //Change selected text color
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "load spinner siceee df: errorr rr + "+e.getMessage());
        }

    }

    private  void doapicall(){
        try {
            strAva=ed_availablecount.getText().toString();
            strSellingPrice=ed_sellingprice.getText().toString();
            StrActPrice=ed_actualprice.getText().toString();
            strVar=ed_variants.getText().toString();
            if (strProductId!=null && (!(strProductId.isEmpty()))){
                if (strVar!=null  && (!(strVar.isEmpty()))){
                    if (StrActPrice!=null  && (!(StrActPrice.isEmpty()))){
                        if (strSellingPrice!=null  && (!(strSellingPrice.isEmpty()))){
                            if (strAva!=null  && (!(strAva.isEmpty()))){
                                if (strSize!=null  && (!(strSize.isEmpty()))){
                                    showLoading();
                                    AddStockPresenter addStockPresenter=new AddStockPresenter(this,new AddStockIntract());
                                    addStockPresenter.validateDetails(strProductId,StrActPrice,strSellingPrice,strAva,strSize,strUnit,strVar);
                                }
                                else if(strUnit!=null  && (!(strUnit.isEmpty()))){
                                    showLoading();
                                    AddStockPresenter addStockPresenter=new AddStockPresenter(this,new AddStockIntract());
                                    addStockPresenter.validateDetails(strProductId,StrActPrice,strSellingPrice,strAva,strSize,strUnit,strVar);

                                }
                                else{
                                    Toast.makeText(this,"Select  Units or Select Size",Toast.LENGTH_LONG).show();
                                }
                            }
                            else
                                Toast.makeText(this,"Enter the Available Count",Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(this,"Enter the Selling Price",Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(this,"Enter the Actucal Price",Toast.LENGTH_LONG).show();

                }
                else
                    Toast.makeText(this,"Enter the Variant",Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(this,"Product Id Missing",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "do api call df: errorr rr + "+e.getMessage());
        }

    }

    @Override
    public void addstock_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void addstock_success(GeneralResponse generalResponse) {
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
    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
    }
}
