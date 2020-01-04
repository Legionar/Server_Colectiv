package profile.service;

import login.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import profile.entity.ProjectExperience;
import profile.repository.ProjectExperienceRepository;

import java.util.List;

@Service
public class ProjectExperienceService {
    private ProjectExperienceRepository projectExperienceRepository;

    @Autowired
    public ProjectExperienceService(ProjectExperienceRepository projectExperienceRepository) {
        this.projectExperienceRepository = projectExperienceRepository;
    }

    public List<ProjectExperience> getAllProjectsOfUser(User user) {
        return projectExperienceRepository.findAllByUser(user);
    }
}
