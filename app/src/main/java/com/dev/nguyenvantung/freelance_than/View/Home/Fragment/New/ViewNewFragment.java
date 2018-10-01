package com.dev.nguyenvantung.freelance_than.View.Home.Fragment.New;

import com.dev.nguyenvantung.freelance_than.Model.Person;

import java.util.List;

public interface ViewNewFragment {
    void getDataSuccess(List<Person> personList);
    void getDataFail();
    void swipeRefresh();
    void Error();
    void editPerson(Person person);
    void showAll(Person person);
}
