package com.savale.adapter;

import java.util.List;

import com.savale.model.Album;
import com.savale.moveme.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


@SuppressLint("ViewHolder")
public class AlbumAdapter extends ArrayAdapter<Album> {
	private Context context;
	private List<Album> albums;
	
	public AlbumAdapter(Context context, List<Album> albums) {
		super(context, R.layout.song_row, albums);
		this.context = context;
		this.albums = albums;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View userRow = inflater.inflate(R.layout.album_row, parent, false);
		TextView textView = (TextView) userRow.findViewById(R.id.album_title);
		
		textView.setText(albums.get(position).getTitle());
		
		return userRow;
	}
}
