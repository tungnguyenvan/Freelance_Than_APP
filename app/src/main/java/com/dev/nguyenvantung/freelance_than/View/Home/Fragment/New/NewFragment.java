package com.dev.nguyenvantung.freelance_than.View.Home.Fragment.New;



import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.nguyenvantung.freelance_than.Adapter.Adapter;
import com.dev.nguyenvantung.freelance_than.Common.Common;
import com.dev.nguyenvantung.freelance_than.Model.Person;
import com.dev.nguyenvantung.freelance_than.Presscenter.New.NewPresscenterLogic;
import com.dev.nguyenvantung.freelance_than.R;
import com.dev.nguyenvantung.freelance_than.View.Edit.EditActivity;
import com.dev.nguyenvantung.freelance_than.View.Home.Fragment.HomeView;
import com.dev.nguyenvantung.freelance_than.View.ShowAll.ShowAllActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewFragment extends Fragment implements ViewNewFragment, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = "NEWFRAGMENT";
    private static final int REQUESTCODE_EDIT_PERSON = 12;
    private static final int REQUESTCODE_SHOW_PERSON = 13;
    private View view;
    private ImageView new_btn_timer;
    private TextView new_txt_title;
    private RecyclerView new_recyclerview;
    private ProgressBar new_progressbar;
    private SwipeRefreshLayout new_swipe;

    private HomeView homeView;

    private NewPresscenterLogic newPresscenterLogic;
    private Date date = new Date();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private String dateQuery = simpleDateFormat.format(date);
    private DatePickerDialog datePickerDialog;

    private List<Person> personList;
    private Adapter adapter;

    public NewFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(){
        return new NewFragment();
    }
    public void initIMP(HomeView homeView){
        this.homeView = homeView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new, container, false);
        newPresscenterLogic = new NewPresscenterLogic(this);

        addControlls();
        addEvents();
        setUpDatePicker();
        return view;
    }

    private void setUpDatePicker() {
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                dateQuery = year + "/" + month + "/" + dayOfMonth;
                new_txt_title.setText(dateQuery);
                new_progressbar.setVisibility(View.VISIBLE);
                personList.clear();
                adapter.notifyDataSetChanged();
                newPresscenterLogic.GetPsonFromDate(dateQuery);
            }
        };

        //xu li datepicker
        String[] s = dateQuery.split("/");
        int year = Integer.parseInt(s[0]);
        int month = Integer.parseInt(s[1]) - 1;
        int day = Integer.parseInt(s[2]);

        datePickerDialog = new DatePickerDialog(getContext(), callback, year, month, day);
        datePickerDialog.setTitle("chọn ngày");
    }

    private void addControlls() {
        new_swipe     = view.findViewById(R.id.new_swipe);
        new_btn_timer = view.findViewById(R.id.new_btn_timer);
        new_txt_title = view.findViewById(R.id.new_txt_title);
        new_txt_title.setText(dateQuery);
        new_progressbar= view.findViewById(R.id.new_progressbar);
        new_recyclerview= view.findViewById(R.id.new_recyclerview);
        initRecyclerview();
    }

    private void initRecyclerview() {
        personList = new ArrayList<>();
        newPresscenterLogic.GetPsonFromDate(dateQuery);
        adapter = new Adapter(personList,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        new_recyclerview.setHasFixedSize(true);
        new_recyclerview.setLayoutManager(linearLayoutManager);
        new_recyclerview.setAdapter(adapter);
    }

    private void addEvents() {
        new_btn_timer.setOnClickListener(this);
        new_swipe.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_btn_timer:
                datePickerDialog.show();
                break;
        }
    }

    private void SortList() {

    }

    @Override
    public void getDataSuccess(List<Person> personList) {
        if (personList.size() > 0) {
            this.personList.addAll(personList);
            adapter.notifyDataSetChanged();
            new_progressbar.setVisibility(View.GONE);
        }else {
            Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        }
        new_swipe.setRefreshing(false);
    }

    @Override
    public void getDataFail() {
        new_progressbar.setVisibility(View.GONE);
        Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void swipeRefresh() {
        personList.clear();
        newPresscenterLogic.GetPsonFromDate(dateQuery);
    }

    @Override
    public void Error() {
        new_progressbar.setVisibility(View.GONE);
        Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
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
        if (new_swipe.getY() == 0){
            swipeRefresh();
        }
    }
}
