package in.vdeliverzvendor.myearnings;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.List;
import java.util.Objects;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.bankdetail.AccountSelectionActivity;
import in.vdeliverzvendor.myearnings.adapter.OrderHistoryAdapter;
import in.vdeliverzvendor.myearnings.adapter.WithDrawPaginationAdapter;
import in.vdeliverzvendor.myearnings.model_orderhistory.OrderHistroyResponse;
import in.vdeliverzvendor.myearnings.mvp_orderhistory.OrderHistoryContract;
import in.vdeliverzvendor.myearnings.mvp_orderhistory.OrderHistoryIntract;
import in.vdeliverzvendor.myearnings.mvp_orderhistory.OrderHistoryPresenter;
import in.vdeliverzvendor.requestpayment.model.PaymentRequestResponse;
import in.vdeliverzvendor.requestpayment.model.Withdrawal;
import in.vdeliverzvendor.requestpayment.mvp.RequestPaymentContract;
import in.vdeliverzvendor.requestpayment.mvp.RequestPaymentIntract;
import in.vdeliverzvendor.requestpayment.mvp.RequestPaymentPresenter;
import in.vdeliverzvendor.utils.BaseActivity;
import in.vdeliverzvendor.utils.MnxConstant;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import in.vdeliverzvendor.utils.PaginationScrollListener;

public class MyEarningsActivity extends BaseActivity implements RequestPaymentContract, OrderHistoryContract {

    RecyclerView rv_orderhistorylist,rv_withdraw;
    TextView tv_withdraw,tv_orderhistory,tv_reqwithdraw,tv_availablebal,tv_totalearningvalue,tv_totalordervalue,tv_commissionvalue;
    View view_withdraw,view_history;
    ImageView iv_back;
    String strShopId;
    ConstraintLayout consmain;
    WithDrawPaginationAdapter withDrawPaginationAdapter;

    LinearLayoutManager linearLayoutManager;
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 5;
    private int currentPage = PAGE_START;
    boolean isFirstpage=false;
    boolean isOrdertab=false;
    String total_earn;
    String TAG=MyEarningsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_earnings);

        try{


        if (MnxPreferenceManager.getString(MnxConstant.SELECTEDSHOPID,null)!=null)
            strShopId=MnxPreferenceManager.getString(MnxConstant.SELECTEDSHOPID,null);

        initcall();

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_withdraw.setLayoutManager(linearLayoutManager);

        rv_withdraw.setItemAnimator(new DefaultItemAnimator());

        if(withDrawPaginationAdapter!=null){
            rv_withdraw.removeAllViewsInLayout();
        }else{
            withDrawPaginationAdapter = new WithDrawPaginationAdapter(this);
            rv_withdraw.setAdapter(withDrawPaginationAdapter);
        }


        initclick();

        doapicall();

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "dfg hdfg fgdhgf: errorr rr + "+e.getMessage());
        }

    }

    private  void initcall(){

        try{
        rv_orderhistorylist=findViewById(R.id.rv_historylist);
        rv_withdraw=findViewById(R.id.rv_withdraw);
        tv_withdraw=findViewById(R.id.tv_withdrawhis);
        tv_orderhistory=findViewById(R.id.tv_orderhist);
        view_withdraw=findViewById(R.id.viewwithdraw);
        view_history=findViewById(R.id.vieworderhistory);
        iv_back=findViewById(R.id.iv_back);
        tv_reqwithdraw=findViewById(R.id.tv_reqwithdraw);
        tv_availablebal=findViewById(R.id.tv_availablebal);
        tv_totalearningvalue=findViewById(R.id.tv_totalearningvalue);
        tv_totalordervalue=findViewById(R.id.tv_totalordervalue);
        tv_commissionvalue=findViewById(R.id.tv_commissionvalue);
        consmain=findViewById(R.id.consmain);
        consmain.setVisibility(View.GONE);
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "asdsfd afdf: errorr rr + "+e.getMessage());
        }

    }

    private  void initclick(){

        try{
        rv_withdraw.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                if (!(isOrdertab)) {
                    isLoading = true;
                    currentPage += 1;

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadnextpage(currentPage + "");
                        }
                    }, 1000);
                }
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



        tv_reqwithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (total_earn.equals("0") || total_earn.isEmpty()){
                    //Toast.makeText(MyEarningsActivity.this,"Available balance is only 0",Toast.LENGTH_LONG).show();
                    Log.d("TAG", "onClick: sasasasasas");
                }
                else {
                    Intent myintent = new Intent(MyEarningsActivity.this, AccountSelectionActivity.class);
                    myintent.putExtra("ShopId", strShopId);
                    startActivity(myintent);
                    finish();
                }
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        tv_withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOrdertab=false;
                rv_withdraw.setVisibility(View.VISIBLE);
                rv_orderhistorylist.setVisibility(View.GONE);
                tv_withdraw.setTextColor(getResources().getColor(R.color.gray2));
                view_withdraw.setBackgroundColor(getResources().getColor(R.color.brown));
                tv_withdraw.setTextSize(16);
                Typeface face = ResourcesCompat.getFont(MyEarningsActivity.this, R.font.poppinsmedium);
                tv_withdraw.setTypeface(face);


                tv_orderhistory.setTextColor(getResources().getColor(R.color.gray));
                view_history.setBackgroundColor(getResources().getColor(R.color.gray));
                tv_orderhistory.setTextSize(16);
                face = ResourcesCompat.getFont(MyEarningsActivity.this, R.font.poppinsmedium);
                tv_orderhistory.setTypeface(face);

            }
        });

        tv_orderhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOrdertab=true;
                rv_withdraw.setVisibility(View.GONE);
                rv_orderhistorylist.setVisibility(View.VISIBLE);
                doapicallfororderhistory();
                tv_orderhistory.setTextColor(getResources().getColor(R.color.gray2));
                view_history.setBackgroundColor(getResources().getColor(R.color.brown));
                tv_orderhistory.setTextSize(16);
                Typeface face = ResourcesCompat.getFont(MyEarningsActivity.this, R.font.poppinsmedium);
                tv_orderhistory.setTypeface(face);


                tv_withdraw.setTextColor(getResources().getColor(R.color.gray));
                view_withdraw.setBackgroundColor(getResources().getColor(R.color.gray));
                tv_withdraw.setTextSize(16);
                face = ResourcesCompat.getFont(MyEarningsActivity.this, R.font.poppinsmedium);
                tv_withdraw.setTypeface(face);

            }
        });
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "asdhdghg: errorr rr + "+e.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*Intent myintent=new Intent(MyEarningsActivity.this, OutletListWithSearchActivity.class);
        myintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(myintent);
        finish();*/
    }

    private void doapicall(){
        if (strShopId!=null){
            showLoading();
            isFirstpage=true;
            RequestPaymentPresenter requestPaymentPresenter=new RequestPaymentPresenter(this ,new RequestPaymentIntract());
            requestPaymentPresenter.validateDetails(strShopId,"1");
        }
        else {
            Toast.makeText(this, "Request data issue", Toast.LENGTH_LONG).show();
        }
    }

    private  void loadnextpage(String strpage){
        isFirstpage=false;
        if (strpage!=null) {
            RequestPaymentPresenter requestPaymentPresenter=new RequestPaymentPresenter(this ,new RequestPaymentIntract());
            requestPaymentPresenter.validateDetails(strShopId,strpage);
        }
        else
            Toast.makeText(this,"Request data issue",Toast.LENGTH_LONG).show();
    }

    @Override
    public void requestpayment_success(PaymentRequestResponse paymentRequestResponse) {
        try{
        hideLoading();
        if (paymentRequestResponse!=null) {
            if (paymentRequestResponse.getStatus()) {
                if (paymentRequestResponse.getPagination().getTotalPages()!=null)
                    TOTAL_PAGES=paymentRequestResponse.getPagination().getTotalPages();
                if (paymentRequestResponse.getTotalEarning().toString().equals("0") )
                    tv_reqwithdraw.setBackground(getResources().getDrawable(R.drawable.round_corner_withdisable));
                else
                    tv_reqwithdraw.setBackground(getResources().getDrawable(R.drawable.round_corner_view));

                consmain.setVisibility(View.VISIBLE);

                if (paymentRequestResponse.getTotalEarning() != null && (!(paymentRequestResponse.getTotalEarning().isEmpty()))) {
                    total_earn=paymentRequestResponse.getTotalEarning();
                    tv_availablebal.setText(MnxPreferenceManager.getString(MnxConstant.CURRENCY,"₹") +" "+
                            paymentRequestResponse.getTotalEarning());
                    tv_totalearningvalue.setText(MnxPreferenceManager.getString(MnxConstant.CURRENCY,"₹") +" "+
                            paymentRequestResponse.getTotalEarning());
                }
                if (paymentRequestResponse.getCommission() != null && (!(paymentRequestResponse.getCommission().isEmpty())))
                    tv_commissionvalue.setText(paymentRequestResponse.getCommission());

                if (paymentRequestResponse.getTotalOrders() != null && (!(paymentRequestResponse.getTotalOrders().isEmpty())))
                    tv_totalordervalue.setText(paymentRequestResponse.getTotalOrders());

                    if (paymentRequestResponse.getWithdrawals() != null && paymentRequestResponse.getWithdrawals().size() > 0) {
                        if (isFirstpage){

                            Log.i("TESTCHECK","TESTCHECK"+"IF");
                            withDrawPaginationAdapter.addAll(paymentRequestResponse.getWithdrawals());

                            if (currentPage <= TOTAL_PAGES)
                                withDrawPaginationAdapter.addLoadingFooter(TOTAL_PAGES,paymentRequestResponse.getPagination().getTotal());
                            else isLastPage = true;
                        }
                        else{
                            Log.i("TESTCHECK","TESTCHECK"+"ELSE");
                            withDrawPaginationAdapter.removeLoadingFooter();
                            isLoading = false;

                            List<Withdrawal> results = paymentRequestResponse.getWithdrawals();
                            withDrawPaginationAdapter.addAll(results);

                            if (currentPage != TOTAL_PAGES)
                                withDrawPaginationAdapter.addLoadingFooter(TOTAL_PAGES,paymentRequestResponse.getPagination().getTotal());
                            else isLastPage = true;

                        }

                }
//                    else
//                    Toast.makeText(this, "", Toast.LENGTH_LONG).show();
            }
//            else
//                Toast.makeText(this, "", Toast.LENGTH_LONG).show();
        }

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "suceessss: errorr rr + "+e.getMessage());
        }

    }

    @Override
    public void requestpayment_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
    }

    /*@Override
    protected void onResume() {
        super.onResume();
        if (AccountSelectionActivity.isUpdated){
            doapicall();
            AccountSelectionActivity.isUpdated=false;
        }
    }*/

    private  void doapicallfororderhistory(){
        if (strShopId!=null){
            showLoading();
            OrderHistoryPresenter orderHistoryPresenter=new OrderHistoryPresenter(this,new OrderHistoryIntract());
            orderHistoryPresenter.validateDetails(strShopId);
        }
    }


    @Override
    public void orderhistory_success(OrderHistroyResponse orderHistroyResponse) {
        try{
        hideLoading();
        if (orderHistroyResponse!=null){
            if (orderHistroyResponse.getStatus()){
                if (orderHistroyResponse.getOrders()!=null && orderHistroyResponse.getOrders().size()>0){
                    OrderHistoryAdapter orderHistoryAdapter= new OrderHistoryAdapter(this,orderHistroyResponse.getOrders());
                    LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                    rv_orderhistorylist.setLayoutManager(linearLayoutManager1);
                    rv_orderhistorylist.setItemAnimator(new DefaultItemAnimator());
                    rv_orderhistorylist.setAdapter(orderHistoryAdapter);
                }
            }
        }
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "asdddsfdff: errorr rr + "+e.getMessage());
        }
    }

    @Override
    public void orderhistory_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }
}
