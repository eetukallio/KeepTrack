package eetu.kallio.project.tiko.tamk.fi.keeptrack;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class EventService extends Service implements Runnable {

    private Thread thread;
    private boolean isRunning;
    private final static String TAG = "EventService";
    private WorkEvent event;
    LocalBroadcastManager manager;

    public EventService () {
    }

    @Override
    public IBinder onBind (Intent intent) {
        Log.d(TAG, "onBind()");

        return null;
    }

    @Override
    public void onDestroy () {
        Log.d(TAG, "onDestroy()");
        sendEvent();
        isRunning = false;
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId) {
        Log.d(TAG, "onStart()");
        if ( !isRunning ) {
            thread.start();
        }
        return START_STICKY;
    }

    @Override
    public void onCreate () {
        Log.d(TAG, "onCreate");
        manager = LocalBroadcastManager.getInstance(this);
        isRunning = false;
        thread = new Thread(this);
        event = new WorkEvent();
    }

    @Override
    public void run () {
        isRunning = true;
        System.out.println("Run started");
        while ( isRunning ) {
            try {
                event.endEvent();
//                Log.d(TAG, ""+event.getDurationSeconds());
                sendEvent();
                Thread.sleep(1000);
            } catch ( InterruptedException e ) {
                e.printStackTrace();
            }
        }
    }

    public void sendEvent() {
        Intent intent;
        intent = new Intent();
        intent.setAction("fi.tamk.tiko.project.kallio.eetu.event");
        event.endEvent();
        intent.putExtra("event", event);
        manager.sendBroadcast(intent);
    }
}
