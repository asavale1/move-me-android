package com.savale.model;

public class User {
	private String id;
	private String name;
	
	public User(){
		this.id = "1";
		this.name = "Vault";
	}
	
	public User(String id, String name){
		this.id = id;
		this.name = name;
	}
	
	public String getName(){ return this.name; }
	public String getId(){ return this.id; }
}
