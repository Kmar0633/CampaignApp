package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ChallengeActionsCustomAdapter extends BaseAdapter {
    ArrayList<ActionChallengeEntity> groupChallengeEntityArrayList;
    private final Context activity;
private Bundle bundle;
    private String urlLink="https://campaigndata-campaign.appspot.com/?t=upd&w=500&crop=true&file=";
    public ChallengeActionsCustomAdapter(ArrayList<ActionChallengeEntity> groupChallengeEntityArrayList1, Context activity1){
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
        view = layoutInflater.inflate(R.layout.listview_action_layout, null);
        TextView textView=(TextView)view.findViewById(R.id.titleAction);
        TextView descriptionAction=(TextView)view.findViewById(R.id.descriptionAction);
        textView.setText(groupChallengeEntityArrayList.get(i).getActionTitle());
        descriptionAction.setText(groupChallengeEntityArrayList.get(i).getActionDescrip());
ImageView imageAction=(ImageView)view.findViewById(R.id.imageAction);
        Glide.with(activity).load(groupChallengeEntityArrayList.get(i).getImageUrl()).into(imageAction);

        return view;
    }

}
