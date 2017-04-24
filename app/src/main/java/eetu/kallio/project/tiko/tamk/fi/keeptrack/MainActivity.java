package eetu.kallio.project.tiko.tamk.fi.keeptrack;


import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private boolean EVENT_ON = false;
    private CoordinatorLayout coordinatorLayout;
    private EventReceiver  receiver;
    private Button startButton;
    LocalBroadcastManager manager;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.myCoordinatorLayout);
        startButton = (Button) findViewById(R.id.startTrackingButton);
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
            startButton.setText("STOP TRACKING");
        } else {
            WorkEvent event;
            stopService(new Intent(this, EventService.class));
            event = receiver.getEvent();
            System.out.println("Event ended");
            startButton.setText("START TRACKING");
            String endMsg = "Event ended. Duration: " + event.getDurationSeconds();
            Snackbar.make(coordinatorLayout, endMsg, Snackbar.LENGTH_SHORT).show();
            stopService(new Intent(this, EventService.class));
            EVENT_ON = false;
            postEvent(event);
        }
    }

    public void  postEvent(WorkEvent event) {


    }
}
