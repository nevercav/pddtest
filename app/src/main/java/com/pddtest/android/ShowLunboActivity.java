package com.pddtest.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ShowLunboActivity extends AppCompatActivity {

    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_lunbo);
        imageView=(ImageView)findViewById(R.id.lunbo_image);
        Intent i=getIntent();
        imageView.setImageResource(i.getIntExtra("imageurl",R.mipmap.ic_launcher));

    }
}
