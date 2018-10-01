package com.dev.nguyenvantung.freelance_than.View.ShowAll;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.dev.nguyenvantung.freelance_than.Common.Common;
import com.dev.nguyenvantung.freelance_than.Model.Message;
import com.dev.nguyenvantung.freelance_than.Model.Person;
import com.dev.nguyenvantung.freelance_than.R;
import com.dev.nguyenvantung.freelance_than.View.ViewImage.ViewImageActivity;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowAllActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ShowAllActivity";
    private TextView tt_id, tt_name, tt_blood, tt_status, tt_phone, tt_zalo,
            tt_job, tt_birthday, tt_cmnd, tt_address, tt_agress, tt_date_register, tt_description;
    private ImageView SHK, XNDS, GKS, GKH, show_img_khac1, show_img_khac2, show_img_khac3, show_img_khac4, show_img_khac5;
    private Toolbar show_all_toolbar;
    private Button show_btn_active, show_btn_wait;

    private Person person;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showall);

        person = (Person) getIntent().getSerializableExtra(Common.INTENT_PERSON);
        Log.d("test", person.getId());

        addControlls();
        addEvents();
    }

    private void addControlls() {
        tt_id = findViewById(R.id.tt_id);
        tt_name = findViewById(R.id.tt_name);
        tt_blood = findViewById(R.id.tt_blood);
        tt_status = findViewById(R.id.tt_status);
        tt_phone = findViewById(R.id.tt_phone);
        tt_zalo = findViewById(R.id.tt_zalo);
        tt_job = findViewById(R.id.tt_job);
        tt_birthday = findViewById(R.id.tt_birthday);
        tt_cmnd = findViewById(R.id.tt_cmnd);
        tt_address = findViewById(R.id.tt_address);
        tt_agress = findViewById(R.id.tt_agress);
        tt_date_register = findViewById(R.id.tt_date_register);
        tt_description = findViewById(R.id.tt_description);
        show_img_khac1 = findViewById(R.id.show_img_khac1);
        show_img_khac2 = findViewById(R.id.show_img_khac2);
        show_img_khac3 = findViewById(R.id.show_img_khac3);
        show_img_khac4 = findViewById(R.id.show_img_khac4);
        show_img_khac5 = findViewById(R.id.show_img_khac5);

        show_all_toolbar = findViewById(R.id.show_all_toolbar);

        SHK = findViewById(R.id.SHK);
        XNDS = findViewById(R.id.XNDS);
        GKS = findViewById(R.id.GKS);
        GKH = findViewById(R.id.GKH);

        show_btn_active = findViewById(R.id.show_btn_active);
        if (Integer.parseInt(person.getStatus()) == 2) show_btn_active.setEnabled(false);
        show_btn_wait = findViewById(R.id.show_btn_wait);
        if (Integer.parseInt(person.getStatus()) >= 1) show_btn_wait.setEnabled(false);

        addData();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void addEvents() {
        show_all_toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        show_all_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        show_btn_active.setOnClickListener(this);
        show_btn_wait.setOnClickListener(this);

        //SHK, XNDS, GKS, GKH
        SHK.setOnClickListener(this);
        XNDS.setOnClickListener(this);
        GKS.setOnClickListener(this);
        GKH.setOnClickListener(this);
    }

    private void addData() {
        tt_id.setText("ID: " + person.getId());
        tt_name.setText("Tên: " + person.getName());

        if (Integer.parseInt(person.getBlood()) == 1){
            tt_blood.setText("O");
        }else if (Integer.parseInt(person.getBlood()) == 2){
            tt_blood.setText("A");
        }else if (Integer.parseInt(person.getBlood()) == 3){
            tt_blood.setText("B");
        }else if (Integer.parseInt(person.getBlood()) == 4){
            tt_blood.setText("AB");
        }else if (Integer.parseInt(person.getBlood()) == 5){
            tt_blood.setText("Chưa biết");
        }

        if (Integer.parseInt(person.getStatus()) == 2){
            tt_status.setText("Đã kích hoạt");
            tt_status.setTextColor(Color.GREEN);
        }else if (Integer.parseInt(person.getStatus()) == 1){
            tt_status.setText("Chờ");
            tt_status.setTextColor(Color.BLUE);
        }else if (Integer.parseInt(person.getStatus()) == 0){
            tt_status.setText("Chưa kích hoạt");
            tt_status.setTextColor(Color.RED);
        }

        tt_phone.setText("Số điện thoại: " + person.getPhone());
        tt_zalo.setText("Zalo: " + person.getZalo());
        tt_job.setText("Công việc: " + person.getJob());
        tt_birthday.setText("Ngày sinh: " + person.getBirthday());
        tt_cmnd.setText("CMND: " + person.getCMND());
        tt_address.setText("Địa chỉ: " + person.getAddress());

        tt_date_register.setText("Ngày đăng ký: " + person.getDateRegister());
        tt_description.setText("Mô tả: " + person.getDescription());

        if (Integer.parseInt(person.getArgee()) == 1){
            tt_agress.setText("Bố hoặc mẹ");
        }else if (Integer.parseInt(person.getArgee()) == 1){
            tt_agress.setText("Vợ");
        }else if (Integer.parseInt(person.getArgee()) == 1){
            tt_agress.setText("Không có");
        }

        Picasso.get().load(Common.WEB_IMAGE + person.getsHK())
                .resize(450, 350).centerCrop()
                .into(SHK);

        Picasso.get().load(Common.WEB_IMAGE + person.getxNDS())
                .resize(450, 350).centerCrop()
                .into(XNDS);

        Picasso.get().load(Common.WEB_IMAGE + person.getgKH())
                .resize(450, 350).centerCrop()
                .into(GKH);

        Picasso.get().load(Common.WEB_IMAGE + person.getgKS())
                .resize(450, 350).centerCrop()
                .into(GKS);

        Picasso.get().load(Common.WEB_IMAGE + person.getKhac1())
                .resize(450, 350).centerCrop().into(show_img_khac1);
        Picasso.get().load(Common.WEB_IMAGE + person.getKhac2())
                .resize(450, 350).centerCrop().into(show_img_khac2);
        Picasso.get().load(Common.WEB_IMAGE + person.getKhac3())
                .resize(450, 350).centerCrop().into(show_img_khac3);
        Picasso.get().load(Common.WEB_IMAGE + person.getKhac4())
                .resize(450, 350).centerCrop().into(show_img_khac4);
        Picasso.get().load(Common.WEB_IMAGE + person.getKhac5())
                .resize(450, 350).centerCrop().into(show_img_khac5);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.show_btn_active:
                activePerson(2);
                break;
            case R.id.show_btn_wait:
                activePerson(1);
                break;
            //SHK, XNDS, GKS, GKH
            case R.id.SHK:
                sendToViewImage(SHK, person.getsHK());
                break;
            case R.id.XNDS:
                sendToViewImage(XNDS, person.getxNDS());
                break;
            case R.id.GKS:
                sendToViewImage(GKS, person.getgKS());
                break;
            case R.id.GKH:
                sendToViewImage(GKH, person.getgKH());
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void sendToViewImage(ImageView imageView, String link){
        Intent intent = new Intent(this, ViewImageActivity.class);

        intent.putExtra(Common.INTENT_LINK_IMAGE, link);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String> (imageView, "shareImage");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, pairs);

        startActivity(intent, options.toBundle());
    }

    private void activePerson(int status) {
        Call<Message> callback = Common.DATA_CLIENT.activePerson(Common.CONTROLLER_PERSON,
                Common.ACTION_ACTIVE_PERSON, Integer.parseInt(person.getId()), status);
        callback.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (!response.body().getMessage().equals("true")){
                    Toast.makeText(ShowAllActivity.this, "thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ShowAllActivity.this, "thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.d(TAG, t.getLocalizedMessage());
            }
        });
    }
}
