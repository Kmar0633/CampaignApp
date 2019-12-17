package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Bundle;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.content.Intent;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Currency;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
ListView list;
ArrayList<GroupChallengeEntity> groupChallengeEntityArrayList=new ArrayList<GroupChallengeEntity>();
private DatabaseReference mDatabase;// ...
     CustomAdapter customAdapter;
public void getData() {
  //  FirebaseApp.initializeApp(this);
    mDatabase = FirebaseDatabase.getInstance().getReference();

    ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // Get Post object and use the values to update the UI
            //GroupChallengeEntity post = dataSnapshot.getValue(Post.class);
            // ...
            if(dataSnapshot.getValue()!=null){
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String description = StringFromObject(dataSnapshot1.child("desc").getValue());
                    String title = StringFromObject(dataSnapshot1.child("title").getValue());
                    String pict = StringFromObject(dataSnapshot1.child("pict").getValue());
                    GroupChallengeEntity groupChallengeEntity = new GroupChallengeEntity();
                    groupChallengeEntity.setDescription(description);
                    groupChallengeEntity.setTitle(title);
                    groupChallengeEntity.setImageUrl(pict);
                    groupChallengeEntityArrayList.add(groupChallengeEntity);
                }


            }


            customAdapter.notifyDataSetChanged();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);

        //mDatabase.intializeApp(this);

        setContentView(R.layout.activity_main);
        list = (ListView) findViewById(R.id.list);
        //EventChallenge eventChallenge= new EventChallenge(this);
        customAdapter=new CustomAdapter(groupChallengeEntityArrayList, this);
        list.setAdapter(customAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),EventChallenge.class);

                intent.putExtra("photo event",groupChallengeEntityArrayList.get(i).getImageUrl());
                intent.putExtra("title",groupChallengeEntityArrayList.get(i).getTitle());

                //intent.putExtra("name",groupChallengeEntityArrayList.get(i));
                startActivity(intent);


            }
        });
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



