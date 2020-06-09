package com.ocrapi.myapp.model;

public class Tags
{
    private String name;

    private double confidence;

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setConfidence(double confidence){
        this.confidence = confidence;
    }
    public double getConfidence(){
        return this.confidence;
    }
}
