package eetu.kallio.project.tiko.tamk.fi.keeptrack.resources;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

import eetu.kallio.project.tiko.tamk.fi.keeptrack.R;
import eetu.kallio.project.tiko.tamk.fi.keeptrack.http.EventDeleteTask;
import eetu.kallio.project.tiko.tamk.fi.keeptrack.ui.EventListActivity;

/**
 * Created by Eetu Kallio on 7.5.2017
 */

public class EventArrayAdapter extends ArrayAdapter<WorkEvent> {

    private String dayOfMonth;
    private String month;
    private String dayName;
    private String year;
    private String startHrs;
    private String startMinutes;
    private float duration;
    private EventListActivity main;


    public EventArrayAdapter (@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId) {
        super(context, resource, textViewResourceId);
        main = (EventListActivity) context;
    }

    @NonNull
    @Override
    public View getView (int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Log.d("getView", "getting view");

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView tv = (TextView) convertView.findViewById(R.id.itemTextView);

        final WorkEvent event = getItem(position);

        String durationMetric = "s";

        if ( event.getStartDateToString() != null ) {
            String date = event.getStartDateToString();
            String[] split = date.split(" ");
            dayName = split[0];
            month = split[1];
            dayOfMonth = split[2];
            year = split[5];
            startHrs = split[3].split(":")[0];
            startMinutes = split[3].split(":")[1];
            duration = event.getDurationSeconds();
        }

        if ( duration > 3600 ) {
            duration /= 3600;
            durationMetric = "hrs";
        }else if ( duration > 60 ) {
            duration /= 60;
            durationMetric = "min";
        }
        DecimalFormat df = new DecimalFormat("#.#");
        String displayedDuration = df.format(duration);

        tv.setText(dayName + " " + dayOfMonth + " " + month + " " + year +
                " " + startHrs + ":" + startMinutes + " \n\nDuration: " +
                displayedDuration + durationMetric);

        final EventArrayAdapter adapter = this;


        ImageView deleteButton = (ImageView)  convertView.findViewById(R.id.deleteButton);
        deleteButton.setTag(position);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                int position = (Integer) v.getTag();
                new EventDeleteTask(event, adapter, main).execute();
                remove(event);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
