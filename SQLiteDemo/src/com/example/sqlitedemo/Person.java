package com.example.sqlitedemo;

public class Person {
	private String nameString;
	private int id;
	private String phoneNum;
	private int amount;
	
	
	public Person(String nameString, String phoneNum, int amount) {
		super();
		this.nameString = nameString;
		this.phoneNum = phoneNum;
		this.amount = amount;
	}
	
	public Person(String nameString, int id, String phoneNum, int amount) {
		super();
		this.nameString = nameString;
		this.id = id;
		this.phoneNum = phoneNum;
		this.amount = amount;
	}
	
	public String getNameString() {
		return nameString;
	}
	public void setNameString(String nameString) {
		this.nameString = nameString;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Person [nameString=" + nameString + ", id=" + id
				+ ", phoneNum=" + phoneNum + ", amount=" + amount + "]";
	}
	
	
}
