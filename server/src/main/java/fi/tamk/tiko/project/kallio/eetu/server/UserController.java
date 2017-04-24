package fi.tamk.tiko.project.kallio.eetu.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for the user repository
 *
 * @author Henri Kankaanpää
 * @version 1.0
 * @since 1.0
 */
@RestController
public class UserController {

    @Autowired
    private UserRepository users;

    /**
     * Saves the user to the database
     * @param user The user object to be saved
     */
    @RequestMapping(value = "/users",  method=RequestMethod.POST)
    public void saveUser(@RequestBody User user) {
        users.save(user);
    }

    /**
     * Gets all the users from the database
     * @return Returns the result
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<User> fetchUsers() {
        return users.findAll();
    }

    /**
     * Gets a specific user from the database
     * @param userId ID of the user to be found
     * @return Returns the result
     */
    @RequestMapping(value = "/users/{userId}",  method=RequestMethod.GET)
    public User fetchUsers(@PathVariable long userId) {
        return users.findOne(userId);
    }
}
