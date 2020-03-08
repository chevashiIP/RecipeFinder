package com.recipefinder.utils;

public class Ingridient {
    private String mTitle;
    private String mImageurl;
    private String mAmmount;
    private String mUnit;

    public Ingridient(String title, String imageurl, String ammount, String unit){
        this.mTitle = title;
        this.mImageurl = imageurl;
        this.mAmmount = ammount;
        this.mUnit = unit;

    }

    public String getmTitle(){
        return mTitle;
    }

    public String getmImageurl(){
        return mImageurl;
    }

    public String getmAmmount(){
        return mAmmount;
    }

    public String getmUnit(){return mUnit;}
}
