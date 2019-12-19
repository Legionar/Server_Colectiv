package login.rest;

import login.entity.User;
import login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@ComponentScan("login.service")
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService service){
        this.userService=service;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> login(@RequestBody UserDTO user) {
        User response = userService.loginByEmail(user.getUsername(), user.getPassword());
        response.setSupervisor(null);
        return new ResponseEntity<>(response, response != null ? HttpStatus.ACCEPTED : HttpStatus.FORBIDDEN);
    }

}
