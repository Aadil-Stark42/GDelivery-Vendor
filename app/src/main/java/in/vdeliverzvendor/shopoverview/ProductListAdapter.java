package in.vdeliverzvendor.shopoverview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.shopoverview.model_product.Shop;
import in.vdeliverzvendor.productview.ProductviewActivity;
import in.vdeliverzvendor.utils.MnxConstant;
import in.vdeliverzvendor.utils.MnxPreferenceManager;

public class ProductListAdapter  extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    Context context;
    List<Shop> shopList;

    public ProductListAdapter(Context context, List<Shop> datalist) {
        this.context = context;
        this.shopList =datalist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder vh = null;
        try {
            LayoutInflater lf = LayoutInflater.from(parent.getContext());
            View Listitem = lf.inflate(R.layout.productlistitem, parent, false);
            vh = new ViewHolder(Listitem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.consitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent=new Intent(context, ProductviewActivity.class);
                MnxPreferenceManager.setBoolean(MnxConstant.IsOutletViewFrom,true);
                myintent.putExtra("ProductId", shopList.get(0).getProducts().get(position).getId());
                context.startActivity(myintent);
            }
        });
        if (shopList.get(0).getProducts().get(position).getName()!=null)
            holder.tv_productname.setText(shopList.get(0).getProducts().get(position).getName());

        if (shopList.get(0).getProducts().get(position).getActive()!=null && shopList.get(0).getProducts().get(position).getActive().equals("1")){
            holder.tv_status.setText("Active");
            holder.tv_status.setTextColor(context.getResources().getColor(R.color.green));
        }

        if (shopList.get(0).getProducts().get(position).getActive()!=null && shopList.get(0).getProducts().get(position).getActive().equals("0")){
            holder.tv_status.setText("InActive");
            holder.tv_status.setTextColor(context.getResources().getColor(R.color.red));
        }

        if (shopList.get(0).getProducts().get(position).getImage()!=null){
            Glide.with(context)
                    .load(Uri.parse(shopList.get(0).getProducts().get(position).getImage()))
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imageView);
        }

    }


    @Override
    public int getItemCount() {
        return shopList.get(0).getProducts().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout consitem;
        ShapeableImageView imageView;
        TextView tv_productname,tv_status;


        public ViewHolder(View itemView) {
            super(itemView);
            consitem=itemView.findViewById(R.id.cons_item);
            imageView=itemView.findViewById(R.id.iv_img);
            tv_productname=itemView.findViewById(R.id.tv_productname);
            tv_status=itemView.findViewById(R.id.tv_status);
        }
    }
}