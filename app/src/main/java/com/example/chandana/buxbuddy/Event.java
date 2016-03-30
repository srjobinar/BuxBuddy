package com.example.chandana.buxbuddy;



public class Event {

	
	public int userId;
	public String userName;
	public String userPhone;
	public String password;
	public int groupId;
	public String groupName;
	/**/
	
	public Event(int userId,String userName,String userPhone,String password) {
		
		this.userId = userId;
		this.userName = userName;
		this.userPhone = userPhone;
		this.password = password;
	}
	public Event(int userId,String userName,String userPhone) {

		this.userId = userId;
		this.userName = userName;
		this.userPhone = userPhone;
	}

	public Event(int groupId,String groupName) {

		this.groupId = groupId;
		this.groupName = groupName;
	}
	


	
}