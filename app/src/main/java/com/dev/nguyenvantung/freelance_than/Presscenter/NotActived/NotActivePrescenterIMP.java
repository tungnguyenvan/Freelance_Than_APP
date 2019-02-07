package com.dev.nguyenvantung.freelance_than.Presscenter.NotActived;

public interface NotActivePrescenterIMP {
    void getDataActive(int limit);
    void getPersonFromBlood(int status, int blood);
    void getPersonFromSexAndBlood(String sex, int blood, int status);
    void getPersonFromSex(String sex, int status);
}
