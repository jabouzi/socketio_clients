package info.devexchanges.otto;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class InternetBroadcastReceiver extends BroadcastReceiver {

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onReceive(Context context, Intent intent) {
        String status;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork == null) {
            status =  "DISCONNECTED";
        } else if (activeNetwork.isConnected()) {
            status = "CONNECTED";
        } else if (activeNetwork.isConnectedOrConnecting()) {
            status = "CONNECTING";
        } else status = "";

        Log.d("Broadcast", "status: " + status);

        // Get current time
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy:HH:mm:ss");
        String formattedDate = df.format(c.getTime());

        String eventData = "@" + formattedDate + ": device network state: " + status;

        // Post the event with this line
        MyApplication.getInstance().post(new TransferData(eventData));
    }
}
