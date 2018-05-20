package com.blackmanatee.knitone;

import android.content.res.XmlResourceParser;
import android.provider.*;

import com.blackmanatee.knitone.Sugar;
import com.blackmanatee.knitone.Tag;

import org.xmlpull.v1.*;
import java.io.*;
import java.util.*;

public class Contract implements BaseColumns{
	public static final int T_TEXT = 0;
	public static final int T_INT = 1;

	protected ArrayList<Column> grid;
	protected String table;

	public Contract(){
		init("",new String[]{},new int[]{},new int[]{},new String[]{});
	}

	public Contract(String n,String[] c,int[] t){
		int[] l = new int[c.length];
		for(int z=0;z < l.length;z++) l[z] = 1;
		init(n,c,t,l,c);
	}

	public Contract(String n,String[] c,int[] t,int[] w,String[] h){
		init(n,c,t,w,h);
	}

	public Contract(Tag t){
		grid = new ArrayList<>();
		for(Tag c:t.getTags()){
			if("table_name".equals(c.getTag_name()))
				table = c.getContent();
			else if("column".equals(c.getTag_name()))
				grid.add(new Column(c));
		}
	}

	public Contract(String n,String c,String t,String w){
		String[] cols = c.split(";");
		String[] labels = new String[cols.length];
		String[] ty = t.split(";");
		String[] we = w.split(";");
		int[] types = new int[cols.length];
		int[] weights = new int[cols.length];
		for(int z=0;z<labels.length;z++){
			labels[z] = cols[z].substring(0,1).toUpperCase()+cols[z].substring(1);
			types[z] = Integer.parseInt(ty[z]);
			weights[z] = Integer.parseInt(we[z]);
		}
		init(n,cols,types,weights,labels);
	}

	public Contract(XmlResourceParser res) throws XmlPullParserException, IOException {
		grid = new ArrayList<>();
		while(!("contract".equals(res.getName()) && res.getEventType() == XmlPullParser.END_TAG)){
			switch(res.next()){
				case XmlPullParser.START_TAG:
					if(res.getName().equals("column"))
						grid.add(new Column(res));
					break;
				case XmlPullParser.TEXT:
					setName(res.getText());
					break;
			}
			//System.out.println("Contract:"+ Sugar.eventType[res.getEventType()]+"->"+res.getName()+"->"+res.getText());
		}
	}

	private void init(String n,String[] c,int[] t,int[] w,String[] h){
		table = n;
		grid = new ArrayList<>();
		for(int z=0;z<c.length;z++){
			boolean pk = z==0?true:false;
			addColumn(new Column(c[z],t[z],h[z],w[z],true,pk));
		}
	}

	public String toXml(){
		String out = "<contract>\n";
		if(table != null)
			out += "\t<table_name>"+table+"</table_name>\n";
		for(Column c:grid){
			out += "\t"+c.toXml().replaceAll("\n","\n\t")+"\n";
		}
		return out + "</contract>";
	}

	public String[] getColumnList(){
		String[] out = new String[getColCount()];
		for(int z=0;z<getColCount();z++){
			out[z] = grid.get(z).getName();
		}
		return out;
	}

	public void addColumn(Column c){
		grid.add(c);
	}

	@Override
	public boolean equals(Object o){
		if(!(o instanceof Contract))
			return false;
		Contract comp = (Contract) o;
		if (!table.equals(comp.getName()))
			return false;
		if (!grid.equals(comp.getColumns()))
			return false;
		return true;
	}

	public void setName(String n){
		table = n;
	}

	public String getName(){
		return table;
	}

	public void setColumns(ArrayList<Column> c){
		grid = c;
	}

	public ArrayList<Column> getColumns(){
		return grid;
	}

	public String getColumn(int i){
		if(grid.size() > i)
			return grid.get(i).getName();
		return "";
	}

	public int getColCount(){
		return grid.size();
	}

	public void setTypes(int[] t){
		for(int z=0;z<t.length;z++){
			grid.get(z).setType(t[z]);
		}
	}

	public int getType(int i){
		return grid.get(i).getType();
	}

	public void setLayoutWeights(int[] w){
		for(int z=0;z<w.length;z++){
			grid.get(z).setWeight(w[z]);
		}
	}

	public void setLabels(String[] l){
		for(int z=0;z<l.length;z++){
			grid.get(z).setLabel(l[z]);
		}
	}
}
