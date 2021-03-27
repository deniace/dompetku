package ace.deni.com.dompetku;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import ace.deni.com.dompetku.adapter.TransaksiAdapter;
import ace.deni.com.dompetku.model.Transaksi;
import ace.deni.com.dompetku.utility.DataUtility;


public class Transaksi_Fragment extends Fragment {
    private RecyclerView recyclerView;
    private TransaksiAdapter adapter;
    private List<Transaksi> list = new ArrayList<>();
    private FloatingActionButton floatButton;
    private int position = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.transaksi_fragment, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        floatButton = view.findViewById(R.id.buttonAdd);
        floatButton.setOnClickListener(addListener);
        populateData();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            Long id = data.getLongExtra("ID_TXN", 0);
            Transaksi txn = new Select().from(Transaksi.class).where("id = ?", id).executeSingle();
            if(txn == null){
                Toast.makeText(getContext(), "Transaksi error !! id = "+id, Toast.LENGTH_LONG).show();
            }else {
                if (position == -1){
                    list.add(txn);
                }else {
                    list.set(position, txn);
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    private View.OnClickListener addListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            position = -1;
            Intent intent = new Intent(getContext(), TransaksiActivity.class);
            intent.putExtra("ID_TXN", -1);
            startActivityForResult(intent, 1);
        }
    };

    private void populateData(){
//        List<Transaksi> list = DataUtility.getlistDummyTransaksi();
        list = new Select().from(Transaksi.class).execute();
        adapter = new TransaksiAdapter(list, new TransaksiAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Transaksi item) {
//                Toast.makeText(getContext(), "anda click item "+item.getTitle(), Toast.LENGTH_SHORT).show();
                position = list.indexOf(item);
                Intent intent = new Intent(getContext(), TransaksiActivity.class);
                intent.putExtra("ID_TXN", item.getId());
                startActivityForResult(intent, 1);
            }
        });
        recyclerView.setAdapter(adapter);
    }

}
