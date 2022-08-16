package in.vdeliverzvendor.addnewproduct;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import in.vdeliverzvendor.addnewproduct.model_add.AddProductResponse;
import in.vdeliverzvendor.addnewproduct.model_get.Category;
import in.vdeliverzvendor.addnewproduct.model_get.Cuisine;
import in.vdeliverzvendor.addnewproduct.model_get.GetProductResponse;
import in.vdeliverzvendor.addnewproduct.model_get.Shop;
import in.vdeliverzvendor.addnewproduct.model_get.SubCategory;
import in.vdeliverzvendor.addnewproduct.mvp_add.AddProductContract;
import in.vdeliverzvendor.addnewproduct.mvp_add.AddProductIntract;
import in.vdeliverzvendor.addnewproduct.mvp_add.AddProductPresenter;
import in.vdeliverzvendor.addnewproduct.mvp_get.GetProductContract;
import in.vdeliverzvendor.addnewproduct.mvp_get.GetProductIntract;
import in.vdeliverzvendor.addnewproduct.mvp_get.GetProductPresenter;
import in.vdeliverzvendor.addvariants.AddvariantsActivity;
import in.vdeliverzvendor.utils.BaseActivity;

public class AddNewProductDetails extends BaseActivity implements GetProductContract, AddProductContract {

    TextView tv_productstocks;
    ImageView iv_back,iv_productimg;
    Spinner shopcat,shopsspinner,productspinner,variety_spinner,cuisinespinner;
    EditText ed_producname,ed_desc;
    String strShopId,strCatId,strPdCatId,strCuisineId,strVarId;
    int file_chosen_mode=100;
    File file;
    String TAG=AddNewProductDetails.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewproduct);

        try {
            initcall();
            initclick();
            doapicallforlist();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "cafdsfdf df: errorr rr + "+e.getMessage());
        }
    }

    private  void initcall(){
        try {
            iv_back = findViewById(R.id.iv_back);
            tv_productstocks = findViewById(R.id.tv_productstocks);
            iv_productimg = findViewById(R.id.iv_cam);
            shopcat = findViewById(R.id.shopcatsp);
            shopsspinner = findViewById(R.id.shopsp);
            productspinner = findViewById(R.id.spinneproductcat);
            variety_spinner = findViewById(R.id.spinner);
            cuisinespinner = findViewById(R.id.spinnercus);
            ed_desc = findViewById(R.id.ed_desc);
            ed_producname = findViewById(R.id.ed_producname);
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "adsdsadasd df: errorr rr + "+e.getMessage());
        }
    }

    private  void initclick(){
        try{
        tv_productstocks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doapicallforaddproduct();
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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
                        .start(AddNewProductDetails.this);
            }
        });

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "susdjsndjnsdj df: errorr rr + "+e.getMessage());
        }
    }

    private  void doapicallforlist(){
        showLoading();
        GetProductPresenter getProductPresenter=new GetProductPresenter(this,new GetProductIntract());
        getProductPresenter.validateDetails();
    }

    @Override
    public void getproduct_success(GetProductResponse getProductResponse) {
        try {
            hideLoading();
            if (getProductResponse!=null){
                if (getProductResponse.getStatus()){
                    loadvarietyspinner();
                    if (getProductResponse.getCuisines()!=null && getProductResponse.getCuisines().size()>0){
                        loadcuisinespinner(getProductResponse.getCuisines());
                    }
                    if (getProductResponse.getCategories()!=null && getProductResponse.getCategories().size()>0){
                        loadshopcatspinner(getProductResponse.getCategories());
                    }

                    if (getProductResponse.getSubCategories()!=null && getProductResponse.getSubCategories().size()>0){
                        loadproductcat(getProductResponse.getSubCategories());
                    }

                    if (getProductResponse.getShops()!=null && getProductResponse.getShops().size()>0){
                        loadshopsspinner(getProductResponse.getShops());
                    }
                }
                else
                    Toast.makeText(this,getProductResponse.getMessage(),Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(this,getProductResponse.getMessage(),Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "successssssss df: errorr rr + "+e.getMessage());
        }
    }

    @Override
    public void getproduct_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
    }

    private  void loadvarietyspinner(){
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
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "sadjhsdjsdhjsdhj df: errorr rr + "+e.getMessage());
        }

    }

    private  void loadshopsspinner(List<Shop> shopList){

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

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "sadghjsdghjsgd df: errorr rr + "+e.getMessage());
        }

    }

    private  void loadcuisinespinner(List<Cuisine> cuisineList){

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

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "adshgsdshgdvsd df: errorr rr + "+e.getMessage());
        }

    }

    private  void loadproductcat(List<SubCategory> productcatlist){

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

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "asvsdvghsdvhsd df: errorr rr + "+e.getMessage());
        }


    }

    private  void loadshopcatspinner(List<Category> categoryList){
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

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "dxcfgvbjhk df: errorr rr + "+e.getMessage());
        }

    }

    private  void doapicallforaddproduct(){
        try {
            String strname = ed_producname.getText().toString();
            String strdesc = ed_desc.getText().toString();
            if (strShopId!=null){
                if (strCatId!=null){
                    if (strPdCatId!=null){
                        if (strname!=null && (!strname.isEmpty())){
                            if (strVarId!=null){
                                if (strdesc!=null && (!(strdesc.isEmpty()))){
                                    if (file!=null) {
                                        showLoading();
                                        AddProductPresenter addProductPresenter = new AddProductPresenter(this, new AddProductIntract());
                                        addProductPresenter.validateDetails(strShopId, strCatId, strPdCatId, strname, strdesc, strCuisineId, strVarId, file);
                                    }
                                    else
                                        Toast.makeText(this,"Add Product Image",Toast.LENGTH_LONG).show();
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
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "klllllhdsfg df: errorr rr + "+e.getMessage());
        }

    }

    @Override
    public void addproduct_success(AddProductResponse generalResponse) {
        try{
        hideLoading();
        if (generalResponse!=null){
            if (generalResponse.getStatus()){
                Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
                Intent myintent=new Intent(this,AddvariantsActivity.class);
                myintent.putExtra("ProductId",generalResponse.getProductId());
                startActivity(myintent);

            }
            else
                Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,generalResponse.getMessage(),Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "add aproductasasd df: errorr rr + "+e.getMessage());
        }

    }

    @Override
    public void addproduct_failure(String msg) {
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
}
