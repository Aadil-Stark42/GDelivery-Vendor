package in.vdeliverzvendor.productcategory;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.List;
import java.util.Objects;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.productcategory.adapter.ProductCatListAdapter;
import in.vdeliverzvendor.productcategory.model_catlist.Category;
import in.vdeliverzvendor.productcategory.model_catlist.ProductCatListResponse;
import in.vdeliverzvendor.productcategory.mvp_catlist.ProductCatListContract;
import in.vdeliverzvendor.productcategory.mvp_catlist.ProductCatListIntract;
import in.vdeliverzvendor.productcategory.mvp_catlist.ProductCatListPresenter;
import in.vdeliverzvendor.shoplist.OutletListWithSearchActivity;
import in.vdeliverzvendor.utils.BaseActivity;
import in.vdeliverzvendor.utils.MnxConstant;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import in.vdeliverzvendor.utils.PaginationScrollListener;

public class ProductCatListActivity extends BaseActivity implements ProductCatListContract {

    ConstraintLayout toolbar, searchtollbar,cons_main;
    ImageView iv_search,iv_close,iv_back,newshops,iv_insideback;
    RecyclerView rv_list;
    TextView tv_noofshops,tv_title;
    LinearLayoutManager linearLayoutManager;
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 5;
    private int currentPage = PAGE_START;
    boolean isFirstpage=false;
    ProductCatListAdapter adapter;
    EditText ed_search;
    String strSearchText,strShopId;
    boolean isSearchOpened=false;
    String TAG=ProductCatListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_cat_list);

        try{
        if (MnxPreferenceManager.getString(MnxConstant.SELECTEDSHOPID,null)!=null)
            strShopId=MnxPreferenceManager.getString(MnxConstant.SELECTEDSHOPID,null);

        initcall();

        adapter = new ProductCatListAdapter(this);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_list.setLayoutManager(linearLayoutManager);
        rv_list.setItemAnimator(new DefaultItemAnimator());
        rv_list.setAdapter(adapter);

        initclick();

        doapicall();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "asdasdafafagfwr  gewg df: errorr rr + "+e.getMessage());
        }
    }

    private  void initcall(){
        try{
        toolbar = findViewById(R.id.cons_toolbar);
        iv_search=findViewById(R.id.iv_search);
        iv_close=findViewById(R.id.iv_close);
        iv_back=findViewById(R.id.iv_back);
        rv_list=findViewById(R.id.rv_outletlist);
        cons_main=findViewById(R.id.cons_main);
        cons_main.setVisibility(View.GONE);
        tv_noofshops=findViewById(R.id.tv_no_of_shops);
        newshops=findViewById(R.id.iv_newproduct);
        ed_search=findViewById(R.id.ed_search);
        iv_insideback=findViewById(R.id.iv_insideback);
        tv_title=findViewById(R.id.tv_name);
        tv_title.setText("Product Category");
        iv_search.setVisibility(View.GONE);
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "rdctfgvyhbujk56655656fddf df: errorr rr + "+e.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*Intent myintent=new Intent(ProductCatListActivity.this, OutletListWithSearchActivity.class);
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


        rv_list.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                            loadnextpage(currentPage + "");
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        newshops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent=new Intent(ProductCatListActivity.this,CreateProductCategoryActivity.class);
                startActivity(myintent);
                finish();
            }
        });
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "fdjdfhudfhuidfh df: errorr rr + "+e.getMessage());
        }
    }
    private  void doapicall(){
        if (strShopId!=null){
            showLoading();
            ProductCatListPresenter productCatListPresenter=new ProductCatListPresenter(this,new ProductCatListIntract());
            productCatListPresenter.validateDetails(strShopId,"1");
        }
    }

    private  void loadnextpage(String strpage){
        isFirstpage=false;
        if (strpage!=null) {
            if (currentPage<=TOTAL_PAGES) {
                ProductCatListPresenter productCatListPresenter=new ProductCatListPresenter(this,new  ProductCatListIntract());
                productCatListPresenter.validateDetails(strShopId,strpage);
            }
        }
    }


    @Override
    public void productcatlist_success(ProductCatListResponse productCatListResponse) {
        try{
        hideLoading();
        if (productCatListResponse!=null){
            if (productCatListResponse.getStatus()){
                TOTAL_PAGES=productCatListResponse.getPagination().getTotalPages();
                cons_main.setVisibility(View.VISIBLE);
                rv_list.setVisibility(View.GONE);
                if (productCatListResponse.getPagination().getTotal()!=null) {
                    if (productCatListResponse.getPagination().getTotal().equals("1"))
                        tv_noofshops.setText(productCatListResponse.getPagination().getTotal() + " Product Category");
                    else
                        tv_noofshops.setText(productCatListResponse.getPagination().getTotal() + " Product Category");

                    if (productCatListResponse.getCategories() != null)
                        if (productCatListResponse.getCategories() != null && productCatListResponse.getCategories().size() > 0) {
                            rv_list.setVisibility(View.VISIBLE);
                            if (isFirstpage){
                                adapter.addAll(productCatListResponse.getCategories());

                                if (currentPage <= TOTAL_PAGES)
                                    adapter.addLoadingFooter(TOTAL_PAGES,productCatListResponse.getPagination().getTotal());
                                else isLastPage = true;
                            }
                            else{
                                adapter.removeLoadingFooter();
                                isLoading = false;

                                List<Category> results = productCatListResponse.getCategories();
                                adapter.addAll(results);

                                if (currentPage != TOTAL_PAGES)
                                    adapter.addLoadingFooter(TOTAL_PAGES,productCatListResponse.getPagination().getTotal());
                                else
                                    isLastPage = true;

                            }
                        }
                }

            }
            else
                Toast.makeText(this,productCatListResponse.getMessage(),Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,productCatListResponse.getMessage(),Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "dhjskdhjkdsh df: errorr rr + "+e.getMessage());
        }

    }

    @Override
    public void productcatlist_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void dashboard_logout() {
            hideLoading();
            do_logout_and_login_redirect();
    }
}
