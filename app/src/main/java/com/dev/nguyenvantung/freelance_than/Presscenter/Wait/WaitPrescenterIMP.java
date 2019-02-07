package com.dev.nguyenvantung.freelance_than.Presscenter.Wait;

public interface WaitPrescenterIMP {
    void getDataActive(int limit);
    void getPersonFromBlood(int status, int blood);
    void getPersonFromSexAndBlood(String sex, int blood, int status);
    void getPersonFromSex(String sex, int status);
}
