package in.vdeliverzvendor.slot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.Objects;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.productview.mvp_staus.ProductStatusChangeIntract;
import in.vdeliverzvendor.productview.mvp_staus.ProductStatusChangePresenter;
import in.vdeliverzvendor.productview.mvp_staus.ProductStausChangeContract;
import in.vdeliverzvendor.slot.model_getdetail.SlotDetailResponse;
import in.vdeliverzvendor.slot.mvp_delete.SlotDeleteContract;
import in.vdeliverzvendor.slot.mvp_delete.SlotDeleteIntract;
import in.vdeliverzvendor.slot.mvp_delete.SlotDeletePresenter;
import in.vdeliverzvendor.slot.mvp_getdetail.SlotDeatilContract;
import in.vdeliverzvendor.slot.mvp_getdetail.SlotDetailIntract;
import in.vdeliverzvendor.slot.mvp_getdetail.SlotDetailPresenter;
import in.vdeliverzvendor.slotlist.SlotListActivity;
import in.vdeliverzvendor.utils.BaseActivity;
import in.vdeliverzvendor.utils.GeneralResponse;
import in.vdeliverzvendor.utils.MnxConstant;
import in.vdeliverzvendor.utils.MnxPreferenceManager;

public class SlotEditActivity extends BaseActivity implements SlotDeatilContract , ProductStausChangeContract, SlotDeleteContract {

    RelativeLayout rl_edit,rl_edits,rl_delete;
    ImageView iv_menu,iv_back;
    ConstraintLayout cons_main;
    TextView tv_shopName,tv_timevalues,tv_openingdays;
    CheckBox checkboxava,checkboxunava;
    int nStatus;
    String strSlotId,strShopId;
    String TAG=SlotEditActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot_edit);

        try {

            if (MnxPreferenceManager.getString(MnxConstant.SELECTEDSHOPID, null) != null)
                strShopId = MnxPreferenceManager.getString(MnxConstant.SELECTEDSHOPID, null);
            if (MnxPreferenceManager.getString(MnxConstant.SELECTED_SLOT_ID, null) != null)
                strSlotId = MnxPreferenceManager.getString(MnxConstant.SELECTED_SLOT_ID, null);

            initcall();
            initclick();

            doapicall();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "asdjfdjdhfj fdfhjdfh: errorr rr + "+e.getMessage());
        }
    }

    private  void initcall(){
        try{
        rl_edit=findViewById(R.id.rl_edit);
        rl_edits=findViewById(R.id.rl_edits);
        rl_delete=findViewById(R.id.rl_delete);
        iv_menu=findViewById(R.id.iv_menu);
        cons_main=findViewById(R.id.cons_main);
        iv_back=findViewById(R.id.iv_back);
        tv_shopName=findViewById(R.id.tv_shopName);
        tv_timevalues=findViewById(R.id.tv_timevalues);
        tv_openingdays=findViewById(R.id.tv_openingdays);
        checkboxava=findViewById(R.id.checkbox);
        checkboxunava=findViewById(R.id.checkboxnonveg);
        cons_main.setVisibility(View.GONE);
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "cafgdfdf dfgdgfgdf: errorr rr + "+e.getMessage());
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

        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_edit.setVisibility(View.VISIBLE);
            }
        });
        cons_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_edit.setVisibility(View.GONE);
            }
        });

        rl_edits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent=new Intent(SlotEditActivity.this, SlotWeekdaysUpdateActivity.class );
                myintent.putExtra("slotid",strSlotId);
                startActivity(myintent);
                finish();
                rl_edit.setVisibility(View.GONE);

            }
        });
        rl_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_edit.setVisibility(View.GONE);
                showCustomDialog();
            }
        });

        checkboxunava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkboxunava.isChecked()){
                    nStatus=0;
                    checkboxunava.setChecked(true);
                    checkboxava.setChecked(false);
                    doapicallstatuschange();
                }
                else{
                    nStatus=0;
                    checkboxunava.setChecked(true);
                    checkboxava.setChecked(false);
                }
            }
        });

        checkboxava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkboxava.isChecked()){
                    nStatus=1;
                    checkboxava.setChecked(true);
                    checkboxunava.setChecked(false);
                    doapicallstatuschange();
                }
                else{
                    nStatus=0;
                    checkboxava.setChecked(true);
                    checkboxunava.setChecked(false);
                }

            }
        });
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "asd defug hgdgjfg dg f: errorr rr + "+e.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent myintent=new Intent(SlotEditActivity.this, SlotListActivity.class);
        startActivity(myintent);
        finish();
    }

    private  void doapicall(){
        if (strSlotId!=null){
            showLoading();
            SlotDetailPresenter slotDetailPresenter=new SlotDetailPresenter(this,new SlotDetailIntract());
            slotDetailPresenter.validateDetails(strSlotId);
        }

    }

    private  void doapicallstatuschange(){
        try {
            if (strSlotId!=null && nStatus!=5){
                showLoading();
                ProductStatusChangePresenter productStatusChangePresenter=new ProductStatusChangePresenter(this,new ProductStatusChangeIntract());
                productStatusChangePresenter.validateDetails(strSlotId,nStatus+"","slots");
            }
            else
                Toast.makeText(this,"Request Data missing",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "sdgh gfdfghfgdf fdf: errorr rr + "+e.getMessage());
        }
    }




    @Override
    public void slotdetail_success(SlotDetailResponse slotDetailResponse) {
        try{
        hideLoading();
        String strtime="";
        if (slotDetailResponse!=null){
            if (slotDetailResponse.getStatus()){
                cons_main.setVisibility(View.VISIBLE);
                strSlotId=slotDetailResponse.getSlot().getSlotId();
                if (slotDetailResponse.getSlot().getShopName()!=null)
                    tv_shopName.setText(slotDetailResponse.getSlot().getShopName());
                if (slotDetailResponse.getSlot().getFrom()!=null){
                    strtime=slotDetailResponse.getSlot().getFrom();
                }
                if (slotDetailResponse.getSlot().getTo()!=null)
                    strtime=strtime+" - "+slotDetailResponse.getSlot().getTo();
                if (strtime!=null)
                    tv_timevalues.setText(strtime);
                if (slotDetailResponse.getSlot().getActive()!=null && slotDetailResponse.getSlot().getActive().equals("1")){
                    checkboxava.setChecked(true);
                }
                else{
                    checkboxunava.setChecked(true);
                }

                if (slotDetailResponse.getSlot().getWeekdays()!=null && slotDetailResponse.getSlot().getWeekdays().size()>0) {
                    StringBuilder stringBuilder=new StringBuilder();
                    for(int i=0; i<slotDetailResponse.getSlot().getWeekdays().size();i++){
                        stringBuilder.append(slotDetailResponse.getSlot().getWeekdays().get(i));
                        if (i!=slotDetailResponse.getSlot().getWeekdays().size()-1)
                            stringBuilder.append(", ");

                    }
                    if (stringBuilder!=null)
                        tv_openingdays.setText(stringBuilder);
                }
            }
        }

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "jahfdf jfhdfhdfd: errorr rr + "+e.getMessage());
        }

    }

    @Override
    public void slotdetail_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    private void showCustomDialog() {
        try{
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.popup_slotedit, viewGroup, false);
        TextView tv_deletes=dialogView.findViewById(R.id.tv_deletes);
        TextView tv_cancel=dialogView.findViewById(R.id.tv_cancel);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        tv_deletes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_deletes.setBackground(getResources().getDrawable(R.drawable.deletebg));
                alertDialog.dismiss();
                doapicalltodeleteslot();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_cancel.setBackground(getResources().getDrawable(R.drawable.deletebg));
                alertDialog.dismiss();
            }
        });
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "dfghdjfghdfg hjgf: errorr rr + "+e.getMessage());
        }
    }

    @Override
    public void productstatus_success(GeneralResponse generalResponse) {
        hideLoading();
        if (generalResponse!=null){
            if (generalResponse.getStatus()){
                Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void productstatus_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
    }

    private  void doapicalltodeleteslot(){
        if (strSlotId!=null){
            showLoading();
            SlotDeletePresenter slotDeletePresenter=new SlotDeletePresenter(this,new SlotDeleteIntract());
            slotDeletePresenter.validateDetails(strSlotId);
        }
    }

    @Override
    public void slotdelete_success(GeneralResponse generalResponse) {
        hideLoading();
        if (generalResponse!=null){
            if (generalResponse.getStatus()){
                Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
                Intent myintent=new Intent(SlotEditActivity.this, SlotListActivity.class);
                startActivity(myintent);
                finish();
            }
        }

    }

    @Override
    public void slotdelete_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();

    }
}
