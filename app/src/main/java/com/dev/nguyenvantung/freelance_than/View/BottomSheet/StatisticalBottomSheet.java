package com.dev.nguyenvantung.freelance_than.View.BottomSheet;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.nguyenvantung.freelance_than.Common.Common;
import com.dev.nguyenvantung.freelance_than.Presscenter.BottomSheet.BottomSheetPresscenterLogic;
import com.dev.nguyenvantung.freelance_than.R;
import com.dev.nguyenvantung.freelance_than.View.Statistical.StatisticalActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StatisticalBottomSheet extends BottomSheetDialogFragment implements StatisticalBottomSheetView {
    private View mView;
    private int mStatus;
    private SimpleDateFormat simpleDateFormat;
    private Date date;
    private String dateQuery;
    private boolean mFrom = true;
    private BottomSheetPresscenterLogic mPresenter;

    private TextView mTitle;
    private TextView mTotal, mToTalAll;
    private Button mBtnFrom;
    private Button mBtnTo;
    private Button mBtnSee;
    private DatePickerDialog datePickerDialog;
    private ProgressBar mProgressbar;
    private RelativeLayout bottom_sheet_layout_all, bottom_sheet_layout_statistical;

    public StatisticalBottomSheet() {

    }

    public void setTypeStatistical(int mStatus){
        this.mStatus = mStatus;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView  = inflater.inflate(R.layout.bottom_sheet_modal, container, false);

        addControlls();
        setUpDatePicker();
        addEvents();

        return mView;
    }


    private void addControlls() {
        mTitle = mView.findViewById(R.id.bottom_sheet_title);
        mTotal = mView.findViewById(R.id.bottom_sheet_total);
        mToTalAll = mView.findViewById(R.id.bottom_sheet_totalall);
        mBtnFrom = mView.findViewById(R.id.bottom_sheet_from);
        mBtnTo = mView.findViewById(R.id.bottom_sheet_to);
        mBtnSee = mView.findViewById(R.id.bottom_sheet_see);
        mProgressbar = mView.findViewById(R.id.bottom_sheet_progressbar);
        mProgressbar.setVisibility(View.VISIBLE);

        bottom_sheet_layout_all = mView.findViewById(R.id.bottom_sheet_layout_all);
        bottom_sheet_layout_statistical = mView.findViewById(R.id.bottom_sheet_layout_statistical);

        date = new Date();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        mBtnFrom.setText(simpleDateFormat.format(date));
        mBtnTo.setText(simpleDateFormat.format(date));
        dateQuery = simpleDateFormat.format(date);

        mPresenter = new BottomSheetPresscenterLogic(this);
        mPresenter.getTotalPerson(mStatus, convertDate(mBtnFrom.getText().toString()), convertDate(mBtnTo.getText().toString()));
        mPresenter.getTotalAll(mStatus);

        switch (mStatus){
            case -1:
                mTitle.append(" Chưa xem");
                break;
            case 0:
                mTitle.append(" Đang xử lí");
                break;
            case 1:
                mTitle.append(" Chờ");
                break;
            case 2:
                mTitle.append(" Thành công");
                break;
        }
    }

    private void setUpDatePicker() {
        DatePickerDialog.OnDateSetListener callback = (view, year, month, dayOfMonth) -> {
            month += 1;
            dateQuery = year + "/" + month + "/" + dayOfMonth;
            if (mFrom) mBtnFrom.setText(dayOfMonth + "/" + month + "/" + year);
            else mBtnTo.setText(dayOfMonth + "/" + month + "/" + year);
        };

        //xu li datepicker
        String[] s = dateQuery.split("/");
        int year = Integer.parseInt(s[2]);
        int month = Integer.parseInt(s[1]) - 1;
        int day = Integer.parseInt(s[0]);

        datePickerDialog = new DatePickerDialog(getContext(), callback, year, month, day);
        datePickerDialog.setTitle("chọn ngày");
    }

    private void addEvents() {
        mBtnFrom.setOnClickListener((v)->{
            mFrom = true;
            datePickerDialog.show();
        });

        mBtnTo.setOnClickListener((v)->{
            mFrom = false;
            datePickerDialog.show();
        });

        mBtnSee.setOnClickListener((v)->{
            mProgressbar.setVisibility(View.VISIBLE);
            mPresenter.getTotalPerson(mStatus, convertDate(mBtnFrom.getText().toString()),
                    convertDate(mBtnTo.getText().toString()));
        });

        bottom_sheet_layout_all.setOnClickListener(v -> {
            Intent i = new Intent(mView.getContext(), StatisticalActivity.class);
            i.putExtra(Common.STATUS, mStatus);
            startActivity(i);
        });

        bottom_sheet_layout_statistical.setOnClickListener(v -> {
            Intent i = new Intent(mView.getContext(), StatisticalActivity.class);
            i.putExtra(Common.STATUS, mStatus);
            i.putExtra(Common.FROMDAY, convertDate(mBtnFrom.getText().toString()));
            i.putExtra(Common.TODAY, convertDate(mBtnTo.getText().toString()));
            startActivity(i);
        });
    }

    private String convertDate(String date){
        String[] s = date.split("/");
        int year = Integer.parseInt(s[2]);
        int month = Integer.parseInt(s[1]);
        int day = Integer.parseInt(s[0]);
        Log.d("REQUEST STATISTICAL", year + "/" + month + "/" + day + "--  " + mStatus);
        return year + "/" + month + "/" + day;
    }

    @Override
    public void getTotalSuccess(int total) {
        Log.d("TOTAL: ", String.valueOf(total));
        mTotal.setText(String.valueOf(total));
        mProgressbar.setVisibility(View.GONE);
    }

    @Override
    public void getTotalFail() {
        Toast.makeText(mView.getContext(), "Get total fail, please try again", Toast.LENGTH_SHORT).show();
        mProgressbar.setVisibility(View.GONE);
    }

    @Override
    public void getTotallAllSuccess(int total) {
        mToTalAll.setText(String.valueOf(total));
    }

    @Override
    public void getTotalAllFail() {
        Toast.makeText(mView.getContext(), "Get total all fail, please try again", Toast.LENGTH_SHORT).show();
    }
}
