package login.service;

import login.entity.User;
import login.exception.Messages;
import login.exception.UserException;
import login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User loginByEmail(String email, String password) {
        return this.userRepository.loginByEmail(email, password);
    }

    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    public User createUser(User user) {
        return this.userRepository.save(user);
    }

    public User deleteUser(Long id) throws UserException {
        User user = this.userRepository.getOne(id);
        if (user == null)
            throw new UserException(Messages.USER_NOT_FOUND);
        this.userRepository.delete(user);
        return user;
    }

}
