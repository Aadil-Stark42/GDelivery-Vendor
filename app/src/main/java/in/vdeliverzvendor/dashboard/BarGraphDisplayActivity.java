package in.vdeliverzvendor.dashboard;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.mdgiitr.suyash.graphkit.BarGraph;
import com.mdgiitr.suyash.graphkit.DataPoint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.analytics.model.AnalyticsResponse;
import in.vdeliverzvendor.analytics.mvp.AnalyticsContract;
import in.vdeliverzvendor.analytics.mvp.AnalyticsIntract;
import in.vdeliverzvendor.analytics.mvp.AnalyticsPresenter;
import in.vdeliverzvendor.shoplist.OutletListWithSearchActivity;
import in.vdeliverzvendor.utils.BaseActivity;
import in.vdeliverzvendor.utils.MnxConstant;
import in.vdeliverzvendor.utils.MnxPreferenceManager;

public class BarGraphDisplayActivity extends BaseActivity implements AnalyticsContract {

    ImageView iv_back,iv_menu;
    TextView tv_totalorderscount,tv_totalearnings,tv_packedandassigned,tv_noofcust,tv_selectoutlet;
    String stryear="2022",strmonth,strShopid,strshopname;
    Spinner weekspinner,spinner_year;
    ConstraintLayout consmain;
    RelativeLayout rl_edit;

    String TAG= BarGraphDisplayActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_status);

        try {

            initcall();
            initclick();

            stryear = Calendar.getInstance().get(Calendar.YEAR) + "";
            DateFormat dateFormat = new SimpleDateFormat("MM", Locale.ENGLISH);
            Date date = new Date();
            Log.d("Month", dateFormat.format(date));
            strmonth = dateFormat.format(date);

            initalload();

            loadmonth();
            loadyearspinner();


            if (strmonth != null) {
                weekspinner.setSelection(Integer.parseInt(strmonth) - 1, true);
            }
            if (getIntent().getStringExtra("shopname") != null)
                strshopname = getIntent().getStringExtra("shopname");

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "onCreate: dsdsdsdsf rr + "+e.getMessage());
        }
    }


    private  void initcall(){
        try {
            iv_back = findViewById(R.id.iv_back);
            iv_menu = findViewById(R.id.iv_menu);
            weekspinner = findViewById(R.id.weekspinner);
            spinner_year = findViewById(R.id.spinner_year);
            tv_totalorderscount = findViewById(R.id.tv_totalorderscount);
            tv_totalearnings = findViewById(R.id.tv_totalearnings);
            tv_packedandassigned = findViewById(R.id.tv_packedandassigned);
            tv_noofcust = findViewById(R.id.tv_noofcust);
            consmain = findViewById(R.id.consmain);
            consmain.setVisibility(View.GONE);
            rl_edit = findViewById(R.id.rl_edit);
            tv_selectoutlet = findViewById(R.id.tv_selectoutlet);

            rl_edit.setVisibility(View.GONE);
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "initntinti: errorr rr + "+e.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*Intent myintent=new Intent(BarGraphDisplayActivity.this, OutletListWithSearchActivity.class);
        startActivity(myintent);
        finish();*/
    }

    private  void initclick(){

        try {
            iv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

            iv_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    rl_edit.setVisibility(View.VISIBLE);

                }
            });

            rl_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    doapicall();
                    rl_edit.setVisibility(View.GONE);
                }
            });
            consmain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rl_edit.setVisibility(View.GONE);
                }
            });
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "onclick: errorr rr + "+e.getMessage());
        }
    }

    private  void initalload(){

        try{
        strShopid= MnxPreferenceManager.getString(MnxConstant.SELECTEDSHOPID,null);
        if (strShopid!=null){
            if (stryear!=null){
                if (strmonth!=null){
                    showLoading();
                    if (strmonth.equals("10")||strmonth.equals("11")){
                        //strmonth="0"+strmonth;
                    }
                    Log.i("TESTMONTH","TESTMONTH"+strmonth+"-"+stryear);
                    AnalyticsPresenter analyticsPresenter=new AnalyticsPresenter(this,new AnalyticsIntract());
                    analyticsPresenter.validateDetails(strmonth,stryear,strShopid);

                }
                else
                    Toast.makeText(this,"Select Month",Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(this,"Select Year",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,"Shop Id Missing",Toast.LENGTH_LONG).show();
        }
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "inyinyiysbjfdf: errorr rr + "+e.getMessage());
        }

    }

    private  void doapicall(){
        try{
        strShopid= MnxPreferenceManager.getString(MnxConstant.SELECTEDSHOPID,null);
        if (strShopid!=null){
            if (stryear!=null){
                if (strmonth!=null){
                    showLoading();
                    if (strmonth.equals("10")||strmonth.equals("11")){
                        strmonth="0"+strmonth;
                    }
                    Log.i("TESTMONTH","TESTMONTH"+strmonth);
                    AnalyticsPresenter analyticsPresenter=new AnalyticsPresenter(this,new AnalyticsIntract());
                    analyticsPresenter.validateDetails(strmonth,stryear,strShopid);

                }
                else
                    Toast.makeText(this,"Select Month",Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(this,"Select Year",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,"Shop Id Missing",Toast.LENGTH_LONG).show();
        }
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "api calllll: errorr rr + "+e.getMessage());
        }

    }


    @Override
    public void analytics_success(AnalyticsResponse analyticsResponse) {
        try{
        hideLoading();
        if (analyticsResponse!=null){
            if (analyticsResponse.getStatus()){
                if (analyticsResponse.getMonthlyData()!=null ){
                    consmain.setVisibility(View.VISIBLE);
                    if (strshopname!=null)
                        tv_selectoutlet.setText(strshopname);

                    if (analyticsResponse.getUsers()!=null)
                        tv_noofcust.setText(analyticsResponse.getUsers());
                    if (analyticsResponse.getTotalEarnings()!=null)
                        tv_totalearnings.setText(analyticsResponse.getTotalEarnings());
                    if (analyticsResponse.getTotalDelivered()!=null)
                        tv_totalorderscount.setText(analyticsResponse.getTotalDelivered());
                    if (analyticsResponse.getUnassigned()!=null)
                        tv_packedandassigned.setText(analyticsResponse.getUnassigned());

                    final BarGraph barGraph = findViewById(R.id.barGraph);
                    ArrayList<DataPoint> points = new ArrayList<>();

                    points.add(new DataPoint("Jan",Float.parseFloat(analyticsResponse.getMonthlyData().get01()) , getResources().getColor(R.color.brown)));

                    points.add(new DataPoint("Feb",Float.parseFloat(analyticsResponse.getMonthlyData().get02()) , getResources().getColor(R.color.brown)));

                    points.add(new DataPoint("Mar",Float.parseFloat(analyticsResponse.getMonthlyData().get03()) , getResources().getColor(R.color.brown)));

                    points.add(new DataPoint("April",Float.parseFloat(analyticsResponse.getMonthlyData().get04()) , getResources().getColor(R.color.brown)));

                    points.add(new DataPoint("May",Float.parseFloat(analyticsResponse.getMonthlyData().get05()) , getResources().getColor(R.color.brown)));

                    points.add(new DataPoint("June",Float.parseFloat(analyticsResponse.getMonthlyData().get06()) , getResources().getColor(R.color.brown)));

                    points.add(new DataPoint("July",Float.parseFloat(analyticsResponse.getMonthlyData().get07()) , getResources().getColor(R.color.brown)));

                    points.add(new DataPoint("Aug",Float.parseFloat(analyticsResponse.getMonthlyData().get08()) , getResources().getColor(R.color.brown)));


                    points.add(new DataPoint("Sep",Float.parseFloat(analyticsResponse.getMonthlyData().get09()) , getResources().getColor(R.color.brown)));

                    points.add(new DataPoint("Oct",Float.parseFloat(analyticsResponse.getMonthlyData().get10()) , getResources().getColor(R.color.brown)));

                    points.add(new DataPoint("Nov",Float.parseFloat(analyticsResponse.getMonthlyData().get11()) , getResources().getColor(R.color.brown)));

                    points.add(new DataPoint("Dec",Float.parseFloat(analyticsResponse.getMonthlyData().get12()) , getResources().getColor(R.color.brown)));

                    barGraph.setfalse();
                    barGraph.setPoints(points);

                    BarGraph barGraphweek;
                    ArrayList<DataPoint> pointsweek;

                    barGraphweek = findViewById(R.id.barGraphweek);
                    pointsweek = new ArrayList<>();

                    pointsweek.add(new DataPoint("Week1",Float.parseFloat(analyticsResponse.getWeeklyData().get1()), getResources().getColor(R.color.brown)));
                    pointsweek.add(new DataPoint("Week2", Float.parseFloat(analyticsResponse.getWeeklyData().get2()), getResources().getColor(R.color.brown)));
                    pointsweek.add(new DataPoint("Week3", Float.parseFloat(analyticsResponse.getWeeklyData().get3()), getResources().getColor(R.color.brown)));
                    pointsweek.add(new DataPoint("Week4",Float.parseFloat(analyticsResponse.getWeeklyData().get4()), getResources().getColor(R.color.brown)));

                    barGraphweek.setfalse();
                    barGraphweek.setPoints(pointsweek);

                }
            }
        }
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "rershsksjskjs: errorr rr + "+e.getMessage());
        }
    }

    @Override
    public void analytics_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
    }


    private  void loadyearspinner(){

        try {

            String[] country = {"2022"};

            ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinneritem, country);
            aa.setDropDownViewResource(R.layout.spinneritem);
            spinner_year.setAdapter(aa);

            spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    stryear = "2022";
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "loaddasda : errorr rr + "+e.getMessage());
        }

    }

    private  void loadmonth(){

        try {

            String[] country = {"Jan", "Feb", "Mar", "April", "May", "June", "July", "Aug", "Sep", "Oct", "Nov",
                    "Dec"};

            ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinneritem, country);
            aa.setDropDownViewResource(R.layout.spinneritem);
            weekspinner.setAdapter(aa);

            weekspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    strmonth = position + 1 + "";
                    Log.i("TESTPOS", "TESTPOS" + strmonth);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "asasssaasasas: errorr rr + "+e.getMessage());
        }

    }
}
