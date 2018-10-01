package com.dev.nguyenvantung.freelance_than.Presscenter.Wait;

import android.util.Log;

import com.dev.nguyenvantung.freelance_than.Common.Common;
import com.dev.nguyenvantung.freelance_than.Model.Person;
import com.dev.nguyenvantung.freelance_than.View.Home.Fragment.Wait.WaitFragmentView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WaitPrescenterLogic implements WaitPrescenterIMP {
    private static final String TAG = "WaitPrescenterLogic";
    private WaitFragmentView waitFragmentView;

    public WaitPrescenterLogic(WaitFragmentView waitFragmentView){
        this.waitFragmentView = waitFragmentView;
    }


    @Override
    public void getDataActive() {
        Call<List<Person>> call = Common.DATA_CLIENT.getPersonStatus(Common.CONTROLLER_PERSON,
                Common.ACTION_GET_PERSON_STATUS, 1);
        call.enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                if (response.body().size() > 0){
                    Log.d(TAG, response.body().get(0).getAddress());
                    waitFragmentView.getDataSuccess(response.body());
                }else {
                    waitFragmentView.getDataFail();
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
                    waitFragmentView.sortSuccess();
                    Log.d(TAG, response.body().get(0).getAddress());
                    waitFragmentView.getDataSuccess(response.body());
                }else {
                    waitFragmentView.getDataFail();
                }
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                Log.e(TAG, t.getLocalizedMessage());
            }
        });
    }
}
