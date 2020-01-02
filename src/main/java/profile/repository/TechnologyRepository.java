package profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import profile.entity.Technology;

@Repository
public interface TechnologyRepository extends JpaRepository<Technology, Long> {
}
