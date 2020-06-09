package com.ocrapi.myapp.model;

import java.util.ArrayList;
import java.util.List;
public class ImageTagModel
{
    private List<Tags> tags;

    private String requestId;

    private Metadata metadata;

    public void setTags(List<Tags> tags){
        this.tags = tags;
    }
    public List<Tags> getTags(){
        return this.tags;
    }
    public void setRequestId(String requestId){
        this.requestId = requestId;
    }
    public String getRequestId(){
        return this.requestId;
    }
    public void setMetadata(Metadata metadata){
        this.metadata = metadata;
    }
    public Metadata getMetadata(){
        return this.metadata;
    }
}
