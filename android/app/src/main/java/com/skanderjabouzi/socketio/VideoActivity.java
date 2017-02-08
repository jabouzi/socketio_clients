package com.skanderjabouzi.socketio;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.widget.MediaController;
import android.view.MotionEvent;
import android.widget.VideoView;
import android.util.Log;

import de.greenrobot.event.EventBus;

/*
 * This code is to accompany the Tutsplus tutorial:
 * Streaming Video in Android Apps
 * 
 * Sue Smith 
 * March 2014
 */

public class VideoActivity extends Activity {

    VideoView vidView;
    int currentPosition;
    SocketTask socketTask;
	WebSocketIo socketIo;
    public static Activity ma;

	private EventBus eventBus = EventBus.getDefault();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        eventBus.register(this);
        ma = this;
        Log.d("VIDEO", "onCreate");
		setContentView(R.layout.activity_main);

		vidView = (VideoView)findViewById(R.id.myVideo);
		MediaController vidControl = new MediaController(this);
		vidControl.setAnchorView(vidView);
		vidView.setMediaController(vidControl);

//		String vidAddress = "https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";
		String vidAddress = "https://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
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

    public void onEvent(String event) {
        Log.i("EVENTBUS2", event);
        if (event.equals("on")) {
            startActivity(new Intent(this, SocketActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
            finish();
        }
    }

	@Override
	protected void onResume() {
		super.onResume();
        Log.d("VIDEO", "onResume");

        /*socketTask = (SocketTask) getLastNonConfigurationInstance();
        if (socketTask == null)
        {
            socketTask = new SocketTask("http://10.5.4.151:6543/pa", getApplicationContext(), "off");
            socketTask.execute();
        }*/
		socketIo = new WebSocketIo();
		socketIo.connect("http://192.168.1.14:6543/pa", getApplicationContext(), "off");

        vidView.start();
        //vidView.seekTo(38484);
        Log.i("VIDEO", "POSITION : " + String.valueOf(currentPosition));
	}

	@Override
	protected void onPause() {
		super.onPause();
//        socketTask.m_activity = null;
//        socketTask.cancel(true);
//        socketTask.disconnect();
        socketIo.disconnect();
        eventBus.unregister(this);
		Log.d("VIDEO", "onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
//        socketTask.m_activity = null;
//        socketTask.cancel(true);
//        socketTask.disconnect();
        socketIo.disconnect();
        eventBus.unregister(this);
//        Log.d("ASYNCTASK ", socketTask.isCancelled() ? "CANCELLED" : "NOT CANCELLED");
		if (vidView.isPlaying()) Log.i("VIDEO", "IS PLAYING 1");
		else Log.i("VIDEO", "IS NOT PLAYING 1");
        currentPosition = vidView.getCurrentPosition();
        Log.i("VIDEO", "POSITION 1 : " + String.valueOf(currentPosition));
		Log.d("VIDEO", "onStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
//        socketTask.m_activity = null;
//        socketTask.cancel(true);
//        socketTask.disconnect();
        socketIo.disconnect();
        eventBus.unregister(this);
//        Log.d("ASYNCTASK ", socketTask.isCancelled() ? "CANCELLED" : "NOT CANCELLED");
		if (vidView.isPlaying()) Log.i("VIDEO", "IS PLAYING 2");
		else Log.i("VIDEO", "IS NOT PLAYING 2");
        currentPosition = vidView.getCurrentPosition();
        Log.i("VIDEO", "POSITION 2 : " + String.valueOf(currentPosition));
		Log.d("VIDEO", "onDestroy");
	}

}
