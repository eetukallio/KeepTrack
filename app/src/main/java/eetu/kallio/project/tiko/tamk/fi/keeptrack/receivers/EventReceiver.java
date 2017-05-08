package eetu.kallio.project.tiko.tamk.fi.keeptrack.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import eetu.kallio.project.tiko.tamk.fi.keeptrack.resources.WorkEvent;

/**
 * A custom BroadcastReceiver for the listView.
 *
 * @author Eetu Kallio
 * @version 4.0
 * @since 2.0
 */
public class EventReceiver extends BroadcastReceiver {

    private WorkEvent event;

    /**
     * Called when a broadcast is received.
     *
     * @param context The Context in which the receiver is running.
     * @param intent The Intent being received.
     */
    @Override
    public void onReceive (Context context, Intent intent) {

        Log.d("Receiver", "Received");
        event = (WorkEvent) intent.getSerializableExtra("event");
        Log.d("Receiver", "" + event.getDurationSeconds());
    }

    /**
     * Getter for the received event.
     *
     * @return Received event.
     */
    public WorkEvent getEvent () {
        return event;
    }
}
