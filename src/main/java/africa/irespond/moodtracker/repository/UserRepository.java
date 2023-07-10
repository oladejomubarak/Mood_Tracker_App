package africa.irespond.moodtracker.repository;

import africa.irespond.moodtracker.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    boolean existsUserByUsernameIgnoreCase(String username);
    AppUser findUserByUsernameIgnoreCase(String username);
}
