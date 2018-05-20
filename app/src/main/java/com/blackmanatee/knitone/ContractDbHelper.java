package com.blackmanatee.knitone;

import android.database.sqlite.*;
import android.content.*;
import android.util.*;

public class ContractDbHelper extends SQLiteOpenHelper{
	private static final boolean debug = true;

	protected static final int VER = 1;

	protected Contract schema;

	public ContractDbHelper(Context con,String d,Contract s){
		super(con,d,null,VER);
		schema = s;
	}

	@Override
	public void onCreate(SQLiteDatabase db){
		String createSql = "CREATE TABLE "+schema.getName()+" (_id INTEGER PRIMARY KEY";

		for(int a=0;a<schema.getColCount();a++){
			switch(schema.getType(a)){
				case Contract.T_TEXT:
					createSql += ", "+schema.getColumn(a)+" TEXT";
					break;
				case Contract.T_INT:
					createSql += ", "+schema.getColumn(a)+" INTEGER";
					break;
			}
		}
		createSql += ")";
		db.execSQL(createSql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase p1, int p2, int p3){
		if(debug)
			Log.d("manaT","ContractDbHelper.onUpgrade");
	}

	public void setContract(Contract c){
		schema = c;
	}

	public Contract getContract(){
		return schema;
	}
}
