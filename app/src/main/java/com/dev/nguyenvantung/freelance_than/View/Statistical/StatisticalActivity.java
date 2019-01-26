package com.dev.nguyenvantung.freelance_than.View.Statistical;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.nguyenvantung.freelance_than.Adapter.Adapter;
import com.dev.nguyenvantung.freelance_than.Common.Common;
import com.dev.nguyenvantung.freelance_than.Model.Person;
import com.dev.nguyenvantung.freelance_than.Presscenter.Statistical.StatisticalPersenterLogic;
import com.dev.nguyenvantung.freelance_than.R;
import com.dev.nguyenvantung.freelance_than.View.Edit.EditActivity;
import com.dev.nguyenvantung.freelance_than.View.ShowAll.ShowAllActivity;

import java.util.ArrayList;
import java.util.List;

public class StatisticalActivity extends AppCompatActivity implements ViewStatisticalActivity {
    private static final String TAG = StatisticalActivity.class.getName();
    private static final int REQUESTCODE_EDIT_PERSON = 12;
    private static final int REQUESTCODE_SHOW_PERSON = 13;

    private TextView statistical_txt_title;
    private RecyclerView statistical_recyclerview;
    private ProgressBar statistical_progressbar;
    private ImageView statistical_back;

    private StatisticalPersenterLogic statisticalPersenterLogic;
    private List<Person> personList;
    private Adapter activedAdapter;

    private int status;
    private String fromDate;
    private String toDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistical);

        addControlls();
        addEvents();
    }

    private void addControlls() {
        getDateFromIntent();

        statistical_txt_title = findViewById(R.id.statistical_txt_title);
        statistical_recyclerview = findViewById(R.id.statistical_recyclerview);
        statistical_progressbar = findViewById(R.id.statistical_progressbar);
        statistical_back = findViewById(R.id.statistical_back);

        initRecyclerView();
    }

    private void getDateFromIntent() {
        Intent i = getIntent();
        status = i.getIntExtra(Common.STATUS, -2);
        fromDate = i.getStringExtra(Common.FROMDAY);
        toDate = i.getStringExtra(Common.TODAY);

        switch (status){
            case -1:
                statistical_txt_title.append(" Chưa xem");
                break;
            case 0:
                statistical_txt_title.append(" Đang xử lí");
                break;
            case 1:
                statistical_txt_title.append(" Chờ");
                break;
            case 2:
                statistical_txt_title.append(" Thành công");
                break;
        }
    }

    private void initRecyclerView() {
        statisticalPersenterLogic = new StatisticalPersenterLogic(this);
        personList = new ArrayList<>();

        if (fromDate == null && toDate == null) {
            statisticalPersenterLogic.getAllPersonFromStatus(status);
        }else {
            statisticalPersenterLogic.getPersonRangeDate(status, fromDate, toDate);
        }

        activedAdapter = new Adapter(personList,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        statistical_recyclerview.setHasFixedSize(true);
        statistical_recyclerview.setLayoutManager(linearLayoutManager);
        statistical_recyclerview.setAdapter(activedAdapter);
    }

    private void addEvents() {
        statistical_back.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    @Override
    public void getDataSuccess(List<Person> personList) {
        if (personList.size() > 0) {
            this.personList.clear();
            this.personList.addAll(personList);
        }else {
            Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        }
        activedAdapter.notifyDataSetChanged();
        statistical_progressbar.setVisibility(View.GONE);
    }

    @Override
    public void getDataFail() {
        statistical_progressbar.setVisibility(View.GONE);
        Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        activedAdapter.notifyDataSetChanged();
    }

    @Override
    public void editPerson(Person person) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra(Common.INTENT_PERSON, person);
        startActivityForResult(intent, REQUESTCODE_EDIT_PERSON);
    }

    @Override
    public void showAll(Person person) {
        Intent intent = new Intent(this, ShowAllActivity.class);
        intent.putExtra(Common.INTENT_PERSON, person);
        startActivityForResult(intent, REQUESTCODE_SHOW_PERSON);
    }
}
