package africa.irespond.moodtracker.repository;

import africa.irespond.moodtracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUserByUsernameIgnoreCase(String username);
    User findUserByUsernameIgnoreCase(String username);
}
