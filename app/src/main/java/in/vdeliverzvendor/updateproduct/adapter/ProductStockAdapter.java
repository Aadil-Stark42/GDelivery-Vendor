package in.vdeliverzvendor.updateproduct.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.productview.model.Stock;
import in.vdeliverzvendor.updateproduct.UpdateProductActivity;
import in.vdeliverzvendor.updatetstock.UpdateStockDetailActivity;
import in.vdeliverzvendor.utils.MnxConstant;
import in.vdeliverzvendor.utils.MnxPreferenceManager;

public class ProductStockAdapter extends RecyclerView.Adapter<ProductStockAdapter.ViewHolder> {

    Context context;
    List<Stock> stocklist;
    String strProductid;

    public ProductStockAdapter(Context context, List<Stock> stocklist,String strproductid) {
        this.context = context;
        this.stocklist=stocklist;
        this.strProductid=strproductid;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder vh = null;
        try {
            LayoutInflater lf = LayoutInflater.from(parent.getContext());
            View Listitem = lf.inflate(R.layout.productstocklistitem, parent, false);
            vh = new ViewHolder(Listitem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {
            String cur = MnxPreferenceManager.getString(MnxConstant.CURRENCY,"₹");
            if (stocklist.get(position).getVariant()!=null)
                holder.tv_variantname.setText(stocklist.get(position).getVariant());
            if (stocklist.get(position).getActualPrice()!=null)
                holder.tv_actualprice.setText(cur+" "+stocklist.get(position).getActualPrice());
            if (stocklist.get(position).getSellingPrice()!=null)
                holder.tv_sellingprice.setText(cur+" "+stocklist.get(position).getSellingPrice());
            if (stocklist.get(position).getSize()!=null)
                holder.tv_unitsizevalue.setText(stocklist.get(position).getSize());
            if (stocklist.get(position).getUnit()!=null)
                holder.tv_unitvalue.setText(stocklist.get(position).getUnit());
            if ((stocklist.get(position).getAvailable())!=null)
                holder.tv_availablevalue.setText(stocklist.get(position).getAvailable());

            holder.cons_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.rl_main.setVisibility(View.GONE);
                }
            });


            holder.iv_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.rl_main.setVisibility(View.VISIBLE);


                }
            });
            holder.rl_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.rl_main.setVisibility(View.GONE);
                    ((UpdateProductActivity)context).deletestockapicall(stocklist.get(position).getStockId());
                }
            });

            holder.rl_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.rl_main.setVisibility(View.GONE);
                    Intent myintent=new Intent(context, UpdateStockDetailActivity.class);
                    myintent.putExtra("stockid",stocklist.get(position).getStockId());
                    myintent.putExtra("productid",strProductid);
                    context.startActivity(myintent);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return stocklist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_variantname,tv_unitvalue,tv_unitsizevalue,tv_actualprice,tv_sellingprice,tv_availablevalue;
        ImageView iv_more;
        RelativeLayout rl_edit,rl_delete,rl_main;
        ConstraintLayout cons_main;

        public ViewHolder(View itemView) {

            super(itemView);
            tv_variantname=itemView.findViewById(R.id.tv_variantname);
            tv_unitvalue=itemView.findViewById(R.id.tv_unitvalue);
            tv_unitsizevalue=itemView.findViewById(R.id.tv_unitsizevalue);
            tv_actualprice=itemView.findViewById(R.id.tv_actualprice);
            tv_sellingprice=itemView.findViewById(R.id.tv_sellingprice);
            tv_availablevalue=itemView.findViewById(R.id.tv_availablevalue);
            iv_more=itemView.findViewById(R.id.iv_more);
            rl_edit=itemView.findViewById(R.id.rl_edits);
            rl_delete=itemView.findViewById(R.id.rl_delete);
            rl_main=itemView.findViewById(R.id.rl_edit);
            cons_main=itemView.findViewById(R.id.cons_main);

        }
    }
}