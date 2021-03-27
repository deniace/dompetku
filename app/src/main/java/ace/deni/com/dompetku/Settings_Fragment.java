package ace.deni.com.dompetku;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.Date;

import ace.deni.com.dompetku.model.Saldo;
import ace.deni.com.dompetku.model.Transaksi;


public class Settings_Fragment extends Fragment {

    private EditText textFullName, textJob, textUserName, textPassword;
    private Button buttonSave;
    private CheckBox checkReset, checkInitSaldo, loginAdmin;
    private ApplicationContext context;
    private TextView textLogout;

    @Override
    public void onCreate(Bundle savedInstanceState){super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        context = new ApplicationContext(getContext());
        textFullName = view.findViewById(R.id.text_setting_fullname);
        textJob = view.findViewById(R.id.text_setting_job);
        textUserName = view.findViewById(R.id.text_setting_username);
        textPassword = view.findViewById(R.id.text_setting_password);
        checkReset = view.findViewById(R.id.check_reset_transaksi);
        checkInitSaldo =view.findViewById(R.id.check_init_saldo);
        loginAdmin =view.findViewById(R.id.check_admin_login);
        buttonSave = view.findViewById(R.id.button_save_setting);
        textLogout = view.findViewById(R.id.text_logout);

        textLogout.setOnClickListener(logoutListener);
        populateControls();

        return view;
    }

    private void populateControls() {
        textFullName.setText(context.getFullname());
        textJob.setText(context.getJob());
        textUserName.setText(context.getUserName());
        textPassword.setText(context.getPassword());
        loginAdmin.setChecked(context.isAdminLogin());
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSetting();
            }
        });
    }

    private void populateObjects(){
        context.setFullName(textFullName.getText().toString());
        context.setJob(textJob.getText().toString());
        context.setUserName(textUserName.getText().toString());
        context.setPassword(textPassword.getText().toString());
    }

    private void saveSetting(){
        populateObjects();
        if(checkReset.isChecked()){
            new Delete().from(Transaksi.class).execute();
        }
        context.setAdminLogin(loginAdmin.isChecked());
        if(checkInitSaldo.isChecked()){
            Saldo saldo = null;
            int count = new Select().from(Saldo.class).count();
            if(count == 0){
                saldo = new Saldo(0, new Date());
                saldo.save();
            }else {
                saldo = new Select().from(Saldo.class).executeSingle();
                saldo.setAmoutn(0);
                saldo.setLastUpdate(new Date());
            }
            saldo.save();
        }
        Toast.makeText(getContext(), "Setting telah disimpan", Toast.LENGTH_SHORT).show();
    }

    public void logout(View view){

    }

    View.OnClickListener logoutListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            context.setIsLogin(false);
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    };

}
