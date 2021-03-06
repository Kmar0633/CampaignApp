package com.example.myapplication.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.EventChallengesActivity;
import com.example.myapplication.R;

public class Main3Activity extends AppCompatActivity {
private ViewPager slideviewPager;
private SliderAdapter sliderAdapter;
private TextView[] mDots;
private LinearLayout mDotsLayout;
private TextView loginText;
private SessionSharedPreferences sessionSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        sessionSharedPreferences = new SessionSharedPreferences(Main3Activity.this);
        loginText=(TextView)findViewById(R.id.text_activityMain3_loginBtn);
        mDotsLayout=(LinearLayout) findViewById(R.id.linearLayout_activityMain3_linearLayout);
        slideviewPager=(ViewPager)findViewById(R.id.viewpager);
        sliderAdapter=new SliderAdapter(this);
        slideviewPager.setAdapter(sliderAdapter);

        addDotsIndicator();

        slideviewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {

            }
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {



            }

            public void onPageSelected(int position) {
                // Check if this is the page you want.
                for(int i=0;i<mDots.length;i++){

                    mDots[i].setTextColor(getResources().getColor(R.color.gray_1));
                }

                mDots[position].setTextColor(getResources().getColor(R.color.Blue));
            }
        });
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                sessionSharedPreferences.setAFTER_OPEN("1");
            }
        });
    }

    private void addDotsIndicator(){
        mDots=new TextView[4];
        for(int i=0; i<mDots.length;i++){

mDots[i]=new TextView(this);
            mDots[i].setText(Html.fromHtml(("&#8226;")));
            mDots[i].setTextSize(35);
            mDots[0].setTextColor(getResources().getColor(R.color.Blue));

            mDotsLayout.addView(mDots[i]);
        }
        mDots[1].setTextColor(getResources().getColor(R.color.gray_1));
        mDots[2].setTextColor(getResources().getColor(R.color.gray_1));
        mDots[3].setTextColor(getResources().getColor(R.color.gray_1));
    }

}
