package com.example.chandana.buxbuddy;



public class Event {

	
	public int userId;
	public String userName;
	public String userPhone;
	public String password;
	public int groupId;
	public String groupName;
	public int transactionId;
	public String transactionName;
	public int amount;
	public int fund;
	public String message;
	public int status;
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

	public Event(int transactionId,String transactionName,int amount) {

		this.transactionId = transactionId;
		this.transactionName = transactionName;
		this.amount = amount;
	}
	public Event(int userId,int amount,String userName) {

		this.userId = userId;
		this.userName = userName;
		this.amount = amount;
	}

	public Event(String userName,int userId,int fund) {

		this.userId = userId;
		this.userName = userName;
		this.fund = fund;
	}

	public Event(String groupName,int fund) {
		this.groupName = groupName;
		this.fund = fund;
	}

	public Event(int userId, int amount)
	{
		this.userId=userId;
		this.amount=amount;
	}

	public Event(String username,String transaction,int tid,int status){
		this.userName=username;
		this.transactionName=transaction;
		this.transactionId=tid;
		this.message= userName+" requests rollback of "+transactionName;
		this.status=status;
	}


	
}
