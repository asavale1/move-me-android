package com.savale.model;

public class Song {
	private String id;
	private String name;
	private String url;
	
	public Song(String id, String name, String url){
		this.id = id;
		this.name = name;
		this.url = url;
	}
	
	public String getId(){ return this.id; }
	public String getName(){ return this.name; }
	public String getUrl(){ return this.url; }
}
