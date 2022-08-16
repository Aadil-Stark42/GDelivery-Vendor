package in.vdeliverzvendor.addnewproductwithstock;

import android.content.Intent;
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

import java.io.File;
import java.util.Objects;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.productlist.ProductListWithSearchActivity;
import in.vdeliverzvendor.addnewproductwithstock.mvp.AddProducWithStockPresenter;
import in.vdeliverzvendor.addnewproductwithstock.mvp.AddProductWithStockIntract;
import in.vdeliverzvendor.addnewproductwithstock.mvp.AddProductwithStockContract;
import in.vdeliverzvendor.utils.BaseActivity;
import in.vdeliverzvendor.utils.GeneralResponse;

public class AddVariantWIthStockActivity extends BaseActivity implements AddProductwithStockContract {

    TextView tv_addtoppings;
    ImageView iv_back;
    EditText ed_variants,ed_size,ed_actualprice,ed_sellingprice,ed_availablecount;
    Spinner spinnerunit,spinnersize;
    String strProductId,StrActPrice,strSellingPrice,strAva,strVar,strUnit,strSize;
    String strShopId,strCatId,strPdCatId,strCuisineId,strVarId,strname,strdesc,strFilepath;
    CheckBox checkboxyes,checkboxno;
    int nStatus=0;
    File file;
    public  static  boolean isUpdated=false;
    String TAG=AddVariantWIthStockActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addvariants);

        try{

        if (getIntent().getStringExtra("shop_id")!=null)
            strShopId=getIntent().getStringExtra("shop_id");

        if (getIntent().getStringExtra("category_id")!=null)
            strCatId=getIntent().getStringExtra("category_id");

        if (getIntent().getStringExtra("name")!=null)
            strname=getIntent().getStringExtra("name");

        if (getIntent().getStringExtra("sub_category_id")!=null)
            strPdCatId=getIntent().getStringExtra("sub_category_id");

        if (getIntent().getStringExtra("description")!=null)
            strdesc=getIntent().getStringExtra("description");

        if (getIntent().getStringExtra("cuisine_id")!=null)
            strCuisineId=getIntent().getStringExtra("cuisine_id");

        if (getIntent().getStringExtra("variety")!=null)
            strVarId=getIntent().getStringExtra("variety");

        if (getIntent().getStringExtra("strFilepath")!=null)
            strFilepath=getIntent().getStringExtra("strFilepath");

        initcall();

        inticlick();

        loadspinner();

        loadspinnersize();

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "afdfdfdgggsd df: errorr rr + "+e.getMessage());
        }

    }

    private  void initcall(){
        ed_variants=findViewById(R.id.ed_variants);
        spinnerunit=findViewById(R.id.spinnerunit);
        spinnersize=findViewById(R.id.spinnersize);
        ed_actualprice=findViewById(R.id.ed_price);
        ed_sellingprice=findViewById(R.id.ed_sellingprice);
        ed_availablecount=findViewById(R.id.ed_availablecount);
        tv_addtoppings=findViewById(R.id.tv_addtoppings);
        iv_back=findViewById(R.id.iv_back);
        checkboxyes=findViewById(R.id.checkbox);
        checkboxno=findViewById(R.id.checkboxnonveg);
        tv_addtoppings.setVisibility(View.GONE);
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
                donextprocess(nStatus);

            }
        });

        checkboxyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_addtoppings.setVisibility(View.VISIBLE);
                if (checkboxyes.isChecked()){
                    tv_addtoppings.setText("Add Product Toppings");
                    nStatus=1;
                    checkboxyes.setChecked(true);
                    checkboxno.setChecked(false);
                }
                else{
                    nStatus=1;
                    checkboxyes.setChecked(true);
                    checkboxno.setChecked(false);
                }


            }
        });

        checkboxno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_addtoppings.setVisibility(View.VISIBLE);
                if (checkboxno.isChecked()){
                    tv_addtoppings.setText("Submit");
                    nStatus=0;
                    checkboxno.setChecked(true);
                    checkboxyes.setChecked(false);
                }
                else{
                    nStatus=0;
                    checkboxno.setChecked(true);
                    checkboxyes.setChecked(false);
                }

            }
        });

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "adsadsadsadasfdfaf df: errorr rr + "+e.getMessage());
        }


    }

    private  void donextprocess(int Staus){
        try{
        if (Staus==1){
            boolean isallavailable=false;
            strAva=ed_availablecount.getText().toString();
            strSellingPrice=ed_sellingprice.getText().toString();
            StrActPrice=ed_actualprice.getText().toString();
            strVar=ed_variants.getText().toString();

            if (strVar!=null  && (!(strVar.isEmpty()))){
                if (StrActPrice!=null  && (!(StrActPrice.isEmpty()))){
                    if (strSellingPrice!=null  && (!(strSellingPrice.isEmpty()))){
                        if (strAva!=null  && (!(strAva.isEmpty()))){
                            if (strSize!=null  && (!(strSize.isEmpty()))){
                                isallavailable=true;
                            }
                            else if(strUnit!=null  && (!(strUnit.isEmpty()))){
                                isallavailable=true;
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

            if (isallavailable) {

                Intent myintent = new Intent(AddVariantWIthStockActivity.this, AddToppingWithStockActivity.class);
                myintent.putExtra("shop_id", strShopId);
                myintent.putExtra("category_id", strCatId);
                myintent.putExtra("name", strname);
                myintent.putExtra("sub_category_id", strPdCatId);
                myintent.putExtra("description", strdesc);
                myintent.putExtra("cuisine_id", strCuisineId);
                myintent.putExtra("variety", strVarId);
                myintent.putExtra("strFilepath", strFilepath);

                myintent.putExtra("StrActPrice", StrActPrice);
                myintent.putExtra("strSellingPrice", strSellingPrice);
                myintent.putExtra("strAva", strAva);
                myintent.putExtra("strSize", strSize);
                myintent.putExtra("strUnit", strUnit);
                myintent.putExtra("strVar", strVar);

                startActivity(myintent);
            }


        }else{
            doapicall();
        }
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "sffdafdfdf df: errorr rr + "+e.getMessage());
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
            Log.d(TAG, "dxcfgvhbjk df: errorr rr + "+e.getMessage());
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
            Log.d(TAG, "gfydgfgfyhfdyfh df: errorr rr + "+e.getMessage());
        }

    }

    private  void doapicall(){
        try {
            strAva=ed_availablecount.getText().toString();
            strSellingPrice=ed_sellingprice.getText().toString();
            StrActPrice=ed_actualprice.getText().toString();
            strVar=ed_variants.getText().toString();
                if (strVar!=null  && (!(strVar.isEmpty()))){
                    if (StrActPrice!=null  && (!(StrActPrice.isEmpty()))){
                        if (strSellingPrice!=null  && (!(strSellingPrice.isEmpty()))){
                            if (strAva!=null  && (!(strAva.isEmpty()))){
                                if (strSize!=null  && (!(strSize.isEmpty()))){
                                    Log.i("TESTCHECK","TESTCHECK"+"1"+strSize+"-"+strSize);

                                    if (strUnit==null )
                                        strUnit="";

                                    showLoading();
                                    AddProducWithStockPresenter addProducWithStockPresenter=new AddProducWithStockPresenter(this,new AddProductWithStockIntract());
                                    addProducWithStockPresenter.directValidation(strShopId,strCatId,strPdCatId,strCuisineId,strVarId,strname,strdesc,new File(strFilepath),
                                            StrActPrice,strSellingPrice,strAva,strSize,strUnit,strVar,
                                            "","","","","");
                                }
                                else if(strUnit!=null  && (!(strUnit.isEmpty()))){
                                    if (strSize==null)
                                        strSize="";
                                    showLoading();
                                    AddProducWithStockPresenter addProducWithStockPresenter=new AddProducWithStockPresenter(this,new AddProductWithStockIntract());
                                    addProducWithStockPresenter.directValidation(strShopId,strCatId,strPdCatId,strCuisineId,strVarId,strname,strdesc,new File(strFilepath),
                                            StrActPrice,strSellingPrice,strAva,strSize,strUnit,strVar,
                                            "","","","","");
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
//            }
//            else
//                Toast.makeText(this,"Product Id Missing",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "edfgasdfdfdfsdhjk df: errorr rr + "+e.getMessage());
        }

    }

    @Override
    public void addstockproduct_success(GeneralResponse generalResponse) {
        try{
        hideLoading();
        if (generalResponse!=null){
            if (generalResponse.getStatus()){
                Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
                isUpdated=true;
                Intent myintent=new Intent(this,ProductListWithSearchActivity.class);
                myintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(myintent);
                finish();

            }
            else
                Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "sadaddasdsadasd df: errorr rr + "+e.getMessage());
        }
    }


    @Override
    public void addstockproduct__failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();

    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
    }

}
