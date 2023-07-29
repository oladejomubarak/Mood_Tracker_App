package africa.irespond.moodtracker.repository;

import africa.irespond.moodtracker.model.AppUser;
import africa.irespond.moodtracker.model.Mood;
import africa.irespond.moodtracker.model.MoodEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoodEntryRepository extends JpaRepository<MoodEntry, Long> {
    List<MoodEntry> findMoodEntriesByUser(AppUser appUser);
  List<MoodEntry> findMoodEntriesByUserAndMood(AppUser appUser, Mood mood);
    List<MoodEntry> findMoodEntriesByUserAndCreatedOn(AppUser appUser, String date);

}
