package eetu.kallio.project.tiko.tamk.fi.keeptrack.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import eetu.kallio.project.tiko.tamk.fi.keeptrack.resources.WorkEvent;

/**
 * Service to track an ongoing event.
 */
public class EventService extends Service implements Runnable {

    private Thread thread;
    private boolean isRunning;
    private final static String TAG = "EventService";
    private WorkEvent event;
    private LocalBroadcastManager manager;

    /**
     * Default constructor.
     */
    public EventService () {
    }

    /**
     * Called to determine the binder for the service.
     *
     * @param intent The Intent that was used to bind to this service.
     * @return Returns null since this service is not bound.
     */
    @Override
    public IBinder onBind (Intent intent) {
        Log.d(TAG, "onBind()");

        return null;
    }

    /**
     * Called when the service is destroyed.
     */
    @Override
    public void onDestroy () {
        Log.d(TAG, "onDestroy()");
        sendEvent();
        isRunning = false;
    }

    /**
     * Called when the service is started.
     *
     * @param intent The Intent that was used to start this service.
     * @param flags Additional data about this start request.
     * @param startId A unique integer representing this specific request to start.
     * @return The return value indicates what semantics the system should use for the service's current started state.
     */
    @Override
    public int onStartCommand (Intent intent, int flags, int startId) {
        Log.d(TAG, "onStart()");
        if ( !isRunning ) {
            thread.start();
        }
        return START_STICKY;
    }

    /**
     * Called when the service is created.
     */
    @Override
    public void onCreate () {
        Log.d(TAG, "onCreate");
        manager = LocalBroadcastManager.getInstance(this);
        isRunning = false;
        thread = new Thread(this);
        event = new WorkEvent();
    }

    /**
     * A Runnable interface method. Actions performed in this method are run when
     * a Thread is started using 'this'.
     */
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

    /**
     * Used to broadcast the data in an event to other activities while the event is running.
     */
    public void sendEvent() {
        Intent intent;
        intent = new Intent();
        intent.setAction("fi.tamk.tiko.project.kallio.eetu.event");
        event.endEvent();
        intent.putExtra("event", event);
        manager.sendBroadcast(intent);
    }
}
