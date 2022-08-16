package in.vdeliverzvendor.productlist.adapter;

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
import java.util.ArrayList;
import java.util.List;
import in.vdeliverzvendor.R;
import in.vdeliverzvendor.productcategory.model_productlist.Datum;
import in.vdeliverzvendor.productlist.ProductListWithSearchActivity;
import in.vdeliverzvendor.productview.ProductviewActivity;

public class CatProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    List<Datum> productlist;
    private Context context;

    private boolean isLoadingAdded = false;

    public CatProductListAdapter(Context context) {
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
        View v1 = inflater.inflate(R.layout.product_list_item, parent, false);
        viewHolder = new ViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Datum result = productlist.get(position); // Movie

        switch (getItemViewType(position)) {
            case ITEM:
                final ProductListpageAdapter.ViewHolder viewHolder = (ProductListpageAdapter.ViewHolder) holder;
                viewHolder.consitem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myintent=new Intent(context, ProductviewActivity.class);
                        myintent.putExtra("ProductId",result.getProductId());
                        ((ProductListWithSearchActivity)context).hidesearch();
                        context.startActivity(myintent);
                    }
                });
                if (result.getProductName()!=null)
                    viewHolder.tv_productname.setText(result.getProductName());

                if (result.getStatus()!=null && result.getStatus().equals("1")){
                    viewHolder.tv_status.setText("Available");
                    viewHolder.tv_status.setTextColor(context.getResources().getColor(R.color.green));
                }

                if (result.getStatus()!=null && result.getStatus().equals("0")){
                    viewHolder.tv_status.setText("Unavailable");
                    viewHolder.tv_status.setTextColor(context.getResources().getColor(R.color.red));
                }

                if (result.getImage()!=null){
                    Glide.with(context)
                            .load(Uri.parse(result.getImage()))
                            .fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(viewHolder.imageView);
                }

                break;

            case LOADING:
//                Do nothing
                break;

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


    public void add(Datum r) {
        productlist.add(r);
        notifyItemInserted(productlist.size() - 1);
    }

    public void addAll(List<Datum> moveResults) {
        for (Datum result : moveResults) {
            add(result);
        }
    }

    public void remove(Datum r) {
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
            add(new Datum());
        }
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = productlist.size() - 1;
        if (position!=-1) {
            Datum result = getItem(position);

            if (result != null) {
                productlist.remove(position);
                notifyItemRemoved(position);
            }
        }
    }

    public Datum getItem(int position) {

        return productlist.get(position);
    }

    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }

    public  void  clearall(){
        productlist.clear();
        notifyDataSetChanged();
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
