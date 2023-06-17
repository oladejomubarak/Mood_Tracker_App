package africa.irespond.moodtracker.service;

import africa.irespond.moodtracker.dto.MoodDto;
import africa.irespond.moodtracker.model.Mood;
import africa.irespond.moodtracker.model.SocialMoodInfluence;
import africa.irespond.moodtracker.model.MoodTracker;
import africa.irespond.moodtracker.model.WeatherMoodInfluence;
import africa.irespond.moodtracker.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class MoodTrackerServiceImpl implements MoodTrackerService{
    @Autowired
    private TrackerRepository trackerRepository;
    @Autowired
    private DailyMoodTrackerRepository dailyMoodTrackerRepository;
    @Autowired
    private WeeklyMoodTrackerRepository weeklyMoodTrackerRepository;
    @Autowired
    private MonthlyMoodTrackerRepository monthlyMoodTrackerRepository;
    @Autowired
    private AnnualMoodTrackerRepository annualMoodTrackerRepository;


    @Override
    public MoodTracker createMood(MoodDto moodDto) {
        MoodTracker moodTracker = new MoodTracker();
       moodTracker.setMood(Mood.valueOf(moodDto.getMood()));
       moodTracker.setComment(moodDto.getComment());
       moodTracker.setSocialMoodInfluence(SocialMoodInfluence.valueOf(moodDto.getSocialMoodInfluence()));
       moodTracker.setWeatherMoodInfluence(WeatherMoodInfluence.valueOf(moodDto.getWeatherMoodInfluence()));
       moodTracker.setTodayDate((LocalDate.now()).toString());
       if(moodTracker.getMood().equals(Mood.VERY_SAD)){
           moodTracker.setRatings(1);
       } else if (moodTracker.getMood().equals(Mood.SAD)) {
            moodTracker.setRatings(2);
       } else if (moodTracker.getMood().equals(Mood.FAIR)) {
            moodTracker.setRatings(3);
       } else if (moodTracker.getMood().equals(Mood.HAPPY)) {
            moodTracker.setRatings(4);
       }else {
           moodTracker.setRatings(5);
       }
        trackerRepository.save(moodTracker);
       dailyMoodTrackerRepository.save(moodTracker);
        return moodTracker;
    }

    @Override
    public MoodTracker findMood(Long moodTrackerId) {

        return trackerRepository.findById(moodTrackerId).orElseThrow(()-> new RuntimeException("Mood tracker not found"));
    }

    @Override
    public MoodTracker editMoodTracker(Long moodTrackerId, MoodDto moodDto) {
        MoodTracker foundTracker = findMood(moodTrackerId);

        return null;
    }

    @Override
    public void deleteMoodTracker(Long moodTrackerId) {
        MoodTracker foundTracker = findMood(moodTrackerId);
        trackerRepository.delete(foundTracker);
    }

    @Override
    public String calculateWeeklyMoodRate() {
        List<Integer> listOfRatings = new ArrayList<>();
        List<MoodTracker> allDailyMoodTrackers= dailyMoodTrackerRepository.findAll();
        allDailyMoodTrackers.forEach(tracker -> listOfRatings.add(tracker.getRatings()));
        double average = listOfRatings.stream()
                .mapToDouble(Integer::doubleValue)
                .average()
                .orElse(0.0);
        return null;
    }
}
