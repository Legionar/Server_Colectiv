package profile.rest;

import login.entity.User;
import login.rest.UserDTO;
import login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import profile.entity.Skill;
import profile.entity.UserSkill;
import profile.service.SkillsService;
import profile.service.UserSkillService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/profiles/skills")
@ComponentScan("profile.service")
public class SkillController {
    private UserSkillService userSkillService;

    private UserService userService;

    private SkillsService skillsService;

    @Autowired
    public SkillController(UserSkillService userSkillService, UserService userService, SkillsService skillsService) {
        this.userSkillService = userSkillService;
        this.userService = userService;
        this.skillsService = skillsService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllSkills(@RequestParam Long id, @RequestBody UserDTO userDTO) {
        if (userDTO != null) {
            List<Skill> allSkills = getAllSkills(userDTO);
            if (allSkills == null) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>(allSkills, HttpStatus.OK);
        }
        if (id != null) {
            User user = userService.getById(id);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(getSkillsForUser(user.getId()), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private List<Skill> getAllSkills(UserDTO userDTO) {
        boolean admin = userService.isAdmin(userDTO.getUsername(), userDTO.getPassword());
        if (!admin) {
            return null;
        }
        return skillsService.getAll();
    }

    private List<UserSkill> getSkillsForUser(Long userId) {
        User user = userService.getById(userId);
        if (user == null) {
            return null;
        }
        return userSkillService.getSkillsForUser(user.getEmail());
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> createSkill(@RequestBody Skill skill, @RequestParam String user) {
        User userByEmail = userService.getUserByEmail(user);
        if (skill == null || userByEmail == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        skillsService.createNewSkill(skill, userByEmail);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(path = "/employees", method = RequestMethod.GET)
    public ResponseEntity<Set<User>> usersWithSKills(@RequestParam List<String> skills, @RequestBody UserDTO user) {
        if (!userService.isAdmin(user.getUsername(), user.getPassword())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(userSkillService.getAllUsersWithSkills(skillsService.getAllByName(skills)), HttpStatus.OK);
    }
}
