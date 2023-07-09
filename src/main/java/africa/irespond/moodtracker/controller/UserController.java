package africa.irespond.moodtracker.controller;

import africa.irespond.moodtracker.dto.UserDto;
import africa.irespond.moodtracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        try {
            return ResponseEntity.ok(userService.register(userDto));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
