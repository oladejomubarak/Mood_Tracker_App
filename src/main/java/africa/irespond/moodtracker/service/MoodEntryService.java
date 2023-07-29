package africa.irespond.moodtracker.service;

import africa.irespond.moodtracker.dto.MoodDto;
import africa.irespond.moodtracker.model.MoodGraph;
import africa.irespond.moodtracker.model.MoodEntry;

import java.util.List;

public interface MoodEntryService {
    MoodEntry createMood(MoodDto moodDto);

    MoodEntry findMood(Long moodTrackerId);
    MoodEntry editMoodTracker(Long moodTrackerId, MoodDto moodDto);
    void deleteMoodTracker(Long moodTrackerId);
    List<MoodEntry> findAllMoodTrackersForUser(String username);
    List<MoodEntry> findAllMoodTrackersForUserByDate(String username, String date);
//    List<MoodEntry> findAllMoodTrackersForUserByMood(String username, String mood);
    void summarizeMoodRating();

    void plotMoodGraph();

    List<MoodGraph> findGraphsByUser(String username);
    List<MoodGraph> findAllMoodGraphs();

}
