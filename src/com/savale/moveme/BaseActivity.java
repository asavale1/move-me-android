package com.savale.moveme;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.ActionBar;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.savale.adapter.UserAdapter;
import com.savale.helpers.PlayerController;
import com.savale.helpers.PlayerService;
import com.savale.helpers.Search;
import com.savale.helpers.PlayerService.PlayerBinder;
import com.savale.listener.UserListener;
import com.savale.model.User;

import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class BaseActivity extends Activity implements UserListener {

    private ActionBar actionBar;
    private DrawerLayout dl;
    private ListView lv;
    
    private FragmentManager fm;
    
    private PlayerService playerService;
    private Intent playerIntent;
    private boolean playerBound = false;
    
    private PlayerController controller;
    
    
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);
		
		Search search = new Search(Consts.USER);
		search.getUsers(this);
        
        controller = new PlayerController();
        fm = getFragmentManager();
        
        setupActionBar();
        selectTab(Consts.PLAYLIST);
	}
	
	public void setupActionBar(){
		dl = (DrawerLayout) findViewById(R.id.drawer_layout);
		lv = (ListView) findViewById(R.id.list_slidermenu);
		
		actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1C053A")));
		
		LayoutInflater li = LayoutInflater.from(this);
		View abView = li.inflate(R.layout.action_bar, null);
		
		Button songs = (Button) abView.findViewById(R.id.songs_tab);
		songs.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				selectTab(Consts.SONG);
			}
			
		});
		
		Button playlists = (Button)abView.findViewById(R.id.playlist_tab);
		playlists.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				selectTab(Consts.PLAYLIST);
			}
		});
		
		Button player = (Button) abView.findViewById(R.id.player_tab);
		player.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				selectTab(Consts.PLAYER);
			}
		});
		
		Button menu = (Button) abView.findViewById(R.id.menu_drawer);
		menu.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(dl.isDrawerOpen(GravityCompat.START)){
					dl.closeDrawer(lv);
				}else{
					dl.openDrawer(lv);
				}
				
			}
		});
        
        actionBar.setCustomView(abView);
        actionBar.setDisplayShowCustomEnabled(true);
	}

	@Override
	public void onStart(){
		super.onStart();
		if(playerIntent == null){
			playerIntent = new Intent(this, PlayerService.class);
			bindService(playerIntent, playerConnection, Context.BIND_AUTO_CREATE);
			startService(playerIntent);
		}
	}
	
	private ServiceConnection playerConnection = new ServiceConnection(){

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			PlayerBinder binder = (PlayerBinder) service;
			playerService = binder.getService();
			playerBound = true;
			
			controller.setPlayerService(playerService);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			playerBound = false;
		}
	};
	
	public PlayerController getController(){ return this.controller; }
	
	public void selectTab(int tab){
		Fragment fragment;
		switch(tab){
		case Consts.PLAYLIST:
			fragment = new PlaylistFragment();
			fm.beginTransaction().replace(R.id.fragment_container, fragment).commit();
			break;
		case Consts.ARTIST:
			fragment = new ArtistFragment();
			break;
		case Consts.ALBUM:
			break;
		case Consts.SONG:
			fragment = new SongFragment();
			fm.beginTransaction().replace(R.id.fragment_container, fragment).commit();
			break;
		case Consts.PLAYER:
			fragment = new PlayerFragment();
			fm.beginTransaction().replace(R.id.fragment_container, fragment).commit();
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
	  stopService(playerIntent);
	  playerService=null;
	  super.onDestroy();
	}

	@Override
	public void onUserLoadComplete(String userResult) {
		try{
			JSONArray json = new JSONArray(userResult);
			final ArrayList<User> users = new ArrayList<User>();
			for(int i = 0; i < json.length(); i++){
				try {
					User u = new User(json.getJSONObject(i).getString("id"), 
										json.getJSONObject(i).getString("name"));
					users.add(u);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			lv.setAdapter(new UserAdapter(this, users));
			lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,
						long id) {
					
					controller.setUserId(users.get(position).getId());
					selectTab(Consts.PLAYLIST);
				}
			});
		} catch (JSONException e) {
			e.printStackTrace();
		}	
	}
}
