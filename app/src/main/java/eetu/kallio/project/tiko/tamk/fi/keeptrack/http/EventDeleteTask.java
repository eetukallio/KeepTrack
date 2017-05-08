package eetu.kallio.project.tiko.tamk.fi.keeptrack.http;

import android.os.AsyncTask;
import android.util.Log;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import eetu.kallio.project.tiko.tamk.fi.keeptrack.resources.WorkEvent;

/**
 * Created by Eetu Kallio on 7.5.2017
 */

public class EventDeleteTask extends AsyncTask<Void, Integer, Integer> {

    private WorkEvent event;

    public EventDeleteTask (WorkEvent event) {
        this.event = event;
    }

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

    @Override
    protected void onPostExecute (Integer integer) {

    }
}
