package africa.irespond.moodtracker.repository;

import africa.irespond.moodtracker.model.AppUser;
import africa.irespond.moodtracker.model.Mood;
import africa.irespond.moodtracker.model.MoodEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoodEntryRepository extends JpaRepository<MoodEntry, Long> {
    List<MoodEntry> findMoodTrackersByUser(AppUser appUser);
  List<MoodEntry> findMoodTrackersByUserAndMood(AppUser appUser, Mood mood);
    List<MoodEntry> findMoodTrackersByUserAndCreatedOn(AppUser appUser, String date);

}
