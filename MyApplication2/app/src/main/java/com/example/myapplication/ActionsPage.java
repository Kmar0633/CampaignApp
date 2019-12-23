package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ActionsPage extends AppCompatActivity {
    TextView actionTitle;
    private String urlLink="https://campaigndata-campaign.appspot.com/?t=upd&w=500&crop=true&file=";

    TextView actionDescription;
    ImageView actionImage;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getIntent().getExtras();
        //mDatabase.intializeApp(this);
        setContentView(R.layout.action_layout);
        actionImage=(ImageView)findViewById(R.id.actionImage);
        actionDescription=(TextView) findViewById(R.id.actionDescription);
        actionTitle=(TextView) findViewById(R.id.actionTitle);
        if(bundle!=null) {
            actionDescription.setText(bundle.getString("actionDescription"));
            actionTitle.setText(bundle.getString("actionTitle"));
            Glide.with(this).load(urlLink+bundle.getString("actionImage")).into(actionImage);
        }


    }
}
