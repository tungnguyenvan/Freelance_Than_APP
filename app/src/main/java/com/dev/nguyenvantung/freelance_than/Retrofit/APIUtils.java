package com.dev.nguyenvantung.freelance_than.Retrofit;

import com.dev.nguyenvantung.freelance_than.Common.Common;

public class APIUtils {
    public static final String Base_url = Common.WEB_HOST;

    public static DataClient getData(){
        return RetrofitClient.getClient(Base_url).create(DataClient.class);
    }
}
