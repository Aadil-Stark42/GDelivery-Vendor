package in.vdeliverzvendor.shoplist;

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
import in.vdeliverzvendor.createoutlet.OutletActivity;
import in.vdeliverzvendor.dashboard.DashboardActivity;
import in.vdeliverzvendor.shoplist.adapter.OutLetListPageAdapter;
import in.vdeliverzvendor.shoplist.model.Datum;
import in.vdeliverzvendor.shoplist.model.ShopListResponse;
import in.vdeliverzvendor.shoplist.mvp.ShopListContract;
import in.vdeliverzvendor.shoplist.mvp.ShopListIntract;
import in.vdeliverzvendor.shoplist.mvp.ShopLitsPresenter;
import in.vdeliverzvendor.shopsearch.mvp.ShopSearchContract;
import in.vdeliverzvendor.shopsearch.mvp.ShopSearchIntract;
import in.vdeliverzvendor.shopsearch.mvp.ShopSearchPresenter;
import in.vdeliverzvendor.utils.BaseActivity;
import in.vdeliverzvendor.utils.MnxConstant;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import in.vdeliverzvendor.utils.PaginationScrollListener;

public class OutletListWithSearchActivity extends BaseActivity implements ShopListContract, ShopSearchContract {

    ConstraintLayout toolbar, searchtollbar,cons_main;
    ImageView iv_search,iv_close,iv_back,iv_insideback;
    RecyclerView rv_list;
    TextView tv_noofshops,tv_name;
    LinearLayoutManager linearLayoutManager;
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 5;
    private int currentPage = PAGE_START;
    boolean isFirstpage=false;
    OutLetListPageAdapter adapter;
    EditText ed_search;
    String strSearchText;
    boolean isSearchOpened=false;
    String TAG= OutletListWithSearchActivity.class.getSimpleName();
    //ImageView newshops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outletstatus);

        try{

        initcall();

        adapter = new OutLetListPageAdapter(this);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_list.setLayoutManager(linearLayoutManager);
        rv_list.setItemAnimator(new DefaultItemAnimator());
        rv_list.setAdapter(adapter);

        /*if (MnxPreferenceManager.getString(MnxConstant.ISFROM,"").equals("Earnings")||
                (MnxPreferenceManager.getString(MnxConstant.ISFROM,"").equals("Slot"))
        || (MnxPreferenceManager.getString(MnxConstant.ISFROM,"").equals("ProductCat"))
                ||(MnxPreferenceManager.getString(MnxConstant.ISFROM,"").equals("Analytics")))
            newshops.setVisibility(View.GONE);*/

        initclick();
        doapicall();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "dcaddadasd: errorr rr + "+e.getMessage());
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
        //newshops=findViewById(R.id.newshops);
        ed_search=findViewById(R.id.ed_search);
        iv_insideback=findViewById(R.id.iv_insideback);
        tv_name=findViewById(R.id.tv_name);
        tv_name.setText("Shops");
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "initnitnit: errorr rr + "+e.getMessage());
        }
    }

    private  void callnextscreen(){
        try{
        Intent myintent = new Intent(OutletListWithSearchActivity.this, DashboardActivity.class);
        myintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(myintent);
        finish();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "call nextdsjf : errorr rr + "+e.getMessage());
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
            if (isSearchOpened){
                adapter.clearall();
                TOTAL_PAGES=5;
                isSearchOpened=false;
                isFirstpage=false;
                currentPage=1;
                isLoading=false;
                isLastPage=false;
                ed_search.setText("");
                /*if (MnxPreferenceManager.getString(MnxConstant.ISFROM,"").equals("Earnings")
                ||(MnxPreferenceManager.getString(MnxConstant.ISFROM,"").equals("Slot"))
                        || (MnxPreferenceManager.getString(MnxConstant.ISFROM,"").equals("ProductCat"))
                ||(MnxPreferenceManager.getString(MnxConstant.ISFROM,"").equals("Analytics")))
                    newshops.setVisibility(View.GONE);
                else
                    newshops.setVisibility(View.VISIBLE);*/
                searchtollbar.setVisibility(View.GONE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    circleReveal(R.id.cons_toolbar,1,true,true);
                else
                    toolbar.setVisibility(View.GONE);
                doapicall();
            }
            else {
                callnextscreen();
            }

        }
        return true;
    }

    private  void initclick(){

        try{

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callnextscreen();
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ed_search.setText("");
                adapter.clearall();
                TOTAL_PAGES=5;
                isSearchOpened=true;
                isFirstpage=false;
                currentPage=1;
                isLoading=false;
                isLastPage=false;
                tv_noofshops.setText("");
            }
        });

        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clearall();
                //newshops.setVisibility(View.GONE);
                TOTAL_PAGES=5;
                isSearchOpened=true;
                isFirstpage=false;
                currentPage=1;
                isLoading=false;
                isLastPage=false;
                tv_noofshops.setText("");
                searchtollbar=findViewById(R.id.cons_searchtoolbar);
                toolbar.setVisibility(View.GONE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    circleReveal(R.id.cons_searchtoolbar,1,true,true);
                else
                    searchtollbar.setVisibility(View.GONE);

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
                            loadnextpage(currentPage + "");
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

        /*newshops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent=new Intent(OutletListWithSearchActivity.this, OutletActivity.class);
                startActivity(myintent);
                finish();
            }
        });*/

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
                ed_search.setText("");

                searchtollbar.setVisibility(View.GONE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    circleReveal(R.id.cons_toolbar,1,true,true);
                else
                    toolbar.setVisibility(View.GONE);
                doapicall();
            }
        });

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "onclcikckcksbdjsd: errorr rr + "+e.getMessage());
        }

    }

    private  void do_search_apicall(){
        try{
        if (strSearchText!=null && (!strSearchText.isEmpty())){
            showLoading();
            ShopSearchPresenter shopSearchPresenter=new ShopSearchPresenter(this,new ShopSearchIntract());
            shopSearchPresenter.validateDetails(strSearchText,"1");
        }
        else
            Toast.makeText(this,"Enter Seacrh Text",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "searpo callakofj : errorr rr + "+e.getMessage());
        }
    }

    private  void do_search_apicall_nextpage(String strpage){
        try {
            isFirstpage = false;
            if (strpage != null) {
                if (currentPage <= TOTAL_PAGES) {
                    ShopSearchPresenter shopSearchPresenter = new ShopSearchPresenter(this, new ShopSearchIntract());
                    shopSearchPresenter.validateDetails(strSearchText, strpage);
                }
            }
     }catch (Exception e){
        FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
        Log.d(TAG, "sdvbsvdbsdv: errorr rr + "+e.getMessage());
    }
    }

    private  void doapicall(){
        showLoading();
        ShopLitsPresenter shopLitsPresenter=new ShopLitsPresenter(this,new ShopListIntract());
        shopLitsPresenter.validateDetails("1");
    }

    private  void loadnextpage(String strpage){
        isFirstpage=false;
        if (strpage!=null) {
            if (currentPage<=TOTAL_PAGES) {
                ShopLitsPresenter shopLitsPresenter = new ShopLitsPresenter(this, new ShopListIntract());
                shopLitsPresenter.validateDetails(strpage);
            }
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
            Log.i("TESTSEARCH","TESTSEARCH"+"3");
            myView.setVisibility(View.VISIBLE);
        }

        // start the animation
        anim.start();

    }

    @Override
    public void shoplist_success(ShopListResponse shopListResponse) {
        try {
            hideLoading();
            if (shopListResponse != null) {
                if (shopListResponse.getStatus()) {
                    TOTAL_PAGES = shopListResponse.getShops().getPagination().get(0).getTotalPages();
                    cons_main.setVisibility(View.VISIBLE);
                    rv_list.setVisibility(View.GONE);
                    if (shopListResponse.getShops().getPagination().get(0).getTotal() != null) {
                        if (shopListResponse.getShops().getPagination().get(0).getTotal().equals("1"))
                            tv_noofshops.setText(shopListResponse.getShops().getPagination().get(0).getTotal() + " Shop");
                        else
                            tv_noofshops.setText(shopListResponse.getShops().getPagination().get(0).getTotal() + " Shops");

                        if (shopListResponse.getShops() != null)

                            if (shopListResponse.getShops().getData() != null && shopListResponse.getShops().getData().size() > 0) {
                                rv_list.setVisibility(View.VISIBLE);
                                if (isFirstpage) {
                                    adapter.addAll(shopListResponse.getShops().getData());

                                    if (currentPage <= TOTAL_PAGES)
                                        adapter.addLoadingFooter(TOTAL_PAGES, shopListResponse.getShops().getPagination().get(0).getTotal());
                                    else isLastPage = true;
                                } else {
                                    adapter.removeLoadingFooter();
                                    isLoading = false;

                                    List<Datum> results = shopListResponse.getShops().getData();
                                    adapter.addAll(results);

                                    if (currentPage != TOTAL_PAGES)
                                        adapter.addLoadingFooter(TOTAL_PAGES, shopListResponse.getShops().getPagination().get(0).getTotal());
                                    else isLastPage = true;

                                }
                            }

                    }
                } else
                    Toast.makeText(this, shopListResponse.getMessage(), Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(this, shopListResponse.getMessage(), Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "repsonsensemnse: errorr rr + "+e.getMessage());
        }

    }

    @Override
    public void shoplist_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
    }

    @Override
    public void shopsearch_success(ShopListResponse shopSearchRespone) {
        try{
        hideLoading();
        if (shopSearchRespone!=null){
            if (shopSearchRespone.getStatus()){
                TOTAL_PAGES=shopSearchRespone.getShops().getPagination().get(0).getTotalPages();
                cons_main.setVisibility(View.VISIBLE);
                rv_list.setVisibility(View.GONE);
                if (shopSearchRespone.getShops().getPagination().get(0).getTotal()!=null) {
                    if (shopSearchRespone.getShops().getPagination().get(0).getTotal().equals("1"))
                        tv_noofshops.setText(shopSearchRespone.getShops().getPagination().get(0).getTotal() + " Shop");
                    else
                        tv_noofshops.setText(shopSearchRespone.getShops().getPagination().get(0).getTotal() + " Shops");

                    if (shopSearchRespone.getShops() != null)
                        if (shopSearchRespone.getShops().getData() != null && shopSearchRespone.getShops().getData().size() > 0) {
                            rv_list.setVisibility(View.VISIBLE);
                            if (isFirstpage){
                                adapter.addAll(shopSearchRespone.getShops().getData());

                                if (currentPage <= TOTAL_PAGES)
                                    adapter.addLoadingFooter(TOTAL_PAGES,shopSearchRespone.getShops().getPagination().get(0).getTotal());
                                else isLastPage = true;
                            }
                            else{
                                adapter.removeLoadingFooter();
                                isLoading = false;

                                List<Datum> results = shopSearchRespone.getShops().getData();
                                adapter.addAll(results);

                                if (currentPage != TOTAL_PAGES)
                                    adapter.addLoadingFooter(TOTAL_PAGES,shopSearchRespone.getShops().getPagination().get(0).getTotal());
                                else
                                    isLastPage = true;

                            }
                        }

                }
            }
            else
                Toast.makeText(this,shopSearchRespone.getMessage(),Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,shopSearchRespone.getMessage(),Toast.LENGTH_LONG).show();
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "success: errorr rr + "+e.getMessage());
        }
    }

    @Override
    public void shopsearch_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }


}
