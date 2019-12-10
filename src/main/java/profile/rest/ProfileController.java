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
import profile.service.ProfileService;

@RestController
@RequestMapping("/profile")
@ComponentScan({"login.service", "profile.service"})
public class ProfileController {

    private final UserService userService;
    private final ProfileService profileService;

    @Autowired
    public ProfileController(UserService userService, ProfileService profileService) {
        this.userService = userService;
        this.profileService = profileService;
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<HttpStatus> createProfile(@RequestBody ProfileDTO profileDTO, @RequestBody UserDTO userDTO) {
        User user = userService.getUserByMail(userDTO.getUsername(), userDTO.getPassword());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (profileService.createProfile(profileDTO, user.getId(), user.getSupervisor().getId())) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
