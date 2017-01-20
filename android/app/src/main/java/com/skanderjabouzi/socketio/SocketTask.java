package com.skanderjabouzi.socketio;

import android.os.AsyncTask;
import android.content.Context;

public class SocketTask extends AsyncTask<Void, Void, Void> {

    String dstAddress;
    WebSocketIo socketIo;
    Context context;
    String lastState;

    MainActivity m_activity = null;
    boolean m_isFinished  = false;

    SocketTask(String addr, Context _context, String _lastState){
        dstAddress = addr;
        context = _context;
        lastState = _lastState;
    }

    public void disconnect()
    {
        socketIo.disconnect();
        m_isFinished = true;
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        socketIo = new WebSocketIo();
        socketIo.connect(dstAddress, context, lastState);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        //textResponse.setText(response);
        super.onPostExecute(result);
    }

    @Override
    protected void onCancelled()
    {
        super.onCancelled();
        m_isFinished = true;
    }

}