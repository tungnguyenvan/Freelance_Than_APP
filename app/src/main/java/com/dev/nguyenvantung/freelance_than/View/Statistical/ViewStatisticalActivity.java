package com.dev.nguyenvantung.freelance_than.View.Statistical;

import com.dev.nguyenvantung.freelance_than.Model.Person;

import java.util.List;

public interface ViewStatisticalActivity {
    void getDataSuccess(List<Person> personList);
    void getDataFail();
    void editPerson(Person person);
    void showAll(Person person);
}
