package fi.tamk.tiko.project.kallio.eetu.server;

import javax.persistence.*;

/**
 *
 *  The event entity class
 *
 *  @author eetukallio
 *  @version 1.0
 *  @since 1.0
 */
@Entity
@Table(name="events")
public class WorkEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;
    @Column(name="date")
    private String date;
    @Column(name="duration")
    private float duration;
    @Column(name="user")
    private int user;


    /**
     * Default constructor
     */
    public WorkEvent() {}

    /**
     * Constructor for initializing attributes
     *
     * @param id The id of the event.
     * @param date The date of the event.
     * @param duration The duration of the event.
     * @param user The user of the event.
     */
    public WorkEvent(long id, String date, float duration, int user) {
        this.id = id;
        this.date = date;
        this.duration = duration;
        this.user = user;
    }

    /**
     * Getter for the event's id.
     *
     * @return Id of the event.
     */
    public long getId() {
        return this.id;
    }

    /**
     * Setter for the event's id.
     *
     * @param id Id of the event.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter for the event's date.
     *
     * @return Date of the event.
     */
    public String getDate() {
        return date;
    }

    /**
     * Setter for the event's date.
     *
     * @param date Date of the event.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Getter for the duration of the event.
     *
     * @return Duration of the event.
     */
    public float getDuration() {
        return duration;
    }

    /**
     * Setter for the duration of the event.
     *
     * @param duration Duration of the event.
     */
    public void setDuration(float duration) {
        this.duration = duration;
    }

    /**
     * Getter of the user of the event.
     *
     * @return User of the event.
     */
    public int getUser() {
        return user;
    }

    /**
     * Setter for the user of the event.
     *
     * @param user User of the event.
     */
    public void setUser(int user) {
        this.user = user;
    }
}
