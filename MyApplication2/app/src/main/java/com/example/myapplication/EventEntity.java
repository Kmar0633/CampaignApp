package com.example.myapplication;

import com.google.firebase.database.DataSnapshot;

public class EventEntity {
    String title;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    String keyID;
    String imageUrl;
    DataSnapshot dataSnapshot;
    public void setKeyID(String keyID){
        this.keyID=keyID;
    }
    public String getKeyID(){
        return this.keyID;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DataSnapshot getDataSnapshot(){
        return dataSnapshot;
    }
    public void setDataSnapshot(DataSnapshot dataSnapshot){
         this.dataSnapshot=dataSnapshot;
    }
}
