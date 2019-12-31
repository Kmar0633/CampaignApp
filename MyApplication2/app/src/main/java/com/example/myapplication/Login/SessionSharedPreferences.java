package com.example.myapplication.Login;


import android.content.Context;
import android.content.SharedPreferences;

public class SessionSharedPreferences {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context mContext;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "InstallPref";
    private static final String KEY_AFTER_OPEN = "AfterOpen";

    public void setAFTER_OPEN(String after_open){

        editor.putString(KEY_AFTER_OPEN,after_open);
        editor.commit();
    }

    public String getKEY_AFTER_OPEN() {

        return sharedPreferences.getString(KEY_AFTER_OPEN, "");
    }

    public SessionSharedPreferences(Context context){
        this.mContext = context;
        sharedPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();

    }
}
