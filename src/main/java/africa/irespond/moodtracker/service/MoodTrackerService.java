package africa.irespond.moodtracker.service;

import africa.irespond.moodtracker.dto.MoodDto;
import africa.irespond.moodtracker.model.MoodTracker;
import africa.irespond.moodtracker.model.AppUser;

import java.util.List;

public interface MoodTrackerService {
    MoodTracker createMood(MoodDto moodDto);

    MoodTracker findMood(Long moodTrackerId);
    MoodTracker editMoodTracker(Long moodTrackerId, MoodDto moodDto);
    void deleteMoodTracker(Long moodTrackerId);

    List<AppUser> calculateWeeklyMoodRate();

    List<AppUser> calculateMonthlyMoodRate();
    List<AppUser> calculateAnnualMoodRate();



}
