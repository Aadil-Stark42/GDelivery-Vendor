package in.vdeliverzvendor.dashboard;

import static in.vdeliverzvendor.dashboard.DashboardActivity.Coming_Soon_popup;
import static in.vdeliverzvendor.utils.MnxConstant.APP_STATUS;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.dashboard.model_dashboard.Order;
import in.vdeliverzvendor.orders.OrderOverviewActivity;
import in.vdeliverzvendor.utils.MnxConstant;
import in.vdeliverzvendor.utils.MnxPreferenceManager;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder> {

    Context context;
    List<Order> orderList;

    public OrderItemAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder vh = null;
        try {
            LayoutInflater lf = LayoutInflater.from(parent.getContext());
            View Listitem = lf.inflate(R.layout.dashboard_rowitems, parent, false);
            vh = new ViewHolder(Listitem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            if (orderList.get(position).getReferral() != null)
                holder.tv_ordernum.setText("#" + orderList.get(position).getReferral());

            if ((orderList.get(position).getOrderState() != null && orderList.get(position).getOrderState() != null) && (orderList.get(position).getOrderState().equals("Canceled"))) {
                holder.tv_status.setText("Canceled");
                holder.tv_status.setTextColor(context.getResources().getColor(R.color.red));
            } else if ((orderList.get(position).getOrderState() != null && orderList.get(position).getOrderState() != null) && (orderList.get(position).getOrderState().equals("Rejected by Vendor"))) {
                holder.tv_status.setText("Rejected by Vendor");
                holder.tv_status.setTextColor(context.getResources().getColor(R.color.red));
            } else {
                holder.tv_status.setText(orderList.get(position).getOrderState());
                holder.tv_status.setTextColor(context.getResources().getColor(R.color.green));
            }
            if (orderList.get(position).getDate() != null) {
                SimpleDateFormat fmt = new SimpleDateFormat("dd MMM yyyy");
                Date date = fmt.parse(orderList.get(position).getDate());
                boolean istoday = istoday(date);
                if (istoday) {
                    holder.tv_date.setText("Today");
                    holder.tv_date.setTextColor(context.getResources().getColor(R.color.brown));
                } else if (isYesterday(date)) {
                    holder.tv_date.setText("YesterDay");
                    holder.tv_date.setTextColor(context.getResources().getColor(R.color.apptextcoloor));
                } else {
                    holder.tv_date.setText(orderList.get(position).getDate());
                    holder.tv_date.setTextColor(context.getResources().getColor(R.color.apptextcoloor));
                }
            }
            holder.cons_item.setOnClickListener(v -> {
                if (APP_STATUS.equals("0")) {
                    Coming_Soon_popup(context);
                } else {
                    MnxPreferenceManager.setString(MnxConstant.ISFROM, "dashboardorder");
                    Intent myintent = new Intent(context, OrderOverviewActivity.class);
                    myintent.putExtra("OrderId", orderList.get(position).getOrderId());
                    ((Activity) context).startActivity(myintent);


                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static boolean istoday(Date d) {
        return DateUtils.isToday(d.getTime());
    }

    public static boolean isYesterday(Date d) {
        return DateUtils.isToday(d.getTime() + DateUtils.DAY_IN_MILLIS);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_ordernum, tv_status, tv_date;
        ConstraintLayout cons_item;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_ordernum = itemView.findViewById(R.id.tv_ordernum);
            tv_status = itemView.findViewById(R.id.tv_status);
            cons_item = itemView.findViewById(R.id.cons_item);
        }
    }
}