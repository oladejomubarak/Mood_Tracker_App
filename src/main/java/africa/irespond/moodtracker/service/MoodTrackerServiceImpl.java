package africa.irespond.moodtracker.service;

import africa.irespond.moodtracker.dto.MoodDto;
import africa.irespond.moodtracker.model.*;
import africa.irespond.moodtracker.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
@RequiredArgsConstructor
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


    private final ModelMapper modelMapper;


    @Override
    public MoodTracker createMood(MoodDto moodDto) {
        MoodTracker moodTracker = new MoodTracker();
       moodTracker.setMood(moodDto.getMood());
       moodTracker.setComment(moodDto.getComment());
       moodTracker.setSocialMoodInfluence(moodDto.getSocialMoodInfluence());
       moodTracker.setWeatherMoodInfluence(moodDto.getWeatherMoodInfluence());
       moodTracker.setTodayDate((LocalDate.now()).toString());
       if(moodTracker.getMood().equals(Mood.VERY_SAD)){
           moodTracker.setRatings(1.0);
       } else if (moodTracker.getMood().equals(Mood.SAD)) {
            moodTracker.setRatings(2.0);
       } else if (moodTracker.getMood().equals(Mood.FAIR)) {
            moodTracker.setRatings(3.0);
       } else if (moodTracker.getMood().equals(Mood.HAPPY)) {
            moodTracker.setRatings(4.0);
       }else {
           moodTracker.setRatings(5.0);
       }
        trackerRepository.save(moodTracker);

        DailyMoodTracker dailyMoodTracker = new DailyMoodTracker();
        dailyMoodTracker.setMood(moodDto.getMood());
        dailyMoodTracker.setComment(moodDto.getComment());
        dailyMoodTracker.setSocialMoodInfluence(moodDto.getSocialMoodInfluence());
        dailyMoodTracker.setWeatherMoodInfluence(moodDto.getWeatherMoodInfluence());
        dailyMoodTracker.setTodayDate((LocalDate.now()).toString());
        if(dailyMoodTracker.getMood().equals(Mood.VERY_SAD)){
            dailyMoodTracker.setRatings(1.0);
        } else if (dailyMoodTracker.getMood().equals(Mood.SAD)) {
            dailyMoodTracker.setRatings(2.0);
        } else if (dailyMoodTracker.getMood().equals(Mood.FAIR)) {
            dailyMoodTracker.setRatings(3.0);
        } else if (dailyMoodTracker.getMood().equals(Mood.HAPPY)) {
            dailyMoodTracker.setRatings(4.0);
        }else {
            dailyMoodTracker.setRatings(5.0);
        }
        dailyMoodTrackerRepository.save(dailyMoodTracker);

        return moodTracker;
    }

    @Override
    public MoodTracker findMood(Long moodTrackerId) {

        return trackerRepository.findById(moodTrackerId).orElseThrow(()-> new RuntimeException("Mood tracker not found"));
    }

    @Override
    public MoodTracker editMoodTracker(Long moodTrackerId, MoodDto moodDto) {
        MoodTracker foundTracker = findMood(moodTrackerId);
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(moodDto, foundTracker);

        return foundTracker;
    }

    @Override
    public void deleteMoodTracker(Long moodTrackerId) {
        MoodTracker foundTracker = findMood(moodTrackerId);
        trackerRepository.delete(foundTracker);
    }


    @Override
    public String calculateWeeklyMoodRate() {
        List<Double> listOfRatings = new ArrayList<>();
        dailyMoodTrackerRepository.findAll().forEach(tracker -> listOfRatings.add(tracker.getRatings()));
        double average = listOfRatings.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
        dailyMoodTrackerRepository.deleteAll(dailyMoodTrackerRepository.findAll());
        if(average > 0.0 && listOfRatings.size() > 0) {
            WeeklyMoodTracker moodTracker = new WeeklyMoodTracker();
            moodTracker.setRatings(average);
            weeklyMoodTrackerRepository.save(moodTracker);
        }
        if(average > 4.0 && average <= 5.0) {
            return "You had an excellent mood last week";
        } else if (average > 3.0 && average <= 4.0) {
            return "You had a very good mood last week";
        } else if (average > 2.0 && average <= 3.0 ) {
            return "You had a good mood last week";
        } else if (average > 1.0 && average <= 2.0) {
            return "You had a fair mood last week";
        } else if (average > 0.0 && average <= 1.0){
            return "You had a poor mood last week";
        } else {
            return "No mood was available to track last week";} }

    @Override
    public String calculateMonthlyMoodRate() {
        List<Double> ratingList = new ArrayList<>();
        weeklyMoodTrackerRepository.findAll().forEach(tracker -> ratingList.add(tracker.getRatings()));
        double average = ratingList.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
        weeklyMoodTrackerRepository.deleteAll(weeklyMoodTrackerRepository.findAll());
        if(average > 0.0 && weeklyMoodTrackerRepository.findAll().size() > 0) {
            MonthlyMoodTracker moodTracker = new MonthlyMoodTracker();
            moodTracker.setRatings(average);
            monthlyMoodTrackerRepository.save(moodTracker);
        }
        if(average > 4.0 && average <= 5.0) {
            return "You had an excellent mood last month";
        } else if (average > 3.0 && average <= 4.0) {
            return "You had a very good mood last month";
        } else if (average > 2.0 && average <= 3.0 ) {
            return "You had a good mood last month";
        } else if (average > 1.0 && average <= 2.0) {
            return "You had a fair mood last month";
        } else if (average > 0.0 && average <= 1.0){
            return "You had a poor mood last month";
        } else {
            return "No mood was available to track last month";}
    }

    @Override
    public String calculateAnnualMoodRate() {
        List<Double> ratingList = new ArrayList<>();
        monthlyMoodTrackerRepository.findAll().forEach(tracker -> ratingList.add(tracker.getRatings()));
        double average = ratingList.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
        monthlyMoodTrackerRepository.deleteAll();
        if(average > 0.0 && monthlyMoodTrackerRepository.findAll().size() > 0) {
            AnnualMoodTracker moodTracker = new AnnualMoodTracker();
            moodTracker.setRatings(average);
            annualMoodTrackerRepository.save(moodTracker);
        }

        if(average > 4.0 && average <= 5.0) {
            return "You had an excellent mood last year";
        } else if (average > 3.0 && average <= 4.0) {
            return "You had a very good mood last year";
        } else if (average > 2.0 && average <= 3.0 ) {
            return "You had a good mood last year";
        } else if (average > 1.0 && average <= 2.0) {
            return "You had a fair mood last year";
        } else if (average > 0.0 && average <= 1.0){
            return "You had a poor mood last year";
        } else {
            return "No mood was available to track last year";}
    }
}
