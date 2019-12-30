package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
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
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChallengeActionsActivity extends AppCompatActivity {
    TextView actionTitle;
    ExpandableHeightListView list;
    private String urlLink="https://campaigndata-campaign.appspot.com/?t=upd&w=500&crop=true&file=";
    private String urlActionLink="https://campaigndata-campaign.appspot.com/?t=act&w=500&crop=true&file=";
    ChallengeActionsListAdapter challengeActionsListAdapter;
    TextView actionDescription;
    ImageView actionImage;
    SimpleExoPlayerView exoPlayerView;
    static boolean active = false;
    SimpleExoPlayer exoPlayer;
private String videoUrl="";
    String prof_id;
    Bundle bundle;
    String challenge_id;
    Uri videouri;
    Boolean isAVideo=false;
    private DatabaseReference mDatabase;
    ArrayList<ActionChallengeEntity> actionEntityArrayList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle=getIntent().getExtras();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final Intent intent = new Intent(getApplicationContext(), ActionActivity.class);
        actionEntityArrayList=new ArrayList<ActionChallengeEntity>();
        setContentView(R.layout.activity_challenge_actions);
        actionImage=(ImageView)findViewById(R.id.actionImage);
        actionDescription=(TextView) findViewById(R.id.actionDescription);
        actionTitle=(TextView) findViewById(R.id.actionTitle);
        exoPlayerView=(SimpleExoPlayerView)findViewById(R.id.exoplayerview);

        try{

            if(bundle.getBoolean("isVideo")){
                isAVideo=true;
                BandwidthMeter bandwidthMeter=new DefaultBandwidthMeter();
                TrackSelector trackSelector=new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                videoUrl="https://campaigndata-campaign.appspot.com/?t=vidupd&file="+bundle.getString("videoUrl");
                exoPlayer=ExoPlayerFactory.newSimpleInstance(this,trackSelector);
                videouri=Uri.parse(videoUrl);
                DefaultHttpDataSourceFactory defaultHttpDataSourceFactory=new DefaultHttpDataSourceFactory("exoplayer_video");
                ExtractorsFactory extractorsFactory=new DefaultExtractorsFactory();
                MediaSource mediaSource=new ExtractorMediaSource(videouri,defaultHttpDataSourceFactory,extractorsFactory,null,null);
                exoPlayerView.setPlayer(exoPlayer);
                exoPlayer.prepare(mediaSource);
                if(active==true){
                exoPlayer.setPlayWhenReady(true);
                    Log.e("Start","Start");
                }

                Log.e("MainActivity","exoplayer error");
                Log.e("check vid",bundle.getString("videoUrl"));
                Log.e("chec",String.valueOf(bundle.getBoolean("isVideo")));
            }
            else if(!bundle.getBoolean("isVideo")){
                exoPlayerView.setVisibility(View.GONE);
                Glide.with(this).load(urlLink+bundle.getString("actionImage")).into(actionImage);
            }

        }
        catch(Exception e){

            Log.e("Error",e.toString());

        }
        if(bundle!=null) {
            actionDescription.setText(bundle.getString("actionDescription"));
            actionTitle.setText(bundle.getString("actionTitle"));


        }
        challenge_id= bundle.getString("challenge_id");
        prof_id=bundle.getString("prof_id");
        list = (ExpandableHeightListView) findViewById(R.id.actionsList);

        list.setExpanded(true);
        challengeActionsListAdapter =new ChallengeActionsListAdapter(actionEntityArrayList, this);
        setListViewHeight(list);

        list.setAdapter(challengeActionsListAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                intent.putExtra("actionTitle",actionEntityArrayList.get(i).getActionTitle());
                intent.putExtra("actionDescrip",actionEntityArrayList.get(i).getActionDescrip());
                intent.putExtra("actionImage",actionEntityArrayList.get(i).getImageUrl());
               // Log.e("actionTitle",actionEntityArrayList.get(i).getActionTitle());
                startActivity(intent);
            }
        });
getActionsData();

    }
    @Override
    public void onStart() {
        super.onStart();
        active = true;

    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;


    }

    @Override
    public void onBackPressed() {

            super.onBackPressed();
            if (isAVideo==true) {
                exoPlayer.setPlayWhenReady(false);
            }

            Log.e("Stop", "Stop");


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

                                    ActionChallengeEntity actionChallengeEntity=new ActionChallengeEntity();
                                    actionChallengeEntity.setActionId(StringFromObject(dataSnapshot.child("action_id").getValue()));
                                    actionChallengeEntity.setActionDescrip(StringFromObject(dataSnapshot.child("descrip").getValue()));
                                    actionChallengeEntity.setImageUrl(urlActionLink+StringFromObject(dataSnapshot.child("pict").getValue()));

                                   // actionChallengeEntity.setActionTitle(StringFromObject(dataSnapshot.child("action_id").getValue()));
                                    actionEntityArrayList.add(actionChallengeEntity);

                                    challengeActionsListAdapter.notifyDataSetChanged();
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
