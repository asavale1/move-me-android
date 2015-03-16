package com.savale.adapter;

import java.util.List;

import com.savale.model.Song;
import com.savale.moveme.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SongAdapter extends ArrayAdapter<Song> {
	private Context context;
	private List<Song> songs;
	
	public SongAdapter(Context context, List<Song> songs) {
		super(context, R.layout.song_row, songs);
		this.context = context;
		this.songs = songs;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View userRow = inflater.inflate(R.layout.song_row, parent, false);
		TextView textView = (TextView) userRow.findViewById(R.id.song_title);
		
		textView.setText(songs.get(position).getName());
		
		return userRow;
	}

}
