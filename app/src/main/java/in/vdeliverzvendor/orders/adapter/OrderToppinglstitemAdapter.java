package in.vdeliverzvendor.orders.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.orders.model_ordredetails.Topping;
import in.vdeliverzvendor.utils.MnxConstant;
import in.vdeliverzvendor.utils.MnxPreferenceManager;

public class OrderToppinglstitemAdapter extends RecyclerView.Adapter<OrderToppinglstitemAdapter.ViewHolder> {

    Context context;
    List<Topping> toppingList;

    public OrderToppinglstitemAdapter(Context context, List<Topping> toppings) {
        this.context = context;
        this.toppingList=toppings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder vh = null;
        try {
            LayoutInflater lf = LayoutInflater.from(parent.getContext());
            View Listitem = lf.inflate(R.layout.toppinglistitems, parent, false);
            vh = new ViewHolder(Listitem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if ((toppingList.get(position).getName()!=null) && (toppingList.get(position).getPrice()!=null))
            holder.tv_name.setText(toppingList.get(position).getName()+" - "+ MnxPreferenceManager.getString(MnxConstant.CURRENCY,"₹")+
                    toppingList.get(position).getPrice());
        else if(toppingList.get(position).getName()!=null)
            holder.tv_name.setText(toppingList.get(position).getName());

    }


    @Override
    public int getItemCount() {
        return toppingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;

        public ViewHolder(View itemView) {

            super(itemView);
            tv_name=itemView.findViewById(R.id.tv_name);
        }
    }
}