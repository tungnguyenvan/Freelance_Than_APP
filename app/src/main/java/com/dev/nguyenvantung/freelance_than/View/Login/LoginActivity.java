package com.dev.nguyenvantung.freelance_than.View.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dev.nguyenvantung.freelance_than.Common.Common;
import com.dev.nguyenvantung.freelance_than.Model.Admin;
import com.dev.nguyenvantung.freelance_than.Presscenter.Login.LoginPresscenterLogic;
import com.dev.nguyenvantung.freelance_than.R;
import com.dev.nguyenvantung.freelance_than.View.Home.Fragment.HomeActivity;
import com.google.firebase.iid.FirebaseInstanceId;

public class LoginActivity extends AppCompatActivity implements ViewLoginActivity,View.OnClickListener{
    private TextInputEditText ed_username, ed_password;
    private LinearLayout layout_login;

    private LoginPresscenterLogic loginPresscenterLogic;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginPresscenterLogic = new LoginPresscenterLogic(this);
        initProgressbarDialog();

        addControlls();
        addEvents();
    }

    private void initProgressbarDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Đăng nhập");
        progressDialog.setMessage("Đang tiến hành kết nối đến server. Vui lòng chờ");
    }

    private void addControlls() {
        ed_username = findViewById(R.id.ed_username);
        ed_password = findViewById(R.id.ed_password);
        layout_login= findViewById(R.id.layout_login);
    }

    private void addEvents() {
        layout_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_login:
                loginPresscenterLogic.Login(ed_username.getText().toString(), ed_password.getText().toString());
                progressDialog.show();
                break;
        }
    }

    @Override
    public void loginSuccess(Admin admin) {
        UpdateToken(admin);
        SharedPreferences sharedPreferences = getSharedPreferences(Common.PREFERENCES_LOGIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Common.PREFERENCE_SIGNED, true);
        editor.putString(Common.PREFERENCES_LOGIN_USERNAME, ed_username.getText().toString());
        editor.putString(Common.PREFERENCES_LOGIN_PASSWORD, ed_password.getText().toString());
        editor.apply();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        progressDialog.dismiss();
        finish();
    }

    private void UpdateToken(Admin admin) {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        loginPresscenterLogic.updateToken(Integer.parseInt(admin.getId()), refreshedToken);
    }

    @Override
    public void loginFail() {
        progressDialog.dismiss();
        ed_password.setError("Bạn nhập sai tài khoảng hoặc mật khẩu");
        ed_username.setError("Bạn nhập sai tài khoảng hoặc mật khẩu");
    }

    @Override
    public void updateTokenFail() {
        Toast.makeText(this, "Update token Fail", Toast.LENGTH_SHORT).show();
    }
}
