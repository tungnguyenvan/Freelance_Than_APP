package com.dev.nguyenvantung.freelance_than.Presscenter.New;

public interface NewPresscenterIMP {
    void GetPsonFromDate(String date);
    void getPersonFromBlood(String date_register, int blood);
    void getPersonFromSexAndBlood(String sex, int blood, String date_register);
    void getPersonFromSex(String sex, String date_register);
}
