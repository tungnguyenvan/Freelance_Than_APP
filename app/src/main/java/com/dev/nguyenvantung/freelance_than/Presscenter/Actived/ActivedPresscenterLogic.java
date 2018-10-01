package com.dev.nguyenvantung.freelance_than.Presscenter.Actived;

import android.util.Log;

import com.dev.nguyenvantung.freelance_than.Common.Common;
import com.dev.nguyenvantung.freelance_than.Model.Person;
import com.dev.nguyenvantung.freelance_than.View.Home.Fragment.Active.ViewActiveFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivedPresscenterLogic implements ActivedPresscenterIMP {

    private static final String TAG = "NewPresscenterLogic";
    private ViewActiveFragment activeFragment;

    public ActivedPresscenterLogic(ViewActiveFragment activeFragment) {
        this.activeFragment = activeFragment;
    }

    @Override
    public void getDataActive() {
        Call<List<Person>> call = Common.DATA_CLIENT.getPersonStatus(Common.CONTROLLER_PERSON,
                Common.ACTION_GET_PERSON_STATUS, 2);
        call.enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                if (response.body().size() > 0){
                    Log.d(TAG, response.body().get(0).getAddress());
                    activeFragment.getDataSuccess(response.body());
                }else {
                    activeFragment.getDataFail();
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
                    activeFragment.sortSuccess();
                    Log.d(TAG, response.body().get(0).getAddress());
                    activeFragment.getDataSuccess(response.body());
                }else {
                    activeFragment.getDataFail();
                }
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                Log.e(TAG, t.getLocalizedMessage());
            }
        });
    }
}
