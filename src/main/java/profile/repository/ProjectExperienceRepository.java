package profile.repository;

import login.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import profile.entity.ProjectExperience;

import java.util.List;

@Repository
public interface ProjectExperienceRepository extends JpaRepository<ProjectExperience, Long> {
    List<ProjectExperience> findAllByUser(User user);
}
