package in.vdeliverzvendor.productview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.Objects;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.dashboard.DashboardActivity;
import in.vdeliverzvendor.productlist.ProductListWithSearchActivity;
import in.vdeliverzvendor.productview.adapter.ProductStockAdapter;
import in.vdeliverzvendor.productview.adapter.ProductToppingAdapter;
import in.vdeliverzvendor.productview.model.ProductDetailResponse;
import in.vdeliverzvendor.productview.mvp.ProductViewContract;
import in.vdeliverzvendor.productview.mvp.ProductViewIntractor;
import in.vdeliverzvendor.productview.mvp.ProductViewPresenter;
import in.vdeliverzvendor.productview.mvp_delete.ProductDeleteContract;
import in.vdeliverzvendor.productview.mvp_delete.ProductDeleteIntract;
import in.vdeliverzvendor.productview.mvp_delete.ProductDeletePresenter;
import in.vdeliverzvendor.productview.mvp_staus.ProductStatusChangeIntract;
import in.vdeliverzvendor.productview.mvp_staus.ProductStatusChangePresenter;
import in.vdeliverzvendor.productview.mvp_staus.ProductStausChangeContract;
import in.vdeliverzvendor.updateproduct.UpdateProductActivity;
import in.vdeliverzvendor.utils.BaseActivity;
import in.vdeliverzvendor.utils.GeneralResponse;
import in.vdeliverzvendor.utils.MnxConstant;
import in.vdeliverzvendor.utils.MnxPreferenceManager;

public class ProductviewActivity extends BaseActivity implements ProductViewContract, ProductDeleteContract, ProductStausChangeContract {

    ImageView iv_back,iv_menu;
    TextView tv_save,tv_titile;
    EditText ed_productname,ed_shoname,ed_shopcat,ed_productcat,ed_variety,ed_desc,ed_cusine;
    RecyclerView rv_liststock,rv_listtopping;
    String strProdcutId;
    ShapeableImageView prodcutimg;
    CheckBox checkboxunava,checkbox_active;
    ConstraintLayout consmain;
    RelativeLayout rl_edit,rl_edits,rl_delete;
    public static  boolean isDeleted=false;
    int nStatus=5;
    String strfrom;
    String TAG=ProductviewActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_edit);

        try {

            initcall();

            if (getIntent() != null) {
                if (getIntent().getStringExtra("ProductId") != null)
                    strProdcutId = getIntent().getStringExtra("ProductId");
            }

            initclick();

            doapicall();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "dasdasdas: errorr rr + "+e.getMessage());
        }
    }

    private  void initcall(){
        try {
            iv_back=findViewById(R.id.iv_back);
            tv_save=findViewById(R.id.tv_save);
            tv_titile=findViewById(R.id.tv_titile);
            consmain=findViewById(R.id.cons_main);
            consmain.setVisibility(View.GONE);

            ed_productname=findViewById(R.id.ed_productname);
            ed_productcat=findViewById(R.id.ed_prodcutcat);
            ed_shoname=findViewById(R.id.ed_shopname);
            ed_shopcat=findViewById(R.id.ed_shopcat);
            ed_desc=findViewById(R.id.ed_desc);
            ed_variety=findViewById(R.id.ed_variety);
            ed_cusine=findViewById(R.id.ed_cusine);
            prodcutimg=findViewById(R.id.iv_shopimage);
            checkbox_active=findViewById(R.id.checkbox_active);
            checkboxunava=findViewById(R.id.checkboxunava);
            iv_menu=findViewById(R.id.iv_menu);
            rl_edit=findViewById(R.id.rl_edit);
            rl_edits=findViewById(R.id.rl_edits);
            rl_delete=findViewById(R.id.rl_delete);

            rv_liststock=findViewById(R.id.rl_productstocklist);
            rv_listtopping=findViewById(R.id.rv_producttoppinglist);

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "sdsddsd  sadsd: errorr rr + "+e.getMessage());
        }
    }


    private  void initclick(){

        try{
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callnextscreen();
            }
        });

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent=new Intent(ProductviewActivity.this, DashboardActivity.class);
                myintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(myintent);
                finish();
            }
        });

        rl_edits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_edit.setVisibility(View.GONE);
                Intent myintent=new Intent(ProductviewActivity.this,UpdateProductActivity.class);
                myintent.putExtra("productid",strProdcutId);
                startActivity(myintent);

            }
        });

        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_edit.setVisibility(View.VISIBLE);
            }
        });

        consmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            Log.d(TAG, "dsd bhjdsb jdbj : errorr rr + "+e.getMessage());
        }
    }

    private  void doapicallstatuschange(){
        try {
            if (strProdcutId!=null && nStatus!=5){
                showLoading();
                ProductStatusChangePresenter productStatusChangePresenter=new ProductStatusChangePresenter(this,new ProductStatusChangeIntract());
                productStatusChangePresenter.validateDetails(strProdcutId,nStatus+"","products");
            }
            else
                Toast.makeText(this,"Request Data missing",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, " b bc bvcjbv : errorr rr + "+e.getMessage());
        }
    }

    private  void doapicall(){
        try {
            if (strProdcutId!=null){
                showLoading();
                ProductViewPresenter productViewPresenter=new ProductViewPresenter(this,new ProductViewIntractor());
                productViewPresenter.validateDetails(strProdcutId);
            }
            else
                Toast.makeText(this,"Product Id missing",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "bcvncbvb nbcvnbcbv: errorr rr + "+e.getMessage());
        }
    }

    @Override
    public void productview_success(ProductDetailResponse productDetailResponse) {
        try {
            hideLoading();
            if (productDetailResponse!=null){
                if (productDetailResponse.getStatus()){
                    if (productDetailResponse.getProduct()!=null){
                        if (productDetailResponse.getProduct().getName()!=null){
                            consmain.setVisibility(View.VISIBLE);
                            ed_productname.setText(productDetailResponse.getProduct().getName());
                            tv_titile.setText(productDetailResponse.getProduct().getName());
                        }
                        if (productDetailResponse.getProduct().getSubCategoryName()!=null)
                            ed_productcat.setText(productDetailResponse.getProduct().getSubCategoryName());
                        if (productDetailResponse.getProduct().getShopName()!=null)
                            ed_shoname.setText(productDetailResponse.getProduct().getShopName());
                        if (productDetailResponse.getProduct().getCategoryName()!=null)
                            ed_shopcat.setText(productDetailResponse.getProduct().getCategoryName());
                        if (productDetailResponse.getProduct().getDescription()!=null)
                            ed_desc.setText(productDetailResponse.getProduct().getDescription());

                        if (productDetailResponse.getProduct().getActive()!=null){
                            if (productDetailResponse.getProduct().getActive().equals("1")) {
                                checkbox_active.setChecked(true);
                                checkboxunava.setChecked(false);
                            }
                            else {
                                checkboxunava.setChecked(true);
                                checkbox_active.setChecked(false);
                            }
                        }
                        String strcuisinename="";
                        if (productDetailResponse.getProduct().getCuisineId()!=null){
                            for (int i=0;i<productDetailResponse.getCuisines().size();i++){
                                String strid=productDetailResponse.getCuisines().get(i).getCuisineId();
                                if (productDetailResponse.getProduct().getCuisineId().equals(strid)) {
                                    strcuisinename =productDetailResponse.getCuisines().get(i).getName();
                                    break;
                                }
                            }
                        }

                        if (strcuisinename!=null) {
                            ed_cusine.setText(strcuisinename);
                        }

                        if (productDetailResponse.getProduct().getImage()!=null){
                            Glide.with(this)
                                    .load(Uri.parse(productDetailResponse.getProduct().getImage()))
                                    .fitCenter()
                                    .placeholder(R.drawable.products_placeholder)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(prodcutimg);
                        }

                        if (productDetailResponse.getProduct().getVariety()!=null){
                            if (productDetailResponse.getProduct().getVariety().equals("1"))
                                ed_variety.setText("Veg");
                            else
                                ed_variety.setText("Non veg");
                        }

                        if (productDetailResponse.getStocks()!=null){
                            if (productDetailResponse.getStocks().size()>0){
                                rv_liststock.setVisibility(View.VISIBLE);
                                ProductStockAdapter productStockAdapter = new ProductStockAdapter(this,productDetailResponse.getStocks());
                                rv_liststock.setHasFixedSize(true);
                                rv_liststock.setLayoutManager(new LinearLayoutManager(this));
                                rv_liststock.setAdapter(productStockAdapter);

                            }
                            else
                                rv_liststock.setVisibility(View.GONE);
                        }
                        else
                            rv_liststock.setVisibility(View.GONE);

                        if (productDetailResponse.getToppings()!=null){
                            if (productDetailResponse.getToppings().size()>0){
                                rv_listtopping.setVisibility(View.VISIBLE);
                                ProductToppingAdapter productToppingAdapter = new ProductToppingAdapter(this,productDetailResponse.getToppings(),productDetailResponse.getTitles());
                                rv_listtopping.setHasFixedSize(true);
                                rv_listtopping.setLayoutManager(new LinearLayoutManager(this));
                                rv_listtopping.setAdapter(productToppingAdapter);

                            }
                            else
                                rv_listtopping.setVisibility(View.GONE);
                        }
                        else
                            rv_listtopping.setVisibility(View.GONE);
                    }

                }
            }
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "fhgfv hgdf: errorr rr + "+e.getMessage());
        }

    }

    @Override
    public void productview_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();

    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        callnextscreen();
    }

    private  void callnextscreen(){
        try{
        if (isDeleted) {
            Log.i("TESTBACK","TESTBACK"+"11"+ProductListWithSearchActivity.isSearchOpen);
            if (MnxPreferenceManager.getBoolean(MnxConstant.IsOutletViewFrom,false)){
                MnxPreferenceManager.setBoolean(MnxConstant.IsOutletViewFrom,false);
                MnxPreferenceManager.setBoolean(MnxConstant.IsUpdatedProducts,true);
                finish();
            }
            else{
                Log.i("TESTBACK","TESTBACK"+"22"+ProductListWithSearchActivity.isSearchOpen);
                Intent myintent = new Intent(ProductviewActivity.this, ProductListWithSearchActivity.class);
                ProductListWithSearchActivity.isSearchOpen=false;
                myintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(myintent);
                finish();
            }

        }
        else{
            Log.i("TESTBACK","TESTBACK"+"33"+ProductListWithSearchActivity.isSearchOpen);
            if (ProductListWithSearchActivity.isSearchOpen){
                ProductviewActivity.isDeleted=true;
                Intent myintent = new Intent(ProductviewActivity.this, ProductListWithSearchActivity.class);
                ProductListWithSearchActivity.isSearchOpen=false;
                myintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(myintent);
                finish();
            }
            else{
                finish();
            }
        }

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "call nextrr  scjdbf: errorr rr + "+e.getMessage());
        }

    }

    @Override
    public void productdelete_success(GeneralResponse generalResponse) {
        try{
        hideLoading();
        if (generalResponse!=null){
            if (generalResponse.getStatus()){
                if (MnxPreferenceManager.getBoolean(MnxConstant.IsOutletViewFrom,false)){
                    MnxPreferenceManager.setBoolean(MnxConstant.IsOutletViewFrom,false);
                    MnxPreferenceManager.setBoolean(MnxConstant.IsUpdatedProducts,true);
                    finish();
                }
                else {
                    isDeleted = true;
                    Toast.makeText(this, generalResponse.getMessage(), Toast.LENGTH_LONG).show();
                    Intent myintent = new Intent(ProductviewActivity.this, ProductListWithSearchActivity.class);
                    myintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(myintent);
                    finish();
                }
            }
            else
                Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "sudkdsnfj b: errorr rr + "+e.getMessage());
        }
    }

    @Override
    public void productdelete_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    private void showCustomDialog() {

        try{

        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.popup_deleteproduct, viewGroup, false);
        TextView tv_deletes=dialogView.findViewById(R.id.tv_deletes);
        TextView tv_cancel=dialogView.findViewById(R.id.tv_cancel);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        tv_deletes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (strProdcutId!=null){
                    tv_deletes.setBackground(getResources().getDrawable(R.drawable.deletebg));
                    tv_cancel.setBackground(getResources().getDrawable(R.drawable.login_view_bg));
                    showLoading();
                    ProductDeletePresenter productDeletePresenter=new ProductDeletePresenter(ProductviewActivity.this,new ProductDeleteIntract());
                    productDeletePresenter.validateDetails(strProdcutId);
                    alertDialog.dismiss();
                }

            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "adb  df vf vdf : errorr rr + "+e.getMessage());
        }
    }

    @Override
    public void productstatus_success(GeneralResponse generalResponse) {
        hideLoading();
        if (generalResponse!=null){
            if (generalResponse.getStatus()){
                isDeleted=true;
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
    protected void onResume() {
        super.onResume();
        if (UpdateProductActivity.isUpdated){
            isDeleted=true;
            doapicall();
            UpdateProductActivity.isUpdated=false;
        }
    }
}
