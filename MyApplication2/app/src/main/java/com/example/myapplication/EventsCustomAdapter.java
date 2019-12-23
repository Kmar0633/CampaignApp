package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Currency;

class EventsCustomAdapter extends BaseAdapter {
        ArrayList<EventChallengeEntity> groupChallengeEntityArrayList;
private final Context activity;


public EventsCustomAdapter(ArrayList<EventChallengeEntity> groupChallengeEntityArrayList1, Context activity1){
        this.activity=activity1;
        this.groupChallengeEntityArrayList=groupChallengeEntityArrayList1;
        }

@Override
public int getCount() {
        return this.groupChallengeEntityArrayList.size();
        }

@Override
public Object getItem(int i) {
        return null;
        }
@Override
public long getItemId(int i) {
        return 0;
        }

@Override
public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater=(LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.listview_layout, null);
        ImageView imageView=(ImageView)view.findViewById(R.id.imageEvent1);
        TextView textView=(TextView)view.findViewById(R.id.titleEvent1);
    TextView textView1=(TextView)view.findViewById(R.id.descriptiorEvent1);
        Glide.with(this.activity).load(this.groupChallengeEntityArrayList.get(i).getImageUrl()).into(imageView);
    textView1.setText(this.groupChallengeEntityArrayList.get(i).getDescription());
        textView.setText(this.groupChallengeEntityArrayList.get(i).getTitle());
        return view;
        }


}
