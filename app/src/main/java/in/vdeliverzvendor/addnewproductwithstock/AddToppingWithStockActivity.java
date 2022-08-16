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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.addtoppings.model_getcat.Title;
import in.vdeliverzvendor.addtoppings.model_getcat.ToppingCatResponse;
import in.vdeliverzvendor.addtoppings.mvp_getcat.GetToppingCatContract;
import in.vdeliverzvendor.addtoppings.mvp_getcat.GetToppingCatIntract;
import in.vdeliverzvendor.addtoppings.mvp_getcat.GetToppingCatPresenter;
import in.vdeliverzvendor.productlist.ProductListWithSearchActivity;
import in.vdeliverzvendor.addnewproductwithstock.mvp.AddProducWithStockPresenter;
import in.vdeliverzvendor.addnewproductwithstock.mvp.AddProductWithStockIntract;
import in.vdeliverzvendor.addnewproductwithstock.mvp.AddProductwithStockContract;
import in.vdeliverzvendor.utils.BaseActivity;
import in.vdeliverzvendor.utils.GeneralResponse;

public class AddToppingWithStockActivity extends BaseActivity implements AddProductwithStockContract, GetToppingCatContract {

    TextView tv_submit;
    ImageView iv_back;
    EditText ed_name,ed_price,ed_availablecount;
    CheckBox checkbox_veg,checkbox_noonveg;
    Spinner catspinner;
    String strvariantname,strtitleid,strvar,strtoppingava,strtoppingprice;
    String StrActPrice,strSellingPrice,strAva,strVarName,strUnit,strSize;
    String strShopId,strCatId,strPdCatId,strCuisineId,strVarId,strname,strdesc,strFilepath;
    public  static  boolean isUpdated=false;
    String TAG=AddToppingWithStockActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtoppings);

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

        if (getIntent().getStringExtra("StrActPrice")!=null)  //Variants
            StrActPrice=getIntent().getStringExtra("StrActPrice");

        if (getIntent().getStringExtra("strSellingPrice")!=null)
            strSellingPrice=getIntent().getStringExtra("strSellingPrice");

        if (getIntent().getStringExtra("strAva")!=null)
            strAva=getIntent().getStringExtra("strAva");

        if (getIntent().getStringExtra("strUnit")!=null)
            strUnit=getIntent().getStringExtra("strUnit");
        else
            strUnit="";

        if (getIntent().getStringExtra("strVar")!=null)
            strVarName=getIntent().getStringExtra("strVar");

        if (getIntent().getStringExtra("strSize")!=null) {
            strSize = getIntent().getStringExtra("strSize");
        }
        else
            strSize="";

        if (getIntent().getStringExtra("strFilepath")!=null)
            strFilepath=getIntent().getStringExtra("strFilepath");

        initcall();

        intclick();
        gettoppingcat();

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "ftguyhji df: errorr rr + "+e.getMessage());
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
            Log.d(TAG, "edfghjk df: errorr rr + "+e.getMessage());
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
            Log.d(TAG, "sadasdfdfadf df: errorr rr + "+e.getMessage());
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
            Log.d(TAG, "asfdsfdfafd df: errorr rr + "+e.getMessage());
        }

    }
    private  void gettoppingcat(){
        showLoading();
        GetToppingCatPresenter getToppingCatPresenter=new GetToppingCatPresenter(this,new GetToppingCatIntract());
        getToppingCatPresenter.validateDetails();
    }

    private  void doapicall(){
        try {
            strtoppingava=ed_availablecount.getText().toString();
            strvariantname=ed_name.getText().toString();
            strtoppingprice=ed_price.getText().toString();
            if (strvariantname!=null && (!(strvariantname.isEmpty()))){
                if (strtitleid!=null &&  (!(strtitleid.isEmpty()))){
                    if (strtoppingprice!=null && (!(strtoppingprice.isEmpty()))){
                        if (strtoppingava!=null && (!(strtoppingava.isEmpty()))){
                            if (strvar!=null &&  (!(strvar.isEmpty()))) {
                                showLoading();

                                AddProducWithStockPresenter addProducWithStockPresenter = new AddProducWithStockPresenter(this, new AddProductWithStockIntract());
                                addProducWithStockPresenter.directValidation(strShopId, strCatId, strPdCatId, strCuisineId, strVarId, strname, strdesc, new File(strFilepath),
                                        StrActPrice, strSellingPrice, strAva, strSize, strUnit, strVarName,
                                        strtoppingprice, strtoppingava, strtitleid, strvariantname, strvar);
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
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "asfddddddddddd df: errorr rr + "+e.getMessage());
        }

    }

    @Override
    public void addstockproduct_success(GeneralResponse generalResponse) {
        hideLoading();
        if (generalResponse!=null){
            if (generalResponse.getStatus()){
                Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
                isUpdated=true;
                Intent myintent=new Intent(this, ProductListWithSearchActivity.class);
                myintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(myintent);
                finish();
            }
            else
                Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
    }


    @Override
    public void addstockproduct__failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();

    }

    @Override
    public void toppingcat_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();

    }

    @Override
    public void toppingcat_success(ToppingCatResponse toppingCatResponse) {
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

    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
    }
}