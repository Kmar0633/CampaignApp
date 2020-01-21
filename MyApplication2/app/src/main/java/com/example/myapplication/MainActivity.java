package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.content.Intent;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
ExpandableHeightListView list;
ArrayList<EventEntity> groupChallengeEntityArrayList=new ArrayList<EventEntity>();
private DatabaseReference mDatabase;// ...
     EventsListAdapter customAdapter;
     private ImageView profilePic;
     private TextView profileTitle;
    private String urlLink="https://campaigndata-campaign.appspot.com/?t=upd&w=500&crop=true&file=";
    private TextView profileDescrp;

    private String imageUrl="e";
    final ArrayList<String> challengesList=new ArrayList<String>();
     public String prof_id="1000116942";
     LinkedHashMap<String,DataSnapshot> dataSnapshotArrayList=new LinkedHashMap<String,DataSnapshot>();
public void getEventChallengesData() {
  //  FirebaseApp.initializeApp(this);


    ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // Get Post object and use the values to update the UI
            //GroupChallengeEntity post = dataSnapshot.getValue(Post.class);
            // ...
            if(dataSnapshot.getValue()!=null){
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    EventEntity groupChallengeEntity = new EventEntity();


                        if(dataSnapshotArrayList.containsKey(dataSnapshot1.getKey())) {
                            groupChallengeEntity.setDataSnapshot(dataSnapshotArrayList.get(dataSnapshot1.getKey()));
                            groupChallengeEntity.setKeyID(dataSnapshot1.getKey());
                            String description = StringFromObject(dataSnapshot1.child("desc").getValue());
                            String title = StringFromObject(dataSnapshot1.child("title").getValue());
                            String pict = StringFromObject(dataSnapshot1.child("pict").getValue());

                            groupChallengeEntity.setDescription(description);
                            groupChallengeEntity.setTitle(title);
                            groupChallengeEntity.setImageUrl(pict);
                            groupChallengeEntityArrayList.add(groupChallengeEntity);
                        }


                }


            }
            mDatabase.child("profilechallengesactions/"+prof_id).addValueEventListener(new ValueEventListener() {
                @Override

                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot!=null){

                        for(DataSnapshot datasnapshot2: dataSnapshot.getChildren()){



                        }

                    }
                    else{
                        for(DataSnapshot datasnapshot2: dataSnapshot.getChildren()){

                            challengesList.add(datasnapshot2.getKey());

                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("kevin",databaseError.getMessage());

                }
            });



            customAdapter.notifyDataSetChanged();
            mDatabase.child("profilechallengesactions/"+prof_id).addValueEventListener(new ValueEventListener() {
                @Override

                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot!=null){

                        for(DataSnapshot datasnapshot2: dataSnapshot.getChildren()){

                            challengesList.add(datasnapshot2.getKey());

                        }

                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            Log.w("Kevin", "loadPost:onCancelled", databaseError.toException());
            // ...
        }
    };


    mDatabase.child("eventchallenges").addValueEventListener(postListener);
}
    public void getProfileData(){
        mDatabase.child("profile/"+prof_id).addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){

                    for(DataSnapshot datasnapshot2: dataSnapshot.getChildren()){


                    if(datasnapshot2.getKey().equals("firstname")) {
                        profileTitle = (TextView) findViewById(R.id.text_activityMain_profileFirstName);

                        profileTitle.setText(StringFromObject(datasnapshot2.getValue()));
                    }

                        else if(datasnapshot2.getKey().equals("descrip")) {

                        profileDescrp = (TextView) findViewById(R.id.text_activityMain_profileDescripName);

                        profileDescrp.setText(StringFromObject(datasnapshot2.getValue()));
                    }
                    else if(datasnapshot2.getKey().equals("avatar")) {
                        profilePic = (ImageView) findViewById(R.id.image_activityMain_profileImage);
                        imageUrl = urlLink + StringFromObject(datasnapshot2.getValue());
                        String imageLink =urlLink + StringFromObject(datasnapshot2.getValue());
                        imageUrl=imageLink;
                        Glide.with(MainActivity.this).load(imageUrl).into(profilePic);
                    }
                         //   Glide.with(this).load(urlLink+StringFromObject(datasnapshot2.getValue())).into(profilePic);


                    }


                }
                else{

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mDatabase = FirebaseDatabase.getInstance().getReference();
        final Intent intent = new Intent(getApplicationContext(), EventChallengesActivity.class);
        super.onCreate(savedInstanceState);
//profilePic=(ImageView) findViewById(R.id.imageView);



        getProfileData();



        setContentView(R.layout.activity_main);
        list = (ExpandableHeightListView) findViewById(R.id.list_activityMain_eventsList);

        list.setExpanded(true);
        customAdapter=new EventsListAdapter(groupChallengeEntityArrayList, this);
        setListViewHeight(list);

        list.setAdapter(customAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                intent.putExtra("photo event",groupChallengeEntityArrayList.get(i).getImageUrl());
                intent.putExtra("title",groupChallengeEntityArrayList.get(i).getTitle());
                intent.putExtra("description",groupChallengeEntityArrayList.get(i).getDescription());
                if (groupChallengeEntityArrayList.get(i).getDataSnapshot()!=null) {
                    intent.putExtra("challenge", groupChallengeEntityArrayList.get(i).getKeyID());
                    intent.putExtra("prof_id",prof_id);

                }


                startActivity(intent);


            }
        });

intent.putStringArrayListExtra("challenges", challengesList);


    getEventChallengesData();
    getProfileEventChallengesData();
    }



    public void getProfileEventChallengesData(){
        ValueEventListener groupChallengesListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                //GroupChallengeEntity post = dataSnapshot.getValue(Post.class);
                // ...
                if(dataSnapshot.getValue()!=null){
                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        dataSnapshotArrayList.put(dataSnapshot1.getKey(),dataSnapshot1)     ;

                    }


                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Kevin", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        mDatabase.child("profileeventchallenges/"+prof_id).addValueEventListener(groupChallengesListener);

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
    public String StringFromObject(Object x){
        String s="";
        if (x!=null){
            s=x.toString();

        }
        return s;
    }


}



