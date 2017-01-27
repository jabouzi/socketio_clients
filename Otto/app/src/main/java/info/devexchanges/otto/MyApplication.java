package info.devexchanges.otto;

import android.app.Application;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

public class MyApplication extends Application {

    private static Bus eventBus;

    public static Bus getInstance() {
       return eventBus;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        eventBus = new Bus(ThreadEnforcer.ANY);
    }
}
