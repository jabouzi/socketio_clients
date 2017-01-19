package com.skanderjabouzi.socketio;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import java.util.logging.Logger;
import java.util.logging.Level;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URI;
import org.json.JSONObject;
import org.json.JSONException;

import io.socket.SocketIO;
import io.socket.IOCallback;
import io.socket.IOAcknowledge;
import io.socket.SocketIOException;

public class SocketActivity extends AppCompatActivity implements IOCallback{

    private TextView mMessagesView;
    private SocketIO socket;
    private String value = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.socket_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mMessagesView = (TextView) findViewById(R.id.text);
        mMessagesView.setText("TEXT TEXT");

        try {
            socket = new SocketIO();
            socket.connect("http://10.5.4.151:6543/pa", this);
            Logger sioLogger = java.util.logging.Logger.getLogger("io.socket");
            sioLogger.setLevel(Level.OFF);

           // Emits an event to the server.
            socket.emit("set_pa", "test2");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onMessage(JSONObject json, IOAcknowledge ack) {
        try {
            System.out.println("Server said:" + json.toString(2));
            Log.i("SOCKETIO", json.toString());
//            mMessagesView.setText(json.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(String data, IOAcknowledge ack) {
        System.out.println("Server said: " + data);
//        mMessagesView.setText(data);
    }

    @Override
    public void onError(SocketIOException socketIOException) {
        System.out.println("an Error occured");
        socketIOException.printStackTrace();
    }

    @Override
    public void onDisconnect() {
        System.out.println("Connection terminated.");
    }

    @Override
    public void onConnect() {
        System.out.println("Connection established");
    }

    @Override
    public void on(String event, IOAcknowledge ack, Object... args) {
        JSONObject data = (JSONObject) args[0];
        try {
            value = data.getString("value");
            System.out.println("Server triggered event '" + event + "' : " + value);
//            mMessagesView = (TextView) findViewById(R.id.text);
//            mMessagesView.setText("XXX");
            Log.i("JSON : ", data.toString());
            Log.i("EVENT : ", event);
            Log.i("VALUE : ", value);
//            System.out.println("Server triggered event '" + data.getString("value") + "'");
        } catch (JSONException e) {
            return;
        }

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
