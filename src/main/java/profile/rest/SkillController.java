package profile.rest;

import login.rest.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import profile.entity.UserSkill;
import profile.service.UserSkillService;

import java.util.List;

@RestController
@RequestMapping("/profiles")
@ComponentScan("profile.service")
public class SkillController {
    private UserSkillService userSkillService;

    @Autowired
    public SkillController(UserSkillService userSkillService) {
        this.userSkillService = userSkillService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getUserSkillsAndTechnologies(@RequestBody UserDTO user) {
        List<UserSkill> skillsForUser = userSkillService.getSkillsForUser(user.getUsername());
        return new ResponseEntity<>(skillsForUser,HttpStatus.OK);
    }
}
