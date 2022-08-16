package in.vdeliverzvendor.notification;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;
import in.vdeliverzvendor.R;
import in.vdeliverzvendor.notification.model.Datum;


public class NotificationListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int ITEM = 0;
    private static final int LOADING = 1;

    List<Datum> notificationlist;
    private Context context;

    private boolean isLoadingAdded = false;


    public NotificationListAdapter(Context context) {
        this.context = context;
        notificationlist = new ArrayList<>();
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
        View v1 = inflater.inflate(R.layout.notificationlistitem, parent, false);
        viewHolder = new ViewHolder(v1);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            Datum result = notificationlist.get(position);


            switch (getItemViewType(position)) {
                case ITEM:
                    final ViewHolder viewHolder = (ViewHolder) holder;
                    if (result.getTime()!=null)
                        viewHolder.tv_time.setText(result.getTime());

                    if (result.getNotifyHead()!=null)
                        viewHolder.tv_neworderarrived.setText(result.getNotifyHead());

                    if (result.getDescription()!=null)
                        viewHolder.tv_referalnum.setText(result.getDescription());

//                    if (result.getImage()!=null){
//                        Glide.with(context)
//                                .load(Uri.parse(result.getImage()))
//                                .fitCenter()
//                                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                                .into(viewHolder.iv_img);
//                    }

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
        return notificationlist == null ? 0 : notificationlist.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == notificationlist.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    public void add(Datum r) {
        notificationlist.add(r);
        notifyItemInserted(notificationlist.size() - 1);
    }

    public void addAll(List<Datum> moveResults) {
        for (Datum result : moveResults) {
            add(result);
        }
    }

    public void remove(Datum r) {
        int position = notificationlist.indexOf(r);
        if (position > -1) {
            notificationlist.remove(position);
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
        if(notificationlist.size()<ntotal) {
            if (page != 1)
                isLoadingAdded = true;
            add(new Datum());
        }
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = notificationlist.size() - 1;
        if (position!=-1) {
            Datum result = getItem(position);

            if (result != null) {
                notificationlist.remove(position);
                notifyItemRemoved(position);
            }
        }
    }

    public Datum getItem(int position) {

        return notificationlist.get(position);
    }

    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }

    public  void  clearall(){
        notificationlist.clear();
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ShapeableImageView iv_img;
        TextView tv_neworderarrived,tv_referalnum,tv_time;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_img=itemView.findViewById(R.id.iv_img);
            tv_neworderarrived=itemView.findViewById(R.id.tv_neworderarrived);
            tv_referalnum=itemView.findViewById(R.id.tv_referalnum);
            tv_time=itemView.findViewById(R.id.tv_time);
        }
    }

}