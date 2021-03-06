package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventChallengesActivity extends AppCompatActivity{
    ImageView eventImage;
    String imageUrl;
    TextView textTitle;
    TextView textDescription;
    ExpandableHeightGridView list;
    TextView textChallenge;
    DatabaseReference mDatabase;
    String textChallengeInList;
    ArrayList<String> challengesList=new ArrayList<String>();
    EventChallengesListAdapter groupChallengesCustomAdapter;
    String prof_id="";
    ArrayList<EventChallengeEntity> groupChallengeEntityArrayList=new  ArrayList<EventChallengeEntity>();
    public void getData(){




        mDatabase = FirebaseDatabase.getInstance().getReference();

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                //GroupChallengeEntity post = dataSnapshot.getValue(Post.class);
                // ...
                if(dataSnapshot.getValue()!=null){
                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                        // System.out.println(dataSnapshot1);

                        String challengeid;
                        String groupchallenges_id;

                        final  String challenge_id = StringFromObject(dataSnapshot1.child("challenge_id").getValue());

                        // final  String groupchallenge_id = StringFromObject(dataSnapshot1.child("groupchallenge_id"));

                        mDatabase.child("updates/"+challenge_id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshotaction) {

                             final   String imageUrl = StringFromObject(dataSnapshotaction.child("pict/pict1/attfile").getValue());
                                final    String title = StringFromObject(dataSnapshotaction.child("title").getValue());
                                final     String desc = StringFromObject(dataSnapshotaction.child("desc").getValue());
                                final String isVideo=StringFromObject(dataSnapshotaction.child("pict/pict1/is_video").getValue());

                                final   String videoUrl = StringFromObject(dataSnapshotaction.child("pict/pict1/video").getValue());


                                mDatabase.child("profilechallengesactions/"+prof_id+"/"+challenge_id).addValueEventListener(new ValueEventListener() {
                                    @Override

                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        EventChallengeEntity groupChallenges = new EventChallengeEntity();
                                        groupChallenges.setTitle(title);
                                        groupChallenges.setDesciption(desc);
                                        groupChallenges.setVideoUrl(videoUrl);

                                        if(isVideo.equals("1")){
                                            groupChallenges.setVideo(true);
                                         //   Log.e("ler",isVideo);
                                        }
                                        else if(isVideo.equals("0")){
                                            groupChallenges.setVideo(false);
                                        }
                                        groupChallenges.setImage_url(imageUrl);

                                        if(dataSnapshot!=null){
                                            for(DataSnapshot datasnapshot2: dataSnapshot.getChildren()){
                                                groupChallenges.setAction_id(StringFromObject(datasnapshot2.getValue()));
                                            }
                                            if(challengesList.contains(challenge_id)) {
                                                groupChallenges.setAttended(true);
                                            }
                                            else{
                                                groupChallenges.setAttended(false);
                                            }

                                        }
                                        else{

                                        }

                                        groupChallenges.setChallenge_id(challenge_id);

                                        groupChallengeEntityArrayList.add(groupChallenges);

                                        groupChallengesCustomAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });



                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });




                        //   String title = StringFromObject(dataSnapshot1.child("title").getValue());
                        //  String pict = StringFromObject(dataSnapshot1.child("pict").getValue());


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



        mDatabase.child("eventchallengeslist/"+textChallengeInList).addValueEventListener(postListener);




    }
    public static void setListViewHeight(GridView listview){
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
     //   params.height = totalHeight + (listview.getDividerHeight()*(listAdapter.getCount() - 1)) + totalHeight;
     //   listview.setLayoutParams(params);
//listview.setExpand(true);
        //listview.requestLayout();
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_challenges);
        Bundle bundle=getIntent().getExtras();
        eventImage=(ImageView)findViewById(R.id.image_eventChallenges_eventImage);
         textTitle=(TextView)findViewById(R.id.text_eventChallenges_eventTitle);
        textDescription=(TextView)findViewById(R.id.eventDescription);
        textChallenge=(TextView)findViewById(R.id.text_eventChallenges_challengesView);
        Intent intent=getIntent();
        prof_id=intent.getStringExtra("prof_id");
        if (bundle!=null){
            Bundle extras = getIntent().getExtras();
        //  String res=bundle.getInt("photo event");
          //  eventImage.setImageResource(res);

            bundle.getString("photo event");
            Glide.with(this).load(bundle.getString("photo event")).into(eventImage);
            bundle.getString("description");
            bundle.getString("title");
Log.e("e",bundle.getString("description"));
            textTitle.setText(bundle.getString("title"));
            textDescription.setText(bundle.getString("description"));
            textChallenge.setText(bundle.getString("challenge"));
            textChallengeInList=bundle.getString("challenge");
            challengesList=bundle.getStringArrayList("challenges");
           // textView.setText();
            list = (ExpandableHeightGridView) findViewById(R.id.gridview_eventChallenges_challengesList);
            list.setExpanded(true);
            groupChallengesCustomAdapter=new EventChallengesListAdapter(groupChallengeEntityArrayList, this);
            list.setAdapter(groupChallengesCustomAdapter);
            setListViewHeight(list);
            mDatabase = FirebaseDatabase.getInstance().getReference();

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getApplicationContext(), ChallengeActionsActivity.class);
                    intent.putExtra("actionTitle",groupChallengeEntityArrayList.get(i).getTitle());
                    intent.putExtra("actionDescription",groupChallengeEntityArrayList.get(i).getDesciption());
                    intent.putExtra("actionImage",groupChallengeEntityArrayList.get(i).getImage_url());

                    intent.putExtra("isVideo",groupChallengeEntityArrayList.get(i).isVideo());
                    Log.e("k",Boolean.toString(groupChallengeEntityArrayList.get(i).isVideo()));
                    intent.putExtra("videoUrl",groupChallengeEntityArrayList.get(i).getVideoUrl());
                    intent.putExtra("prof_id", prof_id);
                   if(groupChallengeEntityArrayList.get(i).isAttended()){
                       intent.putExtra("prof_id", prof_id);

                       intent.putExtra("challenge_id", groupChallengeEntityArrayList.get(i).getChallenge_id());
                       startActivity(intent);
                   }
                   else{
                   }



                }
            });

        }
        getData();
    }
    public String StringFromObject(Object x){
        String s="";
        if (x!=null){
            s=x.toString();

        }
        return s;
    }


}
