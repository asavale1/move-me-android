package com.savale.helpers;

import java.io.IOException;

import com.savale.model.Song;
import com.savale.moveme.PlayerFragment;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

public class PlayerService extends Service{
	private MediaPlayer player;
	
	private final IBinder playerBind = new PlayerBinder();
	
	private boolean songPlaying = false;
	private int attempt = 0;
	
	@Override
	public void onCreate(){
		super.onCreate();		
		player = new MediaPlayer();
		initPlayer();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return playerBind;
	}
	
	@Override
	public boolean onUnbind(Intent intent){
		super.onUnbind(intent);
		player.stop();
		player.release();
		return false;
	}

	/*
	 * Player initialized
	 */
	private void initPlayer(){
		player.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
		player.setAudioStreamType(AudioManager.STREAM_MUSIC);
		player.setOnCompletionListener(PlayerFragment.completionListener);
		player.setOnPreparedListener(preparedListener);
	}
	
	public class PlayerBinder extends Binder {
		public PlayerService getService() {
			return PlayerService.this;
		}
	}
	
	/*
	 * Get duration of song
	 */
	public int getDuration(){ return player.getDuration(); }
	
	/*
	 * Setup player with song
	 */
	public boolean playerSetup(Song song){
		attempt++;
		boolean setup = false;
		player.reset();
		
		try{
			player.setDataSource(song.getUrl());
		}catch(IllegalStateException e){
			Log.i("PLAYER SERVICE", "ILLEGAL STATE EXCEPTION");
			e.printStackTrace();
		}catch(IOException e){
			Log.i("PLAYER SERVICE", "IO EXCEPTION");
			e.printStackTrace();
		}catch(Exception e){
			Log.i("PLAYER SERVICE", "EXCEPTION");
			e.printStackTrace();
		}
		
		try {
			player.prepare();
			setup = true;
		} catch (IllegalStateException | IOException e) {
			Log.i("ERROR", song.getUrl());
			Log.i("PLAYER SERVICE", "ERROR ON PREPARE");
			e.printStackTrace();
			if(attempt < 2){
				playerSetup(song);
			}
			
		}
		
		return setup;
	}
	
	
	/*
	 * Play song
	 */
	public void play(){
		songPlaying = true;
		player.start();
	}
	
	/*
	 * Pause song
	 */
	public void pause(){ 
		songPlaying = false;
		player.pause(); 
	}
	
	/*
	 * Forward songs by 2 sec
	 */
	public void forward(){ 
		int temp = (int) player.getCurrentPosition();
		if((temp + 2000) <= player.getDuration()){
			player.seekTo(player.getCurrentPosition() + 2000);
		}
		
	}
	
	public void seekTo(int position){ player.seekTo(position); }
	
	public boolean getPlaying(){ return this.songPlaying; }
	public void setPlaying(boolean playing){ this.songPlaying = playing; }
	
	/*
	 * Rewind song by 2 sec
	 */
	public void rewind(){ 
		int temp = player.getCurrentPosition();
		
		if((temp - 2000) >= 0){
			player.seekTo(player.getCurrentPosition() - 2000); 
		}
	}
	
	/*
	 * Return current song time
	 */
	public double getCurTime(){ return player.getCurrentPosition(); }
	
	/*
	 * Set song ready to play
	 */
	MediaPlayer.OnPreparedListener preparedListener = new MediaPlayer.OnPreparedListener() {
		
		@Override
		public void onPrepared(MediaPlayer mp) {
			
		}
	};
	
}
