package com.skanderjabouzi.socketio;

import android.os.AsyncTask;

public class SocketTask extends AsyncTask<Void, Void, Void> {

    String dstAddress;
    WebSocketIo socketIo;

    MainActivity m_activity = null;
    boolean m_isFinished  = false;

    SocketTask(String addr){
        dstAddress = addr;
    }

    public void disconnect()
    {
        socketIo.disconnect();
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        socketIo = new WebSocketIo();
        socketIo.connect(dstAddress);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        //textResponse.setText(response);
        super.onPostExecute(result);
        //m_isFinished = true;
    }

    @Override
    protected void onCancelled()
    {
        super.onCancelled();
        m_isFinished = true;
    }

}