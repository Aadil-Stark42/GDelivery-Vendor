package in.vdeliverzvendor.slotlist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.Objects;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.shoplist.OutletListWithSearchActivity;
import in.vdeliverzvendor.slot.CreateSlotActivity;
import in.vdeliverzvendor.slotlist.adapter.SlotListAdapter;
import in.vdeliverzvendor.slotlist.model.SlotListResponse;
import in.vdeliverzvendor.slotlist.mvp.SlotListContract;
import in.vdeliverzvendor.slotlist.mvp.SlotListIntract;
import in.vdeliverzvendor.slotlist.mvp.SlotListPresenter;
import in.vdeliverzvendor.utils.BaseActivity;
import in.vdeliverzvendor.utils.MnxConstant;
import in.vdeliverzvendor.utils.MnxPreferenceManager;

public class SlotListActivity extends BaseActivity  implements SlotListContract {

    RecyclerView rv_list;
    TextView tv_title,tv_no_of_slot;
    ImageView iv_back,iv_createnew,iv_search;
    String strShopId;
    String TAG=SlotListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot);

        try{

        if (MnxPreferenceManager.getString(MnxConstant.SELECTEDSHOPID,null)!=null)
            strShopId=MnxPreferenceManager.getString(MnxConstant.SELECTEDSHOPID,null);

        initcall();

        initclick();

        doapicall();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "craearte : errorr rr + "+e.getMessage());
        }

    }

    private  void initcall(){
        try{
        rv_list=findViewById(R.id.rv_slotlist);
        tv_title=findViewById(R.id.tv_name);
        tv_title.setText("Slots");
        iv_back=findViewById(R.id.iv_back);
        tv_no_of_slot=findViewById(R.id.tv_no_of_slot);
        iv_createnew=findViewById(R.id.iv_createnew);
        iv_search=findViewById(R.id.iv_search);
        iv_search.setVisibility(View.GONE);
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "fd dfdgfasF Gaf : errorr rr + "+e.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*Intent myintent=new Intent(SlotListActivity.this, OutletListWithSearchActivity.class);
        myintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(myintent);
        finish();*/
    }

    private  void initclick(){

        try{

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        iv_createnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent=new Intent(SlotListActivity.this, CreateSlotActivity.class);
                startActivity(myintent);
                finish();

            }
        });

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "initntt ar : errorr rr + "+e.getMessage());
        }
    }

    private  void doapicall(){
        if (strShopId!=null){
            showLoading();
            SlotListPresenter slotListPresenter=new SlotListPresenter(this,new SlotListIntract());
            slotListPresenter.validateDetails(strShopId);
        }
        else
            Toast.makeText(this,"Shop Id Missing",Toast.LENGTH_LONG).show();
    }

    @Override
    public void slotlist_success(SlotListResponse slotListResponse) {
        try{
        hideLoading();
        if (slotListResponse!=null){
            if (slotListResponse.getStatus()){
                if (slotListResponse.getSlots()!=null && slotListResponse.getSlots().size()>0){
                    if (slotListResponse.getSlots().size()==1)
                        tv_no_of_slot.setText(slotListResponse.getSlots().size()+" Slot");
                    else
                        tv_no_of_slot.setText(slotListResponse.getSlots().size()+" Slots");

                    SlotListAdapter slotListAdapter = new SlotListAdapter(this,slotListResponse.getSlots());
                    rv_list.setHasFixedSize(true);
                    rv_list.setLayoutManager(new LinearLayoutManager(this));
                    rv_list.setAdapter(slotListAdapter);
                }
                else
                    Toast.makeText(this,"No Data found",Toast.LENGTH_LONG).show();
            }
        }
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "successs asf asdf af: errorr rr + "+e.getMessage());
        }
    }

    @Override
    public void slotlist_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void dashboard_logout() {
            hideLoading();
            do_logout_and_login_redirect();
    }
}
