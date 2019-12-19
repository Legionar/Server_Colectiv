package login.service;

import login.entity.User;
import login.exception.Messages;
import login.exception.UserException;
import login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByMail(String email, String password) {
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

    public boolean isAdmin(String email, String password) {
        User user = getUserByMail(email, password);
        return user != null && user.getAdmin() == 1;
    }

    public User updateUser(User user) {
        User old = userRepository.getOne(user.getId());
        User newUser = merge(old,user);
        userRepository.saveAndFlush(newUser);
        return newUser;
    }

    private User merge(User local, User remote) {
        local.setEmail(remote.getEmail() != null ? remote.getEmail() : local.getEmail());
        local.setPassword(remote.getPassword() != null ? remote.getPassword() : local.getPassword());
        local.setFirst_name(remote.getFirst_name() != null ? remote.getFirst_name() : local.getFirst_name());
        local.setLast_name(remote.getLast_name() != null ? remote.getLast_name() : local.getLast_name());
        local.setPhone(remote.getPhone() != null ? remote.getPhone() : local.getPhone());
        local.setAddress(remote.getAddress() != null ? remote.getAddress() : local.getAddress());
        local.setDate(remote.getDate() != null ? remote.getDate() : local.getDate());
        local.setRole(remote.getRole() != null ? remote.getRole() : local.getRole());
        local.setRegion(remote.getRegion() != null ? remote.getRegion() : local.getRegion());
        local.setConsulting_level(remote.getConsulting_level() != null ? remote.getConsulting_level() : local.getConsulting_level());
        local.setProfile_picture(remote.getProfile_picture() != null ? remote.getProfile_picture() : local.getProfile_picture());
        return local;
    }

}
