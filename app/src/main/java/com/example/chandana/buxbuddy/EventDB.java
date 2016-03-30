package com.example.chandana.buxbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class EventDB extends SQLiteOpenHelper {

	public EventDB(Context context) {
		super(context, "tathva.db", null, 1);
		// TODO Auto-generated constructor stub
	}

	public EventDB(Context context, String name, CursorFactory factory,
			int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	public void createuser(String name,String phone,String password) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("phone", phone);
		values.put("password", password);
		database.insert("user", null, values);
		database.close();
	}




	//Read more: http://mrbool.com/how-to-insert-data-into-a-sqlite-database-in-android/28895#ixzz44HicuuFo


	public Event getUserByPhone(String phone){
		SQLiteDatabase db=this.getReadableDatabase();
		String query= "SELECT id, name , password "+
				"FROM user WHERE phone='"+phone+"'";
		Cursor cursor=db.rawQuery(query, null);
		cursor.moveToFirst();
//		Event event=new Event(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),
//				cursor.getString(5),cursor.getString(6),cursor.getString(7),
//				cursor.getString(8),cursor.getString(9),cursor.getString(10),
//				cursor.getString(11),cursor.getString(12),cursor.getString(13),
//				cursor.getString(14),cursor.getString(15)
//				);
		if(cursor.getCount()==0){return null;};
		Event event = new Event(cursor.getColumnIndex("id"),cursor.getString(cursor.getColumnIndex("name")),
						phone,cursor.getString(cursor.getColumnIndex("password")));
		cursor.close();
		db.close();
		return event;
	}


	
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
	
	public String getLocalUpdate(String EventId){
		SQLiteDatabase db=this.getReadableDatabase();
		String query= "SELECT `info_version` from events WHERE id='"+EventId+"'";
		Cursor cursor=db.rawQuery(query, null);
		cursor.moveToFirst();
		String update=cursor.getString(0);
		cursor.close();
		db.close();
		return update;
	}

	public String putContentVersion(String EventId,String version){
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("content_version", version);
		db.update("events",cv , "id=?", new String[]{EventId});
		//db.execSQL("UPDATE events SET `update`='"+update+"' WHERE id="+EventId);
		db.close();
		return version;
	}

	public void updateScheduleData(String EventId,String t1, String t2, String t3, String v1,
			String v2, String v3, String results, String imageurl, String contactname, String contactnumber, String info_ver) {
		
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		if(!t1.equals(""))cv.put("time_d1", t1);
		if(!t2.equals(""))cv.put("time_d2", t2);
		if(!t3.equals(""))cv.put("time_d3", t3);
		if(!v1.equals(""))cv.put("venue_d1", v1);
		if(!v2.equals(""))cv.put("venue_d2", v2);
		if(!v3.equals(""))cv.put("venue_d3", v3);
		if(!imageurl.equals(""))cv.put("image_url", imageurl);
		if(!results.equals(""))cv.put("results", results);
		if(!contactname.equals(""))cv.put("contactname", contactname);
		if(!contactnumber.equals(""))cv.put("contactnumber", contactnumber);
		cv.put("info_version",info_ver);
		db.update("events",cv , "id=?", new String[]{EventId});
		//db.execSQL("UPDATE events SET `update`='"+update+"' WHERE id="+EventId);
		db.close();
		return;
		
	}

	public String getContentVersion(String eventId) {
		SQLiteDatabase db=this.getReadableDatabase();
		String query= "SELECT `content_version` from events WHERE id='"+eventId+"'";
		Cursor cursor=db.rawQuery(query, null);
		cursor.moveToFirst();
		String update=cursor.getString(0);
		cursor.close();
		db.close();
		return update;
		
	}

	public String getInfoVersion(String eventId) {
		SQLiteDatabase db=this.getReadableDatabase();
		String query= "SELECT `info_version` from events WHERE id='"+eventId+"'";
		Cursor cursor=db.rawQuery(query, null);
		cursor.moveToFirst();
		String update=cursor.getString(0);
		cursor.close();
		db.close();
		return update;
	}
}
