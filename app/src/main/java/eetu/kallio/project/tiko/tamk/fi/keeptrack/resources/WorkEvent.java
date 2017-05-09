package eetu.kallio.project.tiko.tamk.fi.keeptrack.resources;

import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Work event resource class.
 */
public class WorkEvent implements Serializable {

    private int id;
    private Date startDate;
    private SimpleDateFormat format;
    private String startDateToString;
    private Date endDate;
    private String endDateToString;
    private long durationMs;
    private long durationSeconds;
    private long durationMinutes;
    private long durationHr;
    private String user;

    /**
     * Default constructor. Initializes most needed attributes.
     */
    public WorkEvent() {

        startDate = new Date();
        format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.UK);
        startDateToString = format.format(startDate);
        user = "";
    }

    /**
     * Constructor to set some crucial attributes.
     *
     * @param id Id of the event.
     * @param startDateToString Start date of the event.
     * @param durationSeconds Duration of the event in seconds.
     * @param user User id of the user that started the event.
     */
    public WorkEvent (int id, String startDateToString, long durationSeconds, String user) {
        this.id = id;
        this.startDateToString = startDateToString;
        this.durationSeconds = durationSeconds;
        this.user = user;
    }

    /**
     * Used to get a string representing the state of the object.
     *
     * @return Returns the start date of the event as a string.
     */
    @Override
    public String toString () {
        return startDateToString;
    }

    /**
     * Used to end an event. Determines the final values to all attributes and returns the object.
     *
     * @return Returns the invoked object.
     */
    public WorkEvent endEvent () {

        endDate = new Date();
        endDateToString = format.format(endDate);
        durationMs = Math.abs(endDate.getTime()-startDate.getTime());
        durationHr = durationMs / (1000*60*60);
        durationSeconds = durationMs / (1000);
        durationMinutes = durationMs / (1000*60);
        return this;
    }

    /**
     * Getter for the start date.
     *
     * @return The start date of the event.
     */
    public Date getStartDate () {
        return startDate;
    }

    /**
     * Getter for the start date string.
     *
     * @return The start date in a formatted string.
     */
    public String getStartDateToString () {
        return startDateToString;
    }

    /**
     * Getter for the end date.
     *
     * @return The end date of the event.
     */
    public Date getEndDate () {
        return endDate;
    }

    /**
     * Getter for the end date string.
     *
     * @return The end date in a formatted string.
     */
    public String getEndDateToString () {
        return endDateToString;
    }

    /**
     * Getter for the duration in milliseconds.
     *
     * @return The duration in milliseconds.
     */
    public long getDurationMs () {
        return durationMs;
    }

    /**
     * Getter for the duration in hours.
     *
     * @return The duration in hours.
     */
    public long getDurationHr () {
        return durationHr;
    }

    /**
     * Getter for the duration in seconds.
     *
     * @return The duration in seconds.
     */
    public long getDurationSeconds () {
        return durationSeconds;
    }

    /**
     * Getter for the duration in minutes.
     *
     * @return The duration in minutes.
     */
    public long getDurationMinutes () {
        return durationMinutes;
    }

    /**
     * Getter for the user.
     *
     * @return The user of the event.
     */
    public String getUser () {
        return user;
    }

    /**
     * Setter for the user .
     */
    public void setUser (String user) {
        this.user = user;
    }

    /**
     * Getter for the id.
     *
     * @return The id of the event.
     */
    public int getId () {
        return id;
    }

    /**
     * Setter for the id.
     */
    public void setId (int id) {
        this.id = id;
    }

    /**
     * Getter for the events duration metric.
     *
     * @return Returns and appropriate metric.
     */
    public String getMetric () {

        String metric;

        if ( durationSeconds > 3600 ) {
            metric = "hrs";
        } else if ( durationSeconds > 60 ) {
            metric = "min";
        } else {
            metric = "s";
        }

        return metric;
    }
}
