package login.rest;

import login.Service.UserService;
import login.entity.User;
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
@ComponentScan("login.Service")
public class LoginController {
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody UserDTO user) {
        User user1 = userService.loginByEmail(user.getUsername(), user.getPassword());
        return new ResponseEntity(user1 != null ? HttpStatus.ACCEPTED : HttpStatus.FORBIDDEN);
    }

}
