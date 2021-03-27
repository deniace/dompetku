package ace.deni.com.dompetku;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Deni Supriyatna on 17/11/2018.
 */
public class ApplicationContext {
    public static final Map<String, Object> data = new HashMap();
    public static final String FULL_NAME = "full_name";
    public static final String USER_NAME = "user_name";
    public static final String PASSWORD = "password";
    public static final String JOB = "job";
    public static final String ADMIN_LOGIN = "admin_login";
    public static final  String IS_LOGIN = "is_login";

    private boolean adminLogin = true;
    private boolean isLogin = false;

    private String fullname;
    private String job;
    private String userName;
    private String password;



    private Context context;
    private SharedPreferences preferences;

    public ApplicationContext (Context context){
        this.context = context;
        preferences = context.getSharedPreferences("DompetKu", Context.MODE_PRIVATE);
    }

    public static Map<String, Object> getData() {return data;}

    public String getFullname() {
        fullname = preferences.getString(FULL_NAME,"");
        return fullname;

    }

    public void setFullName(String name){
        this.fullname = name;
        preferences.edit().putString(FULL_NAME, name).commit();
    }

    public String getJob() {
        job = preferences.getString(JOB, "");
        return job;
    }

    public void setJob(String job) {
        this.job = job;
        preferences.edit().putString(JOB, job).commit();
    }

    public String getUserName() {
        userName = preferences.getString(USER_NAME, "");
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        preferences.edit().putString(USER_NAME, userName).commit();
    }

    public String getPassword() {
        password = preferences.getString(PASSWORD,"");
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        preferences.edit().putString(PASSWORD, password).commit();
    }

    public boolean isAdminLogin() {
        adminLogin = preferences.getBoolean(ADMIN_LOGIN, true);
        return adminLogin;
    }

    public void setAdminLogin(boolean adminLogin) {
        preferences.edit().putBoolean(ADMIN_LOGIN, adminLogin).commit();
        this.adminLogin = adminLogin;
    }

    public boolean isLogin() {
        isLogin = preferences.getBoolean(IS_LOGIN, false);
        return isLogin;
    }

    public void setIsLogin(boolean login) {
        preferences.edit().putBoolean(IS_LOGIN, login).commit();
        isLogin = login;
    }


}
