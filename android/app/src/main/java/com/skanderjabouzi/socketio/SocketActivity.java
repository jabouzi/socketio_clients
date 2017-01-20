package com.skanderjabouzi.socketio;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.content.IntentFilter;


public class SocketActivity extends AppCompatActivity{

    private TextView mMessagesView;
    SocketTask socketTask;
    public static Activity sa;
    PaReceiver receiver;
    IntentFilter filter;

    static final String SEND_PA_NOTIFICATIONS = "com.skanderjabouzi.socketio.SEND_PA_NOTIFICATIONS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sa = this;
        setContentView(R.layout.socket_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mMessagesView = (TextView) findViewById(R.id.text);
        mMessagesView.setText("TEXT TEXT");

        receiver = new PaReceiver();
        filter = new IntentFilter( WebSocketIo.PA_INTENT );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("VIDEO", "onResume");

//        registerReceiver(receiver, filter, SEND_PA_NOTIFICATIONS, null);
        socketTask = (SocketTask) getLastNonConfigurationInstance();
        if (socketTask == null)
        {
            socketTask = new SocketTask("http://10.5.4.151:6543/pa", getApplicationContext(), "on");
//            socketTask.m_activity = this;
            socketTask.execute();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        socketTask.m_activity = null;
        socketTask.cancel(true);
        socketTask.disconnect();
//        if (receiver != null) {
//            unregisterReceiver(receiver);
//            receiver = null;
//        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        socketTask.m_activity = null;
        socketTask.cancel(true);
        socketTask.disconnect();
//        if (receiver != null) {
//            unregisterReceiver(receiver);
//            receiver = null;
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socketTask.m_activity = null;
        socketTask.cancel(true);
        socketTask.disconnect();
//        if (receiver != null) {
//            unregisterReceiver(receiver);
//            receiver = null;
//        }
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
