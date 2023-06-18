package africa.irespond.moodtracker.repository;

import africa.irespond.moodtracker.model.AnnualMoodTracker;
import africa.irespond.moodtracker.model.MoodTracker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnualMoodTrackerRepository extends JpaRepository<AnnualMoodTracker, Long> {
    
}
