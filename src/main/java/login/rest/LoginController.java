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
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/users")
@ComponentScan("login.service")
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService service) {
        this.userService = service;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> login(@NotNull @RequestBody UserDTO user) {
        User response = userService.getUserByMail(user.getUsername(), user.getPassword());
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
}
