package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class EventChallengesListAdapter extends BaseAdapter {
    ArrayList<EventChallengeEntity> groupChallengeEntityArrayList;
    private final Context activity;

    private String urlLink="https://campaigndata-campaign.appspot.com/?t=upd&w=500&crop=true&file=";
    public EventChallengesListAdapter(ArrayList<EventChallengeEntity> groupChallengeEntityArrayList1, Context activity1){
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
        view = layoutInflater.inflate(R.layout.item_event_challenge, null);
        ImageView imageView=(ImageView)view.findViewById(R.id.image_eventChallenges_eventImage);
   //     TextView textView=(TextView)view.findViewById(R.id.text_itemEventChallenge_challengeId);
        ImageView playIconAction=(ImageView)view.findViewById(R.id.image_itemEventChallenge_videoIcon);
   //     textView.setText(this.groupChallengeEntityArrayList.get(i).getTitle());
     //   TextView challengeName=(TextView)view.findViewById(R.id.text_itemEventChallenge_title);
    //    textView.setText(this.groupChallengeEntityArrayList.get(i).getTitle());
        Glide.with(activity).load(urlLink+groupChallengeEntityArrayList.get(i).getImage_url()).into(imageView);
      //  Log.e("turn",String.valueOf(groupChallengeEntityArrayList.get(i).isVideo()));

        if(this.groupChallengeEntityArrayList.get(i).isVideo()==true){
            Glide.with(activity).load("https://cdn4.iconfinder.com/data/icons/round-buttons/512/blue_play.png").into(playIconAction);


        }if(this.groupChallengeEntityArrayList.get(i).isAttended()) {
    imageView.setColorFilter(Color.parseColor("#70FFFF80"), PorterDuff.Mode.MULTIPLY);
}


        return view;
    }

}
