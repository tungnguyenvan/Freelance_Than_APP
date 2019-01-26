package com.dev.nguyenvantung.freelance_than.Presscenter.Statistical;

import android.util.Log;

import com.dev.nguyenvantung.freelance_than.Common.Common;
import com.dev.nguyenvantung.freelance_than.Model.Person;
import com.dev.nguyenvantung.freelance_than.View.Statistical.ViewStatisticalActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatisticalPersenterLogic implements StatisticalPersenterIMP {
    private static final String TAG = StatisticalPersenterLogic.class.getName();
    private ViewStatisticalActivity viewStatisticalActivity;

    public StatisticalPersenterLogic(ViewStatisticalActivity viewStatisticalActivity) {
        this.viewStatisticalActivity = viewStatisticalActivity;
    }

    @Override
    public void getPersonRangeDate(int status, String fromDate, String toDate) {
        Call<List<Person>> call = Common.DATA_CLIENT.getPersonRangeDate(Common.CONTROLLER_PERSON,
                Common.ACTION_GET_PERSON_RANGE_DATE, status, fromDate, toDate);
        call.enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                if (response.body().size() > 0){
                    viewStatisticalActivity.getDataSuccess(response.body());
                }else {
                    viewStatisticalActivity.getDataFail();
                }
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    @Override
    public void getAllPersonFromStatus(int status) {
        Call<List<Person>> call = Common.DATA_CLIENT.getPersonStatus(Common.CONTROLLER_PERSON,
                Common.ACTION_GET_PERSON_STATUS, status);
        call.enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                if (response.body().size() > 0){
                    viewStatisticalActivity.getDataSuccess(response.body());
                }else {
                    viewStatisticalActivity.getDataFail();
                }
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }
}
