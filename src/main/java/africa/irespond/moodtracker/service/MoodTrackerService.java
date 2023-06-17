package africa.irespond.moodtracker.service;

import africa.irespond.moodtracker.dto.MoodDto;
import africa.irespond.moodtracker.model.MoodTracker;

public interface MoodTrackerService {
    MoodTracker createMood(MoodDto moodDto);

    MoodTracker findMood(Long moodTrackerId);
    MoodTracker editMoodTracker(Long moodTrackerId, MoodDto moodDto);
    void deleteMoodTracker(Long moodTrackerId);

    String calculateWeeklyMoodRate();
}
