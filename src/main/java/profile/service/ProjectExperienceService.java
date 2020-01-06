package profile.service;

import login.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import profile.entity.ProjectExperience;
import profile.entity.Request;
import profile.entity.RequestType;
import profile.repository.ProjectExperienceRepository;

import java.util.List;

@Service
public class ProjectExperienceService extends RequestCreator {
    private ProjectExperienceRepository projectExperienceRepository;

    @Autowired
    public ProjectExperienceService(ProjectExperienceRepository projectExperienceRepository, RequestsService requestsService) {
        super(requestsService);
        this.projectExperienceRepository = projectExperienceRepository;
    }

    public List<ProjectExperience> getAllProjectsOfUser(User user) {
        return projectExperienceRepository.findAllByUser(user);
    }

    public void assignExperience(ProjectExperience experience, User assigner) {
        requestsService.addRequest(new Request(RequestType.Create, assigner, assigner.getSupervisor(), experience));
    }
}
