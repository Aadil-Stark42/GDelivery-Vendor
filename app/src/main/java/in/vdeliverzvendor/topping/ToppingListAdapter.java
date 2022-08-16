package in.vdeliverzvendor.topping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.addtoppings.model_getcat.Title;

public class ToppingListAdapter extends RecyclerView.Adapter<ToppingListAdapter.ViewHolder> {

    Context context;
    List<Title> titleList;

    public ToppingListAdapter(Context context,List<Title> titleList) {
        this.context = context;
        this.titleList=titleList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder vh = null;
        try {
            LayoutInflater lf = LayoutInflater.from(parent.getContext());
            View Listitem = lf.inflate(R.layout.listitem_toppings, parent, false);
            vh = new ViewHolder(Listitem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (titleList.get(position).getName()!=null)
            holder.tv_name.setText(titleList.get(position).getName());
    }


    @Override
    public int getItemCount() {
        return titleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;

        public ViewHolder(View itemView) {

            super(itemView);
            tv_name=itemView.findViewById(R.id.tv_name);

        }
    }
}