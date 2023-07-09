package africa.irespond.moodtracker.service;

import africa.irespond.moodtracker.dto.UserDto;
import africa.irespond.moodtracker.model.User;

import java.util.List;

public interface UserService {
    User register(UserDto userDto);
    void saveUser(User user);

    User findUserByUsername(String username);
    List<User> findAllUsers();
}
