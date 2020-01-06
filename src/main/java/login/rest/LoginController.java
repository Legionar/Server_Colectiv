package login.rest;

import login.entity.User;
import login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        User response = userService.getUserByMail(user.getUsername(), user.getPassword());
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
}
