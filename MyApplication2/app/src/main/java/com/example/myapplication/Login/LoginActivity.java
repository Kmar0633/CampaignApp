package com.example.myapplication.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myapplication.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_log_in_new);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
