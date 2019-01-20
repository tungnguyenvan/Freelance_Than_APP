package com.dev.nguyenvantung.freelance_than.View.Login;

import com.dev.nguyenvantung.freelance_than.Model.Admin;

public interface ViewLoginActivity {
    void loginSuccess(Admin admin);
    void loginFail();
    void updateTokenFail();
}
