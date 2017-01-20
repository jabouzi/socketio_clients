package com.skanderjabouzi.socketio;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


class PaReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String pa_state = intent.getStringExtra("PASTATE");
        Toast.makeText(context, "It's PA " + pa_state + "### ", Toast.LENGTH_LONG).show();
        Log.i("PAReceiver", pa_state);
        //SalatApplication.write2sd("SalatReceiver", salatName);
        if (pa_state.equals("on")) {
            //Intent intent = new Intent();
            intent.setClass(context.getApplicationContext(), SocketActivity.class);
            MainActivity.ma.finish();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        } else {
            //Intent intent = new Intent();
            intent.setClass(context.getApplicationContext(), MainActivity.class);
            SocketActivity.sa.finish();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}