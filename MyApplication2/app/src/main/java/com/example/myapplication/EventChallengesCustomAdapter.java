package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class EventChallengesCustomAdapter extends BaseAdapter {
    ArrayList<ActionEntity> groupChallengeEntityArrayList;
    private final Context activity;

    private String urlLink="https://campaigndata-campaign.appspot.com/?t=upd&w=500&crop=true&file=";
    public EventChallengesCustomAdapter(ArrayList<ActionEntity> groupChallengeEntityArrayList1, Context activity1){
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
        view = layoutInflater.inflate(R.layout.listview_group_challenge_layout, null);
        ImageView imageView=(ImageView)view.findViewById(R.id.imageChallenge);
        TextView textView=(TextView)view.findViewById(R.id.challenge_id);
     //  TextView textView1=(TextView)view.findViewById(R.id.action_id);
        textView.setText(this.groupChallengeEntityArrayList.get(i).getTitle());
    //   textView1.setText(this.groupChallengeEntityArrayList.get(i).getDesciption());

        Glide.with(activity).load(urlLink+groupChallengeEntityArrayList.get(i).getImage_url()).into(imageView);
if(this.groupChallengeEntityArrayList.get(i).isAttended()) {
    imageView.setColorFilter(Color.parseColor("#70FFFF80"), PorterDuff.Mode.MULTIPLY);
}

        //textView1.setText(this.groupChallengeEntityArrayList.get(i).getDescription());
     //   textView.setText(this.groupChallengeEntityArrayList.get(i).getTitle());
        return view;
    }

}
