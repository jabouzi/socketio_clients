package com.skanderjabouzi.socketio;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.widget.MediaController;
import android.view.MotionEvent;
import android.widget.VideoView;
import android.util.Log;

/*
 * This code is to accompany the Tutsplus tutorial:
 * Streaming Video in Android Apps
 * 
 * Sue Smith 
 * March 2014
 */

public class MainActivity extends Activity {

    VideoView vidView;
    int currentPosition;
    SocketTask socketTask;
    public static Activity ma;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        ma = this;
        Log.d("VIDEO", "onCreate");
		setContentView(R.layout.activity_main);

		vidView = (VideoView)findViewById(R.id.myVideo);
		MediaController vidControl = new MediaController(this);
		vidControl.setAnchorView(vidView);
		vidView.setMediaController(vidControl);

		String vidAddress =
				"https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";
		Uri vidUri = Uri.parse(vidAddress);
		vidView.setVideoURI(vidUri);
		vidView.start();
	}

	public boolean onTouchEvent (MotionEvent ev)
	{
        currentPosition = vidView.getCurrentPosition();
		Log.i("VIDEO", "POSITION : " + String.valueOf(currentPosition));
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
        Log.d("VIDEO", "onResume");

        socketTask = (SocketTask) getLastNonConfigurationInstance();
        if (socketTask == null)
        {
            socketTask = new SocketTask("http://10.5.4.151:6543/pa", getApplicationContext(), "off");
            socketTask.execute();
        }

        vidView.start();
        //vidView.seekTo(38484);
        Log.i("VIDEO", "POSITION : " + String.valueOf(currentPosition));
	}

	@Override
	protected void onPause() {
		super.onPause();
        socketTask.m_activity = null;
        socketTask.cancel(true);
        socketTask.disconnect();
		Log.d("VIDEO", "onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
        socketTask.m_activity = null;
        socketTask.cancel(true);
        socketTask.disconnect();
        Log.d("ASYNCTASK ", socketTask.isCancelled() ? "CANCELLED" : "NOT CANCELLED");
		if (vidView.isPlaying()) Log.i("VIDEO", "IS PLAYING 1");
		else Log.i("VIDEO", "IS NOT PLAYING 1");
        currentPosition = vidView.getCurrentPosition();
        Log.i("VIDEO", "POSITION 1 : " + String.valueOf(currentPosition));
		Log.d("VIDEO", "onStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
        socketTask.m_activity = null;
        socketTask.cancel(true);
        socketTask.disconnect();
        Log.d("ASYNCTASK ", socketTask.isCancelled() ? "CANCELLED" : "NOT CANCELLED");
		if (vidView.isPlaying()) Log.i("VIDEO", "IS PLAYING 2");
		else Log.i("VIDEO", "IS NOT PLAYING 2");
        currentPosition = vidView.getCurrentPosition();
        Log.i("VIDEO", "POSITION 2 : " + String.valueOf(currentPosition));
		Log.d("VIDEO", "onDestroy");
	}

}
