package fi.tamk.tiko.project.kallio.eetu.server;

import org.springframework.data.repository.CrudRepository;

/**
 *  The event repository class
 *
 *  @author eetukallio
 *  @version 1.0
 *  @since 1.0
 */
public interface EventRepository extends CrudRepository<WorkEvent, Long> {

    /**
     * Returns all the events.
     *
     * @return All events.
     */
    Iterable<WorkEvent> findAll();

    /**
     * Deletes a event from the database via object reference.
     *
     * @param entity A event in the database.
     */
    void delete(WorkEvent entity);

    /**
     * Deletes a event from the database via ID reference.
     *
     * @param id Id of the event.
     */
    void delete(Long id);

    /**
     * Returns a event via ID reference.
     *
     * @param id Id of the event.
     * @return WorkEvent with the specified Id.
     */
    WorkEvent findOne(Long id);
}