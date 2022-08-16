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
import in.vdeliverzvendor.productview.model.Stock;
import in.vdeliverzvendor.utils.MnxConstant;
import in.vdeliverzvendor.utils.MnxPreferenceManager;

public class ProductStockAdapter extends RecyclerView.Adapter<ProductStockAdapter.ViewHolder> {

    Context context;
    TextView tv_editaddress;
    List<Stock> stocklist;

    public ProductStockAdapter(Context context, List<Stock> stocklist) {
        this.context = context;
        this.stocklist=stocklist;
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
                holder.tv_variantname.setText(stocklist.get(position).getVariant()+"");
            if (stocklist.get(position).getActualPrice()!=null)
                holder.tv_actualprice.setText(cur+" "+ stocklist.get(position).getActualPrice());
            if (stocklist.get(position).getSellingPrice()!=null)
                holder.tv_sellingprice.setText(cur+" " +stocklist.get(position).getSellingPrice());
            if (stocklist.get(position).getSize()!=null)
                holder.tv_unitsizevalue.setText(stocklist.get(position).getSize());
            if (stocklist.get(position).getUnit()!=null)
                holder.tv_unitvalue.setText(stocklist.get(position).getUnit());
            if ((stocklist.get(position).getAvailable())!=null)
                holder.tv_availablevalue.setText(stocklist.get(position).getAvailable()+"");
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

        public ViewHolder(View itemView) {
            super(itemView);
            tv_variantname=itemView.findViewById(R.id.tv_variantname);
            tv_unitvalue=itemView.findViewById(R.id.tv_unitvalue);
            tv_unitsizevalue=itemView.findViewById(R.id.tv_unitsizevalue);
            tv_actualprice=itemView.findViewById(R.id.tv_actualprice);
            tv_sellingprice=itemView.findViewById(R.id.tv_sellingprice);
            tv_availablevalue=itemView.findViewById(R.id.tv_availablevalue);
            iv_more=itemView.findViewById(R.id.iv_more);
            iv_more.setVisibility(View.GONE);

        }
    }
}