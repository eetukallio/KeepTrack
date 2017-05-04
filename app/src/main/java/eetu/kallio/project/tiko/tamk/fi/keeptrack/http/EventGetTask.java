package eetu.kallio.project.tiko.tamk.fi.keeptrack.http;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
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
            url = new URL("http://10.0.2.2:8080/events");
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

        JSONArray array = null;
        String date;
        float duration;
        String durationMetric = "s";
        ListView listView = (ListView) main.findViewById(R.id.eventList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(main, R.layout.list_item, R.id.itemTextView);
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

                    date = object.getString("date");
                    duration = (float)object.getDouble("duration");
                    if ( duration > 3600 ) {
                        duration /= 3600;
                        durationMetric = "hrs";
                    }else if ( duration > 60 ) {
                        duration /= 60;
                        durationMetric = "min";
                    }
                    adapter.add(date + " \nDuration: " + duration + durationMetric);
                }
            }
        } catch ( JSONException e ) {
            e.printStackTrace();
        }

        listView.setAdapter(adapter);
    }
}
