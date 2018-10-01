package com.dev.nguyenvantung.freelance_than.View.Home.Fragment.NotActived;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.dev.nguyenvantung.freelance_than.Adapter.Adapter;
import com.dev.nguyenvantung.freelance_than.Common.Common;
import com.dev.nguyenvantung.freelance_than.Model.Person;
import com.dev.nguyenvantung.freelance_than.Presscenter.NotActived.NotActivePrescenterLogic;
import com.dev.nguyenvantung.freelance_than.R;
import com.dev.nguyenvantung.freelance_than.View.Edit.EditActivity;
import com.dev.nguyenvantung.freelance_than.View.Home.Fragment.HomeView;
import com.dev.nguyenvantung.freelance_than.View.ShowAll.ShowAllActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotActivedFragment extends Fragment implements NotActivedFragmentView,
        SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = "ActivedFragment";
    private static final int REQUESTCODE_EDIT_PERSON = 12;
    private static final int REQUESTCODE_SHOW_PERSON = 13;
    private View view;
    private RecyclerView not_active_recyclerview;
    private ProgressBar not_active_progressbar;
    private SwipeRefreshLayout not_active_swipe;
    private Button not_active_sort;
    private Spinner not_active_spinner;

    private HomeView homeView;

    private NotActivePrescenterLogic notActivePrescenterLogic;
    private List<Person> personList;
    private Adapter adapter;

    public NotActivedFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(){
        return new NotActivedFragment();
    }
    public void initIMP(HomeView homeView){
        this.homeView = homeView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_actice_false, container, false);
        notActivePrescenterLogic = new NotActivePrescenterLogic(this);

        addControlls();
        addEvents();

        return view;
    }

    private void addControlls() {
        not_active_swipe     = view.findViewById(R.id.not_active_swipe);
        not_active_progressbar= view.findViewById(R.id.not_active_progressbar);
        not_active_recyclerview= view.findViewById(R.id.notactive_recyclerview);
//        not_active_sort = view.findViewById(R.id.not_active_sort);
        initRecyclerview();


        not_active_spinner = view.findViewById(R.id.not_active_spinner);
        List<String> listBlood = new ArrayList<>();
        listBlood.add("Lọc theo nhóm máu");
        listBlood.add("Chưa biết");
        listBlood.add("O");
        listBlood.add("A");
        listBlood.add("B");
        listBlood.add("AB");
        ArrayAdapter<String> adapterBlood = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_selectable_list_item, listBlood);
        adapterBlood.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        not_active_spinner.setAdapter(adapterBlood);
    }

    private void initRecyclerview() {
        personList = new ArrayList<>();
        notActivePrescenterLogic.getDataActive();
        adapter = new Adapter(personList,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        not_active_recyclerview.setHasFixedSize(true);
        not_active_recyclerview.setLayoutManager(linearLayoutManager);
        not_active_recyclerview.setAdapter(adapter);
    }

    private void addEvents() {
        not_active_swipe.setOnRefreshListener(this);
//        not_active_sort.setOnClickListener(this);

        not_active_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) swipeRefresh();
                else getPersonFormBlood(position - 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getPersonFormBlood(int blood) {
        personList.clear();
        not_active_progressbar.setVisibility(View.VISIBLE);
        notActivePrescenterLogic.getPersonFromBlood(0, blood);
    }

    @Override
    public void getDataSuccess(List<Person> personList) {
        if (personList.size() > 0) {
            this.personList.addAll(personList);
            adapter.notifyDataSetChanged();
            not_active_progressbar.setVisibility(View.GONE);
        }else {
            Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        }
        not_active_swipe.setRefreshing(false);
    }

    @Override
    public void getDataFail() {
        not_active_progressbar.setVisibility(View.GONE);
        Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void swipeRefresh() {
        personList.clear();
        not_active_progressbar.setVisibility(View.VISIBLE);
        if (not_active_spinner.getSelectedItemPosition() == 0) notActivePrescenterLogic.getDataActive();
        else not_active_spinner.setSelection(0);
    }

    @Override
    public void Error() {
        not_active_progressbar.setVisibility(View.GONE);
        Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sortSuccess() {
        Toast.makeText(getContext(), "Sắp xếp thành công", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sortFail() {
        Toast.makeText(getContext(), "Sắp xếp thất bại", Toast.LENGTH_SHORT).show();
        swipeRefresh();
    }

    @Override
    public void onRefresh() {
        if (not_active_swipe.getY() == 0){
            swipeRefresh();
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
}
