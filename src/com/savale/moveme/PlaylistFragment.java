package com.savale.moveme;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.savale.adapter.PlaylistAdapter;
import com.savale.helpers.PlayerController;
import com.savale.helpers.Search;
import com.savale.listener.PlaylistListener;
import com.savale.model.Playlist;
import com.savale.model.User;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class PlaylistFragment extends Fragment implements PlaylistListener {
	private PlayerController controller;
	private ListView playlistList;
	private BaseActivity base;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.playlist_fragment, container, false);
		playlistList = (ListView) view.findViewById(R.id.playlist_list);
		
		base = (BaseActivity) getActivity(); 
		controller = base.getController();
		
		Search search = new Search(Consts.PLAYLIST);
		search.getPlaylists(this, controller.getUserId());
		
		return view;
	}

	@Override
	public void onPlaylistLoadComplete(String playlistsResult) {
		Log.i("PLAYLIST RESULT", playlistsResult);
		try {
			JSONArray json = new JSONArray(playlistsResult);
			final ArrayList<Playlist> playlists = new ArrayList<Playlist>();
			for(int i = 0; i < json.length(); i++){
				Playlist p = new Playlist(
										json.getJSONObject(i).getString("id"),
										json.getJSONObject(i).getString("name"));
				playlists.add(p);
			}
			playlistList.setAdapter(new PlaylistAdapter(base, playlists));
			playlistList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					controller.setPlaylistId(playlists.get(position).getId());
					//controller.setSongs();
					base.selectTab(Consts.SONG);
				}
			
			});
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
