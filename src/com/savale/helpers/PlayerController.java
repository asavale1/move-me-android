package com.savale.helpers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.savale.adapter.SongAdapter;
import com.savale.listener.SongListener;
import com.savale.model.Song;
import com.savale.moveme.Consts;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

public class PlayerController {
	private PlayerService service;

	private String userId = "1";
	private String playlistId = "";
	private String artistId = "";
	private String albumId = "";
	private int songPos = 0;
	
	private ArrayList<Song> songList = new ArrayList<Song>();
	private Song currentSong;
	
	public PlayerController(){ }
	
	public void setPlayerService(PlayerService service){
		this.service = service;
	}
	
	public void setSongList(ArrayList<Song> songList){ this.songList = songList; }
	public ArrayList<Song> getSongList(){ return this.songList; }
	public void selectSong(int songPos){ this.songPos = songPos; }
	public boolean setupPlayer(){ 
		Log.i("PLAYER CONTROLLER", "SETUP PLAYER");
		currentSong = this.songList.get(songPos);
		return service.playerSetup(currentSong);
	}
	
	
	public void setUserId(String userId){ this.userId = userId; }
	public String getUserId(){ return this.userId; }
	
	public void setPlaylistId(String playlistId){ this.playlistId = playlistId; }
	public String getPlaylistId(){ return this.playlistId; }
	
	public void setArtistId(String artistId){ this.artistId = artistId; }
	public String getArtistId(){ return this.artistId; }
	
	public void setAlbumId(String albumId){ this.albumId = albumId; }
	public String getAlbumId(){ return this.albumId; }
	
	public int getSongPos(){ return this.songPos; }
	public String getSongTitle(){ return currentSong.getName(); }
	public int getSongDuration(){ return service.getDuration(); }
	public double getCurTime(){ return service.getCurTime(); }

	public void play(){ 
		service.play();
		service.setPlaying(true);
	}
	public void pause(){ 
		service.pause(); 
		service.setPlaying(false);
	}
	public void forward(){ service.forward(); }
	public void rewind(){ service.rewind(); }
	public void seekTo(int position){ service.seekTo(position);  }
	
	public void setPlaying(boolean playing){ service.setPlaying(playing); }
	public boolean getPlaying(){ return service.getPlaying(); }

}
