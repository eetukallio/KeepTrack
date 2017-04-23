package eetu.kallio.project.tiko.tamk.fi.keeptrack;


import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private boolean EVENT_ON = false;
    private CoordinatorLayout coordinatorLayout;
    private EventReceiver  receiver;
    private WorkEvent event;
    LocalBroadcastManager manager;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.myCoordinatorLayout);
    }

    @Override
    protected void onResume () {
        super.onResume();

        IntentFilter filter = new IntentFilter("fi.tamk.tiko.project.kallio.eetu.event");
        manager = LocalBroadcastManager.getInstance(this);
        receiver = new EventReceiver();
        manager.registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause () {
        super.onPause();
        manager.unregisterReceiver(receiver);
    }

    public void startEvent(View v) {

        if ( !EVENT_ON ) {

            EVENT_ON = true;
            Intent intent;
            intent = new Intent(this, EventService.class);
            startService(intent);
            System.out.println("Event started");
            Snackbar.make(coordinatorLayout, "Event started", Snackbar.LENGTH_SHORT).show();
        } else {
            stopService(new Intent(this, EventService.class));
            event = receiver.getEvent();
            System.out.println("Event ended");
//            String endMsg = "Event ended. Duration: " + event.getDurationSeconds();
//            Snackbar.make(coordinatorLayout, endMsg, Snackbar.LENGTH_SHORT).show();
            EVENT_ON = false;
        }
    }
}
