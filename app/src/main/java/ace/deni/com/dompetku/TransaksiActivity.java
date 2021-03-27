package ace.deni.com.dompetku;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.text.ParseException;
import java.util.Date;

import ace.deni.com.dompetku.model.Saldo;
import ace.deni.com.dompetku.model.Transaksi;
import ace.deni.com.dompetku.utility.AppUtility;

public class TransaksiActivity extends AppCompatActivity {

    private AppUtility utility = new AppUtility();
    private Transaksi txn = null;
    private RadioGroup radioGroup;
    private TextInputEditText textTxnDate, textTxnTitle,textTxnDescription,textTxnAmount;
    private long idTxn;
    private boolean isNew = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);
        setTitle("Transaksi");
        radioGroup = findViewById(R.id.radio_Group);
        textTxnDate = findViewById(R.id.text_txn_date);
        textTxnTitle = findViewById(R.id.text_txn_title);
        textTxnDescription = findViewById(R.id.text_txn_description);
        textTxnAmount = findViewById(R.id.text_txt_amount);

        Intent intent = getIntent();
        idTxn = intent.getLongExtra("ID_TXN", -1);
        if(idTxn> -1){
            isNew = false;
            populateControls();
            textTxnAmount.setEnabled(false);
            radioGroup.getChildAt(0).setEnabled(false);
            radioGroup.getChildAt(1).setEnabled(false);
        }else{
            isNew = true;
            txn = new Transaksi();
            radioGroup.check(R.id.radio_credit);
            textTxnDate.setText(utility.getFormateDate(new Date()));
        }
    }

    private void populateControls() {
        txn= new Select().from(Transaksi.class).where("id = ?", idTxn).executeSingle();
        if (txn != null){
            radioGroup.check(txn.getType() == 0 ? R.id.radio_credit : R.id.radio_debet);
            textTxnDate.setText(utility.getFormateDate(txn.getDate()));
            textTxnTitle.setText(txn.getTitle());
            textTxnDescription.setText(txn.getDescription());
            textTxnAmount.setText(txn.getAmount()+"");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home){
            Intent parent = new Intent();
            setResult(1, parent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void save(View view){
        // validatiion
        double amount = 0;
        Date date = null;
        int type = radioGroup.getCheckedRadioButtonId() == R.id.radio_credit ? 0 : 1;
        try {
            date = utility.getDateFormat(textTxnDate.getText().toString());
            amount = Double.valueOf(textTxnAmount.getText().toString());
        }catch (ParseException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

        if(date == null){
            Toast.makeText(this, "Tanggal tidak valid", Toast.LENGTH_LONG).show();
        }else if(textTxnTitle.getText().toString().length() == 0){
            Toast.makeText(this, "Title Harus diisi", Toast.LENGTH_LONG).show();
        }else if(textTxnDescription.getText().toString().length() == 0){
            Toast.makeText(this, "Deskripsi haris di isi", Toast.LENGTH_LONG).show();
        }else if(amount == 0 || textTxnAmount.getText().toString().length() == 0){
            Toast.makeText(this, "amount harus diisi", Toast.LENGTH_LONG).show();
        }else{
            txn.setType(type);
            txn.setDate(date);
            txn.setTitle(textTxnTitle.getText().toString());
            txn.setDescription(textTxnDescription.getText().toString());
            txn.setAmount(amount);

            try {
                Saldo saldo = new Select().from(Saldo.class).executeSingle();
                if(saldo != null){
                    double preAmount = saldo.getAmoutn();
                    double postAmount = type == 0 ? preAmount - amount : preAmount + amount;
                    long id = txn.save();
                    if(isNew){
                        //update saldo
                        saldo.setAmoutn(postAmount);
                        saldo.setLastUpdate(new Date());
                        saldo.save();
                    }

                    Intent intent = new Intent();
                    intent.putExtra("ID_TXN", id);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }else {
                    Toast.makeText(this, "Saldo Belum Inisialisasi", Toast.LENGTH_LONG).show();
                }
            }catch (Exception ex){
                Toast.makeText(this, "Error on save !!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
