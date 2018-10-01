package com.dev.nguyenvantung.freelance_than.View.Splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.dev.nguyenvantung.freelance_than.Common.Common;
import com.dev.nguyenvantung.freelance_than.R;
import com.dev.nguyenvantung.freelance_than.Retrofit.APIUtils;
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

        Common.DATA_CLIENT = APIUtils.getData();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                }catch (Exception e){}
                finally {
                    NextPage(LoginActivity.class);
                }
            }
        });

        thread.start();
    }

    private void NextPage(Class pageClass) {
        Intent intent = new Intent(this, pageClass);
        startActivity(intent);
        finish();
    }
}
