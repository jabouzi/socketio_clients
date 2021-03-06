package com.skanderjabouzi.socketio;

import android.util.Log;
import java.util.logging.Logger;
import java.util.logging.Level;
import org.json.JSONObject;
import org.json.JSONException;

import de.greenrobot.event.EventBus;
import io.socket.SocketIO;
import io.socket.IOCallback;
import io.socket.IOAcknowledge;
import io.socket.SocketIOException;
import android.content.Intent;
import android.content.Context;

public class WebSocketIo implements IOCallback{

    private SocketIO socket;
    private String value;
    private String lastState;
    private Context context;

    private final EventBus eventBus = EventBus.getDefault();

    public static final String PA_INTENT = "com.skanderjabouzi.socketio.PA_INTENT";

    public void connect(String addr, Context _context, String _lastState) {

        lastState = _lastState;
        context = _context;
        Log.i("### ADDR : ", addr);

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

    public void emit(String action, String value) {
        socket.emit(action, value);
    }

    private void set_pa(String pa_state) {
        eventBus.post(pa_state);
        Log.i("EVENTBUS1", pa_state);
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
                    if (value.toLowerCase().equals("on") || value.toLowerCase().equals("enable")) {
                        set_pa("on");

                    } else {
                        set_pa("off");
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
