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
    public ResponseEntity login(@RequestBody UserDTO user) {
        User user1 = userService.loginByEmail(user.getUsername(), user.getPassword());
        return new ResponseEntity(user1 != null ? HttpStatus.ACCEPTED : HttpStatus.FORBIDDEN);
    }

}
