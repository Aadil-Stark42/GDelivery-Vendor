package in.vdeliverzvendor.notification;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.List;
import java.util.Objects;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.notification.model.Datum;
import in.vdeliverzvendor.notification.model.NotificationListResponse;
import in.vdeliverzvendor.notification.mvp.NotificationListContract;
import in.vdeliverzvendor.notification.mvp.NotificationListIntract;
import in.vdeliverzvendor.notification.mvp.NotificationListPresenter;
import in.vdeliverzvendor.utils.BaseActivity;
import in.vdeliverzvendor.utils.PaginationScrollListener;

public class NotificationListActivity extends BaseActivity implements NotificationListContract {

    RecyclerView rv_list;
    ImageView iv_back;
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 5;
    private int currentPage = PAGE_START;
    boolean isFirstpage=false;
    NotificationListAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    String TAG=NotificationListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        try{

        initcall();

        adapter = new NotificationListAdapter(this);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_list.setLayoutManager(linearLayoutManager);
        rv_list.setItemAnimator(new DefaultItemAnimator());
        rv_list.setAdapter(adapter);

        initclick();

        doapicall();

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "dsfdfd: errorr rr + "+e.getMessage());
        }

    }

    private  void initcall(){
        rv_list=findViewById(R.id.rv_notificationlist);
        iv_back=findViewById(R.id.iv_back);
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
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "intintit : errorr rr + "+e.getMessage());
        }

    }

    private  void doapicall(){
        showLoading();
        NotificationListPresenter notificationListPresenter=new NotificationListPresenter(this,new NotificationListIntract());
        notificationListPresenter.validateDetails("1");

    }

    private  void loadnextpage(String strpage){
        isFirstpage=false;
        if (strpage!=null) {
            if (currentPage<=TOTAL_PAGES) {
                NotificationListPresenter notificationListPresenter=new NotificationListPresenter(this,new NotificationListIntract());
                notificationListPresenter.validateDetails(strpage);
            }
        }

    }

    @Override
    public void notificationlist_success(NotificationListResponse notificationListResponse) {
        try{
        hideLoading();
        if (notificationListResponse!=null){
            if (notificationListResponse.getStatus()){
                TOTAL_PAGES=notificationListResponse.getPagination().getTotalPages();
                if (notificationListResponse.getData()!=null && (notificationListResponse.getData().size()>0)){
                    rv_list.setVisibility(View.VISIBLE);
                    if (isFirstpage){
                        adapter.addAll(notificationListResponse.getData());

                        if (currentPage <= TOTAL_PAGES)
                            adapter.addLoadingFooter(TOTAL_PAGES,notificationListResponse.getPagination().getTotal());
                        else isLastPage = true;
                    }
                    else{
                        adapter.removeLoadingFooter();
                        isLoading = false;

                        List<Datum> results = notificationListResponse.getData();
                        adapter.addAll(results);

                        if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter(TOTAL_PAGES,notificationListResponse.getPagination().getTotal());
                        else isLastPage = true;

                    }
                }
            }
        }
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "dfdfdffd: errorr rr + "+e.getMessage());
        }

    }

    @Override
    public void notificationlist_failure(String msg) {
        hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
    }
}
