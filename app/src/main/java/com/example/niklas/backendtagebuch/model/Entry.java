package com.example.niklas.backendtagebuch.model;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.Calendar;

public class Entry implements Serializable{
    private String title;
    private String date;
    private String content;
    private long id;
    private LatLng location;

    public Entry (){
        this(null,null,null,null);
    }
    public Entry(String title){
            this(title,null, null,null);

        }

    public Entry(String title, String date, String content, LatLng location) {
        this.title = title;
        this.date = date;
        this.content = content;
        this.location = location;
    }


    public LatLng getLocation(){
        return this.location;
    }
    public void setLocation(LatLng location){
        this.location = location;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getId(){
        return this.id;
    }

    public void setId(long ID){ this.id = ID;}

    public String getContent() {return content; }

    public void setContent(String content) {
        this.content = content;
    }
}