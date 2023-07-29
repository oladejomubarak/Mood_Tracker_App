package africa.irespond.moodtracker.service;

import africa.irespond.moodtracker.dto.MoodDto;
import africa.irespond.moodtracker.model.MoodGraph;
import africa.irespond.moodtracker.model.MoodEntry;

import java.util.List;

public interface MoodEntryService {
    MoodEntry createMood(MoodDto moodDto);

    MoodEntry findMood(Long moodTrackerId);
    MoodEntry editMoodEntry(Long moodTrackerId, MoodDto moodDto);
    void deleteMoodEntry(Long moodTrackerId);
    List<MoodEntry> findAllMoodEntriesForUser(String username);
    List<MoodEntry> findAllMoodEntriesForUserByDate(String username, String date);
    List<MoodEntry> findAllMoodEntriesForUserByMood(String username, String mood);
    void summarizeMoodRating();

    void plotMoodGraph();

    List<MoodGraph> findGraphsByUser(String username);
    List<MoodGraph> findAllMoodGraphs();

}
