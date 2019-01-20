package com.dev.nguyenvantung.freelance_than.View.Home.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.dev.nguyenvantung.freelance_than.Common.Common;
import com.dev.nguyenvantung.freelance_than.R;
import com.dev.nguyenvantung.freelance_than.View.Home.Fragment.Search.SearchFragment;
import com.dev.nguyenvantung.freelance_than.View.Home.Fragment.Active.ActivedFragment;
import com.dev.nguyenvantung.freelance_than.View.Home.Fragment.New.NewFragment;
import com.dev.nguyenvantung.freelance_than.View.Home.Fragment.NotActived.NotActivedFragment;
import com.dev.nguyenvantung.freelance_than.View.Home.Fragment.Wait.WaitFragment;
import com.dev.nguyenvantung.freelance_than.View.Login.LoginActivity;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener,
        BottomNavigationView.OnNavigationItemSelectedListener, HomeView{
    private FrameLayout frame_layout;
    private AHBottomNavigation bottom_bar;
    private FloatingActionButton fb_logout;

    private FragmentManager fm;
    private Fragment active;
    private Fragment NewFragment = com.dev.nguyenvantung.freelance_than.View.Home.Fragment.New.NewFragment.newInstance();
    private Fragment NotActivedFragment = com.dev.nguyenvantung.freelance_than.View.Home.Fragment.NotActived.NotActivedFragment.newInstance();
    private Fragment ActivedFragment = com.dev.nguyenvantung.freelance_than.View.Home.Fragment.Active.ActivedFragment.newInstance();
    private Fragment SearchFragment = com.dev.nguyenvantung.freelance_than.View.Home.Fragment.Search.SearchFragment.newInstance();
    private Fragment WaitFragment = com.dev.nguyenvantung.freelance_than.View.Home.Fragment.Wait.WaitFragment.newInstance();

    private AHBottomNavigationItem itemNew;
    AHBottomNavigationItem itemFind;
    AHBottomNavigationItem itemNoSuccess;
    AHBottomNavigationItem itemWait;
    AHBottomNavigationItem itemSuccess;

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
        fb_logout = findViewById(R.id.fb_logout);
//        bottom_bar.setColored(true);

        itemNew = new AHBottomNavigationItem("Mới", R.drawable.ic_new);
        itemNoSuccess = new AHBottomNavigationItem("Đang xử lí", R.drawable.ic_progress);
        itemSuccess = new AHBottomNavigationItem("thành công", R.drawable.ic_success);
        itemWait = new AHBottomNavigationItem("Chờ", R.drawable.ic_wait);
        itemFind = new AHBottomNavigationItem("Tìm", R.drawable.ic_search_24dp);
        bottom_bar.addItem(itemNew);
        bottom_bar.addItem(itemNoSuccess);
        bottom_bar.addItem(itemSuccess);
        bottom_bar.addItem(itemWait);
        bottom_bar.addItem(itemFind);
        bottom_bar.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
        bottom_bar.setDefaultBackgroundColor(Color.WHITE);
    }

    private void addEvents() {
        bottom_bar.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position){
                    case 0:
                        if (!(active instanceof NewFragment)){
                            fm.beginTransaction().hide(active).show(NewFragment).commit();
                            active = NewFragment;
                        }
                        break;
                    case 1:
                        if (!(active instanceof NotActivedFragment)) fm.beginTransaction().hide(active).show(NotActivedFragment).commit();
                        active = NotActivedFragment;
                        break;
                    case 2:
                        if (!(active instanceof ActivedFragment)) fm.beginTransaction().hide(active).show(ActivedFragment).commit();
                        active = ActivedFragment;
                        break;
                    case 3:
                        if (!(active instanceof WaitFragment)) fm.beginTransaction().hide(active).show(WaitFragment).commit();
                        active = WaitFragment;
                        break;
                    case 4:
                        if (!(active instanceof SearchFragment)) fm.beginTransaction().hide(active).show(SearchFragment).commit();
                        active = SearchFragment;
                        break;
                }
                return true;
            }
        });

        fb_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(Common.PREFERENCES_LOGIN, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(Common.PREFERENCE_SIGNED, false);
                editor.apply();

                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
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
                if (!(active instanceof NewFragment)){
                    fm.beginTransaction().hide(active).show(NewFragment).commit();
                    active = NewFragment;
                }
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
