package com.savale.moveme;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.savale.adapter.ArtistAdapter;
import com.savale.helpers.PlayerController;
import com.savale.helpers.Search;
import com.savale.listener.ArtistListener;
import com.savale.model.Artist;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class ArtistFragment extends Fragment implements ArtistListener {
	private PlayerController controller;
	private ListView artistList;
	private BaseActivity base;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.artist_fragment, container, false);
		artistList = (ListView) view.findViewById(R.id.artist_list);
		
		base = (BaseActivity) getActivity(); 
		controller = base.getController();
		
		Search search = new Search(Consts.ARTIST);
		search.getArtists(this, controller.getUserId(), controller.getPlaylistId());
		/*String userId= controller.getUserId();
		
		Search search = new Search();
		final List<Artist> artists = search.getArtists(userId);
		artistList.setAdapter(new ArtistAdapter(getActivity(), artists));
		artistList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				controller.setArtistId(artists.get(position).getId());
				controller.setSongs();
				base.selectTab(1);
			}
			
		});*/
		return view;
	}


	@Override
	public void onArtistLoadComplete(String artistsResult) {
		try {
			JSONArray json = new JSONArray(artistsResult);
			final ArrayList<Artist> artists = new ArrayList<Artist>();
			for(int i = 0; i < json.length(); i++){
				try{
					Artist a = new Artist(json.getJSONObject(i).getString("id"), 
											json.getJSONObject(i).getString("name"));
					artists.add(a);
				}catch(JSONException e){
					e.printStackTrace();
				}
			}
			
			artistList.setAdapter(new ArtistAdapter(base, artists));
			artistList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {

					controller.setArtistId(artists.get(position).getId());
				}
				
			});
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
