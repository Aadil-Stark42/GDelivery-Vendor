package in.vdeliverzvendor.myearnings.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import in.vdeliverzvendor.R;
import in.vdeliverzvendor.bankdetail.SuccessActivity;
import in.vdeliverzvendor.requestpayment.model.Withdrawal;

public class WithDrawPaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    List<Withdrawal> withdrawalList;
    private Context context;

    private boolean isLoadingAdded = false;

    public WithDrawPaginationAdapter(Context context) {
        this.context = context;
        withdrawalList = new ArrayList<>();
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
        View v1 = inflater.inflate(R.layout.listitem_myearnings_withdrawhis, parent, false);
        viewHolder = new ViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {

        try {
            Withdrawal result = withdrawalList.get(holder.getAdapterPosition()); // Movie

            switch (getItemViewType(holder.getAdapterPosition())) {
                case ITEM:
                    final ViewHolder viewHolder = (ViewHolder) holder;
                    if (result.getWithdrawalReferral() != null && (!(result.getWithdrawalReferral().isEmpty())))
                        viewHolder.tv_ordernum.setText("#" + result.getWithdrawalReferral());

                    if (result.getPaymentState()!=null && (!(result.getPaymentState().isEmpty())) &&
                            result.getPaymentState().equals("Accepted")){
                        viewHolder.tv_status.setTextColor(context.getResources().getColor(R.color.green));
                        viewHolder.tv_status.setText(result.getPaymentState());
                    }
                    else if(result.getPaymentState()!=null && (!(result.getPaymentState().isEmpty())) &&
                            result.getPaymentState().equals("Requested")){
                        viewHolder.tv_status.setTextColor(context.getResources().getColor(R.color.red));
                        viewHolder.tv_status.setText(result.getPaymentState());
                    }
                    else{
                        viewHolder.tv_status.setTextColor(context.getResources().getColor(R.color.green));
                        viewHolder.tv_status.setText(result.getPaymentState());
                    }

                    if (result.getAmount()!=null && (!(result.getAmount().isEmpty())))
                        viewHolder.tv_price.setText(result.getAmount());

                    if (result.getDate()!=null) {
                        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-mm-dd");
                        Date date = fmt.parse(result.getDate());
                        boolean istoday=istoday(date);
                        if (istoday){
                            viewHolder.tv_date.setText("Today");
                            viewHolder.tv_date.setTextColor(context.getResources().getColor(R.color.brown));
                        }
                        else if(isYesterday(date)){
                            viewHolder.tv_date.setText("YesterDay");
                            viewHolder.tv_date.setTextColor(context.getResources().getColor(R.color.apptextcoloor));
                        }
                        else {
                            viewHolder.tv_date.setText(result.getDate()+" ");
                            viewHolder.tv_date.setTextColor(context.getResources().getColor(R.color.apptextcoloor));
                        }
                    }

                    viewHolder.consitem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent myintent=new Intent(context, SuccessActivity.class);
                            myintent.putExtra("withdrawid",withdrawalList.get(holder.getAdapterPosition()).getWithdrawalId());
                            context.startActivity(myintent);
                        }
                    });

                    break;
                case LOADING:
                    break;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return withdrawalList == null ? 0 : withdrawalList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == withdrawalList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    public void add(Withdrawal r) {
        withdrawalList.add(r);
        notifyItemInserted(withdrawalList.size() - 1);
    }

    public void addAll(List<Withdrawal> moveResults) {
        for (Withdrawal result : moveResults) {
            add(result);
        }
    }

    public void remove(Withdrawal r) {
        int position = withdrawalList.indexOf(r);
        if (position > -1) {
            withdrawalList.remove(position);
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
        if(withdrawalList.size()<ntotal) {
            if (page != 1)
                isLoadingAdded = true;
            add(new Withdrawal());
        }
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
        int position = withdrawalList.size() - 1;
        Withdrawal result = getItem(position);

        if (result != null) {
            withdrawalList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Withdrawal getItem(int position) {
        return withdrawalList.get(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout consitem;
        TextView tv_ordernum,tv_date,tv_status,tv_price;


        public ViewHolder(View itemView) {
            super(itemView);
            consitem=itemView.findViewById(R.id.consitem);
            tv_status=itemView.findViewById(R.id.tv_status);
            tv_ordernum=itemView.findViewById(R.id.tv_ordernum);
            tv_date=itemView.findViewById(R.id.tv_amount);
            tv_price=itemView.findViewById(R.id.tv_price);

        }
    }
    public  void  clearall(){
        withdrawalList.clear();
        notifyDataSetChanged();
    }

    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }


    public static boolean istoday (Date d) {
        return  DateUtils.isToday(d.getTime());
    }

    public static boolean isYesterday(Date d) {
        return DateUtils.isToday(d.getTime() + DateUtils.DAY_IN_MILLIS);
    }
}