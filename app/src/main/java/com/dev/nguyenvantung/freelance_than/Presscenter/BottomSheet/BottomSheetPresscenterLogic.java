package com.dev.nguyenvantung.freelance_than.Presscenter.BottomSheet;

import android.widget.Toast;

import com.dev.nguyenvantung.freelance_than.Common.Common;
import com.dev.nguyenvantung.freelance_than.Model.Total;
import com.dev.nguyenvantung.freelance_than.View.BottomSheet.StatisticalBottomSheetView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheetPresscenterLogic implements BottomSheetPresscenterIMP {
    private StatisticalBottomSheetView mView;

    public BottomSheetPresscenterLogic(StatisticalBottomSheetView mView){
        this.mView = mView;
    }

    @Override
    public void getTotalPerson(int status, String from_day, String to_day) {
        Call<Total> call = Common.DATA_CLIENT.getTotalPerson(Common.CONTROLLER_PERSON,
                Common.ACTION_GET_TOTAL_PERSON, status, from_day, to_day);
        call.enqueue(new Callback<Total>() {
            @Override
            public void onResponse(Call<Total> call, Response<Total> response) {
                getTotalSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Total> call, Throwable t) {
                getTotalFail();
            }
        });
    }

    @Override
    public void getTotalAll(int status) {
        Call<Total> call = Common.DATA_CLIENT.getTotalPersonFromStatus(
                Common.CONTROLLER_PERSON,
                Common.ACTION_GET_TOTAL_ALL,
        status);
        call.enqueue(new Callback<Total>() {
            @Override
            public void onResponse(Call<Total> call, Response<Total> response) {
                getTotalAllSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Total> call, Throwable t) {
                getTotallAllFail();
            }
        });

    }

    private void getTotalAllSuccess(Total total) {
        mView.getTotallAllSuccess(total.getTotal());
    }

    private void getTotallAllFail() {
        mView.getTotalAllFail();
    }

    private void getTotalSuccess(Total total){
        mView.getTotalSuccess(total.getTotal());
    }

    private void getTotalFail(){
        mView.getTotalFail();
    }
}
