package com.dev.nguyenvantung.freelance_than.Presscenter.NotActived;

import android.util.Log;

import com.dev.nguyenvantung.freelance_than.Common.Common;
import com.dev.nguyenvantung.freelance_than.Model.Person;
import com.dev.nguyenvantung.freelance_than.View.Home.Fragment.NotActived.NotActivedFragmentView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotActivePrescenterLogic implements NotActivePrescenterIMP {

    private static final String TAG = "NewPresscenterLogic";
    private NotActivedFragmentView notActivedFragmentView;

    public NotActivePrescenterLogic(NotActivedFragmentView notActivedFragmentView) {
        this.notActivedFragmentView = notActivedFragmentView;
    }

    @Override
    public void getDataActive(int limit) {
        Call<List<Person>> call = Common.DATA_CLIENT.getPersonStatus(Common.CONTROLLER_PERSON,
                Common.ACTION_GET_PERSON_STATUS, 0, limit);
        call.enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                if (response.body().size() > 0){
                    Log.d(TAG, response.body().get(0).getAddress());
                    notActivedFragmentView.getDataSuccess(response.body());
                }else {
                    notActivedFragmentView.getDataFail();
                }
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                Log.e(TAG, t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void getPersonFromBlood(int status, int blood) {
        Call<List<Person>> call = Common.DATA_CLIENT.getPersonSorted(Common.CONTROLLER_PERSON,
                Common.ACTION_ORDERBY_PERSON, status, blood);
        call.enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                if (response.body().size() > 0){
                    notActivedFragmentView.sortSuccess();
                    Log.d(TAG, response.body().get(0).getAddress());
                    notActivedFragmentView.getDataSuccess(response.body());
                }else {
                    notActivedFragmentView.getDataFail();
                }
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                Log.e(TAG, t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void getPersonFromSexAndBlood(String sex, int blood, int status) {
        Call<List<Person>> call = Common.DATA_CLIENT.getPersonFromSexAndBlood(Common.CONTROLLER_PERSON,
                Common.ACTION_GET_PERSON_FROM_BLOOD_SEX, sex, blood, status);
        call.enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                if (response.body().size() > 0){
                    notActivedFragmentView.sortSuccess();
                    Log.d(TAG, response.body().get(0).getAddress());
                    notActivedFragmentView.getDataSuccess(response.body());
                }else {
                    notActivedFragmentView.getDataFail();
                }
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                Log.e(TAG, t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void getPersonFromSex(String sex, int status) {
        Call<List<Person>> call = Common.DATA_CLIENT.getPersonFromSex(Common.CONTROLLER_PERSON,
                Common.ACTION_GET_PERSON_FROM_SEX, sex, status);
        call.enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                if (response.body().size() > 0){
                    notActivedFragmentView.sortSuccess();
                    Log.d(TAG, response.body().get(0).getAddress());
                    notActivedFragmentView.getDataSuccess(response.body());
                }else {
                    notActivedFragmentView.getDataFail();
                }
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                Log.e(TAG, t.getLocalizedMessage());
            }
        });
    }
}
