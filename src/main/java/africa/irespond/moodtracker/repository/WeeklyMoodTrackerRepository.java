package africa.irespond.moodtracker.repository;

import africa.irespond.moodtracker.model.WeeklyMoodTracker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeeklyMoodTrackerRepository extends JpaRepository<WeeklyMoodTracker, Long> {

}
