package login.repository;

import login.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User loginByEmail(String email, String password);
}
