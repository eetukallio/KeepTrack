package eetu.kallio.project.tiko.tamk.fi.keeptrack.http;

import android.os.AsyncTask;
import android.util.Log;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import eetu.kallio.project.tiko.tamk.fi.keeptrack.resources.WorkEvent;

/**
 * A custom AsyncTask for deleting events from the database via API.
 *
 * @author Eetu Kallio
 * @version 4.0
 * @since 4.0
 */
public class EventDeleteTask extends AsyncTask<Void, Integer, Integer> {

    private WorkEvent event;

    /**
     * A constructor with the event to be deleted.
     *
     * @param event The event to be deleted.
     */
    public EventDeleteTask (WorkEvent event) {
        this.event = event;
    }

    /**
     * Lifecycle method of AsyncTask.
     *
     * Run in a separate thread from the UI. Forms the connection and deletes the event from
     * the database via a HTTP Request.
     *
     * @param params Parameters to be passed, in this case void.
     * @return Returns the result of the task.
     */
    @Override
    protected Integer doInBackground (Void... params) {

        HttpURLConnection connection = null;
        URL url;
        int result = 400;

        try {
            url = new URL("http://207.154.228.188:8080/events/" + event.getId());
            Log.d("DELETETASK", "http://207.154.228.188:8080/events/" + event.getId());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.connect();
            System.out.println(connection.getResponseCode());
            result = connection.getResponseCode();

        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            if ( connection != null ) connection.disconnect();
        }

        return result;
    }
}
