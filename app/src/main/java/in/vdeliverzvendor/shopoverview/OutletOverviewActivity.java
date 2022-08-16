package in.vdeliverzvendor.shopoverview;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.Objects;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.editoutlet.OutletEditActivity;
import in.vdeliverzvendor.productview.mvp_staus.ProductStatusChangeIntract;
import in.vdeliverzvendor.productview.mvp_staus.ProductStatusChangePresenter;
import in.vdeliverzvendor.productview.mvp_staus.ProductStausChangeContract;
import in.vdeliverzvendor.shoplist.OutletListWithSearchActivity;
import in.vdeliverzvendor.shopoverview.model.ShopDetailResponse;
import in.vdeliverzvendor.shopoverview.model_product.ShopProductListResponse;
import in.vdeliverzvendor.shopoverview.mvp.ShopDetailContract;
import in.vdeliverzvendor.shopoverview.mvp.ShopDetailIntract;
import in.vdeliverzvendor.shopoverview.mvp.ShopDetailPresenter;
import in.vdeliverzvendor.shopoverview.mvp_product.ShopProductContract;
import in.vdeliverzvendor.shopoverview.mvp_product.ShopProductIntract;
import in.vdeliverzvendor.shopoverview.mvp_product.ShopProductPresenter;
import in.vdeliverzvendor.utils.BaseActivity;
import in.vdeliverzvendor.utils.GeneralResponse;
import in.vdeliverzvendor.utils.MnxConstant;
import in.vdeliverzvendor.utils.MnxPreferenceManager;

public class OutletOverviewActivity extends BaseActivity implements ShopDetailContract, ShopProductContract,
        ProductStausChangeContract {
    RecyclerView rv_list;
    ImageView iv_back,iv_shopimage,iv_menu;
    TextView tv_outletrefnum,tv_shopname,tv_vendorname,tv_mobilenum,tv_status,tv_address,
            tv_availabledays,tv_outlettimings,tv_vendoremail,tv_title,tv_viewall;
    String strshopId,strAddress,strtime;
    ConstraintLayout cons_main;
    CheckBox checkboxunava,checkbox_active;
    int nStatus=5;
    public static  boolean isUpdated=false;
    RelativeLayout rl_edit,rl_edits;
    String TAG= OutletOverviewActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlet_overview);

        try{

        if (getIntent().getStringExtra("ShopId")!=null)
            strshopId=getIntent().getStringExtra("ShopId");

        initcall();
        initclick();
        doapicall();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "caretet: errorr rr + "+e.getMessage());
        }

    }

    private  void initcall(){
        try{
        cons_main=findViewById(R.id.cons_main);
        iv_back=findViewById(R.id.iv_back);
        rv_list=findViewById(R.id.rv_productlist);
        iv_shopimage=findViewById(R.id.iv_shopimage);
        tv_outletrefnum=findViewById(R.id.tv_outletrefnum);
        tv_shopname=findViewById(R.id.tv_shopname);
        tv_vendorname=findViewById(R.id.tv_vendorname);
        tv_mobilenum=findViewById(R.id.tv_mobilenum);
        tv_status=findViewById(R.id.tv_status);
        tv_address=findViewById(R.id.tv_address);
        tv_availabledays=findViewById(R.id.tv_availabledays);
        tv_outlettimings=findViewById(R.id.tv_outlettimings);
        tv_vendoremail=findViewById(R.id.tv_vendoremail);
        cons_main.setVisibility(View.GONE);
        tv_title=findViewById(R.id.tv_title);
        checkbox_active=findViewById(R.id.checkboxactive);
        checkboxunava=findViewById(R.id.checkboxclosed);
        tv_viewall=findViewById(R.id.tv_viewall);

        iv_menu=findViewById(R.id.iv_menu);
        rl_edit=findViewById(R.id.rl_edit);
        rl_edits=findViewById(R.id.rl_edits);
        tv_viewall.setVisibility(View.GONE);
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "ininiynyi: errorr rr + "+e.getMessage());
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
            Log.d(TAG, "dasdsad: errorr rr + "+e.getMessage());
        }

    }

    private void callnextscreen(){
        try {
            Intent myintent = new Intent(OutletOverviewActivity.this, OutletListWithSearchActivity.class);
            myintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(myintent);
            finish();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "dasdasdasasfagfwg: errorr rr + "+e.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        callnextscreen();
    }

    private  void initclick(){
        try{
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callnextscreen();
            }
        });

        rl_edits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_edit.setVisibility(View.GONE);
                Intent myintent=new Intent(OutletOverviewActivity.this, OutletEditActivity.class);
                myintent.putExtra("ShopId",strshopId);
                startActivity(myintent);

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


        checkboxunava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkboxunava.isChecked()){
                    nStatus=0;
                    checkboxunava.setChecked(true);
                    checkbox_active.setChecked(false);
                    doapicallstatuschange();
                }
                else{
                    nStatus=0;
                    checkboxunava.setChecked(true);
                    checkbox_active.setChecked(false);
                }


            }
        });

        checkbox_active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkbox_active.isChecked()){
                    nStatus=1;
                    checkbox_active.setChecked(true);
                    checkboxunava.setChecked(false);
                    doapicallstatuschange();
                }
                else{
                    nStatus=0;
                    checkbox_active.setChecked(true);
                    checkboxunava.setChecked(false);
                }

            }
        });

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "asdvadvsadsd: errorr rr + "+e.getMessage());
        }

    }
    private  void doapicallstatuschange(){
        try {
            if (strshopId!=null && nStatus!=5){
                showLoading();
                ProductStatusChangePresenter productStatusChangePresenter=new ProductStatusChangePresenter(this,new ProductStatusChangeIntract());
                productStatusChangePresenter.validateDetails(strshopId,nStatus+"","shops");
            }
            else
                Toast.makeText(this,"Request Data missing",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "dcaddadasd: errorr rr + "+e.getMessage());
        }
    }

    @Override
    public void shopdetail_success(ShopDetailResponse shopDetailResponse) {
        try {
            hideLoading();
            if (shopDetailResponse!=null){
                if (shopDetailResponse.getStatus()){
                    cons_main.setVisibility(View.VISIBLE);
                    if (shopDetailResponse.getShop().getName()!=null) {
                        tv_shopname.setText(shopDetailResponse.getShop().getName());
                        tv_title.setText(shopDetailResponse.getShop().getName());
                    }
                    if (shopDetailResponse.getShop().getUsername()!=null)
                        tv_vendorname.setText(shopDetailResponse.getShop().getUsername());
                    if (shopDetailResponse.getShop().getEmail()!=null)
                        tv_vendoremail.setText(shopDetailResponse.getShop().getEmail());
                    if (shopDetailResponse.getShop().getVendorMobile()!=null)
                        tv_mobilenum.setText(shopDetailResponse.getShop().getVendorMobile());
                    if (shopDetailResponse.getReferralPrefix()!=null)
                        tv_outletrefnum.setText(shopDetailResponse.getReferralPrefix()+""+strshopId);
                    if (shopDetailResponse.getShop().getStreet()!=null)
                        strAddress=shopDetailResponse.getShop().getStreet();
                    if (shopDetailResponse.getShop().getArea()!=null)
                        strAddress=strAddress+", "+shopDetailResponse.getShop().getArea();
                    if (shopDetailResponse.getShop().getCity()!=null)
                        strAddress=strAddress+", "+shopDetailResponse.getShop().getCity();
                    if (strAddress!=null)
                         tv_address.setText(strAddress);
                    if (shopDetailResponse.getShop().getWeekdays()!=null && shopDetailResponse.getShop().getWeekdays().size()>0){
                        StringBuilder stringBuilder=new StringBuilder();
                        for(int i=0; i<shopDetailResponse.getShop().getWeekdays().size();i++){
                            stringBuilder.append(shopDetailResponse.getShop().getWeekdays().get(i));
                            if (i!=shopDetailResponse.getShop().getWeekdays().size()-1)
                                stringBuilder.append(", ");

                        }
                        if (stringBuilder!=null)
                        tv_availabledays.setText(stringBuilder);
                    }

                    if (shopDetailResponse.getShop().getOpened()!=null){
                        if (shopDetailResponse.getShop().getOpened().equals("1")){
                            tv_status.setText("Opened");
                            checkbox_active.setChecked(true);
                        }
                        else{
                            checkboxunava.setChecked(true);
                            tv_status.setText("Closed");
                            tv_status.setTextColor(getResources().getColor(R.color.red));
                        }
                    }
                    if (shopDetailResponse.getShop().getOpensAt()!=null)
                        strtime=shopDetailResponse.getShop().getOpensAt();
                    if (shopDetailResponse.getShop().getClosesAt()!=null)
                        strtime=strtime+" - "+shopDetailResponse.getShop().getClosesAt();
                    if (strtime!=null)
                        tv_outlettimings.setText(strtime);

                    if (shopDetailResponse.getShop().getImage()!=null){
                        Glide.with(this)
                                .load(Uri.parse(shopDetailResponse.getShop().getImage()))
                                .fitCenter()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(iv_shopimage);
                    }
                    if (strshopId!=null){
                        showLoading();
                        ShopProductPresenter shopProductPresenter=new ShopProductPresenter(this,new ShopProductIntract());
                        shopProductPresenter.validateDetails(strshopId);
                    }

                }
                else
                    Toast.makeText(this,shopDetailResponse.getMessage(),Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(this,shopDetailResponse.getMessage(),Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "assadgdcxvc dfdf: errorr rr + "+e.getMessage());
        }

    }

    @Override
    public void shopdetail_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();

    }

    @Override
    public void shopproductlist_success(ShopProductListResponse shopProductListResponse) {
        try{
        hideLoading();
        if (shopProductListResponse!=null){
            if (shopProductListResponse.getStatus()){
                if (shopProductListResponse.getShop().size()>0) {
                    ProductListAdapter productListAdapter = new ProductListAdapter(this, shopProductListResponse.getShop());
                    rv_list.setHasFixedSize(true);
                    rv_list.setLayoutManager(new LinearLayoutManager(this));
                    rv_list.setAdapter(productListAdapter);
                }
            }
            else
                Toast.makeText(this,shopProductListResponse.getMessage(),Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,shopProductListResponse.getMessage(),Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "sd dhg d : errorr rr + "+e.getMessage());
        }
    }

    @Override
    public void shopproductlist_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void productstatus_success(GeneralResponse generalResponse) {
        hideLoading();
        if (generalResponse!=null){
            if (generalResponse.getStatus()){
                isUpdated=true;
                Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
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

    @Override
    protected void onResume() {
        super.onResume();
        if (OutletEditActivity.isUpdated){
            doapicall();
            OutletEditActivity.isUpdated=false;
        }
        if (MnxPreferenceManager.getBoolean(MnxConstant.IsUpdatedProducts,false))
            MnxPreferenceManager.setBoolean(MnxConstant.IsUpdatedProducts,false);
            if (strshopId!=null){
                showLoading();
                ShopProductPresenter shopProductPresenter=new ShopProductPresenter(this,new ShopProductIntract());
                shopProductPresenter.validateDetails(strshopId);
            }

    }
}
