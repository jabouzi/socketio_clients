package com.skanderjabouzi.socketio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import de.greenrobot.event.EventBus;


public class SocketActivity extends AppCompatActivity{

    private TextView mMessagesView;
    SocketTask socketTask;
    WebSocketIo socketIo;
    public static Activity sa;

    private final EventBus eventBus = EventBus.getDefault();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sa = this;
        setContentView(R.layout.socket_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mMessagesView = (TextView) findViewById(R.id.text);
        mMessagesView.setText("TEXT TEXT");
        eventBus.register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("VIDEO", "onResume");

//        socketTask = (SocketTask) getLastNonConfigurationInstance();
//        if (socketTask == null)
//        {
//            socketTask = new SocketTask("http://10.5.4.151:6543/pa", getApplicationContext(), "on");
//            socketTask.execute();
//        }
        socketIo = new WebSocketIo();
        socketIo.connect("http://192.168.1.14:6543/pa", getApplicationContext(), "off");

    }

    public void onEvent(String event) {
        Log.i("EVENTBUS3", event);
        if (event.equals("off")) {
            startActivity(new Intent(this, VideoActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        socketTask.m_activity = null;
//        socketTask.cancel(true);
//        socketTask.disconnect();
        socketIo.disconnect();
        eventBus.unregister(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        socketTask.m_activity = null;
//        socketTask.cancel(true);
//        socketTask.disconnect();
        socketIo.disconnect();
        eventBus.unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        socketTask.m_activity = null;
//        socketTask.cancel(true);
//        socketTask.disconnect();
        socketIo.disconnect();
        eventBus.unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
