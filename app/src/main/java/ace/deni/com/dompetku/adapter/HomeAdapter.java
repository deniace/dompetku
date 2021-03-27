package ace.deni.com.dompetku.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import ace.deni.com.dompetku.R;
import ace.deni.com.dompetku.model.Transaksi;

/**
 * Created by Deni Supriyatna on 24/11/2018.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeHolder>{

    DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.GERMANY);
    DecimalFormat dec = new DecimalFormat("#,###.##", symbols);
    private List<Transaksi> list;
    private OnItemClickListener listener;

    public  HomeAdapter(List<Transaksi> list){this.list = list; }

    public HomeAdapter(List<Transaksi>list, OnItemClickListener listener){
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int i){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_item_layout, parent, false);
        return new HomeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeHolder holder, int i){
        Transaksi txn = list.get(i);
        holder.textAmount.setText(dec.format(txn.getAmount()));
        holder.textTitle.setText(txn.getTitle());
        if(listener != null){
            holder.bind(txn, listener);
        }
    }

    @Override
    public int getItemCount(){return list.size();}

    public class HomeHolder extends RecyclerView.ViewHolder{

        private TextView textAmount, textTitle;

        public HomeHolder (@NonNull View itemView){
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_home_title);
            textAmount = itemView.findViewById(R.id.text_home_amount);
        }

        public void bind (final Transaksi txn, final OnItemClickListener listener){
            itemView.setOnClickListener (new View.OnClickListener(){
               @Override
               public void onClick(View v){
                   listener.onItemClick(txn);
               }
            });
        }

    }
    public interface OnItemClickListener{
        public void onItemClick(Transaksi txn);
    }
}
