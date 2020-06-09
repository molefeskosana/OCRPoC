package com.ocrapi.myapp.model;

import java.util.ArrayList;
import java.util.List;
public class OcrApiModel
{
    private String language;

    private int textAngle;

    private String orientation;

    private List<Regions> regions;

    public void setLanguage(String language){
        this.language = language;
    }
    public String getLanguage(){
        return this.language;
    }
    public void setTextAngle(int textAngle){
        this.textAngle = textAngle;
    }
    public int getTextAngle(){
        return this.textAngle;
    }
    public void setOrientation(String orientation){
        this.orientation = orientation;
    }
    public String getOrientation(){
        return this.orientation;
    }
    public void setRegions(List<Regions> regions){
        this.regions = regions;
    }
    public List<Regions> getRegions(){
        return this.regions;
    }
}

