package in.vdeliverzvendor.shoplist.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.imageview.ShapeableImageView;
import java.util.ArrayList;
import java.util.List;
import in.vdeliverzvendor.R;
import in.vdeliverzvendor.myearnings.MyEarningsActivity;
import in.vdeliverzvendor.productcategory.ProductCatListActivity;
import in.vdeliverzvendor.shoplist.model.Datum;
import in.vdeliverzvendor.shopoverview.OutletOverviewActivity;
import in.vdeliverzvendor.utils.MnxConstant;
import in.vdeliverzvendor.utils.MnxPreferenceManager;

public class OutLetListPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int ITEM = 0;
    private static final int LOADING = 1;

    List<Datum> shoplist;
    private Context context;

    private boolean isLoadingAdded = false;

    public OutLetListPageAdapter(Context context) {
        this.context = context;
        shoplist = new ArrayList<>();
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
        View v1 = inflater.inflate(R.layout.outlets_list_item, parent, false);
        viewHolder = new ViewHolder(v1);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
           Datum result = shoplist.get(position);

            switch (getItemViewType(position)) {
                case ITEM:
                    final ViewHolder viewHolder = (ViewHolder) holder;
                    viewHolder.cons_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("TESTFROM","TESTFROM"+MnxPreferenceManager.getString(MnxConstant.ISFROM,""));
                    if (MnxPreferenceManager.getString(MnxConstant.ISFROM,"").equals("OutLet")){
                        if (result.getVerified().equals("1")){
                            Intent myintent = new Intent(context, OutletOverviewActivity.class);
                            myintent.putExtra("ShopId", result.getShopId());
                            context.startActivity(myintent);
                            ((Activity) context).finish();
                        }
                        else
                            Toast.makeText(context,"Shop Not Yet Approved",Toast.LENGTH_LONG).show();
                    }
                    else if(MnxPreferenceManager.getString(MnxConstant.ISFROM,"").equals("Earnings")){
                        if (result.getVerified().equals("1")) {
                            MnxPreferenceManager.setString(MnxConstant.SELECTEDSHOPID, result.getShopId());
                            Intent myintent = new Intent(context, MyEarningsActivity.class);
                            myintent.putExtra("ShopId", result.getShopId());
                            context.startActivity(myintent);
                            ((Activity) context).finish();
                        }
                        else
                            Toast.makeText(context,"Shop Not Yet Approved",Toast.LENGTH_LONG).show();
                    }
                    else if(MnxPreferenceManager.getString(MnxConstant.ISFROM,"").equals("Slot")){
                        if (result.getVerified().equals("1")) {
                            /*MnxPreferenceManager.setString(MnxConstant.SELECTEDSHOPID, result.getShopId());
                            Intent myintent = new Intent(context, SlotListActivity.class);
                            myintent.putExtra("ShopId", result.getShopId());
                            context.startActivity(myintent);
                            ((Activity) context).finish();*/
                        }
                        else
                            Toast.makeText(context,"Shop Not Yet Approved",Toast.LENGTH_LONG).show();
                    }

                    else if(MnxPreferenceManager.getString(MnxConstant.ISFROM,"").equals("ProductCat") ||
                            MnxPreferenceManager.getString(MnxConstant.ISFROM,"").equals("CATLIST")){
                        if (result.getVerified().equals("1")) {
                            MnxPreferenceManager.setString(MnxConstant.SELECTEDSHOPID, result.getShopId());
                            Intent myintent = new Intent(context, ProductCatListActivity.class);
                            myintent.putExtra("ShopId", result.getShopId());
                            context.startActivity(myintent);
                            ((Activity) context).finish();
                        }
                        else
                            Toast.makeText(context,"Shop Not Yet Approved",Toast.LENGTH_LONG).show();
                    }
                    else if(MnxPreferenceManager.getString(MnxConstant.ISFROM,"").equals("Analytics")){
                        MnxPreferenceManager.setString(MnxConstant.SELECTEDSHOPID,result.getShopId());
                        if (result.getVerified().equals("1")) {
                            /*Intent myintent = new Intent(context, BarGraphDisplay.class);
                            myintent.putExtra("ShopId", result.getShopId());
                            myintent.putExtra("shopname", result.getShopName());
                            context.startActivity(myintent);*/

                        }
                        else
                            Toast.makeText(context,"Shop Not Yet Approved",Toast.LENGTH_LONG).show();
                    }

                }
            });

            if (result.getImage()!=null){
                Glide.with(context)
                        .load(Uri.parse(result.getImage()))
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(viewHolder.iv_shopimage);
            }
            if (result.getShopName()!=null)
                viewHolder.tv_shopname.setText(result.getShopName());
            if (result.getOpened().equals("1")){
                viewHolder.tv_status.setText("Opened");
                viewHolder.tv_status.setTextColor(context.getResources().getColor(R.color.green));
            }
            else{
                viewHolder.tv_status.setText("Closed");
                viewHolder.tv_status.setTextColor(context.getResources().getColor(R.color.red));
            }

            if (result.getVerified().equals("1")){
                viewHolder.tv_verified.setText("Approved");
                viewHolder.tv_verified.setTextColor(context.getResources().getColor(R.color.green));
            }
            else{
                viewHolder.tv_verified.setText("Pending");
                viewHolder.tv_verified.setTextColor(context.getResources().getColor(R.color.red));
            }
            if (result.getPrimary().equals("1")){
                viewHolder.iv_primary.setBackground(context.getResources().getDrawable(R.drawable.ic_shopbg));
            }
            else{
                viewHolder.iv_primary.setBackground(context.getResources().getDrawable(R.drawable.ic_outletbg));
            }
                break;

                case LOADING:
                    break;

            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return shoplist == null ? 0 : shoplist.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == shoplist.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    public void add(Datum r) {
        shoplist.add(r);
        notifyItemInserted(shoplist.size() - 1);
    }

    public void addAll(List<Datum> moveResults) {
        for (Datum result : moveResults) {
            add(result);
        }
    }

    public void remove(Datum r) {
        int position = shoplist.indexOf(r);
        if (position > -1) {
            shoplist.remove(position);
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
        if(shoplist.size()<ntotal) {
            if (page != 1)
                isLoadingAdded = true;
            add(new Datum());
        }
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = shoplist.size() - 1;
        if (position!=-1) {
            Datum result = getItem(position);

            if (result != null) {
                shoplist.remove(position);
                notifyItemRemoved(position);
            }
        }
    }

    public Datum getItem(int position) {

        return shoplist.get(position);
    }

    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }

    public  void  clearall(){
        shoplist.clear();
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout cons_item;
        ShapeableImageView iv_shopimage;
        TextView tv_shopname,tv_status,tv_verified;
        ImageView iv_primary;

        public ViewHolder(View itemView) {
            super(itemView);
            cons_item=itemView.findViewById(R.id.cons_item);
            iv_shopimage=itemView.findViewById(R.id.iv_shopimage);
            tv_shopname=itemView.findViewById(R.id.tv_shopname);
            tv_status=itemView.findViewById(R.id.tv_status);
            tv_verified=itemView.findViewById(R.id.tv_verified);
            iv_primary=itemView.findViewById(R.id.iv_primary);
        }
    }
}