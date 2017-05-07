package eetu.kallio.project.tiko.tamk.fi.keeptrack.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import eetu.kallio.project.tiko.tamk.fi.keeptrack.R;
import eetu.kallio.project.tiko.tamk.fi.keeptrack.http.EventGetTask;

public class EventListActivity extends AppCompatActivity {

    private String user;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        user = getIntent().getExtras().getString("user");
        ListView listView = (ListView) findViewById(R.id.eventList);
        new EventGetTask(this).execute();
    }

    public String getUser () {
        return user;
    }
}
