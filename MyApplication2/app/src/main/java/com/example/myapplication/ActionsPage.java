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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActionsPage extends AppCompatActivity {
    TextView actionTitle;
    ExpandableHeightListView list;
    private String urlLink="https://campaigndata-campaign.appspot.com/?t=upd&w=500&crop=true&file=";
    private String urlActionLink="https://campaigndata-campaign.appspot.com/?t=act&w=500&crop=true&file=";
    ChallengeActionsCustomAdapter challengeActionsCustomAdapter;
    TextView actionDescription;
    ImageView actionImage;
    String prof_id;
    Bundle bundle;
    String challenge_id;
    private DatabaseReference mDatabase;
    ArrayList<ActionChallengeEntity> actionEntityArrayList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getIntent().getExtras();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        actionEntityArrayList=new ArrayList<ActionChallengeEntity>();
        setContentView(R.layout.action_layout);
        actionImage=(ImageView)findViewById(R.id.actionImage);
        actionDescription=(TextView) findViewById(R.id.actionDescription);
        actionTitle=(TextView) findViewById(R.id.actionTitle);

        if(bundle!=null) {
            actionDescription.setText(bundle.getString("actionDescription"));
            actionTitle.setText(bundle.getString("actionTitle"));
            Glide.with(this).load(urlLink+bundle.getString("actionImage")).into(actionImage);
        }
        challenge_id= bundle.getString("challenge_id");
        prof_id=bundle.getString("prof_id");
        list = (ExpandableHeightListView) findViewById(R.id.actionsList);

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
        mDatabase.child("profilechallengesactions/"+prof_id+"/"+challenge_id).addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){

                    for(DataSnapshot datasnapshot2: dataSnapshot.getChildren()){

                        mDatabase.child("action/"+datasnapshot2.getKey()).addValueEventListener(new ValueEventListener() {
                            @Override

                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot!=null){

                                    Log.e("e",StringFromObject(dataSnapshot.child("action_id").getValue()));
                                    ActionChallengeEntity actionChallengeEntity=new ActionChallengeEntity();
                                    actionChallengeEntity.setActionId(StringFromObject(dataSnapshot.child("action_id").getValue()));
                                    actionChallengeEntity.setActionDescrip(StringFromObject(dataSnapshot.child("descrip").getValue()));
                                    actionChallengeEntity.setImageUrl(urlActionLink+StringFromObject(dataSnapshot.child("pict").getValue()));

                                   // actionChallengeEntity.setActionTitle(StringFromObject(dataSnapshot.child("action_id").getValue()));
                                    actionEntityArrayList.add(actionChallengeEntity);

                                    challengeActionsCustomAdapter.notifyDataSetChanged();
                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


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

    }
    public String StringFromObject(Object x){
        String s="";
        if (x!=null){
            s=x.toString();

        }
        return s;
    }

}
