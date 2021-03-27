package ace.deni.com.dompetku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private ApplicationContext appContext;
    private TextView textUserId, textPassword, textMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        appContext = new ApplicationContext(this);
        textUserId = findViewById(R.id.text_login_user);
        textPassword = findViewById(R.id.text_login_password);
        textMessage = findViewById(R.id.login_message);
    }

    public void login (View view){
        if (textUserId.getText().toString().equalsIgnoreCase("admin")&& appContext.isAdminLogin()){
            if(textPassword.getText().toString().equals("admin")){
                startMainActivity();
            }else {
                textMessage.setText("Password anda salah");
            }
        }else if(textUserId.getText().toString().equalsIgnoreCase(appContext.getUserName())){
            if(textPassword.getText().toString().equals(appContext.getPassword())){
                startMainActivity();
            }else {
                textMessage.setText("password anda salah!");
            }
        }else{
            textMessage.setText("Userid tidak terdaftar!");
        }
    }


    private void startMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        appContext.setIsLogin(true);
        startActivity(intent);
        finish();
    }
}
