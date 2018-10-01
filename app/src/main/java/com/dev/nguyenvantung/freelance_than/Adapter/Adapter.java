package com.dev.nguyenvantung.freelance_than.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.nguyenvantung.freelance_than.Model.Person;
import com.dev.nguyenvantung.freelance_than.Presscenter.Actived.ActivedPresscenterIMP;
import com.dev.nguyenvantung.freelance_than.R;
import com.dev.nguyenvantung.freelance_than.View.Home.Fragment.Active.ViewActiveFragment;
import com.dev.nguyenvantung.freelance_than.View.Home.Fragment.New.ViewNewFragment;
import com.dev.nguyenvantung.freelance_than.View.Home.Fragment.NotActived.NotActivedFragment;
import com.dev.nguyenvantung.freelance_than.View.Home.Fragment.Search.ViewSearchFragment;
import com.dev.nguyenvantung.freelance_than.View.Home.Fragment.Wait.WaitFragmentView;
import com.dev.nguyenvantung.freelance_than.ViewHolder.PersonVewholder;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<PersonVewholder> {
    private List<Person> personList;
    private ViewNewFragment viewNewFragment;
    private ViewActiveFragment viewActiveFragment;
    private NotActivedFragment notActivedFragment;
    private ViewSearchFragment viewSearchFragment;
    private WaitFragmentView wait_recyclerview;


    public Adapter(List<Person> personList, ViewNewFragment viewNewFragment){
        this.personList = personList;
        this.viewNewFragment = viewNewFragment;
    }

    public Adapter(List<Person> personList, ViewActiveFragment viewActiveFragment){
        this.personList = personList;
        this.viewActiveFragment = viewActiveFragment;
    }

    public Adapter(List<Person> personList, ViewSearchFragment viewSearchFragment){
        this.personList = personList;
        this.viewSearchFragment = viewSearchFragment;
    }

    public Adapter(List<Person> personList, NotActivedFragment notActivedFragment){
        this.personList = personList;
        this.notActivedFragment = notActivedFragment;
    }

    public Adapter(List<Person> personList, WaitFragmentView wait_recyclerview){
        this.personList = personList;
        this.wait_recyclerview = wait_recyclerview;
    }

    @NonNull
    @Override
    public PersonVewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.person_item, viewGroup, false);
        PersonVewholder personVewholder = null;
        if (viewNewFragment != null) personVewholder = new PersonVewholder(view, viewNewFragment);
        if (viewActiveFragment != null) personVewholder = new PersonVewholder(view, viewActiveFragment);
        if (notActivedFragment != null) personVewholder = new PersonVewholder(view, notActivedFragment);
        if (viewSearchFragment != null) personVewholder = new PersonVewholder(view, viewSearchFragment);
        if (wait_recyclerview != null) personVewholder = new PersonVewholder(view, wait_recyclerview);
            return personVewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull PersonVewholder personVewholder, int i) {
        personVewholder.initData(personList.get(i));
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }
}
