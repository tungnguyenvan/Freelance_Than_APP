package com.dev.nguyenvantung.freelance_than.Presscenter.Login;

import android.util.Log;

import com.dev.nguyenvantung.freelance_than.Common.Common;
import com.dev.nguyenvantung.freelance_than.Model.Admin;
import com.dev.nguyenvantung.freelance_than.View.Login.ViewLoginActivity;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresscenterLogic implements LoginPresscenterIMP {
    private static final String TAG = "LoginPresscenterLogic";
    private ViewLoginActivity viewLoginActivity;

    public LoginPresscenterLogic(ViewLoginActivity viewLoginActivity){
        this.viewLoginActivity = viewLoginActivity;
    }

    @Override
    public void Login(String username, String password) {
        Call<Admin> callback = Common.DATA_CLIENT.signin(Common.CONTROLLER_ADMIN,
                Common.ACTION_SIGNIN,
                username, password);

        callback.enqueue(new Callback<Admin>() {
            @Override
            public void onResponse(Call<Admin> call, Response<Admin> response) {
                if (response.body().getId() != null) {
                    if (Integer.parseInt(response.body().getId()) > 0) {
                        viewLoginActivity.loginSuccess();
                    } else {
                        viewLoginActivity.loginFail();
                    }
                }else viewLoginActivity.loginFail();
            }

            @Override
            public void onFailure(Call<Admin> call, Throwable t) {
                Log.e(TAG, t.getLocalizedMessage());
            }
        });
    }
}
