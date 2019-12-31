package com.example.myapplication.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.myapplication.Login.SessionSharedPreferences;
import com.example.myapplication.R;

public class SplashActivity extends AppCompatActivity {
    SessionSharedPreferences sessionSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sessionSharedPreferences = new SessionSharedPreferences(SplashActivity.this);


        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sessionSharedPreferences.getKEY_AFTER_OPEN().equalsIgnoreCase("1")){

                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(getApplicationContext(), Main3Activity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);
    }
}
