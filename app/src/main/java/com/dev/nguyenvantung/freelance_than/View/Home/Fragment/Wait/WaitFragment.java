package com.dev.nguyenvantung.freelance_than.View.Home.Fragment.Wait;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.dev.nguyenvantung.freelance_than.Presscenter.Wait.WaitPrescenterLogic;
import com.dev.nguyenvantung.freelance_than.R;
import com.dev.nguyenvantung.freelance_than.View.Edit.EditActivity;
import com.dev.nguyenvantung.freelance_than.View.Home.Fragment.HomeView;
import com.dev.nguyenvantung.freelance_than.View.ShowAll.ShowAllActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WaitFragment extends Fragment implements WaitFragmentView, SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = "WaitFragment";
    private static final int REQUESTCODE_EDIT_PERSON = 12;
    private static final int REQUESTCODE_SHOW_PERSON = 13;
    private View view;
    private Spinner wait_spinner, wait_snp_sex;
    private SwipeRefreshLayout wait_swipe;
    private ProgressBar wait_progressbar;
    private RecyclerView wait_recyclerview;

    private HomeView homeView;

    private WaitPrescenterLogic waitPrescenterLogic;
    private List<Person> personList;
    private Adapter adapter;

    public WaitFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(){
        return new WaitFragment();
    }
    public void initIMP(HomeView homeView){
        this.homeView = homeView;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_wait, container, false);
        waitPrescenterLogic = new WaitPrescenterLogic(this);

        addControls();
        addEvents();

        return view;
    }

    private void addControls() {
        wait_swipe = view.findViewById(R.id.wait_swipe);
        wait_recyclerview = view.findViewById(R.id.wait_recyclerview);
        wait_progressbar = view.findViewById(R.id.wait_progressbar);
        initRecyclerView();

        wait_spinner = view.findViewById(R.id.wait_spinner);
        List<String> listBlood = new ArrayList<>();
        listBlood.add("Lọc theo nhóm máu");
        listBlood.add("Chưa biết");
        listBlood.add("O");
        listBlood.add("A");
        listBlood.add("B");
        listBlood.add("AB");
        ArrayAdapter<String> adapter_blood = new ArrayAdapter<>
                (getContext(), android.R.layout.simple_selectable_list_item, listBlood);
        adapter_blood.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        wait_spinner.setAdapter(adapter_blood);

        wait_snp_sex = view.findViewById(R.id.wait_snp_sex);
        List<String> listSex = new ArrayList<>();
        listSex.add("Lọc theo giới tính");
        listSex.add("Nam");
        listSex.add("Nữ");
        ArrayAdapter<String> adapterSex = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_selectable_list_item, listSex);
        adapterSex.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        wait_snp_sex.setAdapter(adapterSex);
    }

    private void initRecyclerView() {
        personList = new ArrayList<>();
        waitPrescenterLogic.getDataActive();
        adapter = new Adapter(personList,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        wait_recyclerview.setHasFixedSize(true);
        wait_recyclerview.setLayoutManager(linearLayoutManager);
        wait_recyclerview.setAdapter(adapter);
    }

    private void addEvents() {
        wait_swipe.setOnRefreshListener(this);

        wait_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) swipeRefresh();
                else if (wait_snp_sex.getSelectedItemPosition() == 0) getPersonFormBlood();
                else getPersonSex();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        wait_snp_sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) swipeRefresh();
                else getPersonSex();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getPersonSex() {
        wait_progressbar.setVisibility(View.VISIBLE);
        String sex;
        if (wait_snp_sex.getSelectedItemPosition() == 1) sex = "Nam";
        else sex = "Nữ";
        Log.d(TAG, sex);
        if (wait_spinner.getSelectedItemPosition() > 0){
            personList.clear();
            waitPrescenterLogic.getPersonFromSexAndBlood(sex,
                    wait_spinner.getSelectedItemPosition() - 1, 1);
        }else {
            personList.clear();
            waitPrescenterLogic.getPersonFromSex(sex, 1);
        }
    }

    private void getPersonFormBlood() {
        wait_progressbar.setVisibility(View.VISIBLE);
        personList.clear();
        wait_progressbar.setVisibility(View.VISIBLE);
        waitPrescenterLogic.getPersonFromBlood(1, wait_spinner.getSelectedItemPosition() - 1);
    }

    @Override
    public void getDataSuccess(List<Person> personList) {
        if (personList.size() > 0) {
            this.personList.clear();
            this.personList.addAll(personList);
            wait_progressbar.setVisibility(View.GONE);
        }else {
            Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();
        wait_swipe.setRefreshing(false);
    }

    @Override
    public void getDataFail() {
        wait_progressbar.setVisibility(View.GONE);
        Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void swipeRefresh() {
        personList.clear();
        wait_progressbar.setVisibility(View.VISIBLE);
        if (wait_spinner.getSelectedItemPosition() == 0) waitPrescenterLogic.getDataActive();
        else wait_spinner.setSelection(0);
    }

    @Override
    public void Error() {
        wait_progressbar.setVisibility(View.GONE);
        Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sortSuccess() {

    }

    @Override
    public void sortFail() {

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
        if (requestCode == REQUESTCODE_EDIT_PERSON || requestCode == REQUESTCODE_SHOW_PERSON){
            wait_progressbar.setVisibility(View.VISIBLE);
            if (wait_spinner.getSelectedItemPosition() == 0 && wait_snp_sex.getSelectedItemPosition() == 0)
                swipeRefresh();
            else if (wait_spinner.getSelectedItemPosition() > 0 && wait_snp_sex.getSelectedItemPosition() == 0)
                getPersonFormBlood();
            else getPersonSex();
        }
    }

    @Override
    public void onRefresh() {
        if (wait_swipe.getY() == 0){
            swipeRefresh();
        }
    }

}
