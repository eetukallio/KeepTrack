package eetu.kallio.project.tiko.tamk.fi.keeptrack.resources;

import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Eetu Kallio on 18.4.2017
 */

public class WorkEvent implements Serializable {

    private Date startDate;
    private SimpleDateFormat format;
    private String startDateToString;
    private Date endDate;
    private String endDateToString;
    private long durationMs;
    private long durationSeconds;
    private long durationMinutes;
    private long durationHr;
    private int user;


    public WorkEvent() {

        startDate = new Date();
        format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.UK);
        startDateToString = format.format(startDate);
        user = 1;
    }

    @Override
    public String toString () {
        return startDateToString;
    }

    public WorkEvent endEvent () {

        endDate = new Date();
        endDateToString = format.format(endDate);
        durationMs = Math.abs(endDate.getTime()-startDate.getTime());
        durationHr = durationMs / (1000*60*60);
        durationSeconds = durationMs / (1000);
        durationMinutes = durationMs / (1000*60);
        return this;
    }

    public Date getStartDate () {
        return startDate;
    }

    public SimpleDateFormat getFormat () {
        return format;
    }

    public String getStartDateToString () {
        return startDateToString;
    }

    public Date getEndDate () {
        return endDate;
    }

    public String getEndDateToString () {
        return endDateToString;
    }

    public long getDurationMs () {
        return durationMs;
    }

    public long getDurationHr () {
        return durationHr;
    }

    public long getDurationSeconds () {
        return durationSeconds;
    }

    public long getDurationMinutes () {
        return durationMinutes;
    }

    public int getUser () {
        return user;
    }

    public void setUser (int user) {
        this.user = user;
    }
}
