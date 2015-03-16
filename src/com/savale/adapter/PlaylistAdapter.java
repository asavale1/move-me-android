package com.savale.adapter;

import java.util.ArrayList;
import java.util.List;

import com.savale.model.Playlist;
import com.savale.moveme.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PlaylistAdapter extends ArrayAdapter<Playlist> {
	private Context context;
	private ArrayList<Playlist> playlists;
	
	public PlaylistAdapter(Context context, ArrayList<Playlist> playlists) {
		super(context, R.layout.playlist_row, playlists);
		this.context = context;
		this.playlists = playlists;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View playlistRow = inflater.inflate(R.layout.playlist_row, parent, false);
		TextView textView = (TextView) playlistRow.findViewById(R.id.playlist_name);
		
		textView.setText(playlists.get(position).getName());
		
		return playlistRow;
	}
}
