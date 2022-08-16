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
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.slot.model_getdetail.SlotDetailResponse;
import in.vdeliverzvendor.slot.mvp_getdetail.SlotDeatilContract;
import in.vdeliverzvendor.slot.mvp_getdetail.SlotDetailIntract;
import in.vdeliverzvendor.slot.mvp_getdetail.SlotDetailPresenter;
import in.vdeliverzvendor.slot.mvp_slotupdate.SlotUpdateContract;
import in.vdeliverzvendor.slot.mvp_slotupdate.SlotUpdateIntract;
import in.vdeliverzvendor.slot.mvp_slotupdate.SlotUpdatePresenter;
import in.vdeliverzvendor.utils.BaseActivity;
import in.vdeliverzvendor.utils.GeneralResponse;
import in.vdeliverzvendor.utils.MnxConstant;
import in.vdeliverzvendor.utils.MnxPreferenceManager;

public class SlotWeekdaysUpdateActivity extends BaseActivity implements View.OnClickListener, SlotDeatilContract, SlotUpdateContract {

    TextView tv_sunday,tv_mon,tv_tue,tv_wed,tv_th,tv_friday,tv_sat,tv_opentime,tv_closingtime,tv_save;
    List<String> weekdayslist;
    boolean ismonday=false,istue=false,iswed=false,isthr=false,isfriday=false,issat=false,issun=false;
    String strOpeningTime,strEndTime,strShopId,strSlotId;
    ImageView iv_back;
    ConstraintLayout consmain;
    public  static  boolean isUpdate=false;
    String TAG=SlotWeekdaysUpdateActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot_update);

        try{

        if (MnxPreferenceManager.getString(MnxConstant.SELECTEDSHOPID,null)!=null)
            strShopId=MnxPreferenceManager.getString(MnxConstant.SELECTEDSHOPID,null);
        if (getIntent().getStringExtra("slotid")!=null)
            strSlotId=getIntent().getStringExtra("slotid");



        intcall();

        initclick();

        doapicall();

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, " hfdgfggf: errorr rr + "+e.getMessage());
        }

    }

    private  void intcall(){
        try{
        tv_save=findViewById(R.id.tv_save);
        iv_back=findViewById(R.id.iv_back);


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
        consmain=findViewById(R.id.consmain);

        tv_sunday.setOnClickListener(this);
        tv_mon.setOnClickListener(this);
        tv_tue.setOnClickListener(this);
        tv_wed.setOnClickListener(this);
        tv_friday.setOnClickListener(this);
        tv_sat.setOnClickListener(this);
        tv_th.setOnClickListener(this::onClick);
        tv_opentime.setText("00:00");
        tv_closingtime.setText("00:00");
        consmain.setVisibility(View.GONE);

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "dcaddadasd: errorr rr + "+e.getMessage());
        }
    }

    private  void initclick(){
        try{
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent myintent=new Intent(SlotWeekdaysUpdateActivity.this, DashboardActivity.class);
//                myintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(myintent);
//                finish();
                doupdateapicall();
            }
        });

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


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "sad jdfjdfdjf : errorr rr + "+e.getMessage());
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        callnextscreen();
    }

    private  void callnextscreen(){
         Intent myintent=new Intent(SlotWeekdaysUpdateActivity.this, SlotEditActivity.class);
         startActivity(myintent);
         finish();
    }

    @Override
    public void onClick(View v) {
        try{
            switch (v.getId()){
                case  R.id.tv_sund:
                    if (issun) {
                        tv_sunday.setBackground(getResources().getDrawable(R.drawable.circle_bg));
                        issun=false;
                        tv_sunday.setTextColor(getResources().getColor(R.color.gray2));
                    }
                    else{
                        tv_sunday.setBackground(getResources().getDrawable(R.drawable.orange_crc_bg));
                        tv_sunday.setTextColor(getResources().getColor(R.color.white));
                        issun=true;
                    }
                    break;
                case  R.id.tv_mon:
                    if (ismonday) {
                        tv_mon.setBackground(getResources().getDrawable(R.drawable.circle_bg));
                        tv_mon.setTextColor(getResources().getColor(R.color.gray2));
                        ismonday=false;
                    }
                    else{
                        tv_mon.setBackground(getResources().getDrawable(R.drawable.orange_crc_bg));
                        tv_mon.setTextColor(getResources().getColor(R.color.white));
                        ismonday=true;
                    }
                    break;
                case  R.id.tv_tue:
                    if (istue) {
                        tv_tue.setBackground(getResources().getDrawable(R.drawable.circle_bg));
                        tv_tue.setTextColor(getResources().getColor(R.color.gray2));
                        istue=false;
                    }
                    else{
                        tv_tue.setBackground(getResources().getDrawable(R.drawable.orange_crc_bg));
                        tv_tue.setTextColor(getResources().getColor(R.color.white));
                        istue=true;
                    }
                    break;
                case  R.id.tv_wed:
                    if (iswed) {
                        tv_wed.setBackground(getResources().getDrawable(R.drawable.circle_bg));
                        tv_wed.setTextColor(getResources().getColor(R.color.gray2));
                        iswed=false;
                    }
                    else{
                        tv_wed.setBackground(getResources().getDrawable(R.drawable.orange_crc_bg));
                        tv_wed.setTextColor(getResources().getColor(R.color.white));
                        iswed=true;
                    }
                    break;
                case  R.id.tv_friday:
                    if (isfriday) {
                        tv_friday.setBackground(getResources().getDrawable(R.drawable.circle_bg));
                        tv_friday.setTextColor(getResources().getColor(R.color.gray2));
                        isfriday=false;
                    }
                    else{
                        tv_friday.setBackground(getResources().getDrawable(R.drawable.orange_crc_bg));
                        tv_friday.setTextColor(getResources().getColor(R.color.white));
                        isfriday=true;
                    }
                    break;
                case  R.id.tv_th:
                    if (isthr) {
                        tv_th.setBackground(getResources().getDrawable(R.drawable.circle_bg));
                        tv_th.setTextColor(getResources().getColor(R.color.gray2));
                        isthr=false;
                    }
                    else{
                        tv_th.setBackground(getResources().getDrawable(R.drawable.orange_crc_bg));
                        tv_th.setTextColor(getResources().getColor(R.color.white));
                        isthr=true;
                    }
                    break;
                case  R.id.tv_sat:
                    if (issat) {
                        tv_sat.setBackground(getResources().getDrawable(R.drawable.circle_bg));
                        tv_sat.setTextColor(getResources().getColor(R.color.gray2));
                        issat=false;
                    }
                    else{
                        tv_sat.setBackground(getResources().getDrawable(R.drawable.orange_crc_bg));
                        tv_sat.setTextColor(getResources().getColor(R.color.white));
                        issat=true;
                    }
                    break;
            }
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "tryr jabjbj fjf: errorr rr + "+e.getMessage());
        }
    }

    private  void collectselectedweeklist(){
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
                    final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    final Date dateObj = sdf.parse(hourOfDay+":"+minute);
                    String time = new SimpleDateFormat("hh:mm aa").format(dateObj);

                    SimpleDateFormat h_mm_a   = new SimpleDateFormat("hh:mm aa");
                    SimpleDateFormat hh_mm_ss = new SimpleDateFormat("HH:mm:ss");
                    Date d1 = h_mm_a.parse(time);
                    strOpeningTime=hh_mm_ss.format(d1);

                    SimpleDateFormat hh_mm = new SimpleDateFormat("HH:mm");
                    Date d2 = h_mm_a.parse(time);
                    tv_opentime.setText(hh_mm.format(d2));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, mHour, mMinute, false);
        timePickerDialog.show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "fdfdhfg dgfhdh: errorr rr + "+e.getMessage());
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
                        final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                        final Date dateObj = sdf.parse(hourOfDay+":"+minute);
                        String time = new SimpleDateFormat("hh:mm aa").format(dateObj);

                        SimpleDateFormat h_mm_a   = new SimpleDateFormat("hh:mm aa");
                        SimpleDateFormat hh_mm_ss = new SimpleDateFormat("HH:mm:ss");
                        Date d1 = h_mm_a.parse(time);
                        strEndTime=hh_mm_ss.format(d1);

                        SimpleDateFormat hh_mm = new SimpleDateFormat("HH:mm");
                        Date d2 = h_mm_a.parse(time);
                        tv_closingtime.setText(hh_mm.format(d2));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }, mHour, mMinute, false);
            timePickerDialog.show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "dfd dhf df df dut : errorr rr + "+e.getMessage());
        }

    }


    private  void selectweekdays(List<String> weekdayslist){
        try{
        String strValues=new String();
        for(int i=0; i<weekdayslist.size();i++) {
            strValues = weekdayslist.get(i);
            switch(strValues) {
                case "Sunday" :
                    issun=true;
                    tv_sunday.setBackground(getResources().getDrawable(R.drawable.orange_crc_bg));
                    tv_sunday.setTextColor(Color.parseColor("#ffffff"));
                    break;
                case "Monday" :
                    ismonday=true;
                    tv_mon.setBackground(getResources().getDrawable(R.drawable.orange_crc_bg));
                    tv_mon.setTextColor(Color.parseColor("#ffffff"));
                    break;
                case "Tuesday" :
                    istue=true;
                    tv_tue.setBackground(getResources().getDrawable(R.drawable.orange_crc_bg));
                    tv_tue.setTextColor(Color.parseColor("#ffffff"));
                    break;
                case "Wednesday" :
                    iswed=true;
                    tv_wed.setBackground(getResources().getDrawable(R.drawable.orange_crc_bg));
                    tv_wed.setTextColor(Color.parseColor("#ffffff"));
                    break;
                case "Thursday" :
                    isthr=true;
                    tv_th.setBackground(getResources().getDrawable(R.drawable.orange_crc_bg));
                    tv_th.setTextColor(Color.parseColor("#ffffff"));
                    break;
                case "Friday" :
                    isfriday=true;
                    tv_friday.setBackground(getResources().getDrawable(R.drawable.orange_crc_bg));
                    tv_friday.setTextColor(Color.parseColor("#ffffff"));
                    break;
                case  "Saturday":
                    issat=true;
                    tv_sat.setBackground(getResources().getDrawable(R.drawable.orange_crc_bg));
                    tv_sat.setTextColor(Color.parseColor("#ffffff"));
                    break;
            }
        }
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "select datasjs s  sd: errorr rr + "+e.getMessage());
        }
    }

    @Override
    public void slotdetail_success(SlotDetailResponse slotDetailResponse) {
        try{
        hideLoading();
        if (slotDetailResponse!=null){
            if (slotDetailResponse.getStatus()){
                strSlotId=slotDetailResponse.getSlot().getSlotId();
                consmain.setVisibility(View.VISIBLE);
                if (slotDetailResponse.getSlot().getFrom()!=null) {
                    tv_opentime.setText(slotDetailResponse.getSlot().getFrom());
                    strOpeningTime=slotDetailResponse.getSlot().getFrom();
                }
                if (slotDetailResponse.getSlot().getTo()!=null) {
                    tv_closingtime.setText(slotDetailResponse.getSlot().getTo());
                    strEndTime=slotDetailResponse.getSlot().getTo();
                }

                if (slotDetailResponse.getSlot().getWeekdays()!=null && slotDetailResponse.getSlot().getWeekdays().size()>0){
                    selectweekdays(slotDetailResponse.getSlot().getWeekdays());
                }

            }
        }
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "susasd jabsdbshad: errorr rr + "+e.getMessage());
        }

    }

    @Override
    public void slotdetail_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();

    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
    }

    private  void doupdateapicall(){
        try{
        collectselectedweeklist();
        if (strShopId!=null){
            if (strSlotId!=null) {
                if (weekdayslist != null && weekdayslist.size() > 0) {
                    if (strOpeningTime != null && (!(strOpeningTime.isEmpty()))) {
                        if (strEndTime != null && (!strEndTime.isEmpty())) {
                            showLoading();
                            SlotUpdatePresenter slotUpdatePresenter = new SlotUpdatePresenter(this, new SlotUpdateIntract());
                            slotUpdatePresenter.validateDetails(strOpeningTime, strEndTime, weekdayslist, strShopId, strSlotId);
                        } else
                            Toast.makeText(this, "Select End Time", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(this, "Select Open Time", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(this, "Select weekdays", Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(this,"Slot Id missing",Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,"Shop Id missing",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "do api calllsad df : errorr rr + "+e.getMessage());
        }
    }

    @Override
    public void slotupdate_success(GeneralResponse generalResponse) {
        hideLoading();
        if (generalResponse!=null){
            if (generalResponse.getStatus()){
                Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
                callnextscreen();
            }
        }

    }

    @Override
    public void slotupdate_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();

    }

    private  void doapicall(){
        if (strSlotId!=null){
            showLoading();
            SlotDetailPresenter slotDetailPresenter=new SlotDetailPresenter(this,new SlotDetailIntract());
            slotDetailPresenter.validateDetails(strSlotId);
        }
    }
}
