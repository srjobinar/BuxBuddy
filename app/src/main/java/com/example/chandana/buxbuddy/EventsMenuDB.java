package com.example.chandana.buxbuddy;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class EventsMenuDB extends SQLiteOpenHelper {

	public EventsMenuDB(Context context) {
		super(context, "tathva.db", null, 1);
		// TODO Auto-generated constructor stub
	}

	public EventsMenuDB(Context context, String name, CursorFactory factory,
			int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		

	}
	
	public List<Event> getWorkshopsList(){
		List<Event> list=new ArrayList<Event>();
		String query="SELECT id,name,phone FROM user";
		SQLiteDatabase db=this.getReadableDatabase();
		
		Cursor cursor=db.rawQuery(query,null);
		
		if(cursor.moveToFirst()){
			do{
				Event event=new Event(cursor.getColumnIndex("id"),cursor.getString(cursor.getColumnIndex("name")),
								cursor.getString(cursor.getColumnIndex("phone")));
				list.add(event);
			}while(cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return list;
		
	}

	
	public List<String> getCompList(String branch){
		List<String> list=new ArrayList<String>();
		String query="SELECT name FROM events WHERE type='COMPETITIONS' AND branch='" + branch+"'";
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cursor=db.rawQuery(query,null);
		
		if(cursor.moveToFirst()){
			do{
				list.add(cursor.getString(0));
			}while(cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return list;
		
	}
	
	public String getEventId(String name){
		SQLiteDatabase db=this.getReadableDatabase();
		String query= "SELECT id from events WHERE name='"+name+"'";
		Cursor cursor=db.rawQuery(query, null);
		cursor.moveToFirst();
		String id=cursor.getString(0);
		cursor.close();
		db.close();
		return id;
	}
	
	

}
