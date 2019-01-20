package com.dev.nguyenvantung.freelance_than.View.Home.Fragment.Search;

import com.dev.nguyenvantung.freelance_than.Model.Person;

import java.util.List;

public interface ViewSearchFragment {
    void getPersonSuccess(List<Person> list);
    void getPersonFail();
    void swipeRefresh();
    void editPerson(Person person);
    void showAll(Person person);
}
