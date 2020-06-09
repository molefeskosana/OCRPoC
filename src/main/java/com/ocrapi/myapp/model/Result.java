package com.ocrapi.myapp.model;

import java.util.ArrayList;
import java.util.List;
public class Result
{
    private List<Celebrities> celebrities;

    public void setCelebrities(List<Celebrities> celebrities){
        this.celebrities = celebrities;
    }
    public List<Celebrities> getCelebrities(){
        return this.celebrities;
    }
}
