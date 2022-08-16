package in.vdeliverzvendor.createoutlet;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.createoutlet.model_getcuisine.Cuisine;
import in.vdeliverzvendor.createoutlet.model_getcuisine.CuisineListResponse;
import in.vdeliverzvendor.createoutlet.mvp.CreateOutletContract;
import in.vdeliverzvendor.createoutlet.mvp.CreateOutletIntract;
import in.vdeliverzvendor.createoutlet.mvp.CreateOutletPresenter;
import in.vdeliverzvendor.createoutlet.mvp_getcuisine.GetCuisineContract;
import in.vdeliverzvendor.createoutlet.mvp_getcuisine.GetCuisineIntract;
import in.vdeliverzvendor.createoutlet.mvp_getcuisine.GetCuisinePresenter;
import in.vdeliverzvendor.dashboard.DashboardActivity;
import in.vdeliverzvendor.login.LoginActivity;
import in.vdeliverzvendor.shoplist.OutletListWithSearchActivity;
import in.vdeliverzvendor.utils.BaseActivity;
import in.vdeliverzvendor.utils.GeneralResponse;
import in.vdeliverzvendor.utils.MnxConstant;
import in.vdeliverzvendor.utils.MnxPreferenceManager;

public class OutletActivity extends BaseActivity implements GetCuisineContract , CreateOutletContract, View.OnClickListener {

    ImageView iv_back;
    ShapeableImageView iv_shopimg,iv_bannerimg;
    Spinner spinnerassign;
    String strShopName,strEmail,strPhoneNum,strStreet,strCity,strArea,strLat,strLong,strPrice,strComission,
    strRadius,strDesc,strOpeningTime,strEndTime,strAssign,strminimumorderamount;
    String strFilepathshop,strFilepathbanner;
    TextView tv_opentime_value,tv_endtimevalue,tv_sunday,tv_mon,tv_tue,tv_wed,tv_th,tv_friday,tv_sat,tv_continue,tv_cuisines;
    EditText ed_shopname,ed_street,ed_area,ed_city,ed_lat,ed_long,ed_phonenum,ed_email,ed_comm,ed_price,ed_radius,
            ed_minimumorderamount,ed_desc;
    int file_chosen_mode=100;
    File fileshop,filebanner;
    List<String> weekdayslist;
    String[] cuisinearray;
    boolean ismonday=false,istue=false,iswed=false,isthr=false,isfriday=false,issat=false,issun=false;
    boolean[] isSelectedcuisine;
    ArrayList<Integer> cuisinelistid=new ArrayList<>();
    ArrayList<Integer> selectedcuisineid=new ArrayList<>();
    ArrayList<Integer> finalcuisineid=new ArrayList<>();
    String TAG=OutletActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_outlet);

        try{
        initcall();
        initclick();

        dogetcuisinelist();
        loaddeliverytype();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "creaete df: errorr rr + "+e.getMessage());
        }

    }
//

    private  void callnextscreen(){

        try{
        if (MnxPreferenceManager.getString(MnxConstant.ISFROM,null).equals("EmailVeri")){
            Intent myintent=new Intent(OutletActivity.this, LoginActivity.class);
            myintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(myintent);
            finish();
        }
        else if(MnxPreferenceManager.getString(MnxConstant.ISFROM,null).equals("Dashboard")) {
            Intent myintent=new Intent(OutletActivity.this, DashboardActivity.class);
            myintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(myintent);
            finish();
        }
        else{
            Intent myintent=new Intent(OutletActivity.this, OutletListWithSearchActivity.class);
            myintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(myintent);
            finish();
        }

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "asfgsdgdghdsf df: errorr rr + "+e.getMessage());
        }

    }

    private  void initcall(){
        try{
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
        tv_cuisines=findViewById(R.id.tv_cuisines);
        spinnerassign=findViewById(R.id.spinnerassign);
        tv_opentime_value=findViewById(R.id.tv_opentime_value);
        tv_endtimevalue=findViewById(R.id.tv_endtimevalue);
        ed_shopname=findViewById(R.id.ed_shopname);
        ed_street=findViewById(R.id.ed_street);
        ed_area=findViewById(R.id.ed_area);
        ed_city=findViewById(R.id.ed_city);
        ed_lat=findViewById(R.id.ed_lat);
        ed_long=findViewById(R.id.ed_long);
        ed_phonenum=findViewById(R.id.ed_phonenum);
        ed_email=findViewById(R.id.ed_email);
        ed_comm=findViewById(R.id.ed_comm);
        ed_price=findViewById(R.id.ed_price);
        ed_radius=findViewById(R.id.ed_radius);
        ed_minimumorderamount=findViewById(R.id.ed_minimumorderamount);
        ed_desc=findViewById(R.id.ed_desc);

        tv_sunday.setOnClickListener(this);
        tv_mon.setOnClickListener(this);
        tv_tue.setOnClickListener(this);
        tv_wed.setOnClickListener(this);
        tv_friday.setOnClickListener(this);
        tv_sat.setOnClickListener(this);
        tv_th.setOnClickListener(this::onClick);

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "asdfsdfghjk df: errorr rr + "+e.getMessage());
        }
    }

    private  void dogetcuisinelist(){
        GetCuisinePresenter getCuisinePresenter=new GetCuisinePresenter(this, new GetCuisineIntract());
        getCuisinePresenter.validateDetails();
    }

    @Override
    public void onClick(View v) {
        try{
        switch (v.getId()){
            case  R.id.tv_sund:
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
            Log.d(TAG, "saeierjeb fe v df: errorr rr + "+e.getMessage());
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
            Log.d(TAG, "sdfghj df: errorr rr + "+e.getMessage());
        }
    }

    private  void doapicall(){
        try {
        collectselectedweeklist();
        strShopName=ed_shopname.getText().toString();
        strEmail=ed_email.getText().toString();
        strPhoneNum=ed_phonenum.getText().toString();
        strStreet=ed_street.getText().toString();
        strArea=ed_area.getText().toString();
        strCity=ed_city.getText().toString();
        strLat=ed_lat.getText().toString();
        strLong=ed_long.getText().toString();
        strPrice=ed_price.getText().toString();
        strComission=ed_comm.getText().toString();
        strRadius=ed_radius.getText().toString();
        strminimumorderamount=ed_minimumorderamount.getText().toString();
        strDesc=ed_desc.getText().toString();

        if (strShopName!=null && (!(strShopName.isEmpty()))){
            if (strEmail!=null && (!(strEmail.isEmpty())) && Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
                if (strPhoneNum != null && (!strPhoneNum.isEmpty()) && strPhoneNum.length()==10) {
                    if (strStreet != null && (!(strStreet.isEmpty()))) {
                        if (strCity != null && (!(strCity.isEmpty()))) {
                            if (strArea != null && (!(strArea.isEmpty()))) {
                                if (strLat != null && (!(strLat.isEmpty()))) {
                                    if (strLong != null && (!(strLong.isEmpty()))) {
                                        if (strPrice != null && (!(strPrice.isEmpty()))) {
                                            if (strComission != null && (!(strComission.isEmpty()))) {
                                                if (weekdayslist != null && (weekdayslist.size() > 0)) {
                                                    if (fileshop != null) {
                                                        if (filebanner != null) {
                                                            if (strRadius != null && (!(strRadius.isEmpty()))) {
                                                                if (strOpeningTime != null && (!(strOpeningTime.isEmpty()))) {
                                                                    if (strEndTime != null && (!(strEndTime.isEmpty()))) {
                                                                        if (strAssign != null && (!(strAssign.isEmpty()))) {
                                                                            if (strminimumorderamount != null && (!(strminimumorderamount.isEmpty()))) {

                                                                                showLoading();
                                                                                CreateOutletPresenter createOutletPresenter = new CreateOutletPresenter(this, new CreateOutletIntract());
                                                                                createOutletPresenter.validateDetails(fileshop, filebanner, strShopName, strStreet, strArea, strCity, strLat, strLong,
                                                                                        strPhoneNum, strEmail, strComission, strPrice, strRadius, strDesc, strAssign, strOpeningTime, strEndTime, weekdayslist, finalcuisineid, strminimumorderamount);
                                                                            } else
                                                                                Toast.makeText(this, "Enter Minimum Order Amount", Toast.LENGTH_LONG).show();
                                                                        } else
                                                                            Toast.makeText(this, "Select Assign type", Toast.LENGTH_LONG).show();

                                                                    } else
                                                                        Toast.makeText(this, "Select End Time", Toast.LENGTH_LONG).show();
                                                                } else
                                                                    Toast.makeText(this, "Select Open Time", Toast.LENGTH_LONG).show();

                                                            } else
                                                                Toast.makeText(this, "Enter radius", Toast.LENGTH_LONG).show();
                                                        } else
                                                            Toast.makeText(this, "Choose Banner Image", Toast.LENGTH_LONG).show();

                                                    } else
                                                        Toast.makeText(this, "Choose Shop Image", Toast.LENGTH_LONG).show();

                                                } else
                                                    Toast.makeText(this, "Select Weekdays", Toast.LENGTH_LONG).show();

                                            } else
                                                Toast.makeText(this, "Enter Comission", Toast.LENGTH_LONG).show();

                                        } else
                                            Toast.makeText(this, "Enter Price For 2 Person", Toast.LENGTH_LONG).show();
                                    } else
                                        Toast.makeText(this, "Enter Longitude", Toast.LENGTH_LONG).show();

                                } else
                                    Toast.makeText(this, "Enter Latitude", Toast.LENGTH_LONG).show();
                            } else
                                Toast.makeText(this, "Enter Area", Toast.LENGTH_LONG).show();
                        } else
                            Toast.makeText(this, "Enter City", Toast.LENGTH_LONG).show();

                    } else
                        Toast.makeText(this, "Enter Street", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(this,"Enter Valid Mobile Number",Toast.LENGTH_LONG).show();
            }

            else
                Toast.makeText(this,"Enter Valid Email",Toast.LENGTH_LONG).show();

        }
        else
            Toast.makeText(this,"Enter Shop Name",Toast.LENGTH_LONG).show();

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "doa apo daslckadfjkdf df: errorr rr + "+e.getMessage());
        }

    }

    private  void initclick(){
        try{

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
                        .start(OutletActivity.this);
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
                        .start(OutletActivity.this);
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callnextscreen();
            }
        });

        tv_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doapicall();
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

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "agfhfdjsf df: errorr rr + "+e.getMessage());
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        callnextscreen();
    }

    @Override
    public void getcuisine_success(CuisineListResponse cuisineListResponse) {
        try{
        hideLoading();
        if (cuisineListResponse!=null){
            if (cuisineListResponse.getStatus()){
                if (cuisineListResponse.getCuisines()!=null && cuisineListResponse.getCuisines().size()>0){
                    loadcuisinespinner(cuisineListResponse.getCuisines());
                }
            }
            else
                Toast.makeText(this,cuisineListResponse.getMessage(),Toast.LENGTH_LONG).show();
        }
        else

            Toast.makeText(this,cuisineListResponse.getMessage(),Toast.LENGTH_LONG).show();

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "sddsadsad df: errorr rr + "+e.getMessage());
        }
    }

    @Override
    public void getcuisine_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();

    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
    }

    private  void loadcuisinespinner(List<Cuisine> cuisineList){

        try{

        cuisinearray = new String[cuisineList.size()];
        for (int i=0;i<cuisineList.size();i++){
            cuisinearray[i]=cuisineList.get(i).getName();
        }

        isSelectedcuisine =new boolean[cuisineList.size()];
        tv_cuisines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(OutletActivity.this);
                builder.setTitle("Select Cuisine");
                builder.setCancelable(false);

                builder.setMultiChoiceItems(cuisinearray, isSelectedcuisine, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked){
                            cuisinelistid.add(which);
                            Collections.sort(cuisinelistid);
                        }
                        else{
                            for (int i=0;i<cuisinelistid.size();i++){
                                int nid = cuisinelistid.get(i);
                             if (cuisinelistid.get(i)==which) {
                                 cuisinelistid.remove(i);
                                 for (int k = 0; k < selectedcuisineid.size(); k++) {
                                     if (nid == selectedcuisineid.get(k))
                                         selectedcuisineid.remove(k);
                                 }
                             }
                            }

                        }

                    }
                });
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringBuilder stringBuilder=new StringBuilder();
                        for(int j=0;j<cuisinelistid.size();j++){
                            int nid=cuisinelistid.get(j);
                            String name=cuisineList.get(nid).getName();
                            selectedcuisineid.add(cuisineList.get(nid).getCuisineId());
                            stringBuilder.append(name);
                            if (j!= cuisinelistid.size()-1){
                                stringBuilder.append(", ");
                            }
                        }
                        tv_cuisines.setText(stringBuilder.toString());

                        finalcuisineid=new ArrayList<>();
                        for(int i=0;i<cuisinelistid.size();i++){
                            int npos=cuisinelistid.get(i);
                            finalcuisineid.add(cuisineList.get(npos).getCuisineId());
                        }
                        Log.i("Cuisineid","Cuisineid"+finalcuisineid.size()+"List");

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for(int j=0; j<isSelectedcuisine.length;j++){
                            isSelectedcuisine[j]=false;
                            cuisinelistid.clear();
                            finalcuisineid.clear();
                            tv_cuisines.setText("");
                        }
                    }
                });
                builder.show();
            }
        });

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "dfghsadhsdhsdds df: errorr rr + "+e.getMessage());
        }

    }


    private  void loaddeliverytype(){

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

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "dsdgsgdgd gsd df: errorr rr + "+e.getMessage());
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


    @Override
    public void createoutlet_success(GeneralResponse generalResponse) {
        hideLoading();
        if (generalResponse!=null){
            if (generalResponse.getStatus()){
                Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
                callnextscreen();

            }
            else
                Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();

    }

    @Override
    public void createoutlet_failure(String msg) {
        hideLoading();
        Log.i("TESTCREATE","TESTCREATE"+"3"+msg);
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
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
                    tv_opentime_value.setText(hh_mm.format(d2));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, mHour, mMinute, false);
        timePickerDialog.show();

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "datataatatata df: errorr rr + "+e.getMessage());
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
                        tv_endtimevalue.setText(hh_mm.format(d2));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }, mHour, mMinute, false);
            timePickerDialog.show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "datatatatatatatata df: errorr rr + "+e.getMessage());
        }

    }
}
