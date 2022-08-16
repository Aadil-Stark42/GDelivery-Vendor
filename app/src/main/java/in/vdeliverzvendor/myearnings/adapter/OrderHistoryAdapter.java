package in.vdeliverzvendor.myearnings.adapter;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import in.vdeliverzvendor.R;
import in.vdeliverzvendor.myearnings.model_orderhistory.Order;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {

    Context context;
    List<Order> orderList;

    public OrderHistoryAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder vh = null;
        try {
            LayoutInflater lf = LayoutInflater.from(parent.getContext());
            View Listitem = lf.inflate(R.layout.listitem_myearnings_withdrawhis, parent, false);
            vh = new ViewHolder(Listitem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {
            if (orderList.get(position).getReferral() != null && (!(orderList.get(position).getReferral().isEmpty())))
                 holder.tv_ordernum.setText("#" + orderList.get(position).getReferral());

            if (orderList.get(position).getOrderState()!=null && (!(orderList.get(position).getOrderState().isEmpty())))
                holder.tv_status.setText(orderList.get(position).getOrderState());

            if (orderList.get(position).getAmount()!=null)
                holder.tv_price.setText(orderList.get(position).getAmount());

            if (orderList.get(position).getDate()!=null) {
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-mm-dd");
                Date date = fmt.parse(orderList.get(position).getDate());
                boolean istoday = istoday(date);
                if (istoday) {
                    holder.tv_date.setText("Today");
                    holder.tv_date.setTextColor(context.getResources().getColor(R.color.brown));
                } else if (isYesterday(date)) {
                    holder.tv_date.setText("YesterDay");
                    holder.tv_date.setTextColor(context.getResources().getColor(R.color.apptextcoloor));
                } else {
                    holder.tv_date.setText(orderList.get(position).getDate()+" ");
                    holder.tv_date.setTextColor(context.getResources().getColor(R.color.apptextcoloor));
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout consitem;
        TextView tv_ordernum,tv_date,tv_status,tv_price;


        public ViewHolder(View itemView) {
            super(itemView);
            consitem=itemView.findViewById(R.id.consitem);
            tv_status=itemView.findViewById(R.id.tv_status);
            tv_ordernum=itemView.findViewById(R.id.tv_ordernum);
            tv_date=itemView.findViewById(R.id.tv_amount);
            tv_price=itemView.findViewById(R.id.tv_price);

        }
    }

    public static boolean istoday (Date d) {
        return  DateUtils.isToday(d.getTime());
    }

    public static boolean isYesterday(Date d) {
        return DateUtils.isToday(d.getTime() + DateUtils.DAY_IN_MILLIS);
    }
}


