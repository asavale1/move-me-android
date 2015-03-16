package com.savale.moveme;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;


import com.savale.adapter.AlbumAdapter;
import com.savale.adapter.ArtistAdapter;
import com.savale.helpers.PlayerController;
import com.savale.helpers.Search;
import com.savale.listener.AlbumListener;
import com.savale.model.Album;
import com.savale.model.Artist;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class AlbumFragment extends Fragment implements AlbumListener{
	private PlayerController controller;
	private ListView albumList;
	private BaseActivity base;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.album_fragment, container, false);
		albumList = (ListView) view.findViewById(R.id.album_list);
		base = ((BaseActivity) getActivity());
		controller = base.getController();
		
		Search search = new Search(Consts.ALBUM);
		search.getAlbums(this, 
							controller.getUserId(), 
							controller.getPlaylistId(),
							controller.getArtistId());
		
		
		/*String userId = controller.getUserId(); 
		String artistId = controller.getArtistId(); 
		
		Search search = new Search();
		final List<Album> albums = search.getAlbums(userId, artistId);
		albumList.setAdapter(new AlbumAdapter(getActivity(), albums));
		albumList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				
				Log.i("ALBUM","SELECT ALBUM");
				controller.setAlbumId(albums.get(position).getId());
				controller.setSongs();
				base.selectTab(2);
			}
		
		});*/
		return view;
	}

	@Override
	public void onAlbumLoadComplete(String albumsResult) {
		try {
			JSONArray json = new JSONArray(albumsResult);
			final ArrayList<Album> albums = new ArrayList<Album>();
			for(int i = 0; i < json.length(); i++){
				try{
					Album a = new Album(json.getJSONObject(i).getString("id"), 
											json.getJSONObject(i).getString("name"));
					albums.add(a);
				}catch(JSONException e){
					e.printStackTrace();
				}
			}
			
			albumList.setAdapter(new AlbumAdapter(base, albums));
			albumList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {

					controller.setArtistId(albums.get(position).getId());
				}
				
			});
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
