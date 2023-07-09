package africa.irespond.moodtracker.service;

import africa.irespond.moodtracker.dto.MoodDto;
import africa.irespond.moodtracker.model.MoodTracker;
import africa.irespond.moodtracker.model.User;

import java.util.List;

public interface MoodTrackerService {
    MoodTracker createMood(MoodDto moodDto);

    MoodTracker findMood(Long moodTrackerId);
    MoodTracker editMoodTracker(Long moodTrackerId, MoodDto moodDto);
    void deleteMoodTracker(Long moodTrackerId);

    List<User> calculateWeeklyMoodRate();

    List<User> calculateMonthlyMoodRate();
    List<User> calculateAnnualMoodRate();



}
