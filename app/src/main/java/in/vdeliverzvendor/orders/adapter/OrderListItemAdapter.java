package in.vdeliverzvendor.orders.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.orders.model_ordredetails.OrderItem;

public class OrderListItemAdapter extends RecyclerView.Adapter<OrderListItemAdapter.ViewHolder> {

    Context context;
    TextView tv_editaddress;
    List<OrderItem> orderItemList;

    public OrderListItemAdapter(Context context, List<OrderItem> orderItemList) {
        this.context = context;
        this.orderItemList=orderItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder vh = null;
        try {
            LayoutInflater lf = LayoutInflater.from(parent.getContext());
            View Listitem = lf.inflate(R.layout.order_item_list, parent, false);
            vh = new ViewHolder(Listitem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (orderItemList.get(position).getProductName()!=null)
            holder.tv_name.setText(orderItemList.get(position).getProductName());

        if (orderItemList.get(position).getToppings()!=null && orderItemList.get(position).getToppings().size()>0){
            OrderToppinglstitemAdapter orderListAdapter = new OrderToppinglstitemAdapter(context,orderItemList.get(position).getToppings());
            holder.rv_list.setHasFixedSize(true);
            holder.rv_list.setLayoutManager(new LinearLayoutManager(context));
            holder.rv_list.setAdapter(orderListAdapter);
        }
    }


    @Override
    public int getItemCount() {
        return orderItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout consitem;
        RecyclerView rv_list;
        TextView tv_qty,tv_name;

        public ViewHolder(View itemView) {

            super(itemView);
            rv_list=itemView.findViewById(R.id.rv_toppinglist);
            tv_qty=itemView.findViewById(R.id.tv_qty);
            tv_name=itemView.findViewById(R.id.tv_name);
            consitem=itemView.findViewById(R.id.consitem);
        }
    }
}