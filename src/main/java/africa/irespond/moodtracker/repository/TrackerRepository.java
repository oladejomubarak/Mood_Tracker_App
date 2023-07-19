package africa.irespond.moodtracker.repository;

import africa.irespond.moodtracker.model.AppUser;
import africa.irespond.moodtracker.model.MoodTracker;
import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrackerRepository extends JpaRepository<MoodTracker, Long> {
    List<MoodTracker> findMoodTrackersByUser(AppUser appUser);
    List<MoodTracker> findMoodTrackersByUserAndMoodIgnoreCase(AppUser appUser, String mood);
    List<MoodTracker> findMoodTrackersByUserAndCreatedOn(AppUser appUser, String date);

}
