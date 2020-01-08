package profile.rest;

import login.entity.User;
import login.rest.UserDTO;
import login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import profile.entity.ProjectExperience;
import profile.service.ProjectExperienceService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/profiles/experience")
@ComponentScan({"profile.service","login.service"})
public class ProjectExperienceController {
    private ProjectExperienceService projectExperienceService;

    private UserService userService;

    @Autowired
    public ProjectExperienceController(ProjectExperienceService projectExperienceService, UserService userService) {
        this.projectExperienceService = projectExperienceService;
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ProjectExperience>> getExperienceForUser(@RequestBody UserDTO userDTO) {
        User user = userService.getUserByEmailAndPassword(userDTO.getUsername(), userDTO.getPassword());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        List<ProjectExperience> allProjectsOfUser = projectExperienceService.getAllProjectsOfUser(user);
        if (allProjectsOfUser == null) {
            allProjectsOfUser = Collections.EMPTY_LIST;
        }
        return new ResponseEntity<>(allProjectsOfUser,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> assignExperience(@RequestBody ProjectExperience experience, @RequestParam String user) {
        User userByEmail = userService.getUserByEmail(user);
        if (experience == null || user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        projectExperienceService.assignExperience(experience, userByEmail);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
