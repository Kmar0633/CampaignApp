package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ActionActivity extends AppCompatActivity {

TextView actionTitle;
TextView actionDescrp;
ImageView actionImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        Bundle bundle=getIntent().getExtras();
        actionTitle=(TextView)findViewById(R.id.textview_actionPage_title);
        actionDescrp=(TextView)findViewById(R.id.textview_actionPage_descrp);
        actionImage=(ImageView)findViewById(R.id.imageview_actionPage_image);

        actionTitle.setText(bundle.getString("actionTitle"));
        actionDescrp.setText(bundle.getString("actionDescrp"));

        Glide.with(this).load(bundle.getString("actionImage")).into(actionImage);
    }
}
