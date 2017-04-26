package eetu.kallio.project.tiko.tamk.fi.keeptrack.http;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import eetu.kallio.project.tiko.tamk.fi.keeptrack.resources.WorkEvent;

/**
 * Created by Eetu Kallio on 25.4.2017
 */

public class EventPostTask extends AsyncTask<WorkEvent, Void, Integer> {

    @Override
    protected Integer doInBackground (WorkEvent... params) {
        WorkEvent event;
        String json;
        HttpURLConnection connection;
        URL url;
        int result = 400;
        event = params[0];
        json = buildJson(event);
        Log.d("EventPostTask",  json);

        try {
            url = new URL("http://10.0.2.2:8080/events");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.connect();
            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(json);
            writer.flush();
            writer.close();
            outputStream.close();
            System.out.println(connection.getResponseCode());
            result = connection.getResponseCode();
            connection.disconnect();
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        return result;
    }

    public String buildJson(WorkEvent event) {

        JSONObject json = new JSONObject();
        try {
            json.put("date", new Date());
            json.put("duration", event.getDurationSeconds());
            json.put("user", event.getUser());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }
}
