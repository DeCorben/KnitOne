package com.blackmanatee.knitone;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import android.content.res.XmlResourceParser;

import com.blackmanatee.knitone.Sugar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class ManaTB {
    private static final boolean debug = false;

    private String db;
    private HashMap<String,Contract> tables;

    public ManaTB(){
        db = "";
        tables = new HashMap<>();
    }

    public ManaTB(XmlResourceParser res) throws XmlPullParserException, IOException {
        db = "";
        tables = new HashMap<>();
        loadXml(res);
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof ManaTB))
            return false;
        ManaTB comp = (ManaTB)o;
        if(!db.equals(comp.getDb()))
            return false;
        if(!getTableList().equals(comp.getTableList()))
            return false;
        for(String t:tables.keySet()){
            if(!comp.getTable(t).equals(getTable(t)))
                return false;
        }
        return true;
    }

    public void loadXml(XmlResourceParser res) throws XmlPullParserException, IOException {
        while(res.getEventType() != XmlPullParser.END_DOCUMENT){
            switch(res.next()){
                case XmlPullParser.START_TAG:
                    if(res.getName().equals("contract"))
                        addTable(new Contract(res));
                    break;
                case XmlPullParser.TEXT:
                    setDb(res.getText());
                    break;
            }
            //System.out.println("ManaTB:"+ Sugar.eventType[res.getEventType()]+"->"+res.getName()+"->"+res.getText());
        }
    }

    public void setDb(String d){
        db = d;
    }

    public String getDb(){
        return db;
    }

    public void addTable(Contract c){
        tables.put(c.getName(),c);
		if(debug){
			System.out.println("added contract:"+tables.get(c.getName()).getName());
		}
    }

    public Contract getTable(String t){
        return tables.get(t);
    }

	public void deleteTable(String t){
		if(debug)
			System.out.println("ManaTB Marco "+t);
		tables.remove(t);
	}

    public Contract getDefaultTable(){
        for(String k:tables.keySet()){
            return tables.get(k);
        }
        return new Contract();
    }

	public ArrayList<String> getTableList(){
		ArrayList<String> l = new ArrayList<>();
		for(String s:tables.keySet()){
			l.add(s);
		}
		return l;
	}
}
