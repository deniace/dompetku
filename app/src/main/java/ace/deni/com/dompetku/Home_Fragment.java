package ace.deni.com.dompetku;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ace.deni.com.dompetku.adapter.HomeAdapter;
import ace.deni.com.dompetku.model.Saldo;
import ace.deni.com.dompetku.model.Transaksi;
import ace.deni.com.dompetku.utility.AppUtility;

public class Home_Fragment extends Fragment {
    
    private ApplicationContext context;
    private TextView textFullName, textJob, textSaldo, textTotal, textRerata;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private AppUtility appUtil = new AppUtility();
    
    private SimpleDateFormat df = new SimpleDateFormat("MM-yyyy");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = new ApplicationContext(getContext());
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        
        progressBar = view.findViewById(R.id.progress_bar);
        textFullName = view.findViewById(R.id.text_name);
        textJob = view.findViewById(R.id.text_job);
        textSaldo = view.findViewById(R.id.text_home_saldo);
        textRerata = view.findViewById(R.id.text_average);
        textTotal = view.findViewById(R.id.text_pengeluaran);
        recyclerView = view.findViewById(R.id.week_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        
        populateControls();
        return view;
    }

    private void populateControls() {
        progressBar.setVisibility(View.VISIBLE);
        textFullName.setText(context.getFullname());
        textJob.setText(context.getJob());
        Saldo saldo = new Select().from(Saldo.class).executeSingle();
        if(saldo != null){
            textSaldo.setText("Rp. "+appUtil.formatDecimal(saldo.getAmoutn()));
        }

        List<Transaksi> list = new Select().from(Transaksi.class)
                .where("Type = ?",0)
                .orderBy("id DESC").execute();

        List<Transaksi> weekList = new ArrayList<>();
        if (list != null && list.size() >0){
            double sum = 0;
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            Calendar calTxn = Calendar.getInstance();
            for(Transaksi txn : list){
                cal.setTime(date);
                calTxn.setTime(txn.getDate());
                if(df.format(txn.getDate()).equals(df.format(date))){
                    sum += txn.getAmount();
                    if(calTxn.get(Calendar.WEEK_OF_YEAR) == cal.get(Calendar.WEEK_OF_YEAR)){
                        if(weekList.size()<3){weekList.add(txn);}
                    }
                }
            }
            double avg = sum / 30.0;
            textTotal.setText("RP. "+appUtil.formatDecimal(sum));
            textRerata.setText("RP. "+appUtil.formatDecimal(avg));
        }else {
            textTotal.setText("0");
            textRerata.setText("0");
        }

        HomeAdapter.OnItemClickListener listener = new HomeAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(Transaksi txn){
                Intent intent = new Intent(getContext(), TransaksiActivity.class);
                intent.putExtra("ID_TXN", txn.getId());
                startActivityForResult(intent, 1);
            }
        };

        HomeAdapter homeAdapter = new HomeAdapter(weekList, listener);
        recyclerView.setAdapter(homeAdapter);
        progressBar.setVisibility(View.GONE);
    }


}
