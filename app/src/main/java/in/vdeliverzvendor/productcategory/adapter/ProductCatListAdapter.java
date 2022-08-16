package in.vdeliverzvendor.productcategory.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.productcategory.model_catlist.Category;
import in.vdeliverzvendor.productlist.ProductListWithSearchActivity;
import in.vdeliverzvendor.utils.MnxConstant;
import in.vdeliverzvendor.utils.MnxPreferenceManager;

public class ProductCatListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int ITEM = 0;
    private static final int LOADING = 1;

    List<Category> categorylist;
    private Context context;

    private boolean isLoadingAdded = false;

    public ProductCatListAdapter(Context context) {
        this.context = context;
        categorylist = new ArrayList<>();
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
        View v1 = inflater.inflate(R.layout.productcat_listitem, parent, false);
        viewHolder = new ViewHolder(v1);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            Category result = categorylist.get(position);

            switch (getItemViewType(position)) {
                case ITEM:
                    final ViewHolder viewHolder = (ViewHolder) holder;

                    viewHolder.cons_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MnxPreferenceManager.setString(MnxConstant.ISFROM,"CATLIST");
                            MnxPreferenceManager.setString(MnxConstant.SELECTED_CAT_ID,result.getProductCategoryId());
                            Intent myintent=new Intent(context, ProductListWithSearchActivity.class);
                            context.startActivity(myintent);
                        }
                    });

                    if (result.getName()!=null)
                        viewHolder.tv_name.setText(result.getName());
                    if (result.getProductsCount()!=null && result.getProductsCount().equals("1")) {
                        viewHolder.tv_numofproduct.setText(result.getProductsCount()+" Product");
                    }
                    else if(result.getProductsCount()!=null){
                        viewHolder.tv_numofproduct.setText(result.getProductsCount()+" Products");
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
        return categorylist == null ? 0 : categorylist.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == categorylist.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    public void add(Category r) {
        categorylist.add(r);
        notifyItemInserted(categorylist.size() - 1);
    }

    public void addAll(List<Category> moveResults) {
        for (Category result : moveResults) {
            add(result);
        }
    }

    public void remove(Category r) {
        int position = categorylist.indexOf(r);
        if (position > -1) {
            categorylist.remove(position);
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
        if(categorylist.size()<ntotal) {
            if (page != 1)
                isLoadingAdded = true;
            add(new Category());
        }
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = categorylist.size() - 1;
        if (position!=-1) {
            Category result = getItem(position);

            if (result != null) {
                categorylist.remove(position);
                notifyItemRemoved(position);
            }
        }
    }

    public Category getItem(int position) {

        return categorylist.get(position);
    }

    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }

    public  void  clearall(){
        categorylist.clear();
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout cons_item;
        TextView tv_name,tv_numofproduct;

        public ViewHolder(View itemView) {
            super(itemView);
            cons_item=itemView.findViewById(R.id.cons_item);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_numofproduct=itemView.findViewById(R.id.tv_numofproduct);
        }
    }

}
