package com.ocrapi.myapp.model;

public class Words
{
    private String boundingBox;

    private String text;

    public void setBoundingBox(String boundingBox){
        this.boundingBox = boundingBox;
    }
    public String getBoundingBox(){
        return this.boundingBox;
    }
    public void setText(String text){
        this.text = text;
    }
    public String getText(){
        return this.text;
    }
}
