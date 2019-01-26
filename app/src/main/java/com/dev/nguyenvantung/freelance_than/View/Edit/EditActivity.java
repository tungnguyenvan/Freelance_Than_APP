package com.dev.nguyenvantung.freelance_than.View.Edit;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.nguyenvantung.freelance_than.Common.Common;
import com.dev.nguyenvantung.freelance_than.Model.Message;
import com.dev.nguyenvantung.freelance_than.Model.Person;
import com.dev.nguyenvantung.freelance_than.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "EditActivity";
    private static final int REQUEST_CODE_IMAGE = 1;
    private static final int REQUEST_PERMISSION_READ_EX = 10;
    private static final int REQUEST_PERMISSION_READ_CONTACT = 11;
    private static final int REQUEST_CONTACT = 12;
    private EditText edit_name, edit_phone, edit_zalo, edit_job, edit_birthday, edit_cmnd, edit_scription, edit_address,
            edit_height, edit_weight;
    private TextView edit_id, edit_date_register;
    private Button btn_shk, btn_xnds, btn_gks, btn_gkh, btn_khac1, btn_khac2, btn_khac3, btn_khac4, btn_khac5;
    private ImageView img_shk, img_xnds, img_gks, img_gkh, edit_btn_opencontact, img_khac1, img_khac2,
            img_khac3, img_khac4, img_khac5;
    private Spinner snp_agress, edit_blood, edit_status, snp_sex;
    private ProgressDialog progressDialog;
    private Button update_info;

    private String SHK = "", XNDS = "", GKS = "", GKH = "", khac1 = "", khac2 = "", khac3 = "", khac4 = "", khac5 = "";
    private int AGRESS = 0, BLOOD = 0;

    private String LINK;
    private ImageView IMAGE;

    private Person person;
    private List<String> listBlood;
    private List<String> listAgress;
    private List<String> listSex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //get data
        person = (Person) getIntent().getSerializableExtra(Common.INTENT_PERSON);

        //init progressbardialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang xử lí đến server, vui lòng chờ");

        initList();
        addControlls();
        addData();
        addEvents();
    }

    private void addControlls() {
        edit_id = findViewById(R.id.edit_id);
        edit_name = findViewById(R.id.edit_name);
        edit_phone = findViewById(R.id.edit_phone);
        edit_zalo = findViewById(R.id.edit_zalo);
        edit_job = findViewById(R.id.edit_job);
        edit_birthday = findViewById(R.id.edit_birthday);
        edit_cmnd = findViewById(R.id.edit_cmnd);
        edit_scription = findViewById(R.id.edit_scription);
        edit_btn_opencontact = findViewById(R.id.edit_btn_opencontact);
        edit_height = findViewById(R.id.edit_height);
        edit_weight = findViewById(R.id.edit_weight);
        edit_date_register = findViewById(R.id.edit_date_register);

        edit_address = findViewById(R.id.snp_address);
        snp_agress = findViewById(R.id.snp_agress);
        edit_blood = findViewById(R.id.edit_blood);
        snp_sex = findViewById(R.id.snp_sex);

        btn_shk = findViewById(R.id.btn_shk);
        img_shk = findViewById(R.id.img_shk);
        btn_xnds = findViewById(R.id.btn_xnds);
        img_xnds = findViewById(R.id.img_xnds);
        btn_gks = findViewById(R.id.btn_gks);
        img_gks = findViewById(R.id.img_gks);
        btn_gkh = findViewById(R.id.btn_gkh);
        img_gkh = findViewById(R.id.img_gkh);

        btn_khac1 = findViewById(R.id.btn_khac1);
        btn_khac2 = findViewById(R.id.btn_khac2);
        btn_khac3 = findViewById(R.id.btn_khac3);
        btn_khac4 = findViewById(R.id.btn_khac4);
        btn_khac5 = findViewById(R.id.btn_khac5);

        img_khac1 = findViewById(R.id.img_khac1);
        img_khac2 = findViewById(R.id.img_khac2);
        img_khac3 = findViewById(R.id.img_khac3);
        img_khac4 = findViewById(R.id.img_khac4);
        img_khac5 = findViewById(R.id.img_khac5);

        update_info = findViewById(R.id.update_info);
    }

    private void addData() {
        edit_id.setText(person.getId());
        edit_name.setText(person.getName());
        edit_phone.setText(person.getPhone());
        edit_zalo.setText(person.getZalo());
        edit_job.setText(person.getJob());
        edit_birthday.setText(person.getBirthday());
        edit_scription.setText(person.getDescription());
        edit_cmnd.setText(person.getCMND());
        edit_address.setText(person.getAddress());
        edit_height.setText(person.getHeight());
        edit_weight.setText(person.getWeight());

        String[] arrDateRegister = person.getDateRegister().split("-");
        edit_date_register.setText(arrDateRegister[2] + " - " + arrDateRegister[1] + " - " + arrDateRegister[0]);


        ArrayAdapter<String> adapter_agress = new ArrayAdapter<>
                (this, android.R.layout.simple_selectable_list_item, listAgress);
        adapter_agress.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        snp_agress.setAdapter(adapter_agress);
        snp_agress.setSelection(Integer.parseInt(person.getArgee()));
        AGRESS = Integer.parseInt(person.getArgee());

        ArrayAdapter<String> adapter_blood = new ArrayAdapter<>
                (this, android.R.layout.simple_selectable_list_item, listBlood);
        adapter_blood.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        edit_blood.setAdapter(adapter_blood);
        edit_blood.setSelection(Integer.parseInt(person.getBlood()));
        BLOOD = Integer.parseInt(person.getBlood());

        ArrayAdapter<String> adapter_sex = new ArrayAdapter<>(this,
                android.R.layout.simple_selectable_list_item, listSex);
        adapter_sex.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        snp_sex.setAdapter(adapter_sex);
        if (person.getSex() != null) {
            if (person.getSex().equals("Nam")) snp_sex.setSelection(0);
            else snp_sex.setSelection(1);
        }

        SHK = person.getSHK();
        XNDS = person.getXNDS();
        GKS = person.getGKS();
        GKH = person.getGKH();
        khac1 = person.getKhac1();
        khac2 = person.getKhac2();
        khac3 = person.getKhac3();
        khac4 = person.getKhac4();
        khac5 = person.getKhac5();

        //add data image
        Picasso.get().load(Common.WEB_IMAGE + person.getSHK())
                .resize(350, 350).centerCrop().into(img_shk);

        Picasso.get().load(Common.WEB_IMAGE + person.getXNDS())
                .resize(350, 350).centerCrop().into(img_xnds);

        Picasso.get().load(Common.WEB_IMAGE + person.getGKS())
                .resize(350, 350).centerCrop().into(img_gks);

        Picasso.get().load(Common.WEB_IMAGE + person.getGKH())
                .resize(350, 350).centerCrop().into(img_gkh);

        Picasso.get().load(Common.WEB_IMAGE + khac1)
                .resize(350, 350).centerCrop().into(img_khac1);
        Picasso.get().load(Common.WEB_IMAGE + khac2)
                .resize(350, 350).centerCrop().into(img_khac2);
        Picasso.get().load(Common.WEB_IMAGE + khac3)
                .resize(350, 350).centerCrop().into(img_khac3);
        Picasso.get().load(Common.WEB_IMAGE + khac4)
                .resize(350, 350).centerCrop().into(img_khac4);
        Picasso.get().load(Common.WEB_IMAGE + khac5)
                .resize(350, 350).centerCrop().into(img_khac5);
    }

    private void addEvents() {
        btn_shk.setOnClickListener(this);
        btn_xnds.setOnClickListener(this);
        btn_gks.setOnClickListener(this);
        btn_gkh.setOnClickListener(this);
        update_info.setOnClickListener(this);
        edit_btn_opencontact.setOnClickListener(this);
        btn_khac1.setOnClickListener(this);
        btn_khac2.setOnClickListener(this);
        btn_khac3.setOnClickListener(this);
        btn_khac4.setOnClickListener(this);
        btn_khac5.setOnClickListener(this);

        snp_agress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AGRESS = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edit_blood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BLOOD = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initList() {

        listBlood = new ArrayList<>();
        listBlood.add("Chưa biết");
        listBlood.add("O");
        listBlood.add("A");
        listBlood.add("B");
        listBlood.add("AB");

        listAgress = new ArrayList<>();
        listAgress.add("Không có ai");
        listAgress.add("Bố hoặc mẹ");
        listAgress.add("Vợ");

        listSex = new ArrayList<>();
        listSex.add("Nam");
        listSex.add("Nữ");
    }

    private void showProgressbar(String title){
        progressDialog.setTitle(title);
        progressDialog.show();
    }
    private void dimissProgressbar(){
        progressDialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_shk:
                checkPermissionRead();
                IMAGE = img_shk;
                UploadImage();
                break;
            case R.id.btn_xnds:
                checkPermissionRead();
                IMAGE = img_xnds;
                UploadImage();
                break;
            case R.id.btn_gks:
                checkPermissionRead();
                IMAGE = img_gks;
                UploadImage();
                break;
            case R.id.btn_gkh:
                checkPermissionRead();
                IMAGE = img_gkh;
                UploadImage();
                break;
            case R.id.update_info:
                UpdateInfor();
                break;
            case R.id.edit_btn_opencontact:
                checkPermissionRead();
                openContact();
                break;
            case R.id.btn_khac1:
                IMAGE = img_khac1;
                UploadImage();
                break;
            case R.id.btn_khac2:
                IMAGE = img_khac2;
                UploadImage();
                break;
            case R.id.btn_khac3:
                IMAGE = img_khac3;
                UploadImage();
                break;
            case R.id.btn_khac4:
                IMAGE = img_khac4;
                UploadImage();
                break;
            case R.id.btn_khac5:
                IMAGE = img_khac5;
                UploadImage();
                break;
        }
    }

    private void openContact() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, REQUEST_CONTACT);
    }

    private void UpdateInfor() {
        String sex = "";
        if (snp_sex.getSelectedItemPosition() == 0) sex = "Nam";
        else sex = "Nữ";
        Person newPerson = new Person(edit_id.getText().toString(),
                edit_name.getText().toString(),sex,
                edit_height.getText().toString(),
                edit_weight.getText().toString(),
                edit_phone.getText().toString(),
                edit_zalo.getText().toString(),
                edit_job.getText().toString(),
                edit_birthday.getText().toString(),
                edit_cmnd.getText().toString(),
                SHK, XNDS, GKS, GKH,khac1, khac2, khac3, khac4, khac5, edit_address.getText().toString(),
                String.valueOf(AGRESS), String.valueOf(BLOOD), edit_scription.getText().toString(),
                person.getDateRegister(), person.getStatus());

        Call<Message> callback = Common.DATA_CLIENT.updatePerson(Common.CONTROLLER_PERSON,
                Common.ACTION_UPDATE_PERSON,
                Integer.parseInt(person.getId()),
                edit_name.getText().toString(),
                sex, edit_height.getText().toString(),
                edit_weight.getText().toString(),
                edit_phone.getText().toString(),
                edit_zalo.getText().toString(),
                edit_job.getText().toString(),
                edit_birthday.getText().toString(),
                edit_cmnd.getText().toString(),
                SHK, XNDS, GKS, GKH,khac1, khac2, khac3, khac4, khac5, edit_address.getText().toString(),
                String.valueOf(AGRESS), String.valueOf(BLOOD), edit_scription.getText().toString(),
                person.getDateRegister(), person.getStatus());

        callback.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.body().getMessage())
                    Toast.makeText(EditActivity.this, "Upload thành công", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(EditActivity.this, "Upload Thất bại", Toast.LENGTH_SHORT).show();
            }
            @Override

            public void onFailure(Call<Message> call, Throwable t) {
                Log.e(TAG, t.getLocalizedMessage());
                Toast.makeText(EditActivity.this, "Upload Thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void UploadImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_IMAGE);
    }

    private void checkPermissionRead() {
        if (Build.VERSION.SDK_INT >= 23){
            if (!(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED)){
                ActivityCompat.requestPermissions(this
                        , new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSION_READ_EX);
            }
            if (checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},
                        REQUEST_PERMISSION_READ_CONTACT);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_READ_EX && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            UploadImage();
        }else checkPermissionRead();
        if (requestCode == REQUEST_PERMISSION_READ_CONTACT && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            openContact();
        }else checkPermissionRead();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null){
            switch (IMAGE.getId()){
                case R.id.img_shk:
                    SHK = UploadToServer(getRealPathFromUri(data.getData()), IMAGE);
                    break;
                case R.id.img_xnds:
                    XNDS = UploadToServer(getRealPathFromUri(data.getData()), IMAGE);
                    break;
                case R.id.img_gks:
                    GKS = UploadToServer(getRealPathFromUri(data.getData()), IMAGE);
                    break;
                case R.id.img_gkh:
                    GKH = UploadToServer(getRealPathFromUri(data.getData()), IMAGE);
                    break;
                case R.id.img_khac1:
                    khac1 = UploadToServer(getRealPathFromUri(data.getData()), IMAGE);
                    break;
                case R.id.img_khac2:
                    khac2 = UploadToServer(getRealPathFromUri(data.getData()), IMAGE);
                    break;
                case R.id.img_khac3:
                    khac3 = UploadToServer(getRealPathFromUri(data.getData()), IMAGE);
                    break;
                case R.id.img_khac4:
                    khac4 = UploadToServer(getRealPathFromUri(data.getData()), IMAGE);
                    break;
                case R.id.img_khac5:
                    khac5 = UploadToServer(getRealPathFromUri(data.getData()), IMAGE);
                    break;
            }
        }

        if (requestCode == REQUEST_CONTACT && resultCode == RESULT_OK && data != null){
            Uri contactData = data.getData();
            Cursor cur =  getContentResolver().query(contactData, null, null, null, null);
            if (cur.getCount() > 0) {// thats mean some resutl has been found
                if(cur.moveToNext()) {
                    String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    Log.e("Names", name);

                    if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                    {

                        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,null, null);
                        while (phones.moveToNext()) {
                            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            edit_phone.setText(phoneNumber);
                        }
                        phones.close();
                    }

                }
            }
            cur.close();
        }
    }

    private String UploadToServer(String uri, final ImageView imageView) {
        showProgressbar("Upload Image");
        File file = new File(uri);
        String file_path = file.getAbsolutePath();
        Log.d(TAG, file_path);
        String[] arr_name = file_path.split("\\.");
        file_path = "IMG_POST_" + System.currentTimeMillis() + "." + arr_name[1];
        Log.d(TAG,file_path);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", file_path, requestBody);

        RequestBody requestController = RequestBody.create(MediaType.parse("text/plain"), Common.CONTROLLER_UPLOADIMAGE);
        RequestBody requestAction = RequestBody.create(MediaType.parse("text/plain"), Common.ACTION_UPLOADIMAGE);

        Call<Message> call = Common.DATA_CLIENT.upImage(requestController, requestAction, body);
        final String finalFile_path = file_path;
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Log.d(TAG, response.body().getMessage().toString());
                if (response.body().getMessage().toString().equals("true")) {
                    Picasso.get().load(Common.WEB_IMAGE + finalFile_path).into(imageView);
//                    UpdatePersonImage(imageView,finalFile_path);
                    Toast.makeText(EditActivity.this, "Thanh cong", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(EditActivity.this, "Upload Thất bại, Thử lại", Toast.LENGTH_SHORT).show();

                dimissProgressbar();
//
//                try {
//                    Log.d(TAG, response.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.e(TAG, t.getLocalizedMessage());
                Toast.makeText(EditActivity.this, "Upload that bai, thử lai", Toast.LENGTH_SHORT).show();
                dimissProgressbar();
            }
        });

        return finalFile_path;
    }

//    private void UpdatePersonImage(ImageView imageView, String finalFile_path) {
//        String key = "";
//        switch (imageView.getId()){
//            case R.id.SHK: key = "SHK"; break;
//            case R.id.XNDS: key = "XNDS"; break;
//            case R.id.GKS: key = "GKS"; break;
//            case R.id.GKH: key = "GKH"; break;
//            case R.id.img_khac1: key = "khac1"; break;
//            case R.id.img_khac2: key = "khac2"; break;
//            case R.id.img_khac3: key = "khac3"; break;
//            case R.id.img_khac4: key = "khac4"; break;
//            case R.id.img_khac5: key = "khac5"; break;
//        }

//        Call<Message> call = Common.DATA_CLIENT.updateImagePerson(Common.CONTROLLER_PERSON,
//                Common.ACTION_UPDATE_IMAGE_PERSON,
//                key, finalFile_path, Integer.parseInt(person.getId()));
//        call.enqueue(new Callback<Message>() {
//            @Override
//            public void onResponse(Call<Message> call, Response<Message> response) {
//                if (response.body().getMessage() == true)
//                    Toast.makeText(EditActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
//                else Toast.makeText(EditActivity.this, "Upload that bai, thử lai", Toast.LENGTH_SHORT).show();
////
////                try {
////                    Log.d(TAG, response.body().string());
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
//            }
//
//            @Override
//            public void onFailure(Call<Message> call, Throwable t) {
//
//            }
//        });
//    }


    public String getRealPathFromUri(Uri contentUri){
        String path = null;
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, filePathColumn, null, null, null);
        if(cursor.moveToFirst()){
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            path = cursor.getString(columnIndex);
        } else {
            Log.e(TAG, "Error: ");
        }
        cursor.close();
        return path;
    }
}
