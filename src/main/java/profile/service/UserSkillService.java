package profile.service;

import login.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import profile.entity.Skill;
import profile.entity.UserSkill;
import profile.repository.UserSkillRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
public class UserSkillService extends RequestCreator{
    private UserSkillRepository userSkillRepository;

    @Autowired
    public UserSkillService (UserSkillRepository userSkillRepository, RequestsService requestsService) {
        super(requestsService);
        this.userSkillRepository = userSkillRepository;
    }

    public List<UserSkill> getAll(){
        return userSkillRepository.findAll();
    }

    public List<UserSkill> getSkillsForUser(String email) {
        return userSkillRepository.findAllByUserEmail(email);
    }

    public Set<User> getAllUsersWithSkills(Set<Skill> skills) {
        Set<User> users = new HashSet<>();
        skills.forEach(skill->
                users.addAll(
                userSkillRepository
                        .findAllBySkill(skill)
                        .stream()
                        .map(UserSkill::getUser)
                        .collect(toSet())));
        return users;
    }

}
