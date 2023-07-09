package africa.irespond.moodtracker.service;

import africa.irespond.moodtracker.dto.MoodDto;
import africa.irespond.moodtracker.model.*;
import africa.irespond.moodtracker.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private UserServiceImpl userService;


    private final ModelMapper modelMapper;


    @Override
    public MoodTracker createMood(MoodDto moodDto) {
        User foundUser = userService.findUserByUsername(moodDto.getOwner());
        MoodTracker moodTracker = new MoodTracker();
        moodTracker.setOwner(foundUser.getUsername());
       moodTracker.setMood(moodDto.getMood());
       moodTracker.setComment(moodDto.getComment());
//       moodTracker.setSocialMoodInfluence(moodDto.getSocialMoodInfluence());
//       moodTracker.setWeatherMoodInfluence(moodDto.getWeatherMoodInfluence());
       moodTracker.setDateTimeCreated(LocalDateTime.now().toString());
       moodTracker.setDateTimeUpdated(LocalDateTime.now().toString());
        switch (moodTracker.getMood()) {
            case "SAD" -> moodTracker.setRatings(1.0);
            case "ANXIOUS" -> moodTracker.setRatings(2.0);
            case "NEUTRAL" -> moodTracker.setRatings(3.0);
            case "CALM" -> moodTracker.setRatings(4.0);
            default -> moodTracker.setRatings(5.0);
        }
        trackerRepository.save(moodTracker);

        DailyMoodTracker dailyMoodTracker = new DailyMoodTracker();
        dailyMoodTracker.setOwner(foundUser.getUsername());
        dailyMoodTracker.setMood(moodDto.getMood());
        dailyMoodTracker.setComment(moodDto.getComment());
//        dailyMoodTracker.setSocialMoodInfluence(moodDto.getSocialMoodInfluence());
//        dailyMoodTracker.setWeatherMoodInfluence(moodDto.getWeatherMoodInfluence());
        dailyMoodTracker.setDateTimeCreated(LocalDateTime.now().toString());
        dailyMoodTracker.setDateTimeUpdated(LocalDateTime.now().toString());
        switch (dailyMoodTracker.getMood()) {
            case "SAD" -> dailyMoodTracker.setRatings(1.0);
            case "ANXIOUS" -> dailyMoodTracker.setRatings(2.0);
            case "NEUTRAL" -> dailyMoodTracker.setRatings(3.0);
            case "CALM" -> dailyMoodTracker.setRatings(4.0);
            default -> dailyMoodTracker.setRatings(5.0);
        }
        dailyMoodTrackerRepository.save(dailyMoodTracker);

        foundUser.getMoodTrackers().add(moodTracker);
        foundUser.getDailyMoodTrackers().add(dailyMoodTracker);
        userService.saveUser(foundUser);

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
        foundTracker.setDateTimeUpdated(LocalDateTime.now().toString());
        trackerRepository.save(foundTracker);

        return foundTracker;
    }

    @Override
    public void deleteMoodTracker(Long moodTrackerId) {
        MoodTracker foundTracker = findMood(moodTrackerId);
        trackerRepository.delete(foundTracker);
    }


    @Override
    public List<User> calculateWeeklyMoodRate() {
        List<User> allUsers = userService.findAllUsers();
        List<Double> listOfRatings = new ArrayList<>();
        for (User user: allUsers ) {
            user.getDailyMoodTrackers().forEach(tracker -> listOfRatings.add(tracker.getRatings()));
            double average = listOfRatings.stream()
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0.0);
            user.getDailyMoodTrackers().clear();
            if(average > 0.0) {
                WeeklyMoodTracker moodTracker = new WeeklyMoodTracker();
                moodTracker.setRatings(average);
                weeklyMoodTrackerRepository.save(moodTracker);
                user.getWeeklyMoodTrackers().add(moodTracker);
            }
            if(average > 4.0 && average <= 5.0) {
                user.setMoodTrackerMessage("Dear "+user.getUsername()+" , from the mood entries you recorded last week," +
                        "it can be concluded that you had an excellent mood change");
            } else if (average > 3.0 && average <= 4.0) {
                user.setMoodTrackerMessage("Dear "+user.getUsername()+" , from the mood entries you recorded last week," +
                        "it can be concluded that you had a very good mood change");
            } else if (average > 2.0 && average <= 3.0 ) {
                user.setMoodTrackerMessage("Dear "+user.getUsername()+" , from the mood entries you recorded last week," +
                        "it can be concluded that you had a good mood change");
            } else if (average > 1.0 && average <= 2.0) {
                user.setMoodTrackerMessage("Dear "+user.getUsername()+" , from the mood entries you recorded last week," +
                        "it can be concluded that you had a fair mood change");
            } else if (average > 0.0 && average <= 1.0){
                user.setMoodTrackerMessage("Dear "+user.getUsername()+" , from the mood entries you recorded last week," +
                        "it can be concluded that you had a poor mood change");
            } else {
                user.setMoodTrackerMessage("No mood was available to track last week");
            }
            System.out.println(user.getMoodTrackerMessage());
        }

//        List<Double> listOfRatings = new ArrayList<>();
//        dailyMoodTrackerRepository.findAll().forEach(tracker -> listOfRatings.add(tracker.getRatings()));
//        double average = listOfRatings.stream()
//                .mapToDouble(Double::doubleValue)
//                .average()
//                .orElse(0.0);
//        dailyMoodTrackerRepository.deleteAll(dailyMoodTrackerRepository.findAll());
//        if(average > 0.0 && listOfRatings.size() > 0) {
//            WeeklyMoodTracker moodTracker = new WeeklyMoodTracker();
//            moodTracker.setRatings(average);
//            weeklyMoodTrackerRepository.save(moodTracker);
//        }
//        if(average > 4.0 && average <= 5.0) {
//            return "You had an excellent mood last week";
//        } else if (average > 3.0 && average <= 4.0) {
//            return "You had a very good mood last week";
//        } else if (average > 2.0 && average <= 3.0 ) {
//            return "You had a good mood last week";
//        } else if (average > 1.0 && average <= 2.0) {
//            return "You had a fair mood last week";
//        } else if (average > 0.0 && average <= 1.0){
//            return "You had a poor mood last week";
//        } else {
//            return "No mood was available to track last week";}

        return allUsers;
    }

    @Override
    public List<User> calculateMonthlyMoodRate() {
        List<User> allUsers = userService.findAllUsers();
        List<Double> listOfRatings = new ArrayList<>();
        for (User user: allUsers ) {
            user.getWeeklyMoodTrackers().forEach(tracker -> listOfRatings.add(tracker.getRatings()));
            double average = listOfRatings.stream()
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0.0);
            user.getWeeklyMoodTrackers().clear();
            if(average > 0.0) {
                MonthlyMoodTracker moodTracker = new MonthlyMoodTracker();
                moodTracker.setRatings(average);
                monthlyMoodTrackerRepository.save(moodTracker);
                user.getMonthlyMoodTrackers().add(moodTracker);
            }
            if(average > 4.0 && average <= 5.0) {
                user.setMoodTrackerMessage("Dear "+user.getUsername()+" , from the mood entries you recorded last month," +
                        "it can be concluded that you had an excellent mood change");
            } else if (average > 3.0 && average <= 4.0) {
                user.setMoodTrackerMessage("Dear "+user.getUsername()+" , from the mood entries you recorded last month," +
                        "it can be concluded that you had a very good mood change");
            } else if (average > 2.0 && average <= 3.0 ) {
                user.setMoodTrackerMessage("Dear "+user.getUsername()+" , from the mood entries you recorded last month," +
                        "it can be concluded that you had a good mood change");
            } else if (average > 1.0 && average <= 2.0) {
                user.setMoodTrackerMessage("Dear "+user.getUsername()+" , from the mood entries you recorded last month," +
                        "it can be concluded that you had a fair mood change");
            } else if (average > 0.0 && average <= 1.0){
                user.setMoodTrackerMessage("Dear "+user.getUsername()+" , from the mood entries you recorded last month," +
                        "it can be concluded that you had a poor mood change");
            } else {
                user.setMoodTrackerMessage("No mood was available to track last month");
            }
            System.out.println(user.getMoodTrackerMessage());
        }
        return allUsers;
    }

    @Override
    public List<User> calculateAnnualMoodRate() {
        List<User> allUsers = userService.findAllUsers();
        List<Double> listOfRatings = new ArrayList<>();
        for (User user : allUsers) {
            user.getMonthlyMoodTrackers().forEach(tracker -> listOfRatings.add(tracker.getRatings()));
            double average = listOfRatings.stream()
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0.0);
            user.getMonthlyMoodTrackers().clear();
            if (average > 0.0) {
                AnnualMoodTracker moodTracker = new AnnualMoodTracker();
                moodTracker.setRatings(average);
                annualMoodTrackerRepository.save(moodTracker);
                user.getAnnualMoodTrackers().add(moodTracker);
            }
            if (average > 4.0 && average <= 5.0) {
                user.setMoodTrackerMessage("Dear " + user.getUsername() + " , from the mood entries you recorded throughout last year," +
                        "it can be concluded that you had an excellent mood change");
            } else if (average > 3.0 && average <= 4.0) {
                user.setMoodTrackerMessage("Dear " + user.getUsername() + " , from the mood entries you recorded throughout last year," +
                        "it can be concluded that you had a very good mood change");
            } else if (average > 2.0 && average <= 3.0) {
                user.setMoodTrackerMessage("Dear " + user.getUsername() + " , from the mood entries you recorded throughout last year," +
                        "it can be concluded that you had a good mood change");
            } else if (average > 1.0 && average <= 2.0) {
                user.setMoodTrackerMessage("Dear " + user.getUsername() + " , from the mood entries you recorded throughout last year," +
                        "it can be concluded that you had a fair mood change");
            } else if (average > 0.0 && average <= 1.0) {
                user.setMoodTrackerMessage("Dear " + user.getUsername() + " , from the mood entries you recorded throughout last year," +
                        "it can be concluded that you had a poor mood change");
            } else {
                user.setMoodTrackerMessage("No mood was available to track last month");
            }
            System.out.println(user.getMoodTrackerMessage());
        }
        return allUsers;
    }

}
