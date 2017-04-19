package eetu.kallio.project.tiko.tamk.fi.keeptrack;

import android.content.ComponentName;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private boolean EVENT_ON = false;
    private WorkEvent event;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.myCoordinatorLayout);
    }

    public void startEvent(View v) {

        if ( !EVENT_ON ) {

            EVENT_ON = true;
            event = new WorkEvent();
            System.out.println("Event started");
            Snackbar.make(coordinatorLayout, "Event started", Snackbar.LENGTH_SHORT).show();
            System.out.println(event.toString());
        } else {

            System.out.println("Event ended");
            event.endEvent();
            System.out.println(event.getDurationSeconds());
            String endMsg = "Event ended. Duration: " + event.getDurationSeconds();
            Snackbar.make(coordinatorLayout, endMsg, Snackbar.LENGTH_SHORT).show();
            EVENT_ON = false;
        }
    }
}
