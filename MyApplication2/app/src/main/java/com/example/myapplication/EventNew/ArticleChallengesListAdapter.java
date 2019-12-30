package com.example.myapplication.EventNew;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ArticleChallengesListAdapter extends BaseAdapter {
    private final Context activity;
    private Bundle bundle;
    private ArrayList<ArticleChallengeEntity> articleChallengeEntities;
    private String urlLink="https://campaigndata-campaign.appspot.com/?t=upd&w=500&crop=true&file=";
    public ArticleChallengesListAdapter(ArrayList<ArticleChallengeEntity> articleChallengeEntities, Context activity1){
        this.activity=activity1;
        this.articleChallengeEntities=articleChallengeEntities;
    }

    @Override
    public int getCount() {
        return this.articleChallengeEntities.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        return view;
    }

}
