package eetu.kallio.project.tiko.tamk.fi.keeptrack.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import eetu.kallio.project.tiko.tamk.fi.keeptrack.resources.WorkEvent;

public class EventReceiver extends BroadcastReceiver {

    WorkEvent event;

    @Override
    public void onReceive (Context context, Intent intent) {

        Log.d("Receiver", "Received");
        event = (WorkEvent) intent.getSerializableExtra("event");
        Log.d("Receiver", "" + event.getDurationSeconds());
    }

    public WorkEvent getEvent () {
        return event;
    }
}
