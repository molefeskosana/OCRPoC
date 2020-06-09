package com.ocrapi.myapp.model;

public class Celebrities
{
    private FaceRectangle faceRectangle;

    private String name;

    private double confidence;

    public void setFaceRectangle(FaceRectangle faceRectangle){
        this.faceRectangle = faceRectangle;
    }
    public FaceRectangle getFaceRectangle(){
        return this.faceRectangle;
    }
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
