package ace.deni.com.dompetku;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.activeandroid.query.Select;

import java.util.Date;

import ace.deni.com.dompetku.model.Saldo;

public class MainActivity extends AppCompatActivity
{
    private FragmentManager fm;
    private BottomNavigationView bottomNavigationView;
    private final Home_Fragment home_fragment = new Home_Fragment();
    private final Transaksi_Fragment transaksi_fragment = new Transaksi_Fragment();
    private final Schedule_Fragment schedule_fragment = new Schedule_Fragment();
    private final Settings_Fragment settings_fragment = new Settings_Fragment();

    private void replaceFragment (Fragment fragment){
        fm.beginTransaction().replace(R.id.frag_container, fragment).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener itemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            replaceFragment(home_fragment);
                            return true;
                        case R.id.nav_trasaction:
                            replaceFragment(transaksi_fragment);
                            return true;
                        case R.id.nav_schedule:
                            replaceFragment(schedule_fragment);
                            return true;
                        case R.id.nav_settings:
                            replaceFragment(settings_fragment);
                            return true;
                        case R.id.nav_info:
                            final Intent info = new Intent(getApplicationContext(), Info_Activity.class);
                            startActivity(info);
                            return true;
                    }

                    return false;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ApplicationContext applicationContext = new ApplicationContext(this);
        //jika user belum login maka akan diarahkan ke login
        if(applicationContext.isLogin()){
            fm = getSupportFragmentManager();
            bottomNavigationView = findViewById(R.id.bottom_navigation);
            replaceFragment(home_fragment);
            bottomNavigationView.setOnNavigationItemSelectedListener(itemSelectedListener);
        }else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        initDatabase();
    }

    private void initDatabase(){
        Saldo saldo = new Select().from(Saldo.class).executeSingle();
        if (saldo == null){
            saldo = new Saldo(0, new Date());
            saldo.save();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        transaksi_fragment.onActivityResult(requestCode, resultCode, data);
    }


//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//        Fragment fragment = null;
//
//        switch (menuItem.getItemId()){
//            case R.id.nav_home:
//                fragment = new Home_Fragment();
//                break;
//
//            case R.id.nav_trasaction:
//                fragment = new Transaksi_Fragment();
//                break;
//
//            case R.id.nav_schedule:
//                fragment = new Schedule_Fragment();
//                break;
//
//            case R.id.nav_settings:
//                fragment = new Settings_Fragment();
//                break;
//        }
//
//        //return false;
//        return loadFragment(fragment);
//    }
//
//    private boolean loadFragment(Fragment fragment){
//        if (fragment != null){
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.frag_container, fragment)
//                    .commit();
//            return true;
//        }
//        return false;
//    }
}
