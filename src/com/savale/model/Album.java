package com.savale.model;

public class Album {
	private String title;
	private String id;
	
	public Album(){
		this.id = "";
		this.title = "All";
	}
	
	public Album(String id, String title){
		this.id = id;
		this.title = title;
	}
	
	public String getTitle(){ return this.title; }
	public String getId(){ return this.id; }
}
