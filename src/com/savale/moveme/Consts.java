package com.savale.moveme;

public final class Consts {
	private Consts(){
		//this prevents even the native class from 
		//calling this ctor as well :
		throw new AssertionError();
	}
	
	public static final int USER = 0;
	public static final int PLAYLIST = 1;
	public static final int ARTIST = 2;
	public static final int ALBUM = 3;
	public static final int SONG = 4;
	public static final int PLAYER = 5;
}