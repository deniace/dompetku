package ace.deni.com.dompetku.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ace.deni.com.dompetku.R;
import ace.deni.com.dompetku.model.Transaksi;
import ace.deni.com.dompetku.utility.AppUtility;

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.TransaksiHolder>{

    private final AppUtility utility = new AppUtility();
    public interface OnItemClickListener{
        public void OnItemClick(Transaksi item);
    }

    class TransaksiHolder extends RecyclerView.ViewHolder{
        public ImageView imageTxn;
        public TextView textTitle, textAmount, textDay, textDate;

        public TransaksiHolder(@NonNull View itemView){
            super(itemView);
            imageTxn = itemView.findViewById(R.id.icon_transaction);
            textTitle = itemView.findViewById(R.id.text_title);
            textAmount = itemView.findViewById(R.id.text_amount);
            textDay = itemView.findViewById(R.id.text_day);
            textDate = itemView.findViewById(R.id.text_date);
        }

        public void bind(final Transaksi item, final OnItemClickListener listener){
            imageTxn.setImageResource(item.getType() == 0 ?
            R.drawable.ic_credit_card : R.drawable.ic_wallet);
            textTitle.setText(item.getTitle());
            textAmount.setText("RP. "+ utility.formatDecimal(item.getAmount()));
            textDay.setText(utility.getDayFormat(item.getDate()));
            textDate.setText(utility.getFormateDate(item.getDate()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.OnItemClick(item);
                    }
                }
            });
        }
    }

    private List<Transaksi> list = new ArrayList<>();
    private OnItemClickListener listener;

    public TransaksiAdapter(){

    }

    public TransaksiAdapter(List<Transaksi>list){
        this.list = list;
    }

    public TransaksiAdapter(List<Transaksi> list, OnItemClickListener listener){
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TransaksiHolder onCreateViewHolder (@NonNull ViewGroup parent, int i){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaksi_list_item, parent, false);
        TransaksiHolder holder = new TransaksiHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransaksiHolder holder, int i){
        Transaksi item = list.get(i);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount(){
        return list.size();
    }

}
