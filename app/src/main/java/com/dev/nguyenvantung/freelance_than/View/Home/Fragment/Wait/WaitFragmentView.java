package com.dev.nguyenvantung.freelance_than.View.Home.Fragment.Wait;

import com.dev.nguyenvantung.freelance_than.Model.Person;

import java.util.List;

public interface WaitFragmentView {
    void getDataSuccess(List<Person> personList);
    void getDataFail();
    void swipeRefresh();
    void Error();
    void sortSuccess();
    void sortFail();
    void editPerson(Person person);
    void showAll(Person person);
}
