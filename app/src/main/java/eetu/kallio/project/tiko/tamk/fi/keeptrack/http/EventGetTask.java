package eetu.kallio.project.tiko.tamk.fi.keeptrack.http;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import eetu.kallio.project.tiko.tamk.fi.keeptrack.R;
import eetu.kallio.project.tiko.tamk.fi.keeptrack.resources.EventArrayAdapter;
import eetu.kallio.project.tiko.tamk.fi.keeptrack.resources.WorkEvent;
import eetu.kallio.project.tiko.tamk.fi.keeptrack.ui.EventListActivity;


/**
 * A custom AsyncTask for getting events from the database via API.
 *
 * @author Eetu Kallio
 * @version 4.0
 * @since 2.0
 */
public class EventGetTask extends AsyncTask<Void, Integer, Integer> {

    private String json = "";
    private EventListActivity main;

    /**
     * A constructor with the context from which invoked.
     *
     * @param main The context from which the task was invoked.
     */
    public EventGetTask (Context main) {

        this.main = (EventListActivity) main;
    }

    /**
     * Lifecycle method of AsyncTask.
     *
     * Run in a separate thread from the UI. Forms the connection and does the JSON conversion
     * to get the data from the database via a HTTP Request.
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
            url = new URL("http://207.154.228.188:8080/events");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("GET");
            connection.connect();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"),8);
            StringBuilder builder = new StringBuilder();
            String line;
            while ( (line = bufferedReader.readLine()) != null ) {
                builder.append(line);
                builder.append("\n");
            }
            json = builder.toString();
            System.out.println(connection.getResponseCode());
            result = connection.getResponseCode();

        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            if ( connection != null ) connection.disconnect();
        }

        return result;
    }

    /**
     * Lifecycle method of AsyncTask.
     *
     * Run after the execution of doInBackground. UI related things such as
     * adding the listView happen here.
     *
     * @param integer integer
     */
    @Override
    protected void onPostExecute (Integer integer) {

        Log.d("EventGetTask", "getting events");
        JSONArray array = null;
        ListView listView = (ListView) main.findViewById(R.id.eventList);
        EventArrayAdapter adapter = new EventArrayAdapter(main, R.layout.list_item, R.id.itemTextView);

        try {

            array = new JSONArray(json);
            Log.d("JSONArray", array.toString());

        }catch ( JSONException e ) {

            e.printStackTrace();
        }

        try {
            if ( array != null ) {
                for ( int i = 0; i < array.length(); i++ ) {

                    JSONObject object = array.getJSONObject(i);
                    WorkEvent event = new WorkEvent(object.getInt("id"), object.getString("date"), object.getLong("duration"), object.getString("user"));

                    if ( event.getUser().equals(main.getUser()) ) {
                        adapter.add(event);
                    }
                }
            }
        } catch ( JSONException e ) {

            e.printStackTrace();
        }

        listView.setAdapter(adapter);
    }
}
