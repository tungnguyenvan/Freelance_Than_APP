package com.dev.nguyenvantung.freelance_than.View.ShowAll;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
            tt_job, tt_birthday, tt_cmnd, tt_address, tt_agress, tt_description, tt_height, tt_weight,
            tt_sex, tt_date_register;
    private ImageView SHK, XNDS, GKS, GKH, show_img_khac1, show_img_khac2, show_img_khac3, show_img_khac4,
            show_img_khac5, tt_img_sex;
    private android.support.v7.widget.Toolbar show_all_toolbar;
    private Button show_btn_active, show_btn_wait, show_btn_progressing;

    private Person person;
    private Drawable drawable;

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
        tt_sex = findViewById(R.id.tt_sex);
        tt_height = findViewById(R.id.tt_height);
        tt_weight = findViewById(R.id.tt_weight);
        tt_blood = findViewById(R.id.tt_blood);
        tt_status = findViewById(R.id.tt_status);
        tt_phone = findViewById(R.id.tt_phone);
        tt_zalo = findViewById(R.id.tt_zalo);
        tt_job = findViewById(R.id.tt_job);
        tt_birthday = findViewById(R.id.tt_birthday);
        tt_cmnd = findViewById(R.id.tt_cmnd);
        tt_address = findViewById(R.id.tt_address);
        tt_agress = findViewById(R.id.tt_agress);
        tt_img_sex = findViewById(R.id.tt_img_sex);
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
        drawable = show_btn_active.getBackground();
        if (Integer.parseInt(person.getStatus()) == 2){
            show_btn_active.setBackgroundColor(Color.parseColor("#004bba"));
            show_btn_active.setTextColor(Color.WHITE);
        }
        show_btn_wait = findViewById(R.id.show_btn_wait);
        if (Integer.parseInt(person.getStatus()) == 1){
            show_btn_wait.setBackgroundColor(Color.parseColor("#004bba"));
            show_btn_wait.setTextColor(Color.WHITE);
        }
        show_btn_progressing = findViewById(R.id.show_btn_progressing);
        if (Integer.parseInt(person.getStatus()) == 0){
            show_btn_progressing.setBackgroundColor(Color.parseColor("#004bba"));
            show_btn_progressing.setTextColor(Color.WHITE);
        }

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
        show_btn_progressing.setOnClickListener(this);

        //SHK, XNDS, GKS, GKH
        SHK.setOnClickListener(this);
        XNDS.setOnClickListener(this);
        GKS.setOnClickListener(this);
        GKH.setOnClickListener(this);

        show_img_khac1.setOnClickListener(this);
        show_img_khac2.setOnClickListener(this);
        show_img_khac3.setOnClickListener(this);
        show_img_khac4.setOnClickListener(this);
        show_img_khac5.setOnClickListener(this);
    }

    private void addData() {
        tt_id.setText("ID: " + person.getId());
        tt_name.setText(person.getName());
        tt_height.setText(person.getHeight() + " Cm");
        tt_weight.setText(person.getWeight() + " Kg");
        tt_sex.setText(person.getSex());

        String[] arr_date_register = person.getDateRegister().split("-");
        tt_date_register.setText(arr_date_register[2] + " - " + arr_date_register[1] + " - " + arr_date_register[0]);

        if (Integer.parseInt(person.getBlood()) == 1){
            tt_blood.setText("O");
        }else if (Integer.parseInt(person.getBlood()) == 2){
            tt_blood.setText("A");
        }else if (Integer.parseInt(person.getBlood()) == 3){
            tt_blood.setText("B");
        }else if (Integer.parseInt(person.getBlood()) == 4){
            tt_blood.setText("AB");
        }else if (Integer.parseInt(person.getBlood()) == 0){
            tt_blood.setText("Chưa biết");
        }

        if (Integer.parseInt(person.getStatus()) == 2){
            tt_status.setText("Thành công");
            tt_status.setTextColor(Color.GREEN);
        }else if (Integer.parseInt(person.getStatus()) == 1){
            tt_status.setText("Chờ");
            tt_status.setTextColor(Color.BLUE);
        }else if (Integer.parseInt(person.getStatus()) == 0){
            tt_status.setText("Đang xử lí");
            tt_status.setTextColor(Color.RED);
        }

        tt_phone.setText(person.getPhone());
        tt_zalo.setText(person.getZalo());
        tt_job.setText(person.getJob());
        tt_birthday.setText(person.getBirthday());
        tt_cmnd.setText(person.getCMND());
        tt_address.setText(person.getAddress());

        if (person.getSex().equals("Nam")) tt_img_sex.setImageResource(R.drawable.ic_man);
        else tt_img_sex.setImageResource(R.drawable.ic_girl);

        tt_description.setText(person.getDescription());

        if (Integer.parseInt(person.getArgee()) == 1){
            tt_agress.setText("Bố hoặc mẹ");
        }else if (Integer.parseInt(person.getArgee()) == 2){
            tt_agress.setText("Vợ");
        }else if (Integer.parseInt(person.getArgee()) == 0){
            tt_agress.setText("Không có");
        }

        Picasso.get().load(Common.WEB_IMAGE + person.getsHK()).into(SHK);

        Picasso.get().load(Common.WEB_IMAGE + person.getxNDS()).into(XNDS);

        Picasso.get().load(Common.WEB_IMAGE + person.getgKH()).into(GKH);

        Picasso.get().load(Common.WEB_IMAGE + person.getgKS()).into(GKS);

        Picasso.get().load(Common.WEB_IMAGE + person.getKhac1()).into(show_img_khac1);
        Picasso.get().load(Common.WEB_IMAGE + person.getKhac2()).into(show_img_khac2);
        Picasso.get().load(Common.WEB_IMAGE + person.getKhac3()).into(show_img_khac3);
        Picasso.get().load(Common.WEB_IMAGE + person.getKhac4()).into(show_img_khac4);
        Picasso.get().load(Common.WEB_IMAGE + person.getKhac5()).into(show_img_khac5);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.show_btn_active:
                ShowDialog(2);
                break;
            case R.id.show_btn_wait:
                ShowDialog(1);
                break;
            case R.id.show_btn_progressing:
                ShowDialog(0);
//                activePerson(0);
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
            case R.id.btn_khac1:
                sendToViewImage(show_img_khac1, person.getKhac1());
                break;
            case R.id.btn_khac2:
                sendToViewImage(show_img_khac1, person.getKhac2());
                break;
            case R.id.btn_khac3:
                sendToViewImage(show_img_khac1, person.getKhac3());
                break;
            case R.id.btn_khac4:
                sendToViewImage(show_img_khac1, person.getKhac4());
                break;
            case R.id.btn_khac5:
                sendToViewImage(show_img_khac1, person.getKhac5());
                break;
        }
    }

    private void ShowDialog(final int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Bạn có chắc chắn");
        builder.setCancelable(false);

        switch (i){
            case 0 : builder.setMessage("Bạn có chắc chắn chuyển sang đang xử lí không?"); break;
            case 1 : builder.setMessage("Bạn có chắc chắn chuyển sang Chờ không?"); break;
            case 2 : builder.setMessage("Bạn có chắc chắn chuyển thành công không?"); break;
        }

        builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activePerson(i);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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

    private void activePerson(final int status) {
        Call<Message> callback = Common.DATA_CLIENT.activePerson(Common.CONTROLLER_PERSON,
                Common.ACTION_ACTIVE_PERSON, Integer.parseInt(person.getId()), status);
        callback.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (!response.body().getMessage().equals("true")){
                    setButton(status);
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

    private void setButton(int status) {
        show_btn_progressing.setBackgroundDrawable(drawable); show_btn_progressing.setTextColor(Color.BLACK);
        show_btn_wait.setBackgroundDrawable(drawable); show_btn_wait.setTextColor(Color.BLACK);
        show_btn_active.setBackgroundDrawable(drawable); show_btn_active.setTextColor(Color.BLACK);

        switch (status){
            case 0:
                show_btn_progressing.setBackgroundColor(Color.parseColor("#004bba"));
                show_btn_progressing.setTextColor(Color.WHITE);
                break;
            case 1:
                show_btn_wait.setBackgroundColor(Color.parseColor("#004bba"));
                show_btn_wait.setTextColor(Color.WHITE);
                break;
            case 2:
                show_btn_active.setBackgroundColor(Color.parseColor("#004bba"));
                show_btn_active.setTextColor(Color.WHITE);
                break;
        }
    }
}
