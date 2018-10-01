package com.dev.nguyenvantung.freelance_than.Presscenter.Search;

import android.util.Log;

import com.dev.nguyenvantung.freelance_than.Common.Common;
import com.dev.nguyenvantung.freelance_than.Model.Person;
import com.dev.nguyenvantung.freelance_than.View.Home.Fragment.Search.ViewSearchFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPrescenterLogic implements SearchPrescenterIMP {
    private ViewSearchFragment viewSearchFragment;

    public SearchPrescenterLogic(ViewSearchFragment viewSearchFragment){
        this.viewSearchFragment = viewSearchFragment;
    }

    @Override
    public void GetDataSearch(String find) {
        Call<List<Person>> callback = Common.DATA_CLIENT.searchPerson(Common.CONTROLLER_PERSON,
                Common.ACTION_SEARCH_PERSON, find);
        callback.enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                if (response.body().size() > 0) viewSearchFragment.getPersonSuccess(response.body());
                else viewSearchFragment.getPersonFail();
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                Log.e("SEARCH", t.getLocalizedMessage());
                viewSearchFragment.getPersonFail();
            }
        });
    }
}
