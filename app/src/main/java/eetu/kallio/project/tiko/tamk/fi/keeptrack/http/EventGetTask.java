package eetu.kallio.project.tiko.tamk.fi.keeptrack.http;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

import eetu.kallio.project.tiko.tamk.fi.keeptrack.R;
import eetu.kallio.project.tiko.tamk.fi.keeptrack.resources.EventArrayAdapter;
import eetu.kallio.project.tiko.tamk.fi.keeptrack.resources.WorkEvent;
import eetu.kallio.project.tiko.tamk.fi.keeptrack.ui.EventListActivity;

/**
 * Created by Eetu Kallio on 25.4.2017
 */

public class EventGetTask extends AsyncTask<Void, Integer, Integer> {

    private String json = "";
    private EventListActivity main;

    public EventGetTask (EventListActivity main) {
        this.main = main;
    }

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
