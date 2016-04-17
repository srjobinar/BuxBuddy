package com.example.chandana.buxbuddy;

import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
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

	/*public EventsMenuDB(Context context, String name, CursorFactory factory,
			int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
		// TODO Auto-generated constructor stub
	}*/

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		

	}
	
	public List<Event> getUsersList(Integer uid){
		List<Event> list=new ArrayList<Event>();
		String query="SELECT id,name,phone FROM user where id!="+uid;
		SQLiteDatabase db=this.getReadableDatabase();
		
		Cursor cursor=db.rawQuery(query,null);
		
		if(cursor.moveToFirst()){
			do{
				Event event=new Event(cursor.getInt(cursor.getColumnIndex("id")),cursor.getString(cursor.getColumnIndex("name")),
								cursor.getString(cursor.getColumnIndex("phone")));
				list.add(event);
			}while(cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return list;
		
	}
	public List<Event> getMembersList(int gid,int uid){
		List<Event> list=new ArrayList<Event>();
		String query="SELECT id,name,phone FROM user inner join userGroup on user.id=userGroup.userid where groupid="+gid+" and userid!="+uid;
		SQLiteDatabase db=this.getReadableDatabase();

		Cursor cursor=db.rawQuery(query,null);

		if(cursor.moveToFirst()){
			do{
				Event event=new Event(cursor.getInt(cursor.getColumnIndex("id")),cursor.getString(cursor.getColumnIndex("name")),
						cursor.getString(cursor.getColumnIndex("phone")));
				list.add(event);
			}while(cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return list;

	}

	public List<Event> getGroupsList(Integer x){
		List<Event> list=new ArrayList<Event>();
		String query="SELECT id,name FROM groups inner join userGroup on groups.id=userGroup.groupid where userid="+x;
		SQLiteDatabase db=this.getReadableDatabase();

		Cursor cursor=db.rawQuery(query,null);

		if(cursor.moveToFirst()){
			do{
				Event event=new Event(cursor.getInt(cursor.getColumnIndex("id")),cursor.getString(cursor.getColumnIndex("name")));
				list.add(event);
			}while(cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return list;

	}

	public List<Event> getRequestGroupsList(){
		List<Event> list=new ArrayList<Event>();
		String query="SELECT id,name FROM groups inner join requests on groups.id=requests.groupid where status = 0";
		SQLiteDatabase db=this.getReadableDatabase();

		Cursor cursor=db.rawQuery(query,null);

		if(cursor.moveToFirst()){
			do{
				Event event=new Event(cursor.getInt(cursor.getColumnIndex("id")),cursor.getString(cursor.getColumnIndex("name")));
				list.add(event);
			}while(cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return list;
	}

	public List<Event> getTransactionsList(int x){
		List<Event> list=new ArrayList<Event>();
		String query="SELECT id,name,amount FROM transactions where groupid="+x;
		SQLiteDatabase db= getReadableDatabase();
		if(db==null){
			Log.d("j","null");
		}

		Cursor cursor=db.rawQuery(query,null);

		if(cursor.moveToFirst()){
			do{
				Event event=new Event(cursor.getInt(cursor.getColumnIndex("id")),cursor.getString(cursor.getColumnIndex("name")),
								cursor.getInt(cursor.getColumnIndex("amount")));
				list.add(event);
			}while(cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return list;

	}

	public List<Event> getTransactionMembersList(int x){
		List<Event> list=new ArrayList<Event>();
		String query="SELECT id,name,amount FROM user inner join userTransaction on user.id=userTransaction.userid where transid="+x;
		SQLiteDatabase db=this.getReadableDatabase();

		Cursor cursor=db.rawQuery(query,null);

		if(cursor.moveToFirst()){
			do{
				Event event=new Event(cursor.getInt(cursor.getColumnIndex("id")), cursor.getInt(cursor.getColumnIndex("amount"))
						,cursor.getString(cursor.getColumnIndex("name")));
				list.add(event);
			}while(cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return list;

	}

	public List<Event> getFundsList(int x){
		List<Event> list=new ArrayList<Event>();
		String query="SELECT name,id,fund FROM user inner join funds on user.id=funds.userid where groupid="+x;
		SQLiteDatabase db=this.getReadableDatabase();

		Cursor cursor=db.rawQuery(query,null);

		if(cursor.moveToFirst()){
			do{
				Event event=new Event(cursor.getString(cursor.getColumnIndex("name")),cursor.getInt(cursor.getColumnIndex("id")),
						cursor.getInt(cursor.getColumnIndex("fund")));
				list.add(event);
			}while(cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return list;

	}

	public List<Event> getPersonalFundsList(int x){
		List<Event> list=new ArrayList<Event>();
		String query="SELECT name,fund FROM groups inner join funds on groups.id=funds.groupid where userid="+x;
		SQLiteDatabase db=this.getReadableDatabase();

		Cursor cursor=db.rawQuery(query,null);

		if(cursor.moveToFirst()){
			do{
				Event event=new Event(cursor.getString(cursor.getColumnIndex("name")),
						cursor.getInt(cursor.getColumnIndex("fund")));
				list.add(event);
			}while(cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return list;

	}

	public long creategroup(String name) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", name);
		long v = database.insert("groups", null, values);
		database.close();
		return v;
	}

	public long createtransaction(Integer amount,String transName,Integer grpid) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("amount", amount);
		values.put("groupid", grpid);
		values.put("name", transName);
		long v = database.insert("transactions", null, values);
		database.close();
		return v;
	}

	public void createfund(Integer userid,Integer grpid) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("userid", userid);
		values.put("groupid", grpid);
		values.put("fund", 0);
		database.insert("funds", null, values);
		database.close();
	}

	public void updatefund(Integer userid,Integer grpid,Integer amnt) {
		SQLiteDatabase database = this.getWritableDatabase();
//		ContentValues values = new ContentValues();
//		values.put("userid", userid);
//		values.put("groupid", grpid);
//		values.put("fund", +amnt);
//		 database.update("funds", values, "where userid =" + userid + " AND groupid=" + grpid, null);
		database.execSQL("UPDATE funds SET fund=fund+" + amnt + " where userid =" + userid + " AND groupid=" + grpid);
		database.close();
	}

	public long createusergroup(int userid,int groupid,int admin) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("userid", userid);
		values.put("groupid", groupid);
		values.put("admin", admin);
		long v = database.insert("userGroup", null, values);
		database.close();
		return v;
	}

	public long createusertransaction(int userid,int transactionid,int amount) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("userid", userid);
		values.put("transid", transactionid);
		values.put("amount", amount);
		long v = database.insert("userTransaction", null, values);
		database.close();
		return v;
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

	public Boolean checkAdmin(Integer uid,Integer gid){
		SQLiteDatabase db=this.getReadableDatabase();
		String query="SELECT admin FROM userGroup where userid="+uid+" AND groupid="+gid;
		Cursor cursor=db.rawQuery(query,null);
		if(cursor.moveToFirst()){
			if(cursor.getInt(0)==1)
				return true;
			else
				return false;
		}
		cursor.close();
		db.close();
		return false;
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

	public void deleteTransaction (int tid)
	{
		SQLiteDatabase db=this.getWritableDatabase();
		String query = "DELETE FROM transactions WHERE id = "+tid;
		db.execSQL(query);
		query = "DELETE FROM userTransaction WHERE transid = "+tid;
		db.execSQL(query);
	}

	public List<Event> getUserTransaction(int tid)
	{
		List<Event> list = new ArrayList<Event>();
		SQLiteDatabase db = this.getReadableDatabase();
		String query= "select userid,amount from userTransaction where transid="+tid;
		Cursor cursor=db.rawQuery(query, null);
		if(cursor.moveToFirst()){
			do{
				Event event=new Event(cursor.getInt(cursor.getColumnIndex("userid")),
						cursor.getInt(cursor.getColumnIndex("amount")));
				list.add(event);
			}while(cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return list;
	}

	public void setRequest(int uid,int gid,int transid,int type){
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("fromid", uid);
		values.put("transid", transid);
		values.put("groupid", gid);
		if(type!=0){
			values.put("amount",type);
			values.put("type", 1);
		}
		else {
			values.put("type", type);
		}
		long v = db.insert("requests", null, values);
		db.close();
	}

	public String getUsername(int uid){
		SQLiteDatabase db = this.getReadableDatabase();
		String query = "select name from user where id="+uid;
		Cursor cursor=db.rawQuery(query, null);
		cursor.moveToFirst();
		return cursor.getString(cursor.getColumnIndex("name"));
	}

	public String getTransaction(int tid){
		String trans="";
		SQLiteDatabase db = this.getReadableDatabase();
		String query = "select name from transactions where id="+tid;
		Cursor cursor=db.rawQuery(query, null);
		if(cursor.moveToFirst()) {
			trans= cursor.getString(cursor.getColumnIndex("name"));
		}
		return trans;
	}

	public List<Event> getRequestList(int gid){
		List<Event> list = new ArrayList<Event>();
		String username,transaction;
		SQLiteDatabase db = this.getReadableDatabase();
		String query= "select fromid,transid,status from requests where groupid="+gid;
		Cursor cursor=db.rawQuery(query, null);
		if(cursor.moveToFirst()){
			do{
				username = getUsername(cursor.getInt(cursor.getColumnIndex("fromid")));
				transaction = getTransaction(cursor.getInt(cursor.getColumnIndex("transid")));
				Event event=new Event(username,transaction,cursor.getInt(cursor.getColumnIndex("transid")),
						cursor.getInt(cursor.getColumnIndex("status")));
				list.add(event);
			}while(cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return list;
	}

	public void updateStatus(int tid,int status){
		SQLiteDatabase database = this.getWritableDatabase();
		database.execSQL("UPDATE requests SET status="+status+" where transid =" + tid);
		database.close();
	}
}