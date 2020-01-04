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
import profile.entity.ProjectExperience;
import profile.service.ProjectExperienceService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/experience")
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
        User user = userService.getUserByMail(userDTO.getUsername(), userDTO.getPassword());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        List<ProjectExperience> allProjectsOfUser = projectExperienceService.getAllProjectsOfUser(user);
        if (allProjectsOfUser == null) {
            allProjectsOfUser = Collections.EMPTY_LIST;
        }
        return new ResponseEntity<>(allProjectsOfUser,HttpStatus.OK);
    }
}
