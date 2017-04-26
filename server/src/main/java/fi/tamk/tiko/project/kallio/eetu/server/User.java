package fi.tamk.tiko.project.kallio.eetu.server;

import javax.persistence.*;

/**
 * User entity class
 *
 * @author Henri Kankaanpää
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name="locations")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;
    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;
    @Column(name="email")
    private String email;

    /**
     * Default constructor
     */
    public User() {}

    /**
     * Constructor for initializing attributes
     *
     * @param id ID
     * @param username The user's username
     * @param password The user's password
     * @param email The user's email
     */
    public User(long id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * Getter for the ID
     *
     * @return Returns the ID
     */
    public long getId() {
        return this.id;
    }

    /**
     * Getter for the email
     *
     * @return Returns the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter for the username
     *
     * @return Returns the username
     */
    public String getUsername() {
        return this.username;

    }

    /**
     * Getter for the password
     *
     * @return Returns the password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Setter for the ID
     *
     * @param id The ID to be set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Setter for the username
     *
     * @param username The username to be set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Setter for the password
     *
     * @param password The password to be set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Setter for the email
     *
     * @param email The email to be set
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
