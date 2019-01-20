package com.dev.nguyenvantung.freelance_than.View.ViewImage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dev.nguyenvantung.freelance_than.Common.Common;
import com.dev.nguyenvantung.freelance_than.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ViewImageActivity extends AppCompatActivity {
    private ImageView view_image_img, view_image_back;
    private ProgressBar progressbar;
    private String link_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        link_image = getIntent().getStringExtra(Common.INTENT_LINK_IMAGE);

        addControls();
        addEvents();
        addData();
    }

    private void addControls() {
        view_image_img = findViewById(R.id.view_image_img);
        view_image_back = findViewById(R.id.view_image_back);
        progressbar = findViewById(R.id.progressbar);
    }

    private void addEvents() {
        view_image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void addData() {
        Picasso.get().load(Common.WEB_IMAGE + link_image).into(view_image_img, new Callback() {
            @Override
            public void onSuccess() {
                progressbar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(ViewImageActivity.this, "Không thể load ảnh", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
