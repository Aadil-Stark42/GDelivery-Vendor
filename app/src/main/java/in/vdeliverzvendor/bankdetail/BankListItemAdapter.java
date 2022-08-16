package in.vdeliverzvendor.bankdetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.bankdetail.model_banklist.Bank;

public class BankListItemAdapter extends RecyclerView.Adapter<BankListItemAdapter.ViewHolder> {

    Context context;
    List<Bank> bankList;
    boolean ischeckfirst=false,ischeck=false;

    public BankListItemAdapter(Context context,List<Bank> banklist) {
        this.context = context;
        this.bankList=banklist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder vh = null;
        try {
            LayoutInflater lf = LayoutInflater.from(parent.getContext());
            View Listitem = lf.inflate(R.layout.activity_bank_listitem, parent, false);
            vh = new ViewHolder(Listitem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (bankList.get(position).getBankName()!=null && (!(bankList.get(position).getBankName().isEmpty())))
            holder.tv_bankname.setText(bankList.get(position).getBankName());
        if (bankList.get(position).getName()!=null && (!(bankList.get(position).getName().isEmpty())))
            holder.tv_holdername.setText(bankList.get(position).getName());


        if ((position==0 ) && (!(ischeck))){
            ischeckfirst=true;
            ischeck=true;
            holder.checkBox.setChecked(true);
            holder.checkBox.setTag(0);
            bankList.get(0).setIschecked(true);
            if (context instanceof  AccountSelectionActivity){
                ((AccountSelectionActivity) context).notfication();
                ((AccountSelectionActivity) context).selectedbank(String.valueOf(bankList.get(0).getBankId()));
            }
        }


        holder.checkBox.setChecked(bankList.get(position).isIschecked());
        holder.checkBox.setTag(position);

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if (ischeckfirst){//20210413 Murugeswari
                        ischeckfirst=false;
                        bankList.get(0).setIschecked(false);
                    }

                    Integer pos = (Integer)(holder).checkBox.getTag();

                    boolean isselect=false;

                    if (bankList.get(pos).isIschecked()) {
                        isselect=false;
                        bankList.get(pos).setIschecked(false);
                    }
                    else {
                        isselect=true;
                        bankList.get(pos).setIschecked(true);

                        for(int i=0;i<bankList.size();i++){
                            if(i==pos){
                                if (context instanceof  AccountSelectionActivity){
                                    ((AccountSelectionActivity) context).notfication();
                                    ((AccountSelectionActivity) context).selectedbank(String.valueOf(bankList.get(i).getBankId()));
                                }
                            }
                            else {
                                bankList.get(i).setIschecked(false);
                                if (context instanceof  AccountSelectionActivity){
                                    ((AccountSelectionActivity) context).notfication();
                                }
                            }
                        }
                    }

                    if (!(isselect)){
                        if (context instanceof  AccountSelectionActivity){
//                            Log.i("TESTADDRESS","TESTADDRESS");
                            ((AccountSelectionActivity) context).selectedbank(null);
                        }
                    }


                }catch (Exception e){
                }
            }


        });


}


    @Override
    public int getItemCount() {
        return bankList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout consitem;
        CheckBox checkBox;
        TextView tv_bankname,tv_holdername;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_bankname=itemView.findViewById(R.id.tv_bankname);
            tv_holdername=itemView.findViewById(R.id.tv_holdername);
            checkBox=itemView.findViewById(R.id.checkboxnonveg);
        }
    }
}