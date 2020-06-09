package com.ocrapi.myapp.model;

import java.util.ArrayList;
import java.util.List;
public class Regions
{
    private String boundingBox;

    private List<Lines> lines;

    public void setBoundingBox(String boundingBox){
        this.boundingBox = boundingBox;
    }
    public String getBoundingBox(){
        return this.boundingBox;
    }
    public void setLines(List<Lines> lines){
        this.lines = lines;
    }
    public List<Lines> getLines(){
        return this.lines;
    }
}

