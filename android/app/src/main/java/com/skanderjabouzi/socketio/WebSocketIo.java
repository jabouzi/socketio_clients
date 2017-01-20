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
import android.content.Intent;
import android.content.Context;

/**
 * Created by dev on 1/19/17.
 */

public class WebSocketIo implements IOCallback{

    private SocketIO socket;
    private String value;
    private String lastState;
    private Context context;

    public static final String PA_INTENT = "com.skanderjabouzi.socketio.PA_INTENT";

    public void connect(String addr, Context _context, String _lastState) {

        lastState = _lastState;
        context = _context;
        Log.i("### LASTE STATE : ", lastState);

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

    private void set_pa(String pa_state, String pa_intent) {
        Intent intent;
        intent = new Intent();
        intent.setAction(pa_intent);
        intent.putExtra("PASTATE", pa_state);
        context.sendBroadcast(intent);
        Log.i("#### BROADCAST ", pa_state + " -> " + pa_intent);
    }

    @Override
    public void onMessage(JSONObject json, IOAcknowledge ack) {
        try {
            System.out.println("Server said:" + json.toString(2));
            Log.i("SOCKETIO", json.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(String data, IOAcknowledge ack) {
        System.out.println("Server said: " + data);
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
            Log.i("JSON : ", data.toString());
            Log.i("EVENT : ", event);
            Log.i("VALUE : ", value);
            Log.i("LASTE STATE : ", lastState);

            if (event.equals("pa_state"))
            {
                if (!lastState.equals(value))
                {
                    if (value.equals("on")) {
                        set_pa("on", PA_INTENT);

                    } else {
                        set_pa("off", PA_INTENT);
                    }
                    lastState = value;
                }
            }
//            System.out.println("Server triggered event '" + data.getString("value") + "'");
        } catch (JSONException e) {
            return;
        }

    }
}
