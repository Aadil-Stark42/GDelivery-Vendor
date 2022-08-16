package in.vdeliverzvendor.slotlist.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.slot.SlotEditActivity;
import in.vdeliverzvendor.slotlist.model.Slot;
import in.vdeliverzvendor.utils.MnxConstant;
import in.vdeliverzvendor.utils.MnxPreferenceManager;

public class SlotListAdapter extends RecyclerView.Adapter<SlotListAdapter.ViewHolder> {

    Context context;
    List<Slot> slotList;

    public SlotListAdapter(Context context, List<Slot> slotList) {
        this.context = context;
        this.slotList=slotList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder vh = null;
        try {
            LayoutInflater lf = LayoutInflater.from(parent.getContext());
            View Listitem = lf.inflate(R.layout.slot_list_item, parent, false);
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
                Intent myintent=new Intent(context, SlotEditActivity.class);
                myintent.putExtra("slotid",slotList.get(position).getSlotId());
                MnxPreferenceManager.setString(MnxConstant.SELECTED_SLOT_ID,slotList.get(position).getSlotId());
                context.startActivity(myintent);
                ((Activity)context).finish();
            }
        });

        if (slotList.get(position).getActive().equals("0")) {
            holder.tv_status.setText("Unavailable");
            holder.tv_status.setTextColor(context.getResources().getColor(R.color.red));
        }
        else {
            holder.tv_status.setText("Available");
            holder.tv_status.setTextColor(context.getResources().getColor(R.color.green));
        }
        if (slotList.get(position).getWeekdays()!=null && slotList.get(position).getWeekdays().size()>0) {
            StringBuilder stringBuilder=new StringBuilder();
            for(int i=0; i<slotList.get(position).getWeekdays().size();i++){
                stringBuilder.append(slotList.get(position).getWeekdays().get(i));
                if (i!=slotList.get(position).getWeekdays().size()-1)
                    stringBuilder.append(", ");

            }
            if (stringBuilder!=null)
                holder.tv_days.setText(stringBuilder);
        }

        String strtime=null;
        if (slotList.get(position).getFrom()!=null)
            strtime=slotList.get(position).getFrom();

        if (slotList.get(position).getTo()!=null)
            strtime=strtime +" - "+slotList.get(position).getTo();
        if (strtime!=null)
            holder.tv_timing.setText(strtime);

    }


    @Override
    public int getItemCount() {
        return slotList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout consitem;
        TextView tv_days,tv_status,tv_timing;

        public ViewHolder(View itemView) {
            super(itemView);
            consitem=itemView.findViewById(R.id.consitem);
            tv_days=itemView.findViewById(R.id.tv_days);
            tv_status=itemView.findViewById(R.id.tv_status);
            tv_timing=itemView.findViewById(R.id.tv_timing);
        }
    }
}