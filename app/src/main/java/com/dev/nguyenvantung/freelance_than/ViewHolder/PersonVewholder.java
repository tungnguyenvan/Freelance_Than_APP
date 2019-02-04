package com.dev.nguyenvantung.freelance_than.ViewHolder;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.LongDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.nguyenvantung.freelance_than.Common.Common;
import com.dev.nguyenvantung.freelance_than.Model.Message;
import com.dev.nguyenvantung.freelance_than.Model.Person;
import com.dev.nguyenvantung.freelance_than.R;
import com.dev.nguyenvantung.freelance_than.View.Edit.EditActivity;
import com.dev.nguyenvantung.freelance_than.View.Home.Fragment.Active.ViewActiveFragment;
import com.dev.nguyenvantung.freelance_than.View.Home.Fragment.New.ViewNewFragment;
import com.dev.nguyenvantung.freelance_than.View.Home.Fragment.NotActived.NotActivedFragmentView;
import com.dev.nguyenvantung.freelance_than.View.Home.Fragment.Search.ViewSearchFragment;
import com.dev.nguyenvantung.freelance_than.View.Home.Fragment.Wait.WaitFragmentView;
import com.dev.nguyenvantung.freelance_than.View.ShowAll.ShowAllActivity;
import com.dev.nguyenvantung.freelance_than.View.Statistical.ViewStatisticalActivity;

import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonVewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final String TAG = "PersonVewholder";
    private Person person;
    private View view;
    private ImageView item_call, item_edit, img_sex, item_delete;
    private TextView txt_name, txt_id, blood, status, txt_agress, txt_paper, txt_birthday, txt_address, txt_day;
    private ProgressDialog progressDialog;
    private RelativeLayout item_layout;

    private ViewNewFragment viewNewFragment;
    private ViewActiveFragment viewActiveFragment;
    private NotActivedFragmentView notActivedFragmentView;
    private ViewSearchFragment viewSearchFragment;
    private WaitFragmentView waitFragmentView;
    private ViewStatisticalActivity viewStatisticalActivity;

    public PersonVewholder(@NonNull View itemView, ViewNewFragment viewNewFragment) {
        super(itemView);
        this.view = itemView;

        this.viewNewFragment = viewNewFragment;
    }

    public PersonVewholder(@NonNull View itemView, ViewActiveFragment viewActiveFragment) {
        super(itemView);
        this.view = itemView;

        this.viewActiveFragment = viewActiveFragment;
    }

    public PersonVewholder(@NonNull View itemView, NotActivedFragmentView notActivedFragmentView) {
        super(itemView);
        this.view = itemView;

        this.notActivedFragmentView = notActivedFragmentView;
    }

    public PersonVewholder(@NonNull View itemView, WaitFragmentView waitFragmentView) {
        super(itemView);
        this.view = itemView;

        this.waitFragmentView = waitFragmentView;
    }
    public PersonVewholder(@NonNull View itemView, ViewSearchFragment viewSearchFragment) {
        super(itemView);
        this.view = itemView;

        this.viewSearchFragment = viewSearchFragment;
    }

    public PersonVewholder(@NonNull View itemView, ViewStatisticalActivity viewStatisticalActivity) {
        super(itemView);
        this.view = itemView;

        this.viewStatisticalActivity = viewStatisticalActivity;
    }


    public void initData(Person person){
        this.person = person;

        addControlls();
        addData();
        initProgressbardialog();
        addEvents();
    }

    private void initProgressbardialog() {
        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setTitle("Xóa");
        progressDialog.setMessage("Đang tiến hành kết nối đến server. Vui lòng chờ");
    }


    private void addControlls() {
        txt_id    = view.findViewById(R.id.txt_id);
        txt_name  = view.findViewById(R.id.txt_name);
        item_call = view.findViewById(R.id.item_call);
        item_layout= view.findViewById(R.id.item_layout);
        status = view.findViewById(R.id.status);
        txt_agress = view.findViewById(R.id.txt_agress);
        txt_paper = view.findViewById(R.id.txt_paper);
        txt_birthday = view.findViewById(R.id.txt_birthday);
        item_edit = view.findViewById(R.id.item_edit);
        blood = view.findViewById(R.id.blood);
        txt_address = view.findViewById(R.id.txt_address);
        img_sex = view.findViewById(R.id.img_sex);
        item_delete = view.findViewById(R.id.item_delete);
        txt_day = view.findViewById(R.id.item_date);
    }

    private void addData() {
        txt_name.setText(person.getName());
        txt_address.setText(person.getAddress());

        // txt day
        String arrDate[] = person.getDateRegister().split("-");
        txt_day.setText(arrDate[1] + "/" + arrDate[2]);

        if (Integer.parseInt(person.getStatus()) == 2){
            status.setText("Đã kích hoạt");
            //status.setTextColor(Color.GREEN);
        }else if (Integer.parseInt(person.getStatus()) == 1){
            status.setText("Chờ");
            //status.setTextColor(Color.BLUE);
        }else if (Integer.parseInt(person.getStatus()) == 0){
            status.setText("Chưa kích hoạt");
            //status.setTextColor(Color.RED);
        }else if (Integer.parseInt(person.getStatus()) == -1){
            status.setText("Chưa xử lý");
        }

        txt_id.setText(person.getId());

        if (person.getSex().equals("Nam")) img_sex.setImageResource(R.drawable.ic_man);
        else img_sex.setImageResource(R.drawable.ic_girl);

        if (Integer.parseInt(person.getBlood()) == 1){
            blood.setText("O");
        }else if (Integer.parseInt(person.getBlood()) == 2){
            blood.setText("A");
        }else if (Integer.parseInt(person.getBlood()) == 3){
            blood.setText("B");
        }else if (Integer.parseInt(person.getBlood()) == 4){
            blood.setText("AB");
        }else if (Integer.parseInt(person.getBlood()) == 0){
            blood.setText("Chưa biết");
        }
//
        if (Integer.parseInt(person.getArgee()) == 1){
            txt_agress.setText("Bố hoặc mẹ");
        }else if (Integer.parseInt(person.getArgee()) == 2){
            txt_agress.setText("Vợ");
        }else if (Integer.parseInt(person.getArgee()) == 0){
            txt_agress.setText("Không có");
        }

        txt_birthday.setText(person.getBirthday());
        txt_paper.setText("");
        if (person.getCMND().equals("")) txt_paper.append("CMND, ");
        if (person.getSHK().equals("")) txt_paper.append("SHK, ");
        if (person.getXNDS().equals("")) txt_paper.append("XNDS, ");
        if (person.getGKS().equals("")) txt_paper.append("GKS, ");
        if (person.getGKH().equals("")) txt_paper.append("GKH, ");

    }

    private void addEvents() {
        item_call.setOnClickListener(this);
        item_layout.setOnClickListener(this);
        item_edit.setOnClickListener(this);
        item_delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item_call:
                ShowContextCall();
                break;
            case R.id.item_layout:
                ShowAll();
                break;
            case R.id.item_edit:
                EditPerson();
                break;
            case R.id.item_delete:
                ShowAlerDialog();
                break;
        }
    }

    private void ShowContextCall() {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), item_call);
        popupMenu.getMenuInflater().inflate(R.menu.menu_call, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()){
                case R.id.open_phone:
                    OpenPhone(person.getPhone());
                    break;
                case R.id.coppy:
                    Coppy(person.getPhone());
                    break;
                case R.id.coppy_zalo:
                    Coppy(person.getZalo());
                    break;
            }
            return false;
        });
        popupMenu.show();
    }

    private void Coppy(String phone) {
        ClipboardManager _clipboard = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        _clipboard.setText(phone);
        Toast.makeText(view.getContext(), "Sao chép thành công", Toast.LENGTH_SHORT).show();
    }

    private void ShowAll() {
        if (viewSearchFragment != null) viewSearchFragment.showAll(person);
        if (viewNewFragment != null) viewNewFragment.showAll(person);
        if (waitFragmentView != null) waitFragmentView.showAll(person);
        if (viewActiveFragment != null) viewActiveFragment.showAll(person);
        if (notActivedFragmentView != null) notActivedFragmentView.showAll(person);
        if (viewStatisticalActivity != null) viewStatisticalActivity.showAll(person);
    }

    private void ActivePerson(int status) {
        Call<Message> callback = Common.DATA_CLIENT.activePerson(Common.CONTROLLER_PERSON,
                Common.ACTION_ACTIVE_PERSON, Integer.parseInt(person.getId()), status);
        callback.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (!response.body().getMessage().equals("true")){
                    Toast.makeText(view.getContext(), "nhiệm vụ thành công", Toast.LENGTH_SHORT).show();
                    if (viewActiveFragment != null) viewActiveFragment.swipeRefresh();
                    if (viewNewFragment != null) viewNewFragment.swipeRefresh();
                    if (notActivedFragmentView != null) notActivedFragmentView.swipeRefresh();
                    if (waitFragmentView != null) waitFragmentView.swipeRefresh();
                }else {
                    Toast.makeText(view.getContext(), "nhiệm vụ thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.d(TAG, t.getLocalizedMessage());
            }
        });
    }

    private void DeletePerson() {
        progressDialog.show();
        Call<Message> callback = Common.DATA_CLIENT.deletePerson(Common.CONTROLLER_PERSON,
                Common.ACTION_DELETE_PERSON, Integer.parseInt(person.getId()));

        callback.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (!response.body().getMessage().equals("true")){
                    if (viewNewFragment != null)viewNewFragment.swipeRefresh();
                    if (viewActiveFragment != null) viewActiveFragment.swipeRefresh();
                    if (notActivedFragmentView != null) notActivedFragmentView.swipeRefresh();
                    if (waitFragmentView != null) waitFragmentView.swipeRefresh();
                    Toast.makeText(view.getContext(), "Xoa thanh cong", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(view.getContext(), "Xoa that bai", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.e(TAG, t.getLocalizedMessage());
            }
        });
    }

    private void ShowAlerDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Xóa");
        builder.setMessage("Bạn có chắc xóa người này không?");
        builder.setCancelable(false);
        builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DeletePerson();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void ShowAlerActive(String title, String message, final int status){
        final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivePerson(status);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void EditPerson() {
        if (viewActiveFragment != null) viewActiveFragment.editPerson(person);
        if (notActivedFragmentView != null) notActivedFragmentView.editPerson(person);
        if (viewNewFragment != null) viewNewFragment.editPerson(person);
        if (viewSearchFragment != null) viewSearchFragment.editPerson(person);
        if (waitFragmentView != null) waitFragmentView.editPerson(person);
        if (viewStatisticalActivity != null) viewStatisticalActivity.editPerson(person);
    }

    private void OpenPhone(String phone) {
        if (!TextUtils.isEmpty(phone)){
            String dial = "tel:" + phone;
            view.getContext().startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
        }else {
            Toast.makeText(view.getContext(), "Chưa có số điện thoại", Toast.LENGTH_SHORT).show();
        }
    }
}
