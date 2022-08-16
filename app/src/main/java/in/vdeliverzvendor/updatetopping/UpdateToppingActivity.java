package in.vdeliverzvendor.updatetopping;

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
import in.vdeliverzvendor.updatetopping.model_gettopping.Title;
import in.vdeliverzvendor.updatetopping.model_gettopping.ToppingDetailResponse;
import in.vdeliverzvendor.updatetopping.mvp_gettopping.GetToppingContract;
import in.vdeliverzvendor.updatetopping.mvp_gettopping.GetToppingIntract;
import in.vdeliverzvendor.updatetopping.mvp_gettopping.GetToppingPresenter;
import in.vdeliverzvendor.updatetopping.mvp_updatetopping.UpdateToppingContract;
import in.vdeliverzvendor.updatetopping.mvp_updatetopping.UpdateToppingIntract;
import in.vdeliverzvendor.updatetopping.mvp_updatetopping.UpdateToppingPresenter;
import in.vdeliverzvendor.utils.BaseActivity;
import in.vdeliverzvendor.utils.GeneralResponse;

public class UpdateToppingActivity  extends BaseActivity implements GetToppingContract, UpdateToppingContract {

    TextView tv_submit;
    ImageView iv_back;
    EditText ed_name,ed_price,ed_availablecount;
    CheckBox checkbox_veg,checkbox_noonveg;
    Spinner catspinner;
    String strvariantname,strProductid,strtitleid,strvar,stravailable,strprice,strtoppingid;
    public static   boolean isUpdated=false;
    String TAG=UpdateToppingActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_topping);

        try{
        if (getIntent().getStringExtra("toppingid")!=null){
            strtoppingid=getIntent().getStringExtra("toppingid");
        }

        if (getIntent().getStringExtra("productid")!=null){
            strProductid=getIntent().getStringExtra("productid");
        }

        initcall();
        intclick();

        doapicalltogetdetail();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "sdsdsdsd: errorr rr + "+e.getMessage());
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
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "sadfdsfsdf dsf df: errorr rr + "+e.getMessage());
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
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "asd hefuhv sg: errorr rr + "+e.getMessage());
        }

    }

    private  void doapicalltogetdetail(){
        if (strtoppingid!=null){
            showLoading();
            GetToppingPresenter getToppingPresenter=new GetToppingPresenter(this,new GetToppingIntract());
            getToppingPresenter.validateDetails(strtoppingid);
        }
        else
            Toast.makeText(this,"Topping Id Missing",Toast.LENGTH_LONG).show();
    }

    @Override
    public void gettopping_success(ToppingDetailResponse toppingDetailResponse) {
        try {
            hideLoading();
            if (toppingDetailResponse!=null){
                if (toppingDetailResponse.getStatus()){

                    if (toppingDetailResponse.getTopping().getName()!=null)
                        ed_name.setText(toppingDetailResponse.getTopping().getName());
                    if (toppingDetailResponse.getTopping().getAvailable()!=null)
                        ed_availablecount.setText(toppingDetailResponse.getTopping().getAvailable());
                    if (toppingDetailResponse.getTopping().getPrice()!=null)
                        ed_price.setText(toppingDetailResponse.getTopping().getPrice());
                    if (toppingDetailResponse.getTopping().getVariety()!=null){
                        if (toppingDetailResponse.getTopping().getVariety().equals("1")) {
                            checkbox_veg.setChecked(true);
                            checkbox_noonveg.setChecked(false);
                            strvar="1";
                        }
                        else{
                            checkbox_noonveg.setChecked(true);
                            checkbox_veg.setChecked(false);
                            strvar="0";
                        }
                    }
                    if (toppingDetailResponse.getTopping().getTitleId()!=null && toppingDetailResponse.getTitles()!=null
                            && toppingDetailResponse.getTitles().size()>0){
                        loadspinner(toppingDetailResponse.getTopping().getTitleId(),toppingDetailResponse.getTitles());

                    }
                }
                else
                    Toast.makeText(this,toppingDetailResponse.getMessage(),Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(this,toppingDetailResponse.getMessage(),Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "fdfdjfhdfhjfd: errorr rr + "+e.getMessage());
        }

    }

    @Override
    public void gettopping_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();

    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
    }

    private  void loadspinner(String strSelectedid,List<Title> titleList){

        try {
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
            int nPos=0;
            if (strSelectedid!=null) {
                for (int i = 0; i < titleList.size(); i++) {
                    if (strSelectedid.equals(titleList.get(i).getTitleId())) {
                        nPos = i;
                        break;
                    }
                }
                catspinner.setSelection(nPos+1, true);
            }
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "dsafhfsfhfh : errorr rr + "+e.getMessage());
        }

    }

    @Override
    public void updatetopping_success(GeneralResponse generalResponse) {
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
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "sdfdf sdfgfg : errorr rr + "+e.getMessage());
        }
    }

    @Override
    public void updatetopping_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    private  void doapicall(){
        try {
            stravailable=ed_availablecount.getText().toString();
            strvariantname=ed_name.getText().toString();
            strprice=ed_price.getText().toString();
            if (strProductid!=null){
                if (strvariantname!=null && (!(strvariantname.isEmpty()))){
                    if (strtitleid!=null && (!(strtitleid.isEmpty()))){
                        if (strprice!=null && (!(strprice.isEmpty()))){
                            if (stravailable!=null && (!(stravailable.isEmpty()))){
                                showLoading();
                                UpdateToppingPresenter updateToppingPresenter=new UpdateToppingPresenter(this,new UpdateToppingIntract());
                                updateToppingPresenter.validateDetails(strProductid,strtoppingid,strprice,stravailable,strtitleid,strvariantname,strvar);

                            }
                            else
                                Toast.makeText(this,"Enter the Num of Availables",Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(this,"Enter the price",Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(this,"Select the category",Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(this,"Enter the title",Toast.LENGTH_LONG).show();

            }
            else
                Toast.makeText(this,"Product Id Missing",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "sdabfhdfdf: errorr rr + "+e.getMessage());
        }

    }

}
