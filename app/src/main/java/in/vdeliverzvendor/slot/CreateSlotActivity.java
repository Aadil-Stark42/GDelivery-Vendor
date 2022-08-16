package in.vdeliverzvendor.slot;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.slot.mvp_create.CreateSlotContract;
import in.vdeliverzvendor.slot.mvp_create.CreateSlotIntract;
import in.vdeliverzvendor.slot.mvp_create.CreateSlotPresenter;
import in.vdeliverzvendor.slotlist.SlotListActivity;
import in.vdeliverzvendor.utils.BaseActivity;
import in.vdeliverzvendor.utils.GeneralResponse;
import in.vdeliverzvendor.utils.MnxConstant;
import in.vdeliverzvendor.utils.MnxPreferenceManager;

public class CreateSlotActivity extends BaseActivity implements  View.OnClickListener, CreateSlotContract {

    TextView tv_sunday,tv_mon,tv_tue,tv_wed,tv_th,tv_friday,tv_sat,tv_opentime,tv_closingtime,tv_save;
    List<String> weekdayslist;
    boolean ismonday=false,istue=false,iswed=false,isthr=false,isfriday=false,issat=false,issun=false;
    String strOpeningTime,strEndTime,strShopId;
    ImageView iv_back;
    String TAG=CreateSlotActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot_creation);

        try{
        if (MnxPreferenceManager.getString(MnxConstant.SELECTEDSHOPID,null)!=null)
            strShopId=MnxPreferenceManager.getString(MnxConstant.SELECTEDSHOPID,null);

        initcall();

        initclick();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "asdasfafaffasafs df: errorr rr + "+e.getMessage());
        }
    }

    private  void initcall(){
        try{
        tv_sunday=findViewById(R.id.tv_sun);
        tv_mon=findViewById(R.id.tv_mon);
        tv_tue=findViewById(R.id.tv_tue);
        tv_wed=findViewById(R.id.tv_wed);
        tv_th=findViewById(R.id.tv_th);
        tv_friday=findViewById(R.id.tv_friday);
        tv_sat=findViewById(R.id.tv_sat);
        tv_opentime=findViewById(R.id.tv_opentime);
        tv_closingtime=findViewById(R.id.tv_closingtime);
        tv_save=findViewById(R.id.tv_save);
        iv_back=findViewById(R.id.iv_back);

        tv_sunday.setOnClickListener(this);
        tv_mon.setOnClickListener(this);
        tv_tue.setOnClickListener(this);
        tv_wed.setOnClickListener(this);
        tv_friday.setOnClickListener(this);
        tv_sat.setOnClickListener(this);
        tv_th.setOnClickListener(this::onClick);
        tv_opentime.setText("00:00");
        tv_closingtime.setText("00:00");

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "fgghjgfjhd df: errorr rr + "+e.getMessage());
        }
    }

    private void  initclick(){

        try{

        tv_closingtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openendtimerdialog();

            }
        });
        tv_opentime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opentimedialog();
            }
        });

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doapicall();
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callnextscreen();
            }
        });

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "sdgjhdgjsdghjsd df: errorr rr + "+e.getMessage());
        }
    }

    private  void callnextscreen(){

        Intent myintent=new Intent(CreateSlotActivity.this, SlotListActivity.class);
        myintent.putExtra("Shopid",strShopId);
        startActivity(myintent);
        finish();


    }

    @Override
    public void onClick(View v) {
        try{
        switch (v.getId()){
            case  R.id.tv_sun:
                if (issun) {
                    tv_sunday.setBackground(getResources().getDrawable(R.drawable.circle_bg));
                    issun=false;
                }
                else{
                    tv_sunday.setBackground(getResources().getDrawable(R.drawable.orange_crc_bg));
                    tv_sunday.setTextColor(Color.parseColor("#ffffff"));
                    issun=true;
                }
                break;
            case  R.id.tv_mon:
                if (ismonday) {
                    tv_mon.setBackground(getResources().getDrawable(R.drawable.circle_bg));
                    ismonday=false;
                }
                else{
                    tv_mon.setBackground(getResources().getDrawable(R.drawable.orange_crc_bg));
                    tv_mon.setTextColor(Color.parseColor("#ffffff"));
                    ismonday=true;
                }
                break;
            case  R.id.tv_tue:
                if (istue) {
                    tv_tue.setBackground(getResources().getDrawable(R.drawable.circle_bg));
                    istue=false;
                }
                else{
                    tv_tue.setBackground(getResources().getDrawable(R.drawable.orange_crc_bg));
                    tv_tue.setTextColor(Color.parseColor("#ffffff"));
                    istue=true;
                }
                break;
            case  R.id.tv_wed:
                if (iswed) {
                    tv_wed.setBackground(getResources().getDrawable(R.drawable.circle_bg));
                    iswed=false;
                }
                else{
                    tv_wed.setBackground(getResources().getDrawable(R.drawable.orange_crc_bg));
                    tv_wed.setTextColor(Color.parseColor("#ffffff"));
                    iswed=true;
                }
                break;
            case  R.id.tv_friday:
                if (isfriday) {
                    tv_friday.setBackground(getResources().getDrawable(R.drawable.circle_bg));
                    isfriday=false;
                }
                else{
                    tv_friday.setBackground(getResources().getDrawable(R.drawable.orange_crc_bg));
                    tv_friday.setTextColor(Color.parseColor("#ffffff"));
                    isfriday=true;
                }
                break;
            case  R.id.tv_th:
                if (isthr) {
                    tv_th.setBackground(getResources().getDrawable(R.drawable.circle_bg));
                    isthr=false;
                }
                else{
                    tv_th.setBackground(getResources().getDrawable(R.drawable.orange_crc_bg));
                    tv_th.setTextColor(Color.parseColor("#ffffff"));
                    isthr=true;
                }
                break;
            case  R.id.tv_sat:
                if (issat) {
                    tv_sat.setBackground(getResources().getDrawable(R.drawable.circle_bg));
                    issat=false;
                }
                else{
                    tv_sat.setBackground(getResources().getDrawable(R.drawable.orange_crc_bg));
                    tv_sat.setTextColor(Color.parseColor("#ffffff"));
                    issat=true;
                }
                break;
        }
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "asdsdfhjg rjgirjgijgk g jrhg df: errorr rr + "+e.getMessage());
        }
    }

    private  void collectselectedweeklist(){
        try{
        weekdayslist = new ArrayList();
        if (issun)
            weekdayslist.add("Sunday");
        if (ismonday)
            weekdayslist.add("Monday");
        if (istue)
            weekdayslist.add("Tuesday");
        if (iswed)
            weekdayslist.add("Wednesday");
        if (isthr)
            weekdayslist.add("Thursday");
        if (isfriday)
            weekdayslist.add("Friday");
        if (issat)
            weekdayslist.add("Saturday");
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "231234564df4dsfsdf5d df: errorr rr + "+e.getMessage());
        }
    }

    private  void opentimedialog(){
        try{
        int  mHour, mMinute; // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                try {
                    final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
                    final Date dateObj = sdf.parse(hourOfDay+":"+minute);
                    String time = new SimpleDateFormat("hh:mm aa",Locale.ENGLISH).format(dateObj);

                    SimpleDateFormat h_mm_a   = new SimpleDateFormat("hh:mm aa",Locale.ENGLISH);
                    SimpleDateFormat hh_mm_ss = new SimpleDateFormat("HH:mm:ss",Locale.ENGLISH);
                    Date d1 = h_mm_a.parse(time);
                    strOpeningTime=hh_mm_ss.format(d1);

                    SimpleDateFormat hh_mm = new SimpleDateFormat("HH:mm",Locale.ENGLISH);
                    Date d2 = h_mm_a.parse(time);
                    tv_opentime.setText(hh_mm.format(d2));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, mHour, mMinute, false);
        timePickerDialog.show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "35saf a4f5a4f54f5 f54f df: errorr rr + "+e.getMessage());
        }
    }


    private  void openendtimerdialog(){
        try {
            int  mHour, mMinute;
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    try {
                        final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm",Locale.ENGLISH);
                        final Date dateObj = sdf.parse(hourOfDay+":"+minute);
                        String time = new SimpleDateFormat("hh:mm aa",Locale.ENGLISH).format(dateObj);

                        SimpleDateFormat h_mm_a   = new SimpleDateFormat("hh:mm aa",Locale.ENGLISH);
                        SimpleDateFormat hh_mm_ss = new SimpleDateFormat("HH:mm:ss",Locale.ENGLISH);
                        Date d1 = h_mm_a.parse(time);
                        strEndTime=hh_mm_ss.format(d1);

                        SimpleDateFormat hh_mm = new SimpleDateFormat("HH:mm",Locale.ENGLISH);
                        Date d2 = h_mm_a.parse(time);
                        tv_closingtime.setText(hh_mm.format(d2));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }, mHour, mMinute, false);
            timePickerDialog.show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "fd124fd5dfd3f2df df: errorr rr + "+e.getMessage());
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        callnextscreen();
    }

    private  void doapicall(){
        try{
        collectselectedweeklist();
        if (strShopId!=null){
            if (weekdayslist!=null && weekdayslist.size()>0){
                if (strOpeningTime!=null && (!(strOpeningTime.isEmpty()))){
                    if (strEndTime!=null && (!strEndTime.isEmpty())){
                        showLoading();
                        CreateSlotPresenter createSlotPresenter=new CreateSlotPresenter(this,new CreateSlotIntract());
                        createSlotPresenter.validateDetails(strOpeningTime,strEndTime,weekdayslist,strShopId);
                    }
                    else
                        Toast.makeText(this,"Select End Time",Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(this,"Select Open Time",Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(this,"Select weekdays",Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,"Shop Id missing",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "adfadfdfsdf df: errorr rr + "+e.getMessage());
        }
    }

    @Override
    public void createslot_success(GeneralResponse generalResponse) {
        hideLoading();
        if (generalResponse!=null){
            if (generalResponse.getStatus()){
                Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
                callnextscreen();
            }
        }
    }

    @Override
    public void createslot_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void dashboard_logout() {
            hideLoading();
            do_logout_and_login_redirect();
    }
}
