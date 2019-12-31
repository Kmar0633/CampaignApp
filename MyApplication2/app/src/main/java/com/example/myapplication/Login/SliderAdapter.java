package com.example.myapplication.Login;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.example.myapplication.R;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    public SliderAdapter(Context context){
this.context=context;

    }
    public int getCount(){
        return slide_images.length;
    }
    public boolean isViewFromObject(View view, Object o){
        return (view==(LinearLayout)o);
    }
    public int[] slide_images={
            R.drawable.tutorial_1,
            R.drawable.tutorial_2,
            R.drawable.tutorial_3,
            R.drawable.tutorial_4
    };
    public Object instantiateItem(ViewGroup container,int position){
        layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view= layoutInflater.inflate(R.layout.slide_layout,container,false);
        LinearLayout linearLayout=view.findViewById(R.id.relativeLayout_activityMain3_slideLayout);
        ImageView sliderImageView=(ImageView) view.findViewById(R.id.image_slideLayout_sliderImage);
        sliderImageView.setImageResource(slide_images[position]);

        container.addView(view);

        return view;
    }
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((LinearLayout)object);
//        super.destroyItem(container,position,object);
    }

}
