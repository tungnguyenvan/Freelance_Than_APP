package com.dev.nguyenvantung.freelance_than.View.Home.Fragment.Search;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dev.nguyenvantung.freelance_than.Adapter.Adapter;
import com.dev.nguyenvantung.freelance_than.Common.Common;
import com.dev.nguyenvantung.freelance_than.Model.Person;
import com.dev.nguyenvantung.freelance_than.Presscenter.Search.SearchPrescenterLogic;
import com.dev.nguyenvantung.freelance_than.R;
import com.dev.nguyenvantung.freelance_than.View.Edit.EditActivity;
import com.dev.nguyenvantung.freelance_than.View.Home.Fragment.HomeView;
import com.dev.nguyenvantung.freelance_than.View.ShowAll.ShowAllActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements ViewSearchFragment, SwipeRefreshLayout.OnRefreshListener{
    private static final int REQUESTCODE_EDIT_PERSON = 12;
    private static final int REQUESTCODE_SHOW_PERSON = 13;
    private View view;
    private EditText ed_search, ed_search_by_phone;
    private SwipeRefreshLayout search_swipe;

    private HomeView homeView;

    private ProgressBar search_progressbar;
    private RecyclerView search_recyclerview;
    private List<Person> personList;
    private Adapter adapter;

    private SearchPrescenterLogic searchPrescenterLogic;


    public SearchFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(){
        return new SearchFragment();
    }
    public void initIMP(HomeView homeView){
        this.homeView = homeView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);
        searchPrescenterLogic = new SearchPrescenterLogic(this);
        addControls();
        addEvents();

        return view;
    }

    private void addControls() {
        ed_search = view.findViewById(R.id.ed_search);
        ed_search_by_phone = view.findViewById(R.id.ed_search_by_phone);
        search_swipe = view.findViewById(R.id.search_swipe);
        search_recyclerview = view.findViewById(R.id.search_recyclerview);
        search_progressbar = view.findViewById(R.id.search_progressbar);
        search_progressbar.setVisibility(View.GONE);
        initRecyclerview();
    }

    private void initRecyclerview() {
        personList = new ArrayList<>();
        adapter = new Adapter(personList,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        search_recyclerview.setHasFixedSize(true);
        search_recyclerview.setLayoutManager(linearLayoutManager);
        search_recyclerview.setAdapter(adapter);
    }

    private void addEvents() {
        search_swipe.setOnRefreshListener(this);

        /**************************************/
        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.equals("") || !s.equals(" ")) {
                    personList.clear();
                    adapter.notifyDataSetChanged();
                    search_progressbar.setVisibility(View.VISIBLE);
                    searchPrescenterLogic.GetDataSearch(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /*************************************/
        ed_search_by_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.equals("") || !s.equals(" ")) {
                    personList.clear();
                    adapter.notifyDataSetChanged();
                    search_progressbar.setVisibility(View.VISIBLE);
                    searchPrescenterLogic.GetDataSearchByPhone(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    //get person
    @Override
    public void getPersonSuccess(List<Person> list) {
        this.personList.clear();
        search_progressbar.setVisibility(View.GONE);
        personList.addAll(list);
        adapter.notifyDataSetChanged();
        search_swipe.setRefreshing(false);
        Log.d("SIZE SEARCH: ", String.valueOf(personList.size()));
    }

    @Override
    public void getPersonFail() {
        Toast.makeText(getContext(), "Không có kết quả", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void swipeRefresh() {
        personList.clear();
        if (ed_search.getText().toString().isEmpty()){
            searchPrescenterLogic.GetDataSearchByPhone(ed_search_by_phone.getText().toString());
        }else {
            searchPrescenterLogic.GetDataSearch(ed_search.getText().toString());
        }
    }

    @Override
    public void editPerson(Person person) {
        Intent intent = new Intent(getContext(), EditActivity.class);
        intent.putExtra(Common.INTENT_PERSON, person);
        startActivityForResult(intent, REQUESTCODE_EDIT_PERSON);
    }

    @Override
    public void showAll(Person person) {
        Intent intent = new Intent(getContext(), ShowAllActivity.class);
        intent.putExtra(Common.INTENT_PERSON, person);
        startActivityForResult(intent, REQUESTCODE_SHOW_PERSON);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUESTCODE_EDIT_PERSON || requestCode == REQUESTCODE_SHOW_PERSON) swipeRefresh();
    }

    @Override
    public void onRefresh() {
        if (search_swipe.getY() == 0){
            swipeRefresh();
        }
    }
}
