package in.vdeliverzvendor.productlist;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.List;
import java.util.Objects;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.addnewproductwithstock.AddToppingWithStockActivity;
import in.vdeliverzvendor.addnewproductwithstock.AddVariantWIthStockActivity;
import in.vdeliverzvendor.dashboard.DashboardActivity;
import in.vdeliverzvendor.productcategory.ProductCatListActivity;
import in.vdeliverzvendor.productcategory.mvp_productlist.CatProductListContract;
import in.vdeliverzvendor.productcategory.mvp_productlist.CatProductListIntract;
import in.vdeliverzvendor.productcategory.mvp_productlist.CatProductListPresenter;
import in.vdeliverzvendor.productlist.adapter.ProductListpageAdapter;
import in.vdeliverzvendor.productlist.model.Datum;
import in.vdeliverzvendor.productlist.model.ProductListResponse;
import in.vdeliverzvendor.productlist.mvp.ProductListContract;
import in.vdeliverzvendor.productlist.mvp.ProductListIntract;
import in.vdeliverzvendor.productlist.mvp.ProductListPresenter;
import in.vdeliverzvendor.productsearch.mvp.ProductSearchContract;
import in.vdeliverzvendor.productsearch.mvp.ProductSearchIntract;
import in.vdeliverzvendor.productsearch.mvp.ProductSearchPresenter;
import in.vdeliverzvendor.productview.ProductviewActivity;
import in.vdeliverzvendor.addnewproductwithstock.NewProductwithStockActivity;
import in.vdeliverzvendor.utils.BaseActivity;
import in.vdeliverzvendor.utils.MnxConstant;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import in.vdeliverzvendor.utils.PaginationScrollListener;

public class ProductListWithSearchActivity extends BaseActivity  implements ProductListContract, ProductSearchContract, CatProductListContract {

    ConstraintLayout toolbar, searchtollbar,cons_main;
    ImageView iv_search,iv_close,iv_back,iv_newproduct,iv_insideback;
    RecyclerView rv_list;
    TextView tv_name,tv_noofproduct;
    EditText ed_search;
    ProductListpageAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 5;
    private int currentPage = PAGE_START;
    boolean isFirstpage=false;
    String strfrom,strSearchText;
    boolean isSearchOpened=false;
    public  static  boolean isSearchOpen=false;
    String TAG=ProductListWithSearchActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search);

        try{

        initcall();

        adapter = new ProductListpageAdapter(this);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_list.setLayoutManager(linearLayoutManager);

        rv_list.setItemAnimator(new DefaultItemAnimator());

        rv_list.setAdapter(adapter);

        initclick();


        if (MnxPreferenceManager.getString(MnxConstant.ISFROM,null).equals("CATLIST")){
             doapicallforcatproductlist();
        }
        else{
            doapicall();
        }

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "carere eret: errorr rr + "+e.getMessage());
        }
    }

    private  void initcall(){
        try{
        toolbar = findViewById(R.id.cons_toolbar);
        iv_search=findViewById(R.id.iv_search);
        iv_close=findViewById(R.id.iv_close);
        iv_insideback=findViewById(R.id.iv_insideback);
        tv_name=findViewById(R.id.tv_name);
        tv_name.setText("Products");
        iv_back=findViewById(R.id.iv_back);
        ed_search=findViewById(R.id.ed_search);
        ed_search.setHint("Search Products");
        iv_newproduct=findViewById(R.id.iv_newproduct);
        rv_list=findViewById(R.id.rv_outletlist);
        tv_noofproduct=findViewById(R.id.tv_no_of_shops);
        cons_main=findViewById(R.id.cons_main);
        cons_main.setVisibility(View.GONE);
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "cacbhjc: errorr rr + "+e.getMessage());
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        //replaces the default 'Back' button action
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            if (isSearchOpen){
                adapter.clearall();
                TOTAL_PAGES=5;
                isSearchOpened=false;
                isFirstpage=false;
                currentPage=1;
                isLoading=false;
                isLastPage=false;
                iv_newproduct.setVisibility(View.VISIBLE);
                ed_search.setText("");
                isSearchOpen=false;
                searchtollbar.setVisibility(View.GONE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    circleReveal(R.id.cons_toolbar,1,true,true);
                else
                    toolbar.setVisibility(View.GONE);
                if (MnxPreferenceManager.getString(MnxConstant.ISFROM,null).equals("CATLIST")){
                    doapicallforcatproductlist();
                }
                else{
                    doapicall();
                }
            }
            else {
                Log.i("TESTCALL","TESTCALL"+"2");
                callnextscreen();
            }

        }
        return true;
    }



    private  void callnextscreen(){
        try{
            if (AddVariantWIthStockActivity.isUpdated || AddToppingWithStockActivity.isUpdated
                    || ProductviewActivity.isDeleted) {
                AddToppingWithStockActivity.isUpdated = false;
                AddToppingWithStockActivity.isUpdated = false;
                ProductviewActivity.isDeleted = false;
                if (MnxPreferenceManager.getString(MnxConstant.ISFROM,null).equals("CATLIST")){
                    Intent myintent = new Intent(ProductListWithSearchActivity.this, ProductCatListActivity.class);
                    startActivity(myintent);
                    finish();
                }
                else{
                    Intent myintent = new Intent(ProductListWithSearchActivity.this, DashboardActivity.class);
                    startActivity(myintent);
                    finish();
                }
            }
            else
                finish();

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "asdgd d dghsgd: errorr rr + "+e.getMessage());
        }

    }

    private  void initclick(){
        try{
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AddVariantWIthStockActivity.isUpdated || AddToppingWithStockActivity.isUpdated
                || ProductviewActivity.isDeleted) {
                    callnextscreen();
                }
                else
                    onBackPressed();
            }
        });
        iv_newproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent=new Intent(ProductListWithSearchActivity.this, NewProductwithStockActivity.class);
                startActivity(myintent);
            }
        });

        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TOTAL_PAGES=5;
                iv_newproduct.setVisibility(View.GONE);
                isSearchOpened=true;
                isFirstpage=false;
                currentPage=1;
                isLoading=false;
                isLastPage=false;
                tv_noofproduct.setText("");
                adapter.clearall();
                isSearchOpen=true;
                searchtollbar=findViewById(R.id.cons_searchtoolbar);
                toolbar.setVisibility(View.GONE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    circleReveal(R.id.cons_searchtoolbar,1,true,true);
                else
                    searchtollbar.setVisibility(View.GONE);

            }
        });

        iv_insideback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clearall();
                TOTAL_PAGES=5;
                isSearchOpened=false;
                isFirstpage=false;
                currentPage=1;
                isLoading=false;
                isLastPage=false;
                iv_newproduct.setVisibility(View.VISIBLE);
                ed_search.setText("");
                isSearchOpen=false;
                searchtollbar.setVisibility(View.GONE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    circleReveal(R.id.cons_toolbar,1,true,true);
                else
                    toolbar.setVisibility(View.GONE);
                if (MnxPreferenceManager.getString(MnxConstant.ISFROM,null).equals("CATLIST")){
                    doapicallforcatproductlist();
                }
                else{
                    doapicall();
                }
            }
        });



        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ed_search.setText("");
                adapter.clearall();
                TOTAL_PAGES=5;
                isSearchOpened=false;
                isFirstpage=false;
                currentPage=1;
                isLoading=false;
                isLastPage=false;
                tv_noofproduct.setText("");
                iv_newproduct.setVisibility(View.GONE);
                ed_search.setText("");

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
                        if (isSearchOpened) {
                            do_search_apicall_nextpage(currentPage + "");
                        }
                        else {
                            if (MnxPreferenceManager.getString(MnxConstant.ISFROM,null).equals("CATLIST")){
                                doapicallforcatproductlistnextpage(currentPage+ "");
                            }
                            else {
                                loadnextpage(currentPage + "");
                            }
                        }
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

        ed_search.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    strSearchText=ed_search.getText().toString().trim();

                    do_search_apicall();
                    return true;
                }
                return false;
            }
        });

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "onclcickck: errorr rr + "+e.getMessage());
        }

    }


    private  void do_search_apicall(){
        try {
            if (strSearchText != null && (!strSearchText.isEmpty())) {
                showLoading();
                ProductSearchPresenter productSearchPresenter = new ProductSearchPresenter(this, new ProductSearchIntract());
                productSearchPresenter.validateDetails(strSearchText, "1");
            } else
                Toast.makeText(this, "Enter Seacrh Text", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "do_search: errorr rr + "+e.getMessage());
        }
    }

    private  void do_search_apicall_nextpage(String strpage){
        try{
        isFirstpage=false;
        if (strpage!=null) {
            if (currentPage<=TOTAL_PAGES) {
                ProductSearchPresenter productSearchPresenter=new ProductSearchPresenter(this,new ProductSearchIntract());
                productSearchPresenter.validateDetails(strSearchText,"1");
            }
        }
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "next pageeee : errorr rr + "+e.getMessage());
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void circleReveal(int viewID, int posFromRight, boolean containsOverflow, final boolean isShow)
    {
        final View myView = findViewById(viewID);

        int width=myView.getWidth();

        if(posFromRight>0)
            width-=(posFromRight*getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material))-(getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material)/ 2);
        if(containsOverflow)
            width-=getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material);

        int cx=width;
        int cy=myView.getHeight()/2;

        Animator anim;
        if(isShow)
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0,(float)width);
        else
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, (float)width, 0);

        anim.setDuration((long)220);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(!isShow)
                {
                    super.onAnimationEnd(animation);
                    myView.setVisibility(View.INVISIBLE);
                }
            }
        });

        // make the view visible and start the animation
        if(isShow) {
            myView.setVisibility(View.VISIBLE);
        }

        // start the animation
        anim.start();

    }


    private  void doapicall(){
        showLoading();
        isFirstpage=true;
        ProductListPresenter productListPresenter=new ProductListPresenter(this,new ProductListIntract());
        productListPresenter.validateDetails("1");
    }

    private  void loadnextpage(String strpage){
        isFirstpage=false;
        if (strpage!=null) {
            if (currentPage<=TOTAL_PAGES) {
                ProductListPresenter productListPresenter = new ProductListPresenter(this, new ProductListIntract());
                productListPresenter.validateDetails(strpage);
            }
        }
    }



    @Override
    public void productlist_success(ProductListResponse productListResponse) {

        try{
        hideLoading();
        if (productListResponse!=null){
            if (productListResponse.getStatus()){
                rv_list.setVisibility(View.GONE);
                if (productListResponse.getProducts()!=null) {
                    TOTAL_PAGES=productListResponse.getProducts().getPagination().get(0).getTotalPages();
                    tv_noofproduct.setText(productListResponse.getProducts().getPagination().get(0).getTotal()+" Products");
                    cons_main.setVisibility(View.VISIBLE);
                    if (productListResponse.getProducts().getData()!=null){
                    if (productListResponse.getProducts().getData().size() > 0) {
                        rv_list.setVisibility(View.VISIBLE);
                        if (isFirstpage){
                            adapter.addAll( productListResponse.getProducts().getData());

                            if (currentPage <= TOTAL_PAGES)
                                adapter.addLoadingFooter(TOTAL_PAGES,productListResponse.getProducts().getPagination().get(0).getTotal());
                            else isLastPage = true;
                        }
                        else{
                            adapter.removeLoadingFooter();
                            isLoading = false;

                            List<Datum> results = productListResponse.getProducts().getData();
                            adapter.addAll(results);

                            if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter(TOTAL_PAGES,productListResponse.getProducts().getPagination().get(0).getTotal());
                            else isLastPage = true;

                        }

                     }
                    }

                }
                else
                    Toast.makeText(this,"No Products Found",Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(this,productListResponse.getMessage(),Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,"Response data is empty",Toast.LENGTH_LONG).show();

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "vsgd dgdg ghd : errorr rr + "+e.getMessage());
        }
    }

    @Override
    public void productlist_failure(String msg) {
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
    }

    @Override
    public void productsearch_success(ProductListResponse productListResponse) {
        try{
        hideLoading();
        if (productListResponse!=null){
            if (productListResponse.getStatus()){
                rv_list.setVisibility(View.GONE);
                if (productListResponse.getProducts()!=null) {
                    TOTAL_PAGES=productListResponse.getProducts().getPagination().get(0).getTotalPages();
                    tv_noofproduct.setText(productListResponse.getProducts().getPagination().get(0).getTotal()+" Products");
                    cons_main.setVisibility(View.VISIBLE);
                    if (productListResponse.getProducts().getData()!=null){
                        if (productListResponse.getProducts().getData().size() > 0) {
                            rv_list.setVisibility(View.VISIBLE);
                            if (isFirstpage){
                                adapter.addAll( productListResponse.getProducts().getData());

                                if (currentPage <= TOTAL_PAGES)
                                    adapter.addLoadingFooter(TOTAL_PAGES,productListResponse.getProducts().getPagination().get(0).getTotal());
                                else isLastPage = true;
                            }
                            else{
                                adapter.removeLoadingFooter();
                                isLoading = false;

                                List<Datum> results = productListResponse.getProducts().getData();
                                adapter.addAll(results);

                                if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter(TOTAL_PAGES,productListResponse.getProducts().getPagination().get(0).getTotal());
                                else isLastPage = true;

                            }

                        }
                    }

                }
                else
                    Toast.makeText(this,"No Products Found",Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(this,productListResponse.getMessage(),Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,"Response data is empty",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "v  dfgdsfggfv hfg: errorr rr + "+e.getMessage());
        }
    }

    @Override
    public void productsearch_failure(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }
    public  void hidesearch(){
        if (isSearchOpen) {
            searchtollbar.setVisibility(View.GONE);
            toolbar.setVisibility(View.VISIBLE);
            ed_search.setText("");
        }
    }

    private  void doapicallforcatproductlist(){
        try {
            String strCatid=null,strShopId=null;
            if (MnxPreferenceManager.getString(MnxConstant.SELECTEDSHOPID,null)!=null)
                strShopId=MnxPreferenceManager.getString(MnxConstant.SELECTEDSHOPID,null);

            if (MnxPreferenceManager.getString(MnxConstant.SELECTED_CAT_ID,null)!=null)
                strCatid=MnxPreferenceManager.getString(MnxConstant.SELECTED_CAT_ID,null);

            if (strCatid!=null) {
                if (strShopId!=null) {
                    showLoading();
                    isFirstpage = true;
                    CatProductListPresenter catProductListPresenter = new CatProductListPresenter(this, new CatProductListIntract());
                    catProductListPresenter.validateDetails(strCatid, strShopId, "1");
                }
                else
                    Toast.makeText(this,"Shop Id missing",Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(this,"Category id missing",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "dsdad  asa: errorr rr + "+e.getMessage());
        }

    }

    private  void doapicallforcatproductlistnextpage(String strpage) {
        try {
            isFirstpage = false;
            if (strpage != null) {
                if (currentPage <= TOTAL_PAGES) {

                    String strCatid=null,strShopId=null;
                    if (MnxPreferenceManager.getString(MnxConstant.SELECTEDSHOPID,null)!=null)
                        strShopId=MnxPreferenceManager.getString(MnxConstant.SELECTEDSHOPID,null);

                    if (MnxPreferenceManager.getString(MnxConstant.SELECTED_CAT_ID,null)!=null)
                        strCatid=MnxPreferenceManager.getString(MnxConstant.SELECTED_CAT_ID,null);

                    if (strCatid!=null) {
                        if (strShopId!=null) {
                            isFirstpage=false;
                            CatProductListPresenter catProductListPresenter = new CatProductListPresenter(this, new CatProductListIntract());
                            catProductListPresenter.validateDetails(strCatid, strShopId, strpage);
                        }
                        else
                            Toast.makeText(this,"Shop Id missing",Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(this,"Category id missing",Toast.LENGTH_LONG).show();

                }
            }
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "ieugbhvbjvj: errorr rr + "+e.getMessage());
        }

    }



    @Override
    public void catproductlist_success(ProductListResponse catProductListresponse) {
        try{
        hideLoading();
        if (catProductListresponse!=null){
            if (catProductListresponse.getStatus()){
                if (catProductListresponse.getProducts()!=null){
                    rv_list.setVisibility(View.GONE);
                        TOTAL_PAGES=catProductListresponse.getProducts().getPagination().get(0).getTotalPages();
                        tv_noofproduct.setText(catProductListresponse.getProducts().getPagination().get(0).getTotal()+" Products");
                        cons_main.setVisibility(View.VISIBLE);
                        if ((catProductListresponse.getProducts().getData()!=null) && (catProductListresponse.getProducts().getData().size() > 0)) {
                                rv_list.setVisibility(View.VISIBLE);
                                if (isFirstpage){
                                    adapter.addAll(catProductListresponse.getProducts().getData());

                                    if (currentPage <= TOTAL_PAGES)
                                        adapter.addLoadingFooter(TOTAL_PAGES,catProductListresponse.getProducts().getPagination().get(0).getTotal());
                                    else isLastPage = true;
                                }
                                else{
                                    adapter.removeLoadingFooter();
                                    isLoading = false;

                                    List<Datum> results = catProductListresponse.getProducts().getData();
                                    adapter.addAll(results);

                                    if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter(TOTAL_PAGES,catProductListresponse.getProducts().getPagination().get(0).getTotal());
                                    else isLastPage = true;

                                }

                            }

                }

            }
        }
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "succste sd sd : errorr rr + "+e.getMessage());
        }

    }

    @Override
    public void catproductlist_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }
}

