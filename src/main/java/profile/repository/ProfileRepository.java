package profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import profile.entity.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
}