package africa.irespond.moodtracker.service;

import africa.irespond.moodtracker.dto.UserDto;
import africa.irespond.moodtracker.model.AppUser;
import africa.irespond.moodtracker.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Override
    public AppUser register(UserDto userDto) {
        boolean existingUser = userRepository.existsUserByUsernameIgnoreCase(userDto.getUsername());
        if(existingUser){
            throw new IllegalStateException("username chosen");
        }
        AppUser user = new AppUser();
        user.setUsername(userDto.getUsername());
        userRepository.save(user);
        return user;
    }

    @Override
    public void saveUser(AppUser user) {
        userRepository.save(user);
    }

    @Override
    public AppUser findUserByUsername(String username) {
        IllegalStateException illegalStateException = new IllegalStateException("User not found");
       AppUser foundUser = userRepository.findUserByUsernameIgnoreCase(username);
       if(foundUser == null){
           throw illegalStateException;
       }
        return foundUser;
    }

    @Override
    public List<AppUser> findAllUsers() {
        return userRepository.findAll();
    }


}
