package africa.irespond.moodtracker.repository;

import africa.irespond.moodtracker.model.MonthlyMoodTracker;
import africa.irespond.moodtracker.model.MoodTracker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthlyMoodTrackerRepository extends JpaRepository<MonthlyMoodTracker, Long> {
}
