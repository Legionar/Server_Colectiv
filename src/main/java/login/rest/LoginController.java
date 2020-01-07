package login.rest;

import login.entity.User;
import login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.rowset.serial.SerialBlob;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@ComponentScan("login.service")
public class LoginController {

    private final UserService userService;

    private Map<String, Integer> blockedEmails = new HashMap<>();

    @Autowired
    public LoginController(UserService service) {
        this.userService = service;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> login(@NotNull @RequestBody UserDTO user) {
        Integer failedAttempts = blockedEmails.get(user.getUsername());
        if (failedAttempts == null) {
            failedAttempts = 0;
        }
        if (failedAttempts >= 3) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        User response = userService.getUserByEmailAndPassword(user.getUsername(), user.getPassword());
        if (response == null) {
            failedAttempts++;
            blockedEmails.put(user.getUsername(), failedAttempts);
        }
        return new ResponseEntity<>(response, response != null ? HttpStatus.ACCEPTED : HttpStatus.FORBIDDEN);
    }

    // For admin only
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers(@NotNull @RequestBody UserDTO userDTO) {
        if (userService.isAdmin(userDTO.getUsername(), userDTO.getPassword())) {
            return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@NotNull @RequestBody User user) {
        User result = userService.updateUser(user);
        return new ResponseEntity<>(user.equals(result) ? HttpStatus.OK : HttpStatus.NOT_MODIFIED);
    }

    // For admin only
    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<?> resetBlockedAccounts(@NotNull @RequestBody UserDTO user, @RequestParam List<String> emails) {
        if (userService.isAdmin(user.getUsername(), user.getPassword())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        emails.forEach(email -> blockedEmails.remove(email));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/supervisor")
    public ResponseEntity<List<User>> getUsersUnderSupervisor(@RequestBody UserDTO supervisorCredentials) {
        User supervisor = userService.getUserByEmailAndPassword(supervisorCredentials.getUsername(), supervisorCredentials.getPassword());
        if (supervisor == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(userService.getUsersUnderSupervisor(supervisor), HttpStatus.OK);
    }

    @RequestMapping(path = "/picture", method = RequestMethod.PUT)
    public ResponseEntity<?> uploadProfilePicture(@NotNull @RequestBody byte[] picture, @NotNull @RequestParam String user) {
        User userByEmail = userService.getUserByEmail(user);
        if (userByEmail == null || picture.length == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            userService.uploadProfilePicture(userByEmail, new SerialBlob(picture));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/picture", method = RequestMethod.GET)
    public ResponseEntity<?> getProfilePicture(@NotNull @RequestBody UserDTO userDTO) {
        User user = userService.getUserByEmailAndPassword(userDTO.getUsername(), userDTO.getPassword());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        try {
            return new ResponseEntity<>(user.getProfile_picture().getBytes(1, (int) user.getProfile_picture().length()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
