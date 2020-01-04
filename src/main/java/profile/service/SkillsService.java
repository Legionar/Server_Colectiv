package profile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import profile.entity.Skill;
import profile.repository.SkillRepository;

import java.util.List;

@Service
@ComponentScan("profile.repository")
public class SkillsService {
    private SkillRepository skillRepository;

    @Autowired
    public SkillsService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public List<Skill> getAll() {
        return skillRepository.findAll();
    }

    public boolean createNewSkill(Skill skill) {
        return skillRepository.saveAndFlush(skill) != null;
    }
}
