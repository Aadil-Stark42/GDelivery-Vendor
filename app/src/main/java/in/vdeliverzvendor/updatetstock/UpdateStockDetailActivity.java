package in.vdeliverzvendor.updatetstock;

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
import in.vdeliverzvendor.updatetstock.model_getstock.StockDetailResponse;
import in.vdeliverzvendor.updatetstock.mvp_getstock.GetStockContract;
import in.vdeliverzvendor.updatetstock.mvp_getstock.GetStockIntract;
import in.vdeliverzvendor.updatetstock.mvp_getstock.GetStockPresenter;
import in.vdeliverzvendor.updatetstock.mvp_update.UpdateStockContract;
import in.vdeliverzvendor.updatetstock.mvp_update.UpdateStockIntract;
import in.vdeliverzvendor.updatetstock.mvp_update.UpdateStockPresenter;
import in.vdeliverzvendor.utils.BaseActivity;
import in.vdeliverzvendor.utils.GeneralResponse;

public class UpdateStockDetailActivity extends BaseActivity implements GetStockContract , UpdateStockContract {

    TextView tv_addtoppings;
    ImageView iv_back;
    EditText ed_variants,ed_size,ed_actualprice,ed_sellingprice,ed_availablecount;
    Spinner spinnerunit,spinnersize;
    String strProductId,StrActPrice,strSellingPrice,strAva,strVar,strUnit,strSize,strStockid;
    public  static  boolean isUpdated=false;
    String TAG=UpdateStockDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_stock);

        try{

        if (getIntent().getStringExtra("stockid")!=null)
            strStockid=getIntent().getStringExtra("stockid");

        if (getIntent().getStringExtra("productid")!=null)
            strProductId=getIntent().getStringExtra("productid");

        initcall();
        inticlick();
        dogetstockdetailapicall();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "sdsdsd: errorr rr + "+e.getMessage());
        }
    }

    private  void initcall(){
        try{
        ed_variants=findViewById(R.id.ed_variants);
        spinnerunit=findViewById(R.id.spinnerunit);
        spinnersize=findViewById(R.id.spinnersize);
        ed_actualprice=findViewById(R.id.ed_price);
        ed_sellingprice=findViewById(R.id.ed_sellingprice);
        ed_availablecount=findViewById(R.id.ed_availablecount);
        tv_addtoppings=findViewById(R.id.tv_addtoppings);
        iv_back=findViewById(R.id.iv_back);
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "dsd: errorr rr + "+e.getMessage());
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
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "sdsfgr: errorr rr + "+e.getMessage());
        }


    }

    private  void loadspinner(String strSelected){

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

        int nPos=0;
        if (strSelected!=null) {
            for (int i = 0; i < country.length; i++) {
                if (strSelected.equals(country[i])) {
                    nPos = i;
                    break;
                }
            }
            spinnerunit.setSelection(nPos, true);
        }

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "dcaddadasd: errorr rr + "+e.getMessage());
        }

    }


    private  void loadspinnersize(String strSelected){
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

        int nPos=0;
        if (strSelected!=null) {
            for (int i = 0; i < country.length; i++) {
                if (strSelected.equals(country[i])) {
                    nPos = i;
                    break;
                }
            }
            spinnersize.setSelection(nPos, true);
        }
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "fgjk: errorr rr + "+e.getMessage());
        }

    }

    private void dogetstockdetailapicall(){
        if (strStockid!=null){
            showLoading();
            GetStockPresenter getStockPresenter=new GetStockPresenter(this,new GetStockIntract());
            getStockPresenter.validateDetails(strStockid);
        }
        else
            Toast.makeText(this,"Stock Id Missing",Toast.LENGTH_LONG).show();
    }

    @Override
    public void getstock_success(StockDetailResponse stockDetailResponse) {
        try{
        hideLoading();
        if (stockDetailResponse.getStatus()){
            if (stockDetailResponse.getStock()!=null){
                if (stockDetailResponse.getStock().getVariant()!=null)
                    ed_variants.setText(stockDetailResponse.getStock().getVariant());
                if (stockDetailResponse.getStock().getAvailableCount()!=null)
                    ed_availablecount.setText(stockDetailResponse.getStock().getAvailableCount());
                if (stockDetailResponse.getStock().getSellingPrice()!=null)
                    ed_sellingprice.setText(stockDetailResponse.getStock().getSellingPrice());
                if (stockDetailResponse.getStock().getActualPrice()!=null)
                    ed_actualprice.setText(stockDetailResponse.getStock().getActualPrice());
                if (stockDetailResponse.getStock().getUnit()!=null){
                    loadspinner(stockDetailResponse.getStock().getUnit().toString());
                }
                else{
                    loadspinner(null);
                }

                if (stockDetailResponse.getStock().getSize()!=null){
                    loadspinnersize(stockDetailResponse.getStock().getSize().toString());
                }
                else{
                    loadspinnersize(null);
                }

            }
            else
                Toast.makeText(this,stockDetailResponse.getMessage(),Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,stockDetailResponse.getMessage(),Toast.LENGTH_LONG).show();

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "sadgfydfgdf: errorr rr + "+e.getMessage());
        }
    }

    @Override
    public void getstock_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();

    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
    }

    @Override
    public void updatestock_success(GeneralResponse generalResponse) {
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
    public void updatestock_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
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
                                    UpdateStockPresenter updateStockPresenter=new UpdateStockPresenter(this,new UpdateStockIntract());
                                    updateStockPresenter.validateDetails(strProductId,strStockid,StrActPrice,strSellingPrice,strAva,strSize,strUnit,strVar);
                                }
                                else if(strUnit!=null  && (!(strUnit.isEmpty()))){
                                    showLoading();
                                    UpdateStockPresenter updateStockPresenter=new UpdateStockPresenter(this,new UpdateStockIntract());
                                    updateStockPresenter.validateDetails(strProductId,strStockid,StrActPrice,strSellingPrice,strAva,strSize,strUnit,strVar);
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
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "asdsadasd grgrg: errorr rr + "+e.getMessage());
        }

    }
}
