package profile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import profile.entity.UserSkill;
import profile.repository.UserSkillRepository;

import java.util.List;

@Service
public class UserSkillService {
    private UserSkillRepository userSkillRepository;

    @Autowired
    public UserSkillService (UserSkillRepository userSkillRepository) {
        this.userSkillRepository = userSkillRepository;
    }

    public List<UserSkill> getAll(){
        return userSkillRepository.findAll();
    }

    public List<UserSkill> getSkillsForUser(String email) {
        return userSkillRepository.findAllByUserEmail(email);
    }

}
