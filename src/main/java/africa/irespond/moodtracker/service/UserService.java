package africa.irespond.moodtracker.service;

import africa.irespond.moodtracker.dto.UserDto;
import africa.irespond.moodtracker.model.AppUser;

import java.util.List;

public interface UserService {
    AppUser register(UserDto userDto);
    void saveUser(AppUser user);

    AppUser findUserByUsername(String username);
    List<AppUser> findAllUsers();

}
