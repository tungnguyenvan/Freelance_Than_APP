package com.dev.nguyenvantung.freelance_than.Utils;

import android.util.Log;

import com.dev.nguyenvantung.freelance_than.Model.Person;

import java.util.ArrayList;
import java.util.List;

public class SortPerson {
    private static final String TAG = "SortPerson";

    public SortPerson() {
    }

    public List<Person> SortSex(List<Person> listPerson, String sex){
        List<Person> newList = new ArrayList<>();

        for (Person person : listPerson){
            Log.d(TAG, person.getSex());
            if (person.getSex().equals(sex)) newList.add(person);
        }

        return newList;
    }
}
