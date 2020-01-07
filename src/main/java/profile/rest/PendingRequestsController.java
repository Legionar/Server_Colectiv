package profile.rest;

import login.entity.User;
import login.rest.UserDTO;
import login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import profile.entity.Request;
import profile.service.RequestsService;

import java.util.List;

@RestController
@ComponentScan("profile.service")
public class PendingRequestsController {
    private RequestsService requestsService;
    private UserService userService;

    @Autowired
    public PendingRequestsController(RequestsService requestsService, UserService userService) {
        this.requestsService = requestsService;
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Request>> getRequestsForSupervisor(@RequestBody UserDTO user) {
        User supervisor = userService.getUserByEmailAndPassword(user.getUsername(), user.getPassword());
        if (supervisor == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(requestsService.getRequestsForSupervisor(supervisor), HttpStatus.OK);
    }
}
