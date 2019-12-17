package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.os.Bundle;
import android.content.Context;
import android.view.View.OnClickListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class EventChallenge extends AppCompatActivity{
    ImageView eventImage;
    String imageUrl;
    TextView textView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_challenge);
        Bundle bundle=getIntent().getExtras();
        eventImage=(ImageView)findViewById(R.id.imageChallenge);
         textView=(TextView)findViewById(R.id.challengeTitle);
        if (bundle!=null){
            Bundle extras = getIntent().getExtras();
        //  String res=bundle.getInt("photo event");
          //  eventImage.setImageResource(res);

            bundle.getString("photo event");
            Glide.with(this).load(bundle.getString("photo event")).into(eventImage);
           // textView.setText();
        }
    }
}
