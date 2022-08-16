package in.vdeliverzvendor.updateproduct;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.addtoppings.AddToppingsActivity;
import in.vdeliverzvendor.addvariants.AddvariantsActivity;
import in.vdeliverzvendor.productview.model.Category;
import in.vdeliverzvendor.productview.model.Cuisine;
import in.vdeliverzvendor.productview.model.ProductDetailResponse;
import in.vdeliverzvendor.productview.model.Shop;
import in.vdeliverzvendor.productview.model.SubCategory;
import in.vdeliverzvendor.productview.mvp.ProductViewContract;
import in.vdeliverzvendor.productview.mvp.ProductViewIntractor;
import in.vdeliverzvendor.productview.mvp.ProductViewPresenter;
import in.vdeliverzvendor.updateproduct.adapter.ProductStockAdapter;
import in.vdeliverzvendor.updateproduct.adapter.ProductToppingAdapter;
import in.vdeliverzvendor.updateproduct.mvp.UpdateProductContract;
import in.vdeliverzvendor.updateproduct.mvp.UpdateProductIntract;
import in.vdeliverzvendor.updateproduct.mvp.UpdateProductPresenter;
import in.vdeliverzvendor.updateproduct.mvp_deletestock.DeleteStockContract;
import in.vdeliverzvendor.updateproduct.mvp_deletestock.DeleteStockIntract;
import in.vdeliverzvendor.updateproduct.mvp_deletestock.DeleteStockPresenter;
import in.vdeliverzvendor.updateproduct.mvp_deletetopping.DeleteToppingContract;
import in.vdeliverzvendor.updateproduct.mvp_deletetopping.DeleteToppingIntract;
import in.vdeliverzvendor.updateproduct.mvp_deletetopping.DeleteToppingPresenter;
import in.vdeliverzvendor.updatetopping.UpdateToppingActivity;
import in.vdeliverzvendor.updatetstock.UpdateStockDetailActivity;
import in.vdeliverzvendor.utils.BaseActivity;
import in.vdeliverzvendor.utils.GeneralResponse;

public class UpdateProductActivity extends BaseActivity implements ProductViewContract, UpdateProductContract,
        DeleteStockContract, DeleteToppingContract {

    TextView tv_productstocks,tv_titile;
    ImageView iv_back,iv_productimg,iv_menu,iv_addstock,iv_addtopping;
    Spinner shopcat,shopsspinner,productspinner,variety_spinner,cuisinespinner;
    EditText ed_producname,ed_desc;
    String strProdcutId,strCatId,strPdCatId,strCuisineId,strVarId,strShopId;
    int file_chosen_mode=100;
    File file;
    ConstraintLayout cons_main;
    ProductDetailResponse productDetailResponsemain;
    RelativeLayout rl_edit,rl_edits;
    RecyclerView rv_liststock,rv_listtopping;
    public  static  boolean isUpdated=false;

    String TAG=UpdateProductActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        try{

        if (getIntent()!=null){
            if (getIntent().getStringExtra("productid")!=null)
                strProdcutId=getIntent().getStringExtra("productid");
        }

        initcall();

        initcick();

        doapicall();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "dcaddadasd: errorr rr + "+e.getMessage());
        }
    }

    private  void initcall(){
        try{
        iv_back=findViewById(R.id.iv_back);
        tv_productstocks=findViewById(R.id.tv_productstocks);
        iv_productimg=findViewById(R.id.iv_prodcutimg);
        shopcat=findViewById(R.id.shopcatsp);
        shopsspinner=findViewById(R.id.shopsp);
        productspinner=findViewById(R.id.spinneproductcat);
        variety_spinner=findViewById(R.id.spinner);
        cuisinespinner=findViewById(R.id.spinnercus);
        ed_desc=findViewById(R.id.ed_desc);
        ed_producname=findViewById(R.id.ed_producname);
        tv_titile=findViewById(R.id.tv_titile);
        cons_main=findViewById(R.id.cons_main);
        cons_main.setVisibility(View.GONE);
        rl_edits=findViewById(R.id.rl_edits);
        rl_edit=findViewById(R.id.rl_edit);
        iv_menu=findViewById(R.id.iv_menu);
        rv_liststock=findViewById(R.id.rl_productstocklist);
        rv_listtopping=findViewById(R.id.rv_producttoppinglist);
        iv_addstock=findViewById(R.id.iv_addstock);
        iv_addtopping=findViewById(R.id.iv_addtopping);
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "sdsdadsad "+e.getMessage());
        }
    }

    private  void initcick(){
        try{
        iv_productimg.setOnClickListener(new View.OnClickListener() {
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
                        .start(UpdateProductActivity.this);
            }
        });

        rl_edits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateproductapicall();

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
        iv_addstock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myinetnt=new Intent(UpdateProductActivity.this, AddvariantsActivity.class);
                myinetnt.putExtra("ProductId",strProdcutId);
                startActivity(myinetnt);

            }
        });
        iv_addtopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myinetnt=new Intent(UpdateProductActivity.this, AddToppingsActivity.class);
                myinetnt.putExtra("ProductId",strProdcutId);
                startActivity(myinetnt);
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
            Log.d(TAG, "clickckccckckc: errorr rr + "+e.getMessage());
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
            Log.d(TAG, "sodododo dodo: errorr rr + "+e.getMessage());
        }
    }

    private  void loadvarietyspinner(String strSelectedid){
        try{
        String[] country = {"Select Variety","Veg", "Non-Veg"};

        ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinneritem, country);
        aa.setDropDownViewResource(R.layout.spinneritem);
        variety_spinner.setAdapter(aa);

        variety_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String  strvar = String.valueOf(country[position]);
                if (strvar.equals("Veg"))
                    strVarId="1";
                else if(strvar.equals("Non-Veg"))
                    strVarId="0";
                else
                    strVarId=null;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (strSelectedid!=null){
            if (strSelectedid.equals("1"))
                variety_spinner.setSelection(1,true);
            else
                variety_spinner.setSelection(2,true);
        }

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "asdhgdjfjshdsaj: errorr rr + "+e.getMessage());
        }

    }

    private  void loadshopsspinner(String strselectedid,List<Shop> shopList){

        try{

        ArrayList<String> names= new ArrayList<>();
        names.add("Select Shop");

        for (int i=0; i<shopList.size();i++){
            names.add(shopList.get(i).getName());
        }

        ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinneritem, names);
        aa.setDropDownViewResource(R.layout.spinneritem);
        shopsspinner.setAdapter(aa);

        shopsspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String  Namehint = String.valueOf(names.get(position));
                if (Namehint.equals("Select Shop"))
                    strShopId=null;
                else
                    strShopId = String.valueOf(shopList.get((int) parent.getItemIdAtPosition(position)-1).getShopId());
                ((TextView) view).setTextColor(getResources().getColor(R.color.apptextcoloor)); //Change selected text color
                Log.i("TESTIDS","TESTIDS"+strShopId+"-"+Namehint);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        int nPos=0;
        if (strselectedid!=null){
            for (int i=0;i<shopList.size();i++){
                if (strselectedid.equals(shopList.get(i).getShopId())) {
                    nPos = i;
                    break;
                }
            }
            shopsspinner.setSelection(nPos+1,true);

        }
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "asdfsadsad: errorr rr + "+e.getMessage());
        }

    }

    private  void loadcuisinespinner(String strselectedid,List<Cuisine> cuisineList){

        try{

        ArrayList<String> names= new ArrayList<>();
        names.add("Select product Cuisine");

        for (int i=0; i<cuisineList.size();i++){
            names.add(cuisineList.get(i).getName());
        }

        ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinneritem, names);
        aa.setDropDownViewResource(R.layout.spinneritem);
        cuisinespinner.setAdapter(aa);

        cuisinespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String  Namehint =  String.valueOf(names.get(position));
                if (Namehint.equals("Select product Cuisine"))
                    strCuisineId="";
                else
                    strCuisineId = String.valueOf(cuisineList.get((int) parent.getItemIdAtPosition(position)-1).getCuisineId());
                ((TextView) view).setTextColor(getResources().getColor(R.color.apptextcoloor)); //Change selected text color
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        int nPos=0;
        if (strselectedid!=null){
            for (int i=0;i<cuisineList.size();i++){
                if (strselectedid.equals(cuisineList.get(i).getCuisineId())) {
                    nPos = i;
                    break;
                }
            }
            cuisinespinner.setSelection(nPos+1,true);
        }
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "oieiuordffhjdfk: errorr rr + "+e.getMessage());
        }
    }

    private  void loadproductcat(String strSelectedid,List<SubCategory> productcatlist){

        try{

        ArrayList<String> names= new ArrayList<>();
        names.add("Select Product Category");

        for (int i=0; i<productcatlist.size();i++){
            names.add(productcatlist.get(i).getName());
        }

        ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinneritem, names);
        aa.setDropDownViewResource(R.layout.spinneritem);
        productspinner.setAdapter(aa);

        productspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String  Namehint =  String.valueOf(names.get(position));
                if (Namehint.equals("Select Product Category"))
                    strPdCatId=null;
                else
                    strPdCatId = String.valueOf(productcatlist.get((int) parent.getItemIdAtPosition(position)-1).getSubCategoryId());
                ((TextView) view).setTextColor(getResources().getColor(R.color.apptextcoloor)); //Change selected text color
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        int nPos=0;
        if (strSelectedid!=null){
            for (int i=0;i<productcatlist.size();i++){
                if (strSelectedid.equals(productcatlist.get(i).getSubCategoryId())) {
                    nPos = i;
                    break;
                }
            }
            productspinner.setSelection(nPos+1,true);

        }

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "unununun: errorr rr + "+e.getMessage());
        }
    }

    private  void loadshopcatspinner(String strselectedid,List<Category> categoryList){
        try{
        ArrayList<String> names= new ArrayList<>();
        names.add("Select Shop Category");

        for (int i=0; i<categoryList.size();i++){
            names.add(categoryList.get(i).getName());
        }

        ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinneritem, names);
        aa.setDropDownViewResource(R.layout.spinneritem);
        shopcat.setAdapter(aa);

        shopcat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String  Namehint =  String.valueOf(names.get(position));
                if (Namehint.equals("Select Shop Category"))
                    strCatId=null;
                else
                    strCatId = String.valueOf(categoryList.get((int) parent.getItemIdAtPosition(position)-1).getCategoryId());
                ((TextView) view).setTextColor(getResources().getColor(R.color.apptextcoloor)); //Change selected text color
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        int nPos=0;
        if (strselectedid!=null){
            for (int i=0;i<categoryList.size();i++){
                if (strselectedid.equals(categoryList.get(i).getCategoryId())) {
                    nPos = i;
                    break;
                }
            }
            shopcat.setSelection(nPos+1,true);

        }
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "jjdjdjdjd: errorr rr + "+e.getMessage());
        }

    }


    @Override
    public void productview_success(ProductDetailResponse productDetailResponse) {
        try{
        hideLoading();
        if (productDetailResponse!=null) {
            if (productDetailResponse.getStatus()) {
                cons_main.setVisibility(View.VISIBLE);
                productDetailResponsemain = productDetailResponse;
                if (productDetailResponse.getProduct() != null) {
                    if (productDetailResponse.getProduct().getShopId() != null)
                        strShopId = productDetailResponse.getProduct().getShopId();
                    if (productDetailResponse.getProduct().getName() != null) {
                        tv_titile.setText(productDetailResponse.getProduct().getName());
                        ed_producname.setText(productDetailResponse.getProduct().getName());
                    }
                    if (productDetailResponse.getProduct().getImage() != null) {
                        Glide.with(this)
                                .load(Uri.parse(productDetailResponse.getProduct().getImage()))
                                .fitCenter()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(iv_productimg);
                    }
                    if (productDetailResponse.getProduct().getDescription() != null)
                        ed_desc.setText(productDetailResponse.getProduct().getDescription());

                    if (productDetailResponse.getProduct().getCategoryId() != null) {
                        if (productDetailResponse.getCategories() != null && productDetailResponse.getCategories().size() > 0) {
                            loadshopcatspinner(productDetailResponse.getProduct().getCategoryId(),
                                    productDetailResponse.getCategories());
                        }
                    }

                    if (productDetailResponse.getProduct().getShopId() != null) {
                        if (productDetailResponse.getShops() != null && productDetailResponse.getShops().size() > 0) {
                            loadshopsspinner(productDetailResponse.getProduct().getShopId(),
                                    productDetailResponse.getShops());
                        }
                    }

                    if (productDetailResponse.getProduct().getSubCategoryId() != null) {
                        if (productDetailResponse.getSubCategories() != null && productDetailResponse.getSubCategories().size() > 0) {
                            loadproductcat(productDetailResponse.getProduct().getSubCategoryId(), productDetailResponse.getSubCategories());
                        }
                    }

                    if (productDetailResponse.getProduct().getVariety() != null) {
                        loadvarietyspinner(productDetailResponse.getProduct().getVariety());
                    }


                    if (productDetailResponse.getProduct().getCuisineId() != null) {
                        if (productDetailResponse.getCuisines() != null && productDetailResponse.getCuisines().size() > 0) {
                            loadcuisinespinner(productDetailResponse.getProduct().getCuisineId(), productDetailResponse.getCuisines());
                        }
                    } else
                        loadcuisinespinner(productDetailResponse.getProduct().getCuisineId(), productDetailResponse.getCuisines());

                    if (productDetailResponse.getStocks()!=null){
                        if (productDetailResponse.getStocks().size()>0){
                            rv_liststock.setVisibility(View.VISIBLE);
                            ProductStockAdapter productStockAdapter = new ProductStockAdapter(this,productDetailResponse.getStocks(),productDetailResponse.getProduct().getProductId());
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
                            ProductToppingAdapter productToppingAdapter = new ProductToppingAdapter(this,productDetailResponse.getProduct().getProductId(),productDetailResponse.getToppings(),productDetailResponse.getTitles());
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
            } else
                Toast.makeText(this, productDetailResponse.getMessage(), Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,productDetailResponse.getMessage(), Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "sdsdddds: errorr rr + "+e.getMessage());
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

    private  void updateproductapicall(){

        try {
            String strname = ed_producname.getText().toString();
            String strdesc = ed_desc.getText().toString();
            if (strShopId!=null){
                if (strCatId!=null){
                    if (strPdCatId!=null){
                        if (strname!=null && (!strname.isEmpty())){
                            if (strVarId!=null){
                                if (strdesc!=null && (!(strdesc.isEmpty()))){
//                                    if (file!=null) {
                                        if (strProdcutId!=null) {
                                            showLoading();
                                            UpdateProductPresenter updateProductPresenter = new UpdateProductPresenter(this, new UpdateProductIntract());
                                            updateProductPresenter.validateDetails(strShopId, strProdcutId, strCatId, strPdCatId, strname, strdesc, strCuisineId, strVarId, file);
                                        }
                                        else
                                            Toast.makeText(this,"Product Id Missing",Toast.LENGTH_LONG).show();
//                                    }
//                                    else
//                                        Toast.makeText(this,"Kindly Add Product Image",Toast.LENGTH_LONG).show();
                                }
                                else

                                    Toast.makeText(this,"Enter the product desc",Toast.LENGTH_LONG).show();
                            }
                            else
                                Toast.makeText(this,"Select Variety",Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(this,"Enter the product name",Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(this,"Select Product Category",Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(this,"Select Shop Category",Toast.LENGTH_LONG).show();

            }
            else
                Toast.makeText(this,"Select Shop",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "asdsd gsgd: errorr rr + "+e.getMessage());
        }

    }

    @Override
    public void updateproduct_success(GeneralResponse generalResponse) {
        try{
        hideLoading();
        if (generalResponse!=null){
            if (generalResponse.getStatus()){
                isUpdated=true;
                rl_edit.setVisibility(View.GONE);
                Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "sdsdddds: errorr rr + "+e.getMessage());
        }

    }

    @Override
    public void updateproduct_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                if(file_chosen_mode==1){
                    Uri path =result.getUri();
                    file = new File(getRealPathFromURI(path));

                    if (path!=null){
                        Glide.with(this)
                                .load(file)
                                .fitCenter()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(iv_productimg);
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

    public   void deletestockapicall(String strstockid){
        if (strstockid!=null){
            showLoading();
            DeleteStockPresenter deleteStockPresenter=new DeleteStockPresenter(this,new DeleteStockIntract());
            deleteStockPresenter.validateDetails(strstockid);
        }
        else
            Toast.makeText(this,"Stcok Id Missing",Toast.LENGTH_LONG).show();

    }

    public   void deletetoppingapicall(String strtoppingid){
        if (strtoppingid!=null){
            showLoading();
            DeleteToppingPresenter deleteToppingPresenter=new DeleteToppingPresenter(this,new DeleteToppingIntract());
            deleteToppingPresenter.validateDetails(strtoppingid);
        }
        else
            Toast.makeText(this,"Stcok Id Missing",Toast.LENGTH_LONG).show();

    }

    @Override
    public void deletestock_success(GeneralResponse generalResponse) {
        hideLoading();
        if (generalResponse!=null){
            if (generalResponse.getStatus()){
                isUpdated=true;
                Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
                doapicall();
            }
            else
                Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void deletestock_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void deletetopping_success(GeneralResponse generalResponse) {
        hideLoading();
        if (generalResponse!=null){
            if (generalResponse.getStatus()){
                isUpdated=true;
                Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
                doapicall();
            }
            else
                Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();

    }

    @Override
    public void deletetopping_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (UpdateStockDetailActivity.isUpdated || AddToppingsActivity.isUpdated || AddvariantsActivity.isUpdated
                || UpdateToppingActivity.isUpdated ||UpdateStockDetailActivity.isUpdated){
            doapicall();
            isUpdated=true;
            UpdateStockDetailActivity.isUpdated=false;
            AddvariantsActivity.isUpdated=false;
            AddToppingsActivity.isUpdated=false;
            UpdateToppingActivity.isUpdated=false;
            UpdateStockDetailActivity.isUpdated=false;
        }
    }
}
