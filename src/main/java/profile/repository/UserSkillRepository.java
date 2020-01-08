package profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import profile.entity.Skill;
import profile.entity.UserSkill;

import java.util.List;
import java.util.Set;

@Repository
public interface UserSkillRepository extends JpaRepository<UserSkill, Long> {
    List<UserSkill> findAllByUserEmail(String userEmail);

    Set<UserSkill> findAllBySkill(Skill skill);
}
