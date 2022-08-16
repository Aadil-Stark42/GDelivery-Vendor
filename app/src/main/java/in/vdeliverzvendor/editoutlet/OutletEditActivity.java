package in.vdeliverzvendor.editoutlet;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.editoutlet.mvp.EditOutletContract;
import in.vdeliverzvendor.editoutlet.mvp.EditOutletIntract;
import in.vdeliverzvendor.editoutlet.mvp.EditOutletPresenter;
import in.vdeliverzvendor.shopoverview.model.ShopDetailResponse;
import in.vdeliverzvendor.shopoverview.mvp.ShopDetailContract;
import in.vdeliverzvendor.shopoverview.mvp.ShopDetailIntract;
import in.vdeliverzvendor.shopoverview.mvp.ShopDetailPresenter;
import in.vdeliverzvendor.utils.BaseActivity;
import in.vdeliverzvendor.utils.GeneralResponse;

public class OutletEditActivity extends BaseActivity implements ShopDetailContract, View.OnClickListener, EditOutletContract {

    ImageView iv_back;
    ShapeableImageView iv_shopimg,iv_bannerimg;
    Spinner spinnerassign;
    String strRadius,strDesc,strOpeningTime,strEndTime,strAssign,strminimumorderamount;
    String strFilepathshop,strFilepathbanner,strshopId;
    TextView tv_opentime_value,tv_endtimevalue,tv_sunday,tv_mon,tv_tue,tv_wed,tv_th,tv_friday,tv_sat,tv_continue,tv_titile;
    EditText ed_radius, ed_minimumorderamount,ed_desc;
    int file_chosen_mode=100;
    File fileshop,filebanner;
    List<String> weekdayslist;
    boolean ismonday=false,istue=false,iswed=false,isthr=false,isfriday=false,issat=false,issun=false;
    public static  boolean isUpdated=false;
    ConstraintLayout consmain;
    String TAG=OutletEditActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlet_edit);

        try{
        if (getIntent().getStringExtra("ShopId")!=null)
            strshopId=getIntent().getStringExtra("ShopId");

        initcall();
        initclick();

        doapicall();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "dasad dsd sdd: errorr rr + "+e.getMessage());
        }
    }

    private  void initcall(){
        try{
        consmain=findViewById(R.id.consmain);
        tv_sunday=findViewById(R.id.tv_sund);
        tv_mon=findViewById(R.id.tv_mon);
        tv_tue=findViewById(R.id.tv_tue);
        tv_wed=findViewById(R.id.tv_wed);
        tv_th=findViewById(R.id.tv_th);
        tv_friday=findViewById(R.id.tv_friday);
        tv_sat=findViewById(R.id.tv_sat);
        tv_continue=findViewById(R.id.tv_continue);

        iv_back=findViewById(R.id.iv_back);
        iv_shopimg=findViewById(R.id.iv_cam);
        iv_bannerimg=findViewById(R.id.iv_bannercam);
        spinnerassign=findViewById(R.id.spinnerassign);
        tv_opentime_value=findViewById(R.id.tv_opentime_value);
        tv_endtimevalue=findViewById(R.id.tv_endtimevalue);
        ed_radius=findViewById(R.id.ed_radius);
        ed_minimumorderamount=findViewById(R.id.ed_minimumorderamount);
        ed_desc=findViewById(R.id.ed_desc);
        tv_titile=findViewById(R.id.tv_titile);

        tv_sunday.setOnClickListener(this);
        tv_mon.setOnClickListener(this);
        tv_tue.setOnClickListener(this);
        tv_wed.setOnClickListener(this);
        tv_friday.setOnClickListener(this);
        tv_sat.setOnClickListener(this);
        tv_th.setOnClickListener(this);
        consmain.setVisibility(View.GONE);

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "df fd dff: errorr rr + "+e.getMessage());
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
        tv_opentime_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            opentimedialog();

            }
        });

        tv_endtimevalue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openendtimerdialog();
            }
        });
        tv_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateapicall();
            }
        });

        iv_shopimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                file_chosen_mode=1;
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setActivityTitle("Crop image")
                        .setCropShape(CropImageView.CropShape.RECTANGLE)
                        .setCropMenuCropButtonTitle("Done")
                        .setRequestedSize(800, 400)
                        .setCropMenuCropButtonIcon(R.drawable.save)
                        .start(OutletEditActivity.this);
            }
        });


        iv_bannerimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                file_chosen_mode=2;
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setActivityTitle("Crop image")
                        .setCropShape(CropImageView.CropShape.RECTANGLE)
                        .setCropMenuCropButtonTitle("Done")
                        .setRequestedSize(800, 400)
                        .setCropMenuCropButtonIcon(R.drawable.save)
                        .start(OutletEditActivity.this);
            }
        });
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "bhjfgejhjhsa 7tbjhb: errorr rr + "+e.getMessage());
        }
    }

    private  void doapicall(){
        try{
        if (strshopId!=null){
            showLoading();
            ShopDetailPresenter shopDetailPresenter=new ShopDetailPresenter(this,new ShopDetailIntract());
            shopDetailPresenter.validateDetails(strshopId);
        }
        else
            Toast.makeText(this,"Shop Id Error",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "654564 sa asf: errorr rr + "+e.getMessage());
        }

    }

    @Override
    public void shopdetail_success(ShopDetailResponse shopDetailResponse) {
        try{
        hideLoading();
        if (shopDetailResponse!=null){
            if (shopDetailResponse.getStatus()){
                consmain.setVisibility(View.VISIBLE);
                if (shopDetailResponse.getShop().getName()!=null)
                    tv_titile.setText(shopDetailResponse.getShop().getName());
                if (shopDetailResponse.getShop().getDescription()!=null)
                    ed_desc.setText(shopDetailResponse.getShop().getDescription());

                if (shopDetailResponse.getShop().getBannerImage()!=null){
                    Glide.with(this)
                            .load(Uri.parse(shopDetailResponse.getShop().getBannerImage()))
                            .fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(iv_bannerimg);

                }

                if (shopDetailResponse.getShop().getImage()!=null){
                    Glide.with(this)
                            .load(Uri.parse(shopDetailResponse.getShop().getImage()))
                            .fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(iv_shopimg);
                }


                if (shopDetailResponse.getShop().getAssign()!=null)
                    loadvarietyspinner(shopDetailResponse.getShop().getAssign());

                if (shopDetailResponse.getShop().getClosesAt()!=null) {
                    tv_endtimevalue.setText(shopDetailResponse.getShop().getClosesAt());
                    strEndTime=shopDetailResponse.getShop().getClosesAt();
                }

                if (shopDetailResponse.getShop().getOpensAt()!=null) {
                    tv_opentime_value.setText(shopDetailResponse.getShop().getOpensAt());
                    strOpeningTime=shopDetailResponse.getShop().getOpensAt();
                }

                if (shopDetailResponse.getShop().getRadius()!=null)
                    ed_radius.setText(shopDetailResponse.getShop().getRadius());

                if (shopDetailResponse.getShop().getMinAmount()!=null)
                    ed_minimumorderamount.setText(shopDetailResponse.getShop().getMinAmount());

                if (shopDetailResponse.getShop().getDescription()!=null)
                    ed_desc.setText(shopDetailResponse.getShop().getDescription());


                if (shopDetailResponse.getShop().getWeekdays()!=null && shopDetailResponse.getShop().getWeekdays().size()>0){
                    selectweekdays(shopDetailResponse.getShop().getWeekdays());
                }

            }
        }
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "52 sadg gf : errorr rr + "+e.getMessage());
        }

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
            Log.d(TAG, "cabdf gf ert : errorr rr + "+e.getMessage());
        }
    }

    @Override
    public void shopdetail_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();

    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
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
                    tv_opentime_value.setText(hh_mm.format(d2));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, mHour, mMinute, false);
        timePickerDialog.show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "clockc c dsdh: errorr rr + "+e.getMessage());
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
                        tv_endtimevalue.setText(hh_mm.format(d2));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }, mHour, mMinute, false);
            timePickerDialog.show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "clockd sdsd sd asdas: errorr rr + "+e.getMessage());
        }

    }

    private  void loadvarietyspinner(String strSelectedid){

        try{
        String[] country = {"Select delivery type","Automatic", "Manual"};

        ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinneritem, country);
        aa.setDropDownViewResource(R.layout.spinneritem);
        spinnerassign.setAdapter(aa);

        spinnerassign.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String  strvar = String.valueOf(country[position]);
                if (strvar.equals("Automatic"))
                    strAssign="1";
                else if(strvar.equals("Manual"))
                    strAssign="0";
                else
                    strAssign=null;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (strSelectedid!=null){
            if (strSelectedid.equals("1"))
                spinnerassign.setSelection(1,true);
            else
                spinnerassign.setSelection(2,true);
        }

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "load spinner values: errorr rr + "+e.getMessage());
        }

    }

    @Override
    public void editoutlet_success(GeneralResponse generalResponse) {
        try {
            hideLoading();
            if (generalResponse!=null){
                if (generalResponse.getStatus()) {
                    Toast.makeText(this, generalResponse.getMessage(), Toast.LENGTH_LONG).show();
                    isUpdated = true;
                }
                else
                    Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "edit success: errorr rr + "+e.getMessage());
        }

    }

    @Override
    public void editoutlet_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    private  void updateapicall(){
        try {
            collectselectedweeklist();
            strminimumorderamount=ed_minimumorderamount.getText().toString();
            strDesc=ed_desc.getText().toString();
            strRadius=ed_radius.getText().toString();
            if (strminimumorderamount!=null && (!(strminimumorderamount.isEmpty()))){
                if (strRadius!=null && (!(strRadius.isEmpty()))){
                    if (strAssign!=null && (!(strAssign.isEmpty()))){
                        if (strOpeningTime!=null && (!(strOpeningTime.isEmpty()))){
                            if (strEndTime!=null && (!(strEndTime.isEmpty()))){
                                if (weekdayslist!=null && weekdayslist.size()>0){
                                    if (strshopId!=null && (!(strshopId.isEmpty()))) {
                                        showLoading();
                                        EditOutletPresenter editOutletPresenter = new EditOutletPresenter(this, new EditOutletIntract());
                                        editOutletPresenter.validateDetails(strshopId, strminimumorderamount, strRadius, strDesc, strAssign, strOpeningTime, strEndTime, weekdayslist, fileshop, filebanner);
                                    }
                                    else
                                        Toast.makeText(this, "Shop Id Missing", Toast.LENGTH_LONG).show();
                                }
                                else
                                    Toast.makeText(this, "Select Weekdays", Toast.LENGTH_LONG).show();
                            }
                            else
                                Toast.makeText(this,"Select End Time",Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(this,"Select Open Time",Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(this,"Select Delivery Type",Toast.LENGTH_LONG).show();

                }
                else
                    Toast.makeText(this,"Enter Radius",Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(this,"Enter Minimum Order Amount",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "update calll  assa: errorr rr + "+e.getMessage());
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                if(file_chosen_mode==1){
                    Uri path =result.getUri();
                    strFilepathshop=getRealPathFromURI(path);

                    fileshop = new File(getRealPathFromURI(path));

                    if (path!=null){
                        Glide.with(this)
                                .load(fileshop)
                                .fitCenter()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(iv_shopimg);
                    }
                }
                else{
                    Uri path =result.getUri();
                    strFilepathbanner=getRealPathFromURI(path);

                    filebanner = new File(getRealPathFromURI(path));

                    if (path!=null){
                        Glide.with(this)
                                .load(filebanner)
                                .fitCenter()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(iv_bannerimg);
                    }
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        Cursor cursor = getContentResolver().query(contentURI, null, null,
                null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
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


}
