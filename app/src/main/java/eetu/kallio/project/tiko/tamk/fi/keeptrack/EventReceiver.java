package eetu.kallio.project.tiko.tamk.fi.keeptrack;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

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
