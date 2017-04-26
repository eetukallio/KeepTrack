package fi.tamk.tiko.project.kallio.eetu.server;

import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for the User class
 *
 * @author Henri Kankaanpää
 * @version 1.0
 * @since 1.0
 */
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Returns all the users from the database
     * @return Returns users in an Iterable
     */
    Iterable<User> findAll();

    /**
     * Deletes a specific user class from the database
     *
     * @param entity The user to be deleted
     */
    void delete(User entity);

    /**
     * Deletes a user from the database by ID
     *
     * @param id The ID of the user to be deleted
     */
    void delete(Long id);

    /**
     * Finds a single user by their ID
     *
     * @param id The ID of the user to be found
     * @return Returns the result of the query
     */
    User findOne(Long id);
}