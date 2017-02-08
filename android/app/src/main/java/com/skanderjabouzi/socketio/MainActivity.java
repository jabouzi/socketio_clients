package com.skanderjabouzi.socketio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private TextView mMessagesView;
    SocketTask socketTask;
    WebSocketIo socketIo = null;
    private Button _connectButton;
    private Button _paonButton;
    private Button _paoffButton;
    private EditText _url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("VIDEO", "onResume");
        _url = (EditText) findViewById(R.id.url);

        _connectButton = (Button) findViewById(R.id.connect);
        _connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (socketIo == null) {
                socketIo = new WebSocketIo();
                socketIo.connect("http://" + _url.getText().toString() + ":6543/pa", getApplicationContext(), "off");
            }
            }
        });

        _paonButton = (Button) findViewById(R.id.paon);
        _paonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (socketIo != null) {
                    socketIo.emit("set_pa", "enabled");
                }
            }
        });

        _paoffButton = (Button) findViewById(R.id.paoff);
        _paoffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (socketIo != null) {
                    socketIo.emit("set_pa", "disabled");
                }
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (socketIo != null) {
            socketIo.disconnect();
            socketIo = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (socketIo != null) {
            socketIo.disconnect();
            socketIo = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (socketIo != null) {
            socketIo.disconnect();
            socketIo = null;
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
