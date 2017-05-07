package fi.tamk.tiko.project.kallio.eetu.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *  The event controller class
 *
 *  @author eetukallio
 *  @version 1.0
 *  @since 1.0
 */
@RestController
public class EventController {

    @Autowired
    EventRepository db;

    /**
     * Saves a event in the database.
     *
     * @param ml WorkEvent to be stored in the database.
     */
    @RequestMapping(value = "/events",  method=RequestMethod.POST)
    public void saveEvent(@RequestBody WorkEvent ml) {
        db.save(ml);
    }

    /**
     * Returns all events in the database.
     *
     * @return All events in the database as an iterable.
     */
    @RequestMapping(value = "/events", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<WorkEvent> fetchEvents() {
        return db.findAll();
    }

    /**
     * Returns all events in the database.
     *
     * @return All events in the database as an iterable.
     */
    @RequestMapping(value = "/events", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteEvents() {
        db.deleteAll();
    }

    /**
     * Returns all events in the database.
     *
     * @return All events in the database as an iterable.
     */
    @RequestMapping(value = "/events/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteEvent(@PathVariable("id") long id) {
        db.delete(id);
    }


    /**
     * Returns a event by id.
     *
     * @param id Id of the event.
     * @return WorkEvent matching the id.
     */
    @RequestMapping(value = "/events/{id}",  method=RequestMethod.GET)
    public WorkEvent fetchEvent(@PathVariable long id) {
        return db.findOne(id);
    }
}
