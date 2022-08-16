package in.vdeliverzvendor.orders;

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

import java.util.List;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.dashboard.DashboardActivity;
import in.vdeliverzvendor.orders.adapter.OrderListPaginationAdapter;
import in.vdeliverzvendor.orders.model.Order;
import in.vdeliverzvendor.orders.model.OrdersListResponse;
import in.vdeliverzvendor.orders.mvp.OrderListContract;
import in.vdeliverzvendor.orders.mvp.OrderListIntract;
import in.vdeliverzvendor.orders.mvp.OrderListPresenter;
import in.vdeliverzvendor.orders.mvp_search.OrderSearchContract;
import in.vdeliverzvendor.orders.mvp_search.OrderSearchIntract;
import in.vdeliverzvendor.orders.mvp_search.OrderSearchPresenter;
import in.vdeliverzvendor.utils.PaginationScrollListener;
import in.vdeliverzvendor.utils.BaseActivity;

public class OrdersListActivity extends BaseActivity implements OrderListContract, OrderSearchContract {
    RecyclerView rv_list;
    ImageView iv_back,iv_insideback,iv_search,iv_close;
    TextView tv_title,tv_no_of_slot;
    OrderListPaginationAdapter orderListPaginationAdapter;
    LinearLayoutManager linearLayoutManager;
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 5;
    private int currentPage = PAGE_START;
    boolean isFirstpage=false;
    ConstraintLayout toolbar, searchtollbar,consmain;
    EditText ed_search;
    String strSearchText;
    boolean isSearchOpened=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_search);

        initcall();

        orderListPaginationAdapter = new OrderListPaginationAdapter(this);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_list.setLayoutManager(linearLayoutManager);

        rv_list.setItemAnimator(new DefaultItemAnimator());

        rv_list.setAdapter(orderListPaginationAdapter);

        initclick();

        doapicall();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
         call();
    }
    private  void call(){
        Intent myintent=new Intent(OrdersListActivity.this, DashboardActivity.class);
        myintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(myintent);
        finish();
    }

    private  void initclick(){

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call();
            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ed_search.setText("");
                orderListPaginationAdapter.clearall();
                TOTAL_PAGES=5;
                isSearchOpened=true;
                isFirstpage=false;
                currentPage=1;
                isLoading=false;
                isLastPage=false;
                tv_no_of_slot.setText("");
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

        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderListPaginationAdapter.clearall();
                TOTAL_PAGES=5;
                isSearchOpened=true;
                isFirstpage=false;
                currentPage=1;
                isLoading=false;
                isLastPage=false;
                tv_no_of_slot.setText("");
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
                orderListPaginationAdapter.clearall();
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


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        //replaces the default 'Back' button action
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            if (isSearchOpened){
                orderListPaginationAdapter.clearall();
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
            else {
                call();
            }
        }
        return true;
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



    private  void initcall(){
        iv_search=findViewById(R.id.iv_search);
        rv_list=findViewById(R.id.rv_orderlist);
        tv_title=findViewById(R.id.tv_name);
        tv_title.setText("Orders");
        iv_back=findViewById(R.id.iv_back);
        tv_no_of_slot=findViewById(R.id.tv_no_of_slot);
        consmain=findViewById(R.id.consmain);
        consmain.setVisibility(View.GONE);
        ed_search=findViewById(R.id.ed_search);
        iv_insideback=findViewById(R.id.iv_insideback);
        toolbar = findViewById(R.id.cons_toolbar);
        iv_close=findViewById(R.id.iv_close);
        ed_search.setHint("Search Order");
    }

    private  void doapicall(){
        showLoading();
        isFirstpage=true;
        OrderListPresenter orderListPresenter=new OrderListPresenter(this,new OrderListIntract());
        orderListPresenter.validateDetails("1");
    }

    private  void loadnextpage(String strpage){
        isFirstpage=false;
        if (strpage!=null) {
            if (currentPage <= TOTAL_PAGES) {
                Log.i("TESTCALLSCROLL", "TESTCALLSCROLL" + "loadnextpage - Orders");
                OrderListPresenter orderListPresenter = new OrderListPresenter(this, new OrderListIntract());
                orderListPresenter.validateDetails(strpage);
            }
        }
    }


    private  void do_search_apicall(){
        if (strSearchText!=null && (!strSearchText.isEmpty())){
            showLoading();
            OrderSearchPresenter orderSearchPresenter=new OrderSearchPresenter(this,new OrderSearchIntract());
            orderSearchPresenter.validateDetails("1",strSearchText);
        }
        else
            Toast.makeText(this,"Enter Seacrh Text",Toast.LENGTH_LONG).show();
    }

    private  void do_search_apicall_nextpage(String strpage){
        isFirstpage=false;
        if (strpage!=null) {
            if (currentPage<=TOTAL_PAGES) {
                OrderSearchPresenter orderSearchPresenter=new OrderSearchPresenter(this,new OrderSearchIntract());
                orderSearchPresenter.validateDetails(strpage,strSearchText);
            }
        }
    }


    @Override
    public void orderList_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
        public void orderList_success(OrdersListResponse ordersListResponse) {
        hideLoading();
        if (ordersListResponse!=null){
            if (ordersListResponse.getStatus()){
                consmain.setVisibility(View.VISIBLE);
                rv_list.setVisibility(View.GONE);
                if (ordersListResponse.getPagination().getTotal()!=null)
                    TOTAL_PAGES=ordersListResponse.getPagination().getTotalPages();
                    tv_no_of_slot.setText(ordersListResponse.getPagination().getTotal()+" Orders" );
                if (ordersListResponse.getOrders()!=null && ordersListResponse.getOrders().size()>0){
                    rv_list.setVisibility(View.VISIBLE);
                    if (isFirstpage){
                        orderListPaginationAdapter.addAll(ordersListResponse.getOrders());
                        if (currentPage <= TOTAL_PAGES)
                            orderListPaginationAdapter.addLoadingFooter(TOTAL_PAGES,ordersListResponse.getPagination().getTotal());
                        else isLastPage = true;
                    }
                    else{
                        orderListPaginationAdapter.removeLoadingFooter();
                        isLoading = false;

                        List<Order> results = ordersListResponse.getOrders();
                        orderListPaginationAdapter.addAll(results);

                        if (currentPage != TOTAL_PAGES) orderListPaginationAdapter.addLoadingFooter(TOTAL_PAGES,ordersListResponse.getPagination().getTotal());
                        else isLastPage = true;

                    }

                }
                else
                    Toast.makeText(this,ordersListResponse.getMessage(),Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(this,ordersListResponse.getMessage(),Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show();

    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
    }

    @Override
    public void orderListsearch_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void orderListsearch_success(OrdersListResponse ordersListResponse) {
        hideLoading();
        if (ordersListResponse!=null){
            if (ordersListResponse.getStatus()){
                consmain.setVisibility(View.VISIBLE);
                rv_list.setVisibility(View.GONE);
                if (ordersListResponse.getPagination().getTotal()!=null)
                    TOTAL_PAGES=ordersListResponse.getPagination().getTotalPages();
                tv_no_of_slot.setText(ordersListResponse.getPagination().getTotal()+" Orders" );
                if (ordersListResponse.getOrders()!=null && ordersListResponse.getOrders().size()>0){
                    rv_list.setVisibility(View.VISIBLE);
                    if (isFirstpage){
                        orderListPaginationAdapter.addAll(ordersListResponse.getOrders());

                        if (currentPage <= TOTAL_PAGES)
                            orderListPaginationAdapter.addLoadingFooter(TOTAL_PAGES,ordersListResponse.getPagination().getTotal());
                        else isLastPage = true;
                    }
                    else{
                        orderListPaginationAdapter.removeLoadingFooter();
                        isLoading = false;

                        List<Order> results = ordersListResponse.getOrders();
                        orderListPaginationAdapter.addAll(results);

                        if (currentPage != TOTAL_PAGES) orderListPaginationAdapter.addLoadingFooter(TOTAL_PAGES,ordersListResponse.getPagination().getTotal());
                        else isLastPage = true;

                    }

                }
                else
                    Toast.makeText(this,ordersListResponse.getMessage(),Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(this,ordersListResponse.getMessage(),Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,ordersListResponse.getMessage(),Toast.LENGTH_LONG).show();
    }
}
