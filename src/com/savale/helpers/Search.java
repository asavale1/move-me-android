package com.savale.helpers;

import com.savale.listener.AlbumListener;
import com.savale.listener.ArtistListener;
import com.savale.listener.PlaylistListener;
import com.savale.listener.SongListener;
import com.savale.listener.UserListener;
import com.savale.model.*;
import com.savale.moveme.Consts;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import android.os.AsyncTask;
import android.util.Log;

public class Search{
	private UserListener uListener;
	private PlaylistListener pListener;
	private ArtistListener artListener;
	private AlbumListener albListener;
	private SongListener sListener;
	private int searchType;
	
	public Search(int searchType){
		this.searchType = searchType;
	}
	
	public void getUsers(UserListener uListener){
		this.uListener = uListener;
		//new RequestTask().execute("http://192.168.0.15:3000/search_users");
		new RequestTask().execute("http://192.168.0.31/search_users");
	}
	
	public void getPlaylists(PlaylistListener pListener, String userId){
		this.pListener = pListener;
		Log.i("SEARCH PLAYLISTS", "http://192.168.0.31/search_playlists?user_id=" + userId);
		new RequestTask().execute("http://192.168.0.31/search_playlists?user_id=" + userId);
	}
	
	public void getArtists(ArtistListener artListener, String userId, String playlistId){
		this.artListener = artListener;
		new RequestTask().execute("http://192.168.0.31/search_artists?user_id="+ userId + 
									"&playlist_id=" + playlistId);
	}
	
	public void getAlbums(AlbumListener albListener, String userId, String playlistId, String artistId){
		this.albListener = albListener;
		new RequestTask().execute("http://192.168.0.31/search_albums?user_id="+ userId + 
									"&playlist_id=" + playlistId + 
									"&artist_id=" + artistId);
	}
	
	public void getSongs(SongListener sListener, String userId,
							String playlistId, String artistId, String albumId){
		this.sListener = sListener;
		new RequestTask().execute("http://192.168.0.31/search_songs?user_id="+ userId + 
									"&playlist_id=" + playlistId + 
									"&artist_id=" + artistId +
									"&album_id=" + albumId); 
	}

	private class RequestTask extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... uri) {
			HttpClient client = new DefaultHttpClient();
			HttpResponse response;
			String responseString = null;
			
			try{
				response = client.execute(new HttpGet(uri[0]));
				StatusLine status = response.getStatusLine();
				if(status.getStatusCode() == HttpStatus.SC_OK){
	                ByteArrayOutputStream out = new ByteArrayOutputStream();
	                response.getEntity().writeTo(out);
	                out.close();
	                responseString = out.toString();
	            } else{
	                response.getEntity().getContent().close();
	                throw new IOException(status.getReasonPhrase());
	            }
			}catch(ClientProtocolException e){
				e.printStackTrace();
			}catch(IOException e){
				e.printStackTrace();
			}
			return responseString;
		}
		
		@Override
		protected void onPostExecute(String result) {
		   switch(searchType){
		   case Consts.USER:
			   uListener.onUserLoadComplete(result);
			   break;
		   case Consts.PLAYLIST:
			   	pListener.onPlaylistLoadComplete(result);
			   break;
		   case Consts.ARTIST:
			   
			   artListener.onArtistLoadComplete(result);
			   break;
		   case Consts.ALBUM:
			   albListener.onAlbumLoadComplete(result);
			   break;
		   case Consts.SONG:
			   sListener.onSongLoadComplete(result);
			   break;
		   }
		}
		
	}

}
