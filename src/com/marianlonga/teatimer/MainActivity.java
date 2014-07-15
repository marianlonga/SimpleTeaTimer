package com.marianlonga.teatimer;

import android.support.v7.app.ActionBarActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	TextView countdownTextView;
	Button time3minButton;
	Button time5minButton;
	Button time8minButton;
	Button time10sButton;
	Button stopTimerButton;
	CountDownTimer timer = null;
	ProgressBar timerProgressBar;
	int currentMaximumMinutes = 0; // selected time in minutes to count from
	int currentMaximumSeconds = 0; // selected time in seconds to count from
	MediaPlayer bellSound;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		countdownTextView = (TextView) findViewById(R.id.countdownTextView);
		time3minButton = (Button) findViewById(R.id.time3minButton);
		time5minButton = (Button) findViewById(R.id.time5minButton);
		time8minButton = (Button) findViewById(R.id.time8minButton);
		time10sButton = (Button) findViewById(R.id.time10sButton);
		stopTimerButton = (Button) findViewById(R.id.stopTimerButton);
		timerProgressBar = (ProgressBar) findViewById(R.id.timerProgressBar);
		bellSound = MediaPlayer.create(this, R.raw.bell);
		
		time3minButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { setNewTimer(3, 0); }
		});
		
		time5minButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { setNewTimer(5, 0); }
		});
		
		time8minButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { setNewTimer(8, 0); }
		});
		
		time10sButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { setNewTimer(0, 10); }
		});
		
		stopTimerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { 
				timer.cancel();
			}
		});
	}

	private void setNewTimer(int start_minutes, int start_seconds) {
		currentMaximumMinutes = start_minutes;
		currentMaximumSeconds = start_seconds;
		if(timer != null) timer.cancel();
		timer = new CountDownTimer(start_minutes * 60 * 1000 + start_seconds * 1000, 1) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				int minutes = (int) (millisUntilFinished / (1000 * 60));
				int seconds = (int) (millisUntilFinished / 1000 - minutes * 60);
				int milliseconds = (int) (millisUntilFinished - minutes * 60 * 1000 - seconds * 1000);
				countdownTextView.setText(
					String.format("%02d", minutes) + ":" + 
					String.format("%02d", seconds) + "." + 
					String.format("%01d", milliseconds/100)
				);
				int proMile = (int)((1.0 - (((double)millisUntilFinished)) / ((double)currentMaximumMinutes * 60.0 * 1000.0 + (double)currentMaximumSeconds * 1000.0)) * 1000.0); 
				timerProgressBar.setProgress(proMile);
			}
			
			@Override
			public void onFinish() {
				countdownTextView.setText("Time\'s up!");
				bellSound.start();
			}
		};
		timer.start();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
