package com.blackmanatee.knitone;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by DeCorben on 12/13/2017.
 */

public class Tag {
    private String tag_name,content;
    private HashMap<String,String> attributes;
    private ArrayList<Tag> children;

    public Tag(){
        tag_name = "";
        attributes = new HashMap<>();
        children = new ArrayList<>();
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public void addAttribute(String n,String v){
        attributes.put(n,v);
    }

    public String getAttribute(String n){
        return attributes.get(n);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void addTag(Tag t){
        children.add(t);
    }

    public ArrayList<Tag> getTags(){
        return children;
    }
}
