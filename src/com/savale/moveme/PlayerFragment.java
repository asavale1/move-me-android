package com.savale.moveme;

import java.util.concurrent.TimeUnit;

import com.savale.helpers.PlayerController;
import android.app.Fragment;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class PlayerFragment extends Fragment {
	private Context context; 
	private double curTime = 0;
	private Handler handler = new Handler();
	private TextView startTimeField;
	private static TextView songTitle, endTimeField;
	private static SeekBar seekBar;
	private ImageButton rewindButton, forwardButton;
	private static ImageButton playButton, pauseButton;
	private static PlayerController controller;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.player_fragment, container, false);
		context = getActivity();
		controller = ((BaseActivity) getActivity()).getController();
		
		if(controller.getSongList().size() > 0){
			handler.postDelayed(UpdateSongTime, 100);

			if(controller.getPlaying()){
				setupView(view);
				
			}else{
				if(controller.setupPlayer()){
					setupView(view);
				}
			}
		}
		
		return view;
	}
	
	public View setupView(View view){
		songTitle = (TextView) view.findViewById(R.id.player_song_title);
		songTitle.setText(controller.getSongTitle());
		
		seekBar = (SeekBar) view.findViewById(R.id.player_seekbar);
		seekBar.setMax((int) controller.getSongDuration());
		seekBar.setOnSeekBarChangeListener(seek);
		
		startTimeField = (TextView) view.findViewById(R.id.player_start_time);
		startTimeField.setText(String.format("%d min, %d sec", 
				TimeUnit.MILLISECONDS.toMinutes((long) curTime),
				TimeUnit.MILLISECONDS.toSeconds((long) curTime) - 
				TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) curTime))));
		
		endTimeField = (TextView) view.findViewById(R.id.player_end_time);
		endTimeField.setText(String.format("%d min, %d sec", 
				TimeUnit.MILLISECONDS.toMinutes((long) controller.getSongDuration()),
				TimeUnit.MILLISECONDS.toSeconds((long) controller.getSongDuration()) - 
				TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) controller.getSongDuration()))));
				
		rewindButton = (ImageButton) view.findViewById(R.id.player_rewind_button);
		rewindButton.setOnClickListener(rewindListener);
		
		playButton = (ImageButton) view.findViewById(R.id.player_play_button);
		playButton.setOnClickListener(playListener);
		
		pauseButton = (ImageButton) view.findViewById(R.id.player_pause_button);
		pauseButton.setOnClickListener(pauseListener);
		
		forwardButton = (ImageButton) view.findViewById(R.id.player_forward_button);
		forwardButton.setOnClickListener(forwardListener);
		
		return view;
	}
	
	SeekBar.OnSeekBarChangeListener seek = new SeekBar.OnSeekBarChangeListener(){

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			controller.seekTo(seekBar.getProgress());
		}
		
	};

	//
	// Listener for pause button
	//
	View.OnClickListener pauseListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if(controller.getPlaying() == true){
				Toast.makeText(context, "Pause", Toast.LENGTH_SHORT).show();
				controller.pause();
			}
		}
	};
	
	//
	// Listener for play button
	//
	View.OnClickListener playListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if(controller.getPlaying() == false){
				Toast.makeText(context, "Play", Toast.LENGTH_SHORT).show();
				controller.play();
			}

			curTime = controller.getCurTime();
			startTimeField.setText(String.format("%d min, %d sec", 
					TimeUnit.MILLISECONDS.toMinutes((long) curTime),
					TimeUnit.MILLISECONDS.toSeconds((long) curTime) - 
					TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) curTime))));
			seekBar.setProgress((int) curTime);
		}
	};
	
	//
	// Listener for rewind button
	//
	View.OnClickListener rewindListener = new View.OnClickListener() {		
		@Override
		public void onClick(View v) {
			Toast.makeText(context, "Rewind", Toast.LENGTH_SHORT).show();
			controller.rewind();
		}
	};
	
	//
	// Listener for forward button
	//
	View.OnClickListener forwardListener = new View.OnClickListener() {		
		@Override
		public void onClick(View v) {
			Toast.makeText(context, "Forward", Toast.LENGTH_SHORT).show();
			controller.forward();
		}
	};
	
	private Runnable UpdateSongTime = new Runnable(){

		@Override
		public void run() {
			curTime = controller.getCurTime();
					
	        startTimeField.setText(String.format("%d min, %d sec", 
	 					TimeUnit.MILLISECONDS.toMinutes((long) curTime),
	 					TimeUnit.MILLISECONDS.toSeconds((long) curTime) - 
	 					TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
	 							toMinutes((long) curTime))));
	        seekBar.setProgress((int)curTime);
	 	         
	        handler.postDelayed(this, 100);
		}
	};
		
	/*
	 * Called when song finishes playing
	 */
	public static MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
		
		@Override
		public void onCompletion(MediaPlayer mp) {
			int songPos = controller.getSongPos();
			controller.setPlaying(false);
			if(controller.getSongList().size() > (songPos + 1)){
				controller.selectSong(songPos + 1);
				
				controller.setupPlayer();
				songTitle.setText(controller.getSongTitle());
				endTimeField.setText(String.format("%d min, %d sec", 
						TimeUnit.MILLISECONDS.toMinutes((long) controller.getSongDuration()),
						TimeUnit.MILLISECONDS.toSeconds((long) controller.getSongDuration()) - 
						TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) controller.getSongDuration()))));
				seekBar.setMax((int) controller.getSongDuration());
				controller.play();
				
			}
			
		}
	};
	
}

