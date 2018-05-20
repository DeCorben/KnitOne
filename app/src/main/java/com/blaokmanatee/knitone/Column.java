package com.blackmanatee.knitone;

import android.content.res.XmlResourceParser;

import com.blackmanatee.knitone.Sugar;
import com.blackmanatee.knitone.Tag;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayDeque;

public class Column{
	private static final boolean debug = false;
	private String name,label;
	private int type,weight;
	private boolean show,prim;

	public Column(XmlResourceParser res) throws XmlPullParserException, IOException {
		ArrayDeque<String> stack = new ArrayDeque<>();
		while(!("column".equals(res.getName()) && res.getEventType() == XmlPullParser.END_TAG)){
			switch(res.next()){
				case XmlPullParser.START_TAG:
					stack.push(res.getName());
					break;
				case XmlPullParser.TEXT:
					switch(stack.peek()){
						case "column_name":
							name = res.getText();
							break;
						case "type":
							switch (res.getText()){
								case "string":
									type = Contract.T_TEXT;
									break;
								case "integer":
									type = Contract.T_INT;
									break;
							}
							break;
						case "label":
							label = res.getText();
							break;
						case "weight":
							weight = Integer.parseInt(res.getText());
							break;
						case "show_column":
							show = Boolean.parseBoolean(res.getText());
							break;
						case "primary":
							prim = Boolean.parseBoolean(res.getText());
							break;
					}
					break;
				case XmlPullParser.END_TAG:
					if(!res.getName().equals("column"))
						stack.pop();
					break;
			}
			//System.out.println("Column:"+ Sugar.eventType[res.getEventType()]+"->"+res.getName()+"->"+res.getText());
		}
	}

	public Column(Tag t){
		for(Tag c:t.getTags()){
			if(debug)
				System.out.println("Tag:"+c.getTag_name());
			switch(c.getTag_name()){
				case "column_name":
					name = c.getContent();
					break;
				case "type":
					if("string".equals(c.getContent()))
						type = Contract.T_TEXT;
					else if("integer".equals(c.getContent()))
						type = Contract.T_INT;
					break;
				case "label":
					if(debug)
						System.out.println("label:"+c.getContent());
					label = c.getContent();
					break;
				case "weight":
					if(debug)
						System.out.println(c.getContent());
					weight = Integer.parseInt(c.getContent());
					break;
				case "show_column":
					show = Boolean.parseBoolean(c.getContent());
					break;
				case "primary":
					prim = Boolean.parseBoolean(c.getContent());
			}
		}
	}

	public String toXml(){
		String out = "<column>\n";
		if(name != null)
			out += "\t<column_name>"+name+"</column_name>\n";
		switch(type){
			case Contract.T_TEXT:
				out += "\t<type>string</type>\n";
				break;
			case Contract.T_INT:
				out += "\t<type>integer</type>\n";
				break;
		}
		if(label != null)
			out += "\t<label>"+label+"</label>\n";
		out += "\t<weight>"+weight+"</weight>\n" +
			"\t<show_column>"+show+"</show_column>\n" +
			"\t<primary>"+prim+"</primary>\n";
		return out + "</column>";
	}

	@Override
	public boolean equals(Object o) {
		if (debug)
			System.out.println("Column.equals:");
		if (!(o instanceof Column)) {
			if (debug)
				System.out.println("Failed class check");
			return false;
		}
		Column comp = (Column) o;
		if (!name.equals(comp.getName())) {
			if (debug)
				System.out.println("Failed name check:" + name + "|" + comp.getName());
			return false;
		}
		if (!label.equals(comp.getLabel())){
			if (debug)
				System.out.println("Failed label check:"+label+"|"+comp.getLabel());
			return false;
		}
		if(type != comp.getType()) {
			if (debug)
				System.out.println("Failed type check:" + type + "|" + comp.getType());
			return false;
		}
		if(weight != comp.getWeight()) {
			if (debug)
				System.out.println("Failed weight check:" + weight + "|" + comp.getWeight());
			return false;
		}
		if(show != comp.isShow()) {
			if (debug)
				System.out.println("Failed show check:" + show);
			return false;
		}
		if(prim != comp.isPrim()) {
			if (debug)
				System.out.println("Failed primary check:" + prim);
			return false;
		}
		return true;
	}

	public void setPrim(boolean prim)
	{
		this.prim = prim;
	}

	public boolean isPrim()
	{
		return prim;
	}

	public void setShow(boolean show)
	{
		this.show = show;
	}

	public boolean isShow()
	{
		return show;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public int getType()
	{
		return type;
	}

	public void setWeight(int weight)
	{
		this.weight = weight;
	}

	public int getWeight()
	{
		return weight;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public String getLabel()
	{
		return label;
	}

	private void init(String n,int t,String l,int w,boolean s,boolean p){
		name = n;
		type = t;
		label = l;
		weight = w;
		show = s;
		prim = p;
	}

	public Column(){
		init("",Contract.T_TEXT,"",1,true,false);
	}

	public Column(String n,int t,String l,int w,boolean s,boolean p){
		init(n,t,l,w,s,p);
	}
}
