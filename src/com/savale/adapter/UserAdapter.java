package com.savale.adapter;

import java.util.ArrayList;
import java.util.List;


import com.savale.model.User;
import com.savale.moveme.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

@SuppressLint("ViewHolder")
public class UserAdapter extends ArrayAdapter<User> {
	private final Context context;
	private final List<User> users;
	
	public UserAdapter(Context context, ArrayList<User> users){
		super(context, R.layout.user_row, users);
		this.context = context;
		this.users = users;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View userRow = inflater.inflate(R.layout.user_row, parent, false);
		TextView textView = (TextView) userRow.findViewById(R.id.user_name);
		
		textView.setText(users.get(position).getName());
		
		return userRow;
	}
}
