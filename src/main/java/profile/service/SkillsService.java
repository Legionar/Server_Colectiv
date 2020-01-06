package profile.service;

import login.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import profile.entity.Request;
import profile.entity.RequestType;
import profile.entity.Skill;
import profile.repository.SkillRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@ComponentScan("profile.repository")
public class SkillsService extends RequestCreator{
    private SkillRepository skillRepository;

    @Autowired
    public SkillsService(SkillRepository skillRepository, RequestsService requestsService) {
        super(requestsService);
        this.skillRepository = skillRepository;
    }

    public List<Skill> getAll() {
        return skillRepository.findAll();
    }

    public void createNewSkill(Skill skill, User user){
        requestsService.addRequest(new Request(RequestType.Create, user, user.getSupervisor(), skill));
    }

    public Set<Skill> getAllByName(List<String> names) {
        Set<Skill> skills = new HashSet<>();
        names.forEach(name -> skills.addAll(skillRepository.findAllByName(name)));
        return skills;
    }
}
