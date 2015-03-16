package com.savale.model;

public class Artist {
	private String id;
	private String name;
	
	public Artist(){
		this.id = "";
		this.name = "All";
	}
	
	public Artist(String id, String name){
		this.id = id;
		this.name = name;
	}
	
	public String getName(){ return this.name; }
	public String getId(){ return this.id; }
}
