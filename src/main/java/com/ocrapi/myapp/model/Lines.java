package com.ocrapi.myapp.model;

import java.util.ArrayList;
import java.util.List;
public class Lines
{
    private String boundingBox;

    private List<Words> words;

    public void setBoundingBox(String boundingBox){
        this.boundingBox = boundingBox;
    }
    public String getBoundingBox(){
        return this.boundingBox;
    }
    public void setWords(List<Words> words){
        this.words = words;
    }
    public List<Words> getWords(){
        return this.words;
    }
}
