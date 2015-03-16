package com.savale.moveme;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.savale.adapter.SongAdapter;

import com.savale.helpers.PlayerController;
import com.savale.helpers.Search;
import com.savale.listener.SongListener;
import com.savale.model.Song;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class SongFragment extends Fragment implements SongListener {
	private PlayerController  controller;
	private BaseActivity base;
	private ListView songList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.song_fragment, container, false);
		songList = (ListView) view.findViewById(R.id.song_list);
		
		base = (BaseActivity) getActivity();
		controller = base.getController();
		
		Search search = new Search(Consts.SONG);
		search.getSongs(this, 
						controller.getUserId(), 
						controller.getPlaylistId(), 
						controller.getArtistId(), 
						controller.getAlbumId());
		
		return view;
	}

	@Override
	public void onSongLoadComplete(String songsResult) {
		try {
			JSONArray json = new JSONArray(songsResult);
			final ArrayList<Song> songs = new ArrayList<Song>();
			for(int i = 0; i < json.length(); i++){
				Song s = new Song(json.getJSONObject(i).getString("id"),
									json.getJSONObject(i).getString("name"),
									json.getJSONObject(i).getString("link"));
				songs.add(s);
			}
			controller.setSongList(songs);
			
			songList.setAdapter(new SongAdapter(base, songs));
			songList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					controller.pause();
					controller.selectSong(position);
					controller.setupPlayer();
					controller.play();
					base.selectTab(Consts.PLAYER);
				}
			});
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
