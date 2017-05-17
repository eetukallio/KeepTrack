package eetu.kallio.project.tiko.tamk.fi.keeptrack.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import eetu.kallio.project.tiko.tamk.fi.keeptrack.R;
import eetu.kallio.project.tiko.tamk.fi.keeptrack.http.EventGetTask;

/**
 * Event List activity
 *
 * @author Eetu Kallio
 * @version 4.0
 * @since 1.0
 */
public class EventListActivity extends AppCompatActivity {

    private String user;
    private Toolbar toolbar;

    /**
     * Called when an activity instance is created. Initializes most attributes.
     *
     * @param savedInstanceState Carries over data from previous use.
     */
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        user = getIntent().getExtras().getString("user");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        new EventGetTask(this).execute();
    }

    /**
     * Getter for the user.
     *
     * @return Returns the user's id.
     */
    public String getUser () {
        return user;
    }
}
