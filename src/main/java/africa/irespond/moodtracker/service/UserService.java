package africa.irespond.moodtracker.service;

import africa.irespond.moodtracker.dto.userDto;
import africa.irespond.moodtracker.model.User;
import jakarta.jws.soap.SOAPBinding;

public interface UserService {
    User register(userDto userDto);
    void saveUser(User user);
}
