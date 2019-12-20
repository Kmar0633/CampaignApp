package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.os.Bundle;
import android.content.Context;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventChallenge extends AppCompatActivity{
    ImageView eventImage;
    String imageUrl;
    TextView textTitle;
    TextView textDescription;
    ExpandableHeightListView list;
    TextView textChallenge;
    DatabaseReference mDatabase;
    String textChallengeInList;
    GroupChallengesCustomAdapter groupChallengesCustomAdapter;

    ArrayList<GroupChallenges> groupChallengeEntityArrayList=new  ArrayList<GroupChallenges>();
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
                        Log.e("kevin", dataSnapshot1.child("challenge_id").getValue() + " challengeid1");
                        String groupchallenges_id;

                        final  String challenge_id = StringFromObject(dataSnapshot1.child("challenge_id").getValue());
                        // final  String groupchallenge_id = StringFromObject(dataSnapshot1.child("groupchallenge_id"));
                      //  Log.e("kevin", dataSnapshot2 + " challengeid");
                        //  Log.e("kevin", dataSnapshot.+ " challengeid");

                        GroupChallenges groupChallenges = new GroupChallenges();
                        groupChallenges.setChallenge_id(challenge_id);

                        groupChallengeEntityArrayList.add(groupChallenges);

                        groupChallengesCustomAdapter.notifyDataSetChanged();


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



        mDatabase.child("groupchallenges/"+textChallengeInList).addValueEventListener(postListener);



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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_challenge);
        Bundle bundle=getIntent().getExtras();
        eventImage=(ImageView)findViewById(R.id.imageChallenge);
         textTitle=(TextView)findViewById(R.id.challengeTitle);
        textDescription=(TextView)findViewById(R.id.eventDescription);
        textChallenge=(TextView)findViewById(R.id.textChallenge);

        if (bundle!=null){
            Bundle extras = getIntent().getExtras();
        //  String res=bundle.getInt("photo event");
          //  eventImage.setImageResource(res);

            bundle.getString("photo event");
            Glide.with(this).load(bundle.getString("photo event")).into(eventImage);
            bundle.getString("description");
            bundle.getString("title");
            textTitle.setText(bundle.getString("title"));
            textDescription.setText(bundle.getString("description"));
            textChallenge.setText(bundle.getString("challenge"));
            textChallengeInList=bundle.getString("challenge");
           // textView.setText();
            list = (ExpandableHeightListView) findViewById(R.id.listChallenges);
            list.setExpanded(true);
            groupChallengesCustomAdapter=new GroupChallengesCustomAdapter(groupChallengeEntityArrayList, this);
            list.setAdapter(groupChallengesCustomAdapter);
            setListViewHeight(list);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



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
