package com.dev.nguyenvantung.freelance_than.View.Home.Fragment.NotActived;

import com.dev.nguyenvantung.freelance_than.Model.Person;

import java.util.List;

public interface NotActivedFragmentView {
    void getDataSuccess(List<Person> personList);
    void getDataFail();
    void swipeRefresh();
    void Error();
    void sortSuccess();
    void sortFail();
    void editPerson(Person person);
    void showAll(Person person);
}
