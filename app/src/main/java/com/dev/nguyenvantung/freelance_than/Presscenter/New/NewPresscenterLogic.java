package com.dev.nguyenvantung.freelance_than.Presscenter.New;

import android.util.Log;

import com.dev.nguyenvantung.freelance_than.Common.Common;
import com.dev.nguyenvantung.freelance_than.Model.Person;
import com.dev.nguyenvantung.freelance_than.View.Home.Fragment.New.ViewNewFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewPresscenterLogic implements NewPresscenterIMP {
    private static final String TAG = "NewPresscenterLogic";
    private ViewNewFragment viewNewFragment;

    public NewPresscenterLogic(ViewNewFragment viewNewFragment){
        this.viewNewFragment = viewNewFragment;
    }

    @Override
    public void GetPsonFromDate(String date) {
        Call<List<Person>> callback = Common.DATA_CLIENT.getPersonFromDate(Common.CONTROLLER_PERSON,
                Common.ACTION_GET_PERSON_FROM_DATE, date);

        callback.enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                if (response.body().size() > 0){
                    Log.d(TAG, response.body().get(0).getAddress());
                    viewNewFragment.getDataSuccess(response.body());
                }else {
                    viewNewFragment.getDataFail();
                }
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                Log.e(TAG, t.getLocalizedMessage());
                viewNewFragment.Error();
            }
        });
    }

    @Override
    public void getPersonFromBlood(String date_register, int blood) {
        Call<List<Person>> call = Common.DATA_CLIENT.getPersonSorted(Common.CONTROLLER_PERSON,
                Common.ACTION_ORDERBY_PERSON, date_register, blood);
        call.enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                if (response.body().size() > 0){
//                    viewNewFragment.sortSuccess();
                    Log.d(TAG, response.body().get(0).getAddress());
                    viewNewFragment.getDataSuccess(response.body());
                }else {
                    viewNewFragment.getDataFail();
                }
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                Log.e(TAG, t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void getPersonFromSexAndBlood(String sex, int blood, String date_register) {
        Call<List<Person>> call = Common.DATA_CLIENT.getPersonFromSexAndBlood(Common.CONTROLLER_PERSON,
                Common.ACTION_GET_PERSON_FROM_BLOOD_SEX, sex, blood, date_register);
        call.enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                if (response.body().size() > 0){
//                    viewNewFragment.sortSuccess();
                    Log.d(TAG, response.body().get(0).getAddress());
                    viewNewFragment.getDataSuccess(response.body());
                }else {
                    viewNewFragment.getDataFail();
                }
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                Log.e(TAG, t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void getPersonFromSex(String sex, String date_register) {
        Call<List<Person>> call = Common.DATA_CLIENT.getPersonFromSex(Common.CONTROLLER_PERSON,
                Common.ACTION_GET_PERSON_FROM_SEX, sex, date_register);
        call.enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                if (response.body().size() > 0){
//                    activeFragment.sortSuccess();
                    Log.d(TAG, response.body().get(0).getAddress());
                    viewNewFragment.getDataSuccess(response.body());
                }else {
                    viewNewFragment.getDataFail();
                }
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                Log.e(TAG, t.getLocalizedMessage());
            }
        });
    }
}
