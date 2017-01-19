package com.skanderjabouzi.socketio;

import android.util.Log;
import java.util.logging.Logger;
import java.util.logging.Level;
import org.json.JSONObject;
import org.json.JSONException;
import io.socket.SocketIO;
import io.socket.IOCallback;
import io.socket.IOAcknowledge;
import io.socket.SocketIOException;

/**
 * Created by dev on 1/19/17.
 */

public class WebSocketIo implements IOCallback{

    private SocketIO socket;
    private String value;

    public void connect(String addr) {
        try {
            socket = new SocketIO();
            socket.connect(addr, this);
            Logger sioLogger = java.util.logging.Logger.getLogger("io.socket");
            sioLogger.setLevel(Level.OFF);

            // Emits an event to the server.
//            socket.emit("set_pa", "test2");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect()
    {
        socket.disconnect();
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
}
