package com.dev.nguyenvantung.freelance_than.View.Splash;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.dev.nguyenvantung.freelance_than.Common.Common;
import com.dev.nguyenvantung.freelance_than.R;
import com.dev.nguyenvantung.freelance_than.Retrofit.APIUtils;
import com.dev.nguyenvantung.freelance_than.View.Home.Fragment.HomeActivity;
import com.dev.nguyenvantung.freelance_than.View.Home.Fragment.HomeView;
import com.dev.nguyenvantung.freelance_than.View.Home.Fragment.New.NewFragment;
import com.dev.nguyenvantung.freelance_than.View.Login.LoginActivity;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.firebase.iid.FirebaseInstanceId;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_main);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        if (isNetWorkAvailable(this)) {
            Common.DATA_CLIENT = APIUtils.getData();

            SharedPreferences sharedPreferences = getSharedPreferences(Common.PREFERENCES_LOGIN, Context.MODE_PRIVATE);
            if (!sharedPreferences.getBoolean(Common.PREFERENCE_SIGNED, false)) {
                NextPage(LoginActivity.class);
            } else {
                NextPage(HomeActivity.class);
            }
        }else {
            notNetworkDialog();
        }
    }

    private void NextPage(Class pageClass) {
        Intent intent = new Intent(this, pageClass);
        startActivity(intent);
        finish();
    }

    private boolean isNetWorkAvailable(Context context){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    private void notNetworkDialog(){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        }else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Not network")
                .setMessage("Turn on network and restart this app")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
    }
}
