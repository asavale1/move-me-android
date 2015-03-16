package com.savale.adapter;

import java.util.List;

import com.savale.model.Artist;
import com.savale.moveme.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

@SuppressLint("ViewHolder")
public class ArtistAdapter extends ArrayAdapter<Artist> {
	private Context context;
	private List<Artist> artists;
	
	public ArtistAdapter(Context context, List<Artist> artists) {
		super(context, R.layout.artist_row, artists);
		this.context = context;
		this.artists = artists;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View artistRow = inflater.inflate(R.layout.artist_row, parent, false);
		TextView textView = (TextView) artistRow.findViewById(R.id.artist_name);
		
		textView.setText(artists.get(position).getName());
		
		return artistRow;
	}
	
	
	
}
