package com.example.myapplication.EventNew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.example.myapplication.ExpandableHeightListView;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private TextView articleChallengeTitle;
    private TextView articleChallengeDescrip;
    private ImageView articleChallengeImage;
    private ArticleChallengesListAdapter customAdapter;
    private ExpandableHeightListView articleChallengesList;
    private String itemId;
    private ArrayList<ArticleChallengeEntity>articleChallengeEntities;
    private String urlLink="https://campaigndata-campaign.appspot.com/?t=upd&w=500&crop=true&file=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        articleChallengeTitle = (TextView) findViewById(R.id.text_activityMain2_articleTitle);
        articleChallengeDescrip = (TextView) findViewById(R.id.text_activityMain2_articleDescription);
        articleChallengeImage=(ImageView) findViewById(R.id.image_activityMain2_articleImage);
        articleChallengesList = (ExpandableHeightListView) findViewById(R.id.list_activityMain2_challengesList);

        articleChallengeEntities=new ArrayList<>();
        getArticleChallengesData();

        articleChallengesList.setExpanded(true);

        customAdapter=new ArticleChallengesListAdapter(articleChallengeEntities, this);
        setListViewHeight(articleChallengesList);

        articleChallengesList.setAdapter(customAdapter);
        getArticleChallengesListData();



    }
    public void getArticleChallengesListData(){
        mDatabase.child("articlechallengesitemlist/"+itemId).addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){

                    for(DataSnapshot datasnapshot2: dataSnapshot.getChildren()){


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
//listview.setExpand(true);
        //listview.requestLayout();
    }

    public void getArticleChallengesData(){
        mDatabase.child("articlechallenges/-Lkad31hNdaDkadER").addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){

                    for(DataSnapshot datasnapshot2: dataSnapshot.getChildren()){

                        if(datasnapshot2.getKey().equals("title")){
                            articleChallengeTitle.setText(StringFromObject(datasnapshot2.getValue()));
                        }
                        if(datasnapshot2.getKey().equals("short_desc")){
                            articleChallengeDescrip.setText(StringFromObject(datasnapshot2.getValue()));
                        }
                        if(datasnapshot2.getKey().equals("cover")){
                            Glide.with(Main2Activity.this).load(urlLink+StringFromObject(datasnapshot2.getValue())).into(articleChallengeImage);
                        }
                        if(datasnapshot2.getKey().equals("item_id")){
                            itemId=StringFromObject(datasnapshot2.getValue());
                            mDatabase.child("articlechallengesitemlist/"+itemId).addValueEventListener(new ValueEventListener() {
                                @Override

                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    if(dataSnapshot!=null){

                                        for(DataSnapshot datasnapshot2: dataSnapshot.getChildren()){
                                            Log.e("jelly",StringFromObject(datasnapshot2.child("content").getValue()));
                                            mDatabase.child("updates/"+itemId).addValueEventListener(new ValueEventListener() {
                                                @Override

                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    if(dataSnapshot!=null){




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
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    public String StringFromObject(Object x){
        String s="";
        if (x!=null){
            s=x.toString();

        }
        return s;
    }
}
