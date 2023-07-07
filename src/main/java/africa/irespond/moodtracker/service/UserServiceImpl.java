package africa.irespond.moodtracker.service;

import africa.irespond.moodtracker.dto.userDto;
import africa.irespond.moodtracker.model.User;
import africa.irespond.moodtracker.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Override
    public User register(userDto userDto) {
        boolean existingUser = userRepository.existsByUsername(userDto.getUsername());
        if(existingUser){
            throw new IllegalStateException("username chosen");
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        userRepository.save(user);
        return user;
    }

    @Override
    public void saveUser(User user) {
       userRepository.save(user);
    }
}
