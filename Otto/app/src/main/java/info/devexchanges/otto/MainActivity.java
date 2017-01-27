package info.devexchanges.otto;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver broadcastReceiver;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text_view);

        MyApplication.getInstance().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        broadcastReceiver = new InternetBroadcastReceiver();
        registerReceiver(broadcastReceiver, new IntentFilter());
    }

    @SuppressLint("SetTextI18n")
    @Subscribe
    public void getMessage(TransferData data) {
        Log.i("Activity", data.getMessage());
        textView.setText(textView.getText().toString() + "\n" + data.getMessage());
    }
}
