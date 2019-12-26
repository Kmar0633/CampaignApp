package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ActionsPage extends AppCompatActivity {
    TextView actionTitle;
    ExpandableHeightListView list;
    private String urlLink="https://campaigndata-campaign.appspot.com/?t=upd&w=500&crop=true&file=";
    ChallengeActionsCustomAdapter challengeActionsCustomAdapter;
    TextView actionDescription;
    ImageView actionImage;
    Bundle bundle;
    ArrayList<ActionEntity> actionEntityArrayList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getIntent().getExtras();
        //mDatabase.intializeApp(this);
        actionEntityArrayList=new ArrayList<ActionEntity>();
        setContentView(R.layout.action_layout);
        actionImage=(ImageView)findViewById(R.id.actionImage);
        actionDescription=(TextView) findViewById(R.id.actionDescription);
        actionTitle=(TextView) findViewById(R.id.actionTitle);
        if(bundle!=null) {
            actionDescription.setText(bundle.getString("actionDescription"));
            actionTitle.setText(bundle.getString("actionTitle"));
            Glide.with(this).load(urlLink+bundle.getString("actionImage")).into(actionImage);
        }
        list = (ExpandableHeightListView) findViewById(R.id.actionsList);
        //EventChallenge eventChallenge= new EventChallenge(this
        list.setExpanded(true);
        challengeActionsCustomAdapter=new ChallengeActionsCustomAdapter(actionEntityArrayList, this);
        setListViewHeight(list);

        list.setAdapter(challengeActionsCustomAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



            }
        });
getActionsData();

    }
    public void getActionsData(){
//        Log.e("ken",bundle.getString("challenge_id"));
    }
    public static void setListViewHeight(ListView listview){
        ListAdapter listAdapter = listview.getAdapter();
        if(listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        // int desiredWidth = MeasureSpec.makeMeasureSpec(listview.getWidth(), MeasureSpec.AT_MOST);
        for(int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listview);
            //listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listview.getLayoutParams();
        params.height = totalHeight + (listview.getDividerHeight()*(listAdapter.getCount() - 1)) + totalHeight;
        listview.setLayoutParams(params);
//listview.setExpand(true);
        //listview.requestLayout();
    }
}
