package com.dev.nguyenvantung.freelance_than.View.Home.Fragment;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.dev.nguyenvantung.freelance_than.R;
import com.dev.nguyenvantung.freelance_than.View.Home.Fragment.Search.SearchFragment;
import com.dev.nguyenvantung.freelance_than.View.Home.Fragment.Active.ActivedFragment;
import com.dev.nguyenvantung.freelance_than.View.Home.Fragment.New.NewFragment;
import com.dev.nguyenvantung.freelance_than.View.Home.Fragment.NotActived.NotActivedFragment;
import com.dev.nguyenvantung.freelance_than.View.Home.Fragment.Wait.WaitFragment;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener,
        BottomNavigationView.OnNavigationItemSelectedListener, HomeView{
    private FrameLayout frame_layout;
    private BottomNavigationView bottom_bar;

    private FragmentManager fm;
    private Fragment active;
    private Fragment NewFragment = com.dev.nguyenvantung.freelance_than.View.Home.Fragment.New.NewFragment.newInstance();
    private Fragment NotActivedFragment = com.dev.nguyenvantung.freelance_than.View.Home.Fragment.NotActived.NotActivedFragment.newInstance();
    private Fragment ActivedFragment = com.dev.nguyenvantung.freelance_than.View.Home.Fragment.Active.ActivedFragment.newInstance();
    private Fragment SearchFragment = com.dev.nguyenvantung.freelance_than.View.Home.Fragment.Search.SearchFragment.newInstance();
    private Fragment WaitFragment = com.dev.nguyenvantung.freelance_than.View.Home.Fragment.Wait.WaitFragment.newInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        addControlls();
        addEvents();
        addFragment();
    }

    private void addControlls() {
        frame_layout = findViewById(R.id.frame_layout);
        bottom_bar = findViewById(R.id.bottom_bar);
    }

    private void addEvents() {
        bottom_bar.setOnNavigationItemSelectedListener(this);
    }

    private void addFragment() {
        fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.frame_layout, NotActivedFragment, "NotActivedFragment").hide(NotActivedFragment).commit();
        fm.beginTransaction().add(R.id.frame_layout, ActivedFragment, "ActivedFragment").hide(ActivedFragment).commit();
        fm.beginTransaction().add(R.id.frame_layout, SearchFragment, "SearchFragment").hide(SearchFragment).commit();
        fm.beginTransaction().add(R.id.frame_layout, WaitFragment, "WaitFragment").hide(WaitFragment).commit();
        fm.beginTransaction().add(R.id.frame_layout, NewFragment, "NewFragment").commit();
        active = NewFragment;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id){
            case R.id.bottom_new:
                if (!(active instanceof NewFragment)) fm.beginTransaction().hide(active).show(NewFragment).commit();
                active = NewFragment;
                break;
            case R.id.bottom_not_active:
                if (!(active instanceof NotActivedFragment)) fm.beginTransaction().hide(active).show(NotActivedFragment).commit();
                active = NotActivedFragment;
                break;
            case R.id.bottom_actived:
                if (!(active instanceof ActivedFragment)) fm.beginTransaction().hide(active).show(ActivedFragment).commit();
                active = ActivedFragment;
                break;
            case R.id.bottom_search:
                if (!(active instanceof SearchFragment)) fm.beginTransaction().hide(active).show(SearchFragment).commit();
                active = SearchFragment;
                break;
            case R.id.bottom_wait:
                if (!(active instanceof WaitFragment)) fm.beginTransaction().hide(active).show(WaitFragment).commit();
                active = WaitFragment;
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {

    }
}
