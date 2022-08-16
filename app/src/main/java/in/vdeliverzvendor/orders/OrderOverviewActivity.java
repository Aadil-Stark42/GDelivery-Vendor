package in.vdeliverzvendor.orders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.Objects;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.dashboard.DashboardActivity;
import in.vdeliverzvendor.deliveryboy_rating.mvp.DBoyRatingContract;
import in.vdeliverzvendor.deliveryboy_rating.mvp.DBoyRatingIntract;
import in.vdeliverzvendor.deliveryboy_rating.mvp.DBoyRatingPresenter;
import in.vdeliverzvendor.orders.adapter.OrderListItemAdapter;
import in.vdeliverzvendor.orders.model_ordredetails.OrderDetailResponse;
import in.vdeliverzvendor.orders.mvp_orderdetails.OrderDetailContract;
import in.vdeliverzvendor.orders.mvp_orderdetails.OrderDetailIntract;
import in.vdeliverzvendor.orders.mvp_orderdetails.OrderDetailPresenter;
import in.vdeliverzvendor.orders.mvp_status.OrderStatucContract;
import in.vdeliverzvendor.orders.mvp_status.OrderStatusIntract;
import in.vdeliverzvendor.orders.mvp_status.OrderStatusPresenter;
import in.vdeliverzvendor.utils.BaseActivity;
import in.vdeliverzvendor.utils.GeneralResponse;
import in.vdeliverzvendor.utils.MnxConstant;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import in.vdeliverzvendor.utils.SvgRatingBar;

public class OrderOverviewActivity extends BaseActivity implements OrderDetailContract, OrderStatucContract,
        DBoyRatingContract {

    ImageView iv_back, iv_close_order, iv_addtopping, iv_addstock;
    RecyclerView rv_list;
    TextView tv_referalnum, tv_inst, tv_mobilenum, tv_orderdate, tv_orderamount, tv_expertdeliverytime, tv_status,
            tv_ordertype, tv_acceptandassign, tv_acceptorder, tv_acceptedat, tv_deliveredat, tv_assignedat, tv_pickedat, tv_address, tv_deliveryboy;
    String strOrderId, strAction;
    ConstraintLayout conmain;

    TextView tv_rate_deliveryboy;

    public static boolean isUpdated = false;

  /*  Accept and assign - action=1
    Accept - action=5
    Reject - action=6*/

    String TAG = OrderOverviewActivity.class.getSimpleName();


    BottomSheetDialog bottomSheetDialog_delivery_rating;
    TextView tv_cancel, tv_submit;
    SvgRatingBar rb_delivery;
    EditText ed_rating_comment;
    DBoyRatingPresenter dBoyRatingPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detailed);

        try {
            if (getIntent().getStringExtra("OrderId") != null)
                strOrderId = getIntent().getStringExtra("OrderId");

            initcall();

            initclick();

            doapicall();
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().log(TAG + " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "dcaddadasd: errorr rr + " + e.getMessage());
        }

    }

    private void initcall() {

        try {
            iv_back = findViewById(R.id.iv_back);
            iv_close_order = findViewById(R.id.iv_close_order);
            rv_list = findViewById(R.id.rv_list);
            tv_referalnum = findViewById(R.id.tv_referalnum);
            tv_inst = findViewById(R.id.tv_instvalue);
            tv_mobilenum = findViewById(R.id.tv_mobilenumvalue);
            tv_orderdate = findViewById(R.id.tv_orderdate);
            tv_orderamount = findViewById(R.id.tv_orderamountvalue);
            tv_expertdeliverytime = findViewById(R.id.tv_expertdeliverytime);
            tv_status = findViewById(R.id.tv_status);
            tv_ordertype = findViewById(R.id.tv_ordertype);
            conmain = findViewById(R.id.conmain);
            conmain.setVisibility(View.GONE);
            tv_acceptandassign = findViewById(R.id.tv_acceptandassign);
            tv_acceptorder = findViewById(R.id.tv_acceptorder);
            tv_acceptedat = findViewById(R.id.tv_acceptedat);
            tv_assignedat = findViewById(R.id.tv_assignedat);
            tv_pickedat = findViewById(R.id.tv_pickedat);
            tv_deliveredat = findViewById(R.id.tv_deliveredat);
            tv_address = findViewById(R.id.tv_address);
            tv_deliveryboy = findViewById(R.id.tv_deliveryboy);

            tv_rate_deliveryboy = findViewById(R.id.tv_rate_deliveryboy);
            tv_rate_deliveryboy.setVisibility(View.INVISIBLE);

            bottomSheetDialog_delivery_rating = new BottomSheetDialog(this, R.style.BottomSheetDialog);
            bottomSheetDialog_delivery_rating.setContentView(R.layout.bottomsheet_deliveryboy_rating);
            tv_cancel = bottomSheetDialog_delivery_rating.findViewById(R.id.immediate_no);
            tv_submit = bottomSheetDialog_delivery_rating.findViewById(R.id.immediate_okay);
            rb_delivery = bottomSheetDialog_delivery_rating.findViewById(R.id.rb_delivery);
            ed_rating_comment = bottomSheetDialog_delivery_rating.findViewById(R.id.editText1);

            tv_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String rating_value = String.valueOf(rb_delivery.getRating());
                    String comment = ed_rating_comment.getText().toString();
                    if (strOrderId != null) {
                        if (rating_value != null && rating_value.length() > 0) {
                            do_delivery_rating_apicall(rating_value, comment);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Order ID Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().log(TAG + " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "sdhdgjhdfghjdgf fdgdfg f : errorr rr + " + e.getMessage());
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        call();
    }

    private void call() {
        try {
            if (MnxPreferenceManager.getString(MnxConstant.ISFROM, null).equals("dashboardorder")) {
                Intent myintent = new Intent(OrderOverviewActivity.this, DashboardActivity.class);
                myintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(myintent);
                finish();
            } else {
                Intent myintent = new Intent(OrderOverviewActivity.this, OrdersListActivity.class);
                myintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(myintent);
                finish();
            }
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().log(TAG + " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "asdsdsdsad: errorr rr + " + e.getMessage());
        }

    }

    private void initclick() {
        try {

            iv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    call();
                }
            });

            iv_close_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCustomDialog();
                }
            });

            tv_acceptandassign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dochangestatus("1");

                }
            });
            tv_acceptorder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dochangestatus("5");
                }
            });


            tv_rate_deliveryboy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomSheetDialog_delivery_rating.show();
                }
            });
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().log(TAG + " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "sdsadsd gtjh : errorr rr + " + e.getMessage());
        }
    }

    private void doapicall() {
        if (strOrderId != null) {
            showLoading();
            OrderDetailPresenter orderDetailPresenter = new OrderDetailPresenter(this, new OrderDetailIntract());
            orderDetailPresenter.validateDetails(strOrderId);
        } else
            Toast.makeText(this, "Request Data issue", Toast.LENGTH_LONG).show();
    }

    public void do_delivery_rating_apicall(String rating, String comment) {
        showLoading();
        dBoyRatingPresenter = new DBoyRatingPresenter(this, new DBoyRatingIntract());
        dBoyRatingPresenter.validateDetails(strOrderId, rating, comment);
    }


    private void showCustomDialog() {
        try {
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(this).inflate(R.layout.popup_cancel_order, viewGroup, false);
            TextView tv_goback = dialogView.findViewById(R.id.tv_goback);
            TextView tv_cancel = dialogView.findViewById(R.id.tv_cancelorder);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(dialogView);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            tv_goback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    onBackPressed();
                }
            });
            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    dochangestatus("6");
                }
            });
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().log(TAG + " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "dcaddadasd: errorr rr + " + e.getMessage());
        }
    }

    @Override
    public void orderdetail_success(OrderDetailResponse orderDetailResponse) {

        try {
            hideLoading();
            if (orderDetailResponse != null) {
                if (orderDetailResponse.getStatus()) {
                    if (orderDetailResponse.getOrder() != null) {
                        conmain.setVisibility(View.VISIBLE);
                        if (orderDetailResponse.getOrder().getReferral() != null)
                            tv_referalnum.setText("#" + orderDetailResponse.getOrder().getReferral());

                        if (orderDetailResponse.getOrder().getInstructions() != null)
                            tv_inst.setText(orderDetailResponse.getOrder().getInstructions().toString());
                        else
                            tv_inst.setText("-");

                        if (orderDetailResponse.getOrder().getMobile() != null)
                            tv_mobilenum.setText(orderDetailResponse.getOrder().getMobile());

                        if (orderDetailResponse.getOrder().getConfirmedAt() != null)
                            tv_orderdate.setText(orderDetailResponse.getOrder().getConfirmedAt());

                        if (orderDetailResponse.getOrder().getAmount() != null)
                            tv_orderamount.setText(MnxPreferenceManager.getString(MnxConstant.CURRENCY, "₹")
                                    + orderDetailResponse.getOrder().getAmount());

                        if (orderDetailResponse.getOrder().getExpectedTime() != null)
                            tv_expertdeliverytime.setText(orderDetailResponse.getOrder().getExpectedTime());


                        if (orderDetailResponse.getOrder().getOrderState() != null)
                            tv_status.setText(orderDetailResponse.getOrder().getOrderState());

                        if (orderDetailResponse.getOrder().getPaymentType() != null)
                            tv_ordertype.setText(orderDetailResponse.getOrder().getPaymentType());

                        if (orderDetailResponse.getOrder().getOrderItems() != null && orderDetailResponse.getOrder().getOrderItems().size() > 0) {
                            OrderListItemAdapter orderListItemAdapter = new OrderListItemAdapter(this, orderDetailResponse.getOrder().getOrderItems());
                            rv_list.setHasFixedSize(true);
                            rv_list.setLayoutManager(new LinearLayoutManager(this));
                            rv_list.setAdapter(orderListItemAdapter);
                        }


                        switch (orderDetailResponse.getOrder().getOrderStatus()) {
                            case "0": //confirmed and not assigned
                                iv_close_order.setVisibility(View.GONE);
                                tv_acceptorder.setVisibility(View.GONE);
                                tv_acceptandassign.setVisibility(View.GONE);

                                break;
                            case "1": //Accepted and Assigned
                                iv_close_order.setVisibility(View.GONE);
                                tv_acceptorder.setVisibility(View.GONE);
                                tv_acceptandassign.setVisibility(View.GONE);
                                break;
                            case "2": //Out for delivery
                                iv_close_order.setVisibility(View.GONE);
                                tv_acceptorder.setVisibility(View.GONE);
                                tv_acceptandassign.setVisibility(View.GONE);

                                break;
                            case "3"://Delivered
                                iv_close_order.setVisibility(View.GONE);
                                tv_acceptorder.setVisibility(View.GONE);
                                tv_acceptandassign.setVisibility(View.GONE);
                                tv_rate_deliveryboy.setVisibility(View.VISIBLE);
                                break;
                            case "4": //Yet to Picked
                                iv_close_order.setVisibility(View.GONE);
                                tv_acceptorder.setVisibility(View.GONE);
                                tv_acceptandassign.setVisibility(View.GONE);

                                break;
                            case "5":// Accepted
                                iv_close_order.setVisibility(View.GONE);
                                tv_acceptorder.setVisibility(View.GONE);
                                tv_acceptandassign.setVisibility(View.VISIBLE);
                                break;
                            case "6":
                                iv_close_order.setVisibility(View.GONE);
                                tv_acceptorder.setVisibility(View.GONE);
                                tv_acceptandassign.setVisibility(View.GONE);

                                break;
                            case "7": // not assigned
                                iv_close_order.setVisibility(View.VISIBLE);
                                tv_acceptorder.setVisibility(View.VISIBLE);
                                tv_acceptandassign.setVisibility(View.VISIBLE);

                                break;
                            case "8": //Created
                                iv_close_order.setVisibility(View.VISIBLE);
                                tv_acceptorder.setVisibility(View.VISIBLE);
                                tv_acceptandassign.setVisibility(View.VISIBLE);

                                break;
                            default:
                                tv_rate_deliveryboy.setVisibility(View.INVISIBLE);
                                break;
                        }


                        if (orderDetailResponse.getOrder().getAcceptedAt() != null && (!(orderDetailResponse.getOrder().getAcceptedAt().isEmpty()))) {
                            tv_acceptedat.setText(orderDetailResponse.getOrder().getAcceptedAt());
                        } else
                            tv_acceptedat.setText("-");

                        if (orderDetailResponse.getOrder().getAssignedAt() != null && (!(orderDetailResponse.getOrder().getAssignedAt().isEmpty())))
                            tv_assignedat.setText(orderDetailResponse.getOrder().getAssignedAt());
                        else
                            tv_assignedat.setText("-");

                        if (orderDetailResponse.getOrder().getDeliveredAt() != null && (!(orderDetailResponse.getOrder().getDeliveredAt().isEmpty())))
                            tv_deliveredat.setText(orderDetailResponse.getOrder().getDeliveredAt());
                        else
                            tv_deliveredat.setText("-");

                        if (orderDetailResponse.getOrder().getPickedAt() != null && (!(orderDetailResponse.getOrder().getPickedAt().isEmpty())))
                            tv_pickedat.setText(orderDetailResponse.getOrder().getPickedAt());
                        else
                            tv_pickedat.setText("-");

                        if (orderDetailResponse.getOrder().getCustomerAddress().getAddress() != null && (!(orderDetailResponse.getOrder().getCustomerAddress().getAddress().isEmpty())))
                            tv_address.setText(orderDetailResponse.getOrder().getCustomerAddress().getAddress());
                        else
                            tv_address.setText("-");

                        if (orderDetailResponse.getOrder().getDeliveryBoy() != null && (!(orderDetailResponse.getOrder().getDeliveryBoy().isEmpty()))
                                && (!(orderDetailResponse.getOrder().getDeliveryBoy().equals("Not Yet Delivered"))))
                            tv_deliveryboy.setText(orderDetailResponse.getOrder().getDeliveryBoy());
                        else
                            tv_deliveryboy.setText("-");


                    } else
                        Toast.makeText(this, orderDetailResponse.getMessage(), Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(this, orderDetailResponse.getMessage(), Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(this, orderDetailResponse.getMessage(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().log(TAG + " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "sfdfdfdf: errorr rr + " + e.getMessage());
        }

    }

    @Override
    public void orderdetail_failure(String msg) {
        hideLoading();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

    }

    @Override
    public void dashboard_logout() {
        hideLoading();
        do_logout_and_login_redirect();
    }


//    / 0 => Order Canceled by Customer
    // 1 => Order accepted and assigned
    // 2 => Order yet to pick
    // 3 => Order delivered
    // 4 => Order picked by delivery boy
    // 5 => Order accepted by vendor
    // 6 => Order rejected by vendor
    // 7 => Confirmed & Not Assigned


    @Override
    public void orderstatus_success(GeneralResponse generalResponse) {
        try {
            hideLoading();
            if (generalResponse != null) {
                if (generalResponse.getStatus()) {
                    Toast.makeText(this, generalResponse.getMessage(), Toast.LENGTH_LONG).show();
                    if (strAction.equals("1")) {
                        iv_close_order.setVisibility(View.GONE);
                        tv_acceptorder.setVisibility(View.GONE);
                        tv_acceptandassign.setVisibility(View.GONE);
                    } else if (strAction.equals("6")) {
                        iv_close_order.setVisibility(View.GONE);
                        tv_acceptorder.setVisibility(View.GONE);
                        tv_acceptandassign.setVisibility(View.GONE);

                    } else if (strAction.equals("5")) {
                        iv_close_order.setVisibility(View.GONE);
                        tv_acceptorder.setVisibility(View.GONE);
                        tv_acceptandassign.setVisibility(View.VISIBLE);
                    }

                } else
                    Toast.makeText(this, generalResponse.getMessage(), Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(this, generalResponse.getMessage(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().log(TAG + " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "sddfdf: errorr rr + " + e.getMessage());
        }
    }

    @Override
    public void orderstatus_failure(String msg) {
        hideLoading();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

    }

    private void dochangestatus(String strStatus) {
        try {
            if (strOrderId != null && (!(strOrderId.isEmpty()))) {
                if (strStatus != null && (!(strStatus.isEmpty()))) {
                    strAction = strStatus;
                    showLoading();
                    OrderStatusPresenter orderStatusPresenter = new OrderStatusPresenter(this, new OrderStatusIntract());
                    orderStatusPresenter.validateDetails(strOrderId, strStatus);
                } else
                    Toast.makeText(this, "Action missing", Toast.LENGTH_LONG).show();

            } else
                Toast.makeText(this, "Order Id missing", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().log(TAG + " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "asdfdafdfdf: errorr rr + " + e.getMessage());
        }
    }

    @Override
    public void dboy_rate_success(GeneralResponse generalResponse) {
        hideLoading();
        if (generalResponse != null) {
            if (generalResponse.getStatus()) {
                Toast.makeText(this, generalResponse.getMessage(), Toast.LENGTH_SHORT).show();
                bottomSheetDialog_delivery_rating.dismiss();
            } else {
                Toast.makeText(this, generalResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void dboy_rate__failure(String msg) {
        hideLoading();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
