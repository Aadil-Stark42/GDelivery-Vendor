package in.vdeliverzvendor.productview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.productview.model.Title;
import in.vdeliverzvendor.productview.model.Topping;

public class ProductToppingAdapter extends RecyclerView.Adapter<ProductToppingAdapter.ViewHolder> {

    Context context;
    TextView tv_editaddress;
    List<Topping> toppings;
    List<Title> catlist;

    public ProductToppingAdapter(Context context, List<Topping> toppings, List<Title> catlist) {
        this.context = context;
        this.toppings=toppings;
        this.catlist=catlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder vh = null;
        try {
            LayoutInflater lf = LayoutInflater.from(parent.getContext());
            View Listitem = lf.inflate(R.layout.topping_listitem, parent, false);
            vh = new ViewHolder(Listitem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {
            if (toppings.get(position).getName()!=null)
                holder.tv_name.setText(toppings.get(position).getName());

            if (toppings.get(position).getPrice()!=null)
                holder.tv_actualprice.setText(toppings.get(position).getPrice());

            if (toppings.get(position).getAvailable()!=null)
                holder.tv_count.setText(toppings.get(position).getAvailable());

            if (toppings.get(position).getVariety()!=null){
                if (toppings.get(position).getVariety().equals("1"))
                    holder.tv_varietyvalue.setText("Veg");
                else
                    holder.tv_varietyvalue.setText("Non Veg");
            }
            String strcatname="";
            if (toppings.get(position).getTitleId()!=null){
                if (catlist !=null && catlist.size()>0) {
                    String strcatid=toppings.get(position).getTitleId();
                    for (int i = 0; i<catlist.size();i++) {
                        if (strcatid.equals(catlist.get(i).getTitleId())) {
                            strcatname = catlist.get(i).getName();
                            break;
                        }
                    }
                }
            }

            if (strcatname!=null)
                holder.tv_catname.setText(strcatname);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return toppings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name,tv_catname,tv_varietyvalue,tv_actualprice,tv_count;
        ImageView iv_more;

        public ViewHolder(View itemView) {

            super(itemView);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_catname=itemView.findViewById(R.id.tv_catname);
            tv_varietyvalue=itemView.findViewById(R.id.tv_varietyvalue);
            tv_actualprice=itemView.findViewById(R.id.tv_actualprice);
            tv_count=itemView.findViewById(R.id.tv_count);
            iv_more=itemView.findViewById(R.id.iv_more);
            iv_more.setVisibility(View.GONE);
        }
    }
}