package in.vdeliverzvendor.orders.adapter;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import in.vdeliverzvendor.R;
import in.vdeliverzvendor.orders.OrderOverviewActivity;
import in.vdeliverzvendor.orders.model.Order;


public class OrderListPaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    List<Order> productlist;
    private Context context;

    private boolean isLoadingAdded = false;

    public OrderListPaginationAdapter(Context context) {
        this.context = context;
        productlist = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.order_list_item, parent, false);
        viewHolder = new ViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        try {
            Order result = productlist.get(position); // Movie

            switch (getItemViewType(position)) {
                case ITEM:
                    final ViewHolder viewHolder = (ViewHolder) holder;
                    viewHolder.consitem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent myintent=new Intent(context, OrderOverviewActivity.class);
                            myintent.putExtra("OrderId",result.getOrderId());
                            context.startActivity(myintent);
                            ((Activity)context).finish();
                        }
                    });

                    if (result.getReferral()!=null){
                        viewHolder.tv_ordernum.setText("#"+result.getReferral());
                    }

                    if (result.getDate()!=null) {
                        SimpleDateFormat fmt = new SimpleDateFormat("dd MMM yyyy");
                        Date date = fmt.parse(result.getDate());
                        boolean istoday=istoday(date);
                        if (istoday){
                            viewHolder.tv_date.setText("Today");
                            viewHolder.tv_date.setTextColor(context.getResources().getColor(R.color.brown));
                        }
                        else if(isYesterday(date)){
                            viewHolder.tv_date.setText("YesterDay");
                            viewHolder.tv_date.setTextColor(context.getResources().getColor(R.color.apptextcoloor));
                        }
                        else {
                            viewHolder.tv_date.setText(result.getDate());
                            viewHolder.tv_date.setTextColor(context.getResources().getColor(R.color.apptextcoloor));
                        }
                    }

                    if ((result.getOrderStatus()!=null && result.getOrderState()!=null) && (result.getOrderState().equals("Canceled"))) {
                        viewHolder.tv_status.setText("Canceled");
                        viewHolder.tv_status.setTextColor(context.getResources().getColor(R.color.red));
                    }
                    else if((result.getOrderStatus()!=null && result.getOrderState()!=null) && (result.getOrderState().equals("Rejected by Vendor"))){
                        viewHolder.tv_status.setText("Rejected by Vendor");
                        viewHolder.tv_status.setTextColor(context.getResources().getColor(R.color.red));
                    }
                    else{
                        viewHolder.tv_status.setText(result.getOrderState());
                        viewHolder.tv_status.setTextColor(context.getResources().getColor(R.color.green));
                    }
                    break;
                case LOADING:
                    break;

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return productlist == null ? 0 : productlist.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == productlist.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public  void  clearall(){
        productlist.clear();
        notifyDataSetChanged();
    }

    public void add(Order r) {
        productlist.add(r);
        notifyItemInserted(productlist.size() - 1);
    }

    public void addAll(List<Order> moveResults) {
        for (Order result : moveResults) {
            add(result);
        }
    }

    public void remove(Order r) {
        int position = productlist.indexOf(r);
        if (position > -1) {
            productlist.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter(int page,int ntotal) {
        if(productlist.size()<ntotal) {
            if (page != 1)
                isLoadingAdded = true;
            add(new Order());
        }
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
        int position = productlist.size() - 1;
        if (position!=-1) {
            Order result = getItem(position);

            if (result != null) {
                productlist.remove(position);
                notifyItemRemoved(position);
            }
        }
    }

    public Order getItem(int position) {
        return productlist.get(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout consitem;
        TextView tv_ordernum,tv_date,tv_status;


        public ViewHolder(View itemView) {
            super(itemView);
            consitem=itemView.findViewById(R.id.consitem);
            tv_status=itemView.findViewById(R.id.tv_status);
            tv_ordernum=itemView.findViewById(R.id.tv_ordernum);
            tv_date=itemView.findViewById(R.id.tv_date);

        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }


    public static boolean istoday (Date d) {
        return  DateUtils.isToday(d.getTime());
    }

    public static boolean isYesterday(Date d) {
        return DateUtils.isToday(d.getTime() + DateUtils.DAY_IN_MILLIS);
    }


}
