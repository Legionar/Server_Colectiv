package profile.service;

import login.entity.User;
import login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import profile.entity.*;
import profile.repository.ProjectExperienceRepository;
import profile.repository.SkillRepository;
import profile.repository.UserSkillRepository;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service("requestsService")
@ComponentScan({"profile.repository", "login.repository"})
public class RequestsService {
    private SkillRepository skillRepository;
    private UserSkillRepository userSkillRepository;
    private UserRepository userRepository;
    private ProjectExperienceRepository projectExperienceRepository;
    private static List<Request> allRequests = new ArrayList<>();

    @Autowired
    public RequestsService(SkillRepository skillRepository, UserSkillRepository userSkillRepository, UserRepository userRepository, ProjectExperienceRepository projectExperienceRepository) {
        this.skillRepository = skillRepository;
        this.userSkillRepository = userSkillRepository;
        this.userRepository = userRepository;
        this.projectExperienceRepository = projectExperienceRepository;
    }

    public List<Request> getRequestsForSupervisor(User supervisor) {
        return allRequests
                .stream()
                .filter(request -> request.getUser().equals(supervisor))
                .collect(toList());
    }

    public synchronized void addRequest(Request request) {
        allRequests.add(request);
    }

    public void approveRequest(Request request) {
        allRequests.remove(request);
        switch (request.getType()) {
            case Create:
            case Update:
                handleCreateAndUpdateRequest(request);
                break;
            case Delete:
                handleDeleteRequest(request);
                break;
        }
    }

    private void handleDeleteRequest(Request request) {
        Action action = request.getAction();
        if (action instanceof User) {
            userRepository.delete((User) action);
        }
        if (action instanceof UserSkill) {
            userSkillRepository.delete((UserSkill) action);
        }
        if (action instanceof Skill) {
            skillRepository.delete((Skill) action);
        }
        if (action instanceof ProjectExperience) {
            projectExperienceRepository.delete((ProjectExperience) action);
        }
    }

    private void handleCreateAndUpdateRequest(Request request) {
        Action action = request.getAction();
        if (action instanceof User) {
            userRepository.saveAndFlush((User) action);
        }
        if (action instanceof UserSkill) {
            userSkillRepository.saveAndFlush((UserSkill) action);
        }
        if (action instanceof Skill) {
            skillRepository.saveAndFlush((Skill) action);
        }
        if (action instanceof ProjectExperience) {
            projectExperienceRepository.saveAndFlush((ProjectExperience) action);
        }
    }
}
