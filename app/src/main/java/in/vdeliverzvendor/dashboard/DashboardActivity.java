package in.vdeliverzvendor.dashboard;

import static in.vdeliverzvendor.utils.MnxConstant.APP_STATUS;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.createoutlet.OutletActivity;
import in.vdeliverzvendor.dashboard.model_dashboard.DashBoardResponse;
import in.vdeliverzvendor.dashboard.mvp.DashboardContract;
import in.vdeliverzvendor.dashboard.mvp.DashboardIntract;
import in.vdeliverzvendor.dashboard.mvp.DashboardPresenter;
import in.vdeliverzvendor.login.LoginActivity;
import in.vdeliverzvendor.myearnings.MyEarningsActivity;
import in.vdeliverzvendor.notification.NotificationListActivity;
import in.vdeliverzvendor.orders.OrdersListActivity;
import in.vdeliverzvendor.productlist.ProductListWithSearchActivity;
import in.vdeliverzvendor.settings.SettingsActivity;
import in.vdeliverzvendor.shoplist.OutletListWithSearchActivity;
import in.vdeliverzvendor.slotlist.SlotListActivity;
import in.vdeliverzvendor.topping.ToppingListActivity;
import in.vdeliverzvendor.utils.BaseActivity;
import in.vdeliverzvendor.utils.MnxConstant;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import in.vdeliverzvendor.utils.SmallRoundRectCornerImageView;

public class DashboardActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,
        DashboardContract {

    RecyclerView rv_list;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ImageView iv_side_menu_open, iv_notification;
    ActionBarDrawerToggle actionBarDrawerToggle;
    TextView tv_totalorderscount, tv_totalearnings, tv_packedandassigned, tv_noofcust, tv_viewall, tv_name;
    ConstraintLayout consmain;
    SmallRoundRectCornerImageView iv_profile;
    String TAG = DashboardActivity.class.getSimpleName();

    //ImageView closenavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard2);

        try {

            initcall();
            initclick();

            doapicall();
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().log(Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "dashhskdksd : errorr rr + " + e.getMessage());
        }

    }

    private void initcall() {
        try {
            navigationView = findViewById(R.id.navigationview_id);
            iv_side_menu_open = findViewById(R.id.iv_menu);
            iv_notification = findViewById(R.id.iv_notification);
            tv_totalorderscount = findViewById(R.id.tv_totalorderscount);
            tv_noofcust = findViewById(R.id.tv_noofcust);
            tv_packedandassigned = findViewById(R.id.tv_packedandassigned);
            tv_totalearnings = findViewById(R.id.tv_totalearnings);
            tv_viewall = findViewById(R.id.tv_viewall);
            consmain = findViewById(R.id.consmain);
            consmain.setVisibility(View.GONE);

            View header = navigationView.getHeaderView(0);
            navigationView.setItemIconTintList(null);

            navigationView.setNavigationItemSelectedListener(this);
            //closenavigation=header.findViewById(R.id.iv_close);
            tv_name = header.findViewById(R.id.nav_header_name_id);
            iv_profile = header.findViewById(R.id.nav_header_circleimageview_id);

            drawerLayout = findViewById(R.id.drawer_layout);
            actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                    R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawerLayout.addDrawerListener(actionBarDrawerToggle);

            customfont();

            navigationView.setNavigationItemSelectedListener(this);
            rv_list = findViewById(R.id.rv_orderlist);

        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().log(Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "iinittinti: errorr rr + " + e.getMessage());
        }
    }

    private void initclick() {

        try {

        /*closenavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            }
        });*/

            iv_side_menu_open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });

            iv_notification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (APP_STATUS.equals("0")) {
                        Coming_Soon_popup(DashboardActivity.this);

                    } else {
                        Intent myintent = new Intent(DashboardActivity.this, NotificationListActivity.class);
                        startActivity(myintent);
                    }

                }
            });

            tv_viewall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (APP_STATUS.equals("0")) {
                        Coming_Soon_popup(DashboardActivity.this);
                    } else {
                        Intent myintet = new Intent(DashboardActivity.this, OrdersListActivity.class);
                        startActivity(myintet);

                    }

                }
            });

        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().log(Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "onclickssss: errorr rr + " + e.getMessage());
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent myintent;
        if (APP_STATUS.equals("0")) {

            switch (item.getItemId()) {
                case R.id.nav_settings:
                    myintent = new Intent(DashboardActivity.this, SettingsActivity.class);
                    startActivity(myintent);
                    navigationView.setVisibility(View.GONE);
                    break;
                default:
                    Coming_Soon_popup(this);
            }

        } else {
            switch (item.getItemId()) {


                case R.id.nav_shops:
                    myintent = new Intent(DashboardActivity.this, OutletListWithSearchActivity.class);
                    MnxPreferenceManager.setString(MnxConstant.ISFROM, "OutLet");
                    startActivity(myintent);
                    navigationView.setVisibility(View.GONE);
                    break;

                case R.id.nav_analytics:
                /*myintent=new Intent(DashboardActivity.this, OutletListWithSearchActivity.class);
                MnxPreferenceManager.setString(MnxConstant.ISFROM,"Analytics");
                startActivity(myintent);*/
                    myintent = new Intent(this, BarGraphDisplayActivity.class);
                    startActivity(myintent);
                    navigationView.setVisibility(View.GONE);
                    break;

                case R.id.nav_product:
                    myintent = new Intent(DashboardActivity.this, ProductListWithSearchActivity.class);
                    MnxPreferenceManager.setString(MnxConstant.ISFROM, "Dashboard");
                    startActivity(myintent);
                    navigationView.setVisibility(View.GONE);
                    break;

                case R.id.nav_catid:
                    myintent = new Intent(DashboardActivity.this, ToppingListActivity.class);
                    startActivity(myintent);
                    navigationView.setVisibility(View.GONE);
                    break;

                case R.id.nav_slots:
                    myintent = new Intent(this, SlotListActivity.class);
                    startActivity(myintent);
                    navigationView.setVisibility(View.GONE);
                    break;

                case R.id.nav_orders:
                    MnxPreferenceManager.setString(MnxConstant.ISFROM, "orderlist");
                    myintent = new Intent(DashboardActivity.this, OrdersListActivity.class);
                    startActivity(myintent);
                    navigationView.setVisibility(View.GONE);
                    break;

                case R.id.nav_earnings:
                    myintent = new Intent(this, MyEarningsActivity.class);
                    startActivity(myintent);
                    navigationView.setVisibility(View.GONE);
                    break;

                case R.id.nav_settings:
                    myintent = new Intent(DashboardActivity.this, SettingsActivity.class);
                    startActivity(myintent);
                    navigationView.setVisibility(View.GONE);
                    break;
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void customfont() {
        Menu menu = navigationView.getMenu();
        Log.i("TESTNAME", "TESTNAME" + MnxPreferenceManager.getString(MnxConstant.USER_NAME, null));

        if (MnxPreferenceManager.getString(MnxConstant.USER_NAME, null) != null) {
            tv_name.setText(MnxPreferenceManager.getString(MnxConstant.USER_NAME, null));
        }

        MenuItem tools = menu.findItem(R.id.support);
        SpannableString s = new SpannableString(tools.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.title_menu), 0, s.length(), 0);
        tools.setTitle(s);

        tools = menu.findItem(R.id.orderanddelivery);
        s = new SpannableString(tools.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.title_menu), 0, s.length(), 0);
        tools.setTitle(s);

        tools = menu.findItem(R.id.outlet);
        s = new SpannableString(tools.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.title_menu), 0, s.length(), 0);
        tools.setTitle(s);

        tools = menu.findItem(R.id.product);
        s = new SpannableString(tools.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.title_menu), 0, s.length(), 0);
        tools.setTitle(s);


        tools = menu.findItem(R.id.nav_shops);
        s = new SpannableString(tools.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.textstyleformenuitem), 0, s.length(), 0);
        tools.setTitle(s);

        tools = menu.findItem(R.id.nav_analytics);
        s = new SpannableString(tools.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.textstyleformenuitem), 0, s.length(), 0);
        tools.setTitle(s);

        tools = menu.findItem(R.id.nav_product);
        s = new SpannableString(tools.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.textstyleformenuitem), 0, s.length(), 0);
        tools.setTitle(s);

        tools = menu.findItem(R.id.nav_product);
        s = new SpannableString(tools.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.textstyleformenuitem), 0, s.length(), 0);
        tools.setTitle(s);

        tools = menu.findItem(R.id.nav_product);
        s = new SpannableString(tools.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.textstyleformenuitem), 0, s.length(), 0);
        tools.setTitle(s);

        tools = menu.findItem(R.id.nav_product);
        s = new SpannableString(tools.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.textstyleformenuitem), 0, s.length(), 0);
        tools.setTitle(s);


        tools = menu.findItem(R.id.nav_catid);
        s = new SpannableString(tools.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.textstyleformenuitem), 0, s.length(), 0);
        tools.setTitle(s);

        tools = menu.findItem(R.id.nav_catid);
        s = new SpannableString(tools.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.textstyleformenuitem), 0, s.length(), 0);
        tools.setTitle(s);


        tools = menu.findItem(R.id.nav_slots);
        s = new SpannableString(tools.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.textstyleformenuitem), 0, s.length(), 0);
        tools.setTitle(s);
//
//        tools= menu.findItem(R.id.nav_coupons);
//        s = new SpannableString(tools.getTitle());
//        s.setSpan(new TextAppearanceSpan(this, R.style.textstyleformenuitem), 0, s.length(), 0);
//        tools.setTitle(s);


        tools = menu.findItem(R.id.nav_orders);
        s = new SpannableString(tools.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.textstyleformenuitem), 0, s.length(), 0);
        tools.setTitle(s);

        tools = menu.findItem(R.id.nav_earnings);
        s = new SpannableString(tools.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.textstyleformenuitem), 0, s.length(), 0);
        tools.setTitle(s);

//        tools= menu.findItem(R.id.deliverysettings);
//        s = new SpannableString(tools.getTitle());
//        s.setSpan(new TextAppearanceSpan(this, R.style.textstyleformenuitem), 0, s.length(), 0);
//        tools.setTitle(s);

        tools = menu.findItem(R.id.nav_settings);
        s = new SpannableString(tools.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.textstyleformenuitem), 0, s.length(), 0);
        tools.setTitle(s);

    }

    private void doapicall() {
        showLoading();
        DashboardPresenter dashboardPresenter = new DashboardPresenter(this, new DashboardIntract());
        dashboardPresenter.validateDetails();
    }

    @Override
    public void dashboard_success(DashBoardResponse dashBoardResponse) {

        try {
            hideLoading();
            if (dashBoardResponse != null) {
                if (dashBoardResponse.getStatus()) {
                    APP_STATUS = dashBoardResponse.getApp_status();
                    consmain.setVisibility(View.VISIBLE);
                    String strpath = MnxPreferenceManager.getString(MnxConstant.USERIMAGE, null);

                    if (dashBoardResponse.getPrivacyPolicy() != null)
                        MnxPreferenceManager.setString(MnxConstant.PRIVACY, dashBoardResponse.getPrivacyPolicy());

                    if (dashBoardResponse.getTermsAndConditions() != null)
                        MnxPreferenceManager.setString(MnxConstant.TERMSOFSERVICE, dashBoardResponse.getTermsAndConditions());

                    MnxPreferenceManager.setString(MnxConstant.SELECTEDSHOPID, dashBoardResponse.getShop_id());
                    if (dashBoardResponse.getCurrency() != null) {
                        MnxPreferenceManager.setString(MnxConstant.CURRENCY, dashBoardResponse.getCurrency());
                    }

                    if (strpath != null) {
                        Glide.with(this)
                                .load(strpath)
                                .fitCenter()
                                .placeholder(R.drawable.user_placeholder_sidenav)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(iv_profile);
                    }

                    if (dashBoardResponse.getUsers() != null)
                        tv_noofcust.setText(dashBoardResponse.getUsers());
                    if (dashBoardResponse.getTotDeliveries() != null) {
                        tv_totalorderscount.setText(dashBoardResponse.getTotDeliveries());

                    }
                    String currency = MnxPreferenceManager.getString(MnxConstant.CURRENCY, "₹");
                    NumberFormat nm = NumberFormat.getNumberInstance();

                    double value = Double.parseDouble( dashBoardResponse.getTotEarning());
                    tv_totalearnings.setText(currency + " " + nm.format(value));
                    if (dashBoardResponse.getUnassigned() != null)
                        tv_packedandassigned.setText(dashBoardResponse.getUnassigned());

                    if (dashBoardResponse.getOrders() != null && dashBoardResponse.getOrders().size() > 0) {
                        OrderItemAdapter orderItemAdapter = new OrderItemAdapter(this, dashBoardResponse.getOrders());
                        rv_list.setHasFixedSize(true);
                        rv_list.setLayoutManager(new LinearLayoutManager(this));
                        rv_list.setAdapter(orderItemAdapter);
                    }
                }
            }
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().log(Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "dashb resposnsesewe: errorr rr + " + e.getMessage());
        }

    }

    @Override
    public void dashboard_failure(String msg) {
        try {
            hideLoading();
            Log.i("TESTDASHBOARD", "TESTDASHBOARD" + "2");
            if (msg.equals("No shops found, Create shop first!")) {
                MnxPreferenceManager.setString(MnxConstant.ISFROM, "Dashboard");
                Intent myintent = new Intent(this, OutletActivity.class);
                startActivity(myintent);
            }
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().log(Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "faillslrjkrej: errorr rr + " + e.getMessage());
        }
    }

    @Override
    public void dashboard_logout() {
        /*MnxPreferenceManager.clearAllPreferences();
        Intent myintent=new Intent(getApplicationContext(), LoginActivity.class);
        myintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(myintent);
        finish();*/
        hideLoading();
        do_logout_and_login_redirect();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    public static void Coming_Soon_popup(Context context) {
        TextView iv_okay;

        View alertCustomdialog = LayoutInflater.from(context).inflate(R.layout.coming_soon_popup, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        iv_okay = alertCustomdialog.findViewById(R.id.iv_okay);

        alert.setView(alertCustomdialog);
        alert.setCancelable(false);
        final AlertDialog dialog = alert.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        iv_okay.setOnClickListener(v -> dialog.dismiss());
    }
}
