package africa.irespond.moodtracker.repository;

import africa.irespond.moodtracker.model.MoodTracker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrackerRepository extends JpaRepository<MoodTracker, Long> {

}
