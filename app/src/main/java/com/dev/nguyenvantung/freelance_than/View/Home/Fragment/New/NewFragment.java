package com.dev.nguyenvantung.freelance_than.View.Home.Fragment.New;



import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.nguyenvantung.freelance_than.Adapter.Adapter;
import com.dev.nguyenvantung.freelance_than.Common.Common;
import com.dev.nguyenvantung.freelance_than.Model.Person;
import com.dev.nguyenvantung.freelance_than.Presscenter.New.NewPresscenterLogic;
import com.dev.nguyenvantung.freelance_than.R;
import com.dev.nguyenvantung.freelance_than.View.BottomSheet.StatisticalBottomSheet;
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
    private ImageView new_btn_statiscal;
    private TextView new_txt_title;
    private RecyclerView new_recyclerview;
    private ProgressBar new_progressbar;
    private SwipeRefreshLayout new_swipe;
    private Spinner new_spinner, new_snp_sex;
    private StatisticalBottomSheet b;

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
                newPresscenterLogic.GetPsonFromDate(dateQuery);
                new_txt_title.setText(dayOfMonth + "/" + month + "/" + year);
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
        new_btn_statiscal = view.findViewById(R.id.new_btn_statiscal);
        new_txt_title = view.findViewById(R.id.new_txt_title);
        String[] arrDay = dateQuery.split("/");
        new_txt_title.setText(arrDay[2] + "/" + arrDay[1] + "/" + arrDay[0]);
        new_progressbar= view.findViewById(R.id.new_progressbar);
        new_recyclerview= view.findViewById(R.id.new_recyclerview);
        initRecyclerview();



        new_spinner = view.findViewById(R.id.new_spinner);
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
        new_spinner.setAdapter(adapterBlood);

        new_snp_sex = view.findViewById(R.id.new_snp_sex);
        List<String> listSex = new ArrayList<>();
        listSex.add("Lọc theo giới tính");
        listSex.add("Nam");
        listSex.add("Nữ");
        ArrayAdapter<String> adapterSex = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_selectable_list_item, listSex);
        adapterSex.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        new_snp_sex.setAdapter(adapterSex);
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

        new_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) swipeRefresh();
                else if (new_snp_sex.getSelectedItemPosition() == 0) getPersonFormBlood();
                else getPersonSex();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        new_snp_sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) swipeRefresh();
                else getPersonSex();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        new_btn_statiscal.setOnClickListener(this);
    }

    private void getPersonSex() {
        new_progressbar.setVisibility(View.VISIBLE);
        String sex;
        if (new_snp_sex.getSelectedItemPosition() == 1) sex = "Nam";
        else sex = "Nữ";
        Log.d(TAG, sex);
        if (new_spinner.getSelectedItemPosition() > 0){
            personList.clear();
            newPresscenterLogic.getPersonFromSexAndBlood(sex,
                    new_spinner.getSelectedItemPosition() - 1, dateQuery);
        }else {
            personList.clear();
            newPresscenterLogic.getPersonFromSex(sex, dateQuery);
        }
    }

    private void getPersonFormBlood() {
        new_progressbar.setVisibility(View.VISIBLE);
        personList.clear();
        new_progressbar.setVisibility(View.VISIBLE);
        newPresscenterLogic.getPersonFromBlood(dateQuery, new_spinner.getSelectedItemPosition() - 1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_btn_timer:
                datePickerDialog.show();
                break;
            case R.id.new_btn_statiscal:
                b = new StatisticalBottomSheet();
                b.setTypeStatistical(-1);
                b.show(getFragmentManager(), NewFragment.TAG);
                break;
        }
    }

    @Override
    public void getDataSuccess(List<Person> personList) {
        if (personList.size() > 0) {
            this.personList.clear();
            this.personList.addAll(personList);
            new_progressbar.setVisibility(View.GONE);
        }else {
            Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();
        new_swipe.setRefreshing(false);
    }

    @Override
    public void getDataFail() {
        new_progressbar.setVisibility(View.GONE);
        personList.clear();
        adapter.notifyDataSetChanged();
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
        if (requestCode == REQUESTCODE_EDIT_PERSON || requestCode == REQUESTCODE_SHOW_PERSON){
            new_progressbar.setVisibility(View.VISIBLE);
            if (new_spinner.getSelectedItemPosition() == 0 && new_snp_sex.getSelectedItemPosition() == 0)
                swipeRefresh();
            else if (new_spinner.getSelectedItemPosition() > 0 && new_snp_sex.getSelectedItemPosition() == 0)
                getPersonFormBlood();
            else getPersonSex();
        }
    }


    @Override
    public void onRefresh() {
        if (new_swipe.getY() == 0){
            swipeRefresh();
        }
    }
}
