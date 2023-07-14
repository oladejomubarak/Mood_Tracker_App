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
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
@RequiredArgsConstructor
public class MoodTrackerServiceImpl implements MoodTrackerService{
    @Autowired
    private TrackerRepository trackerRepository;
    @Autowired
    private MoodGraphRepository moodGraphRepository;


    @Autowired
    private UserServiceImpl userService;


    private final ModelMapper modelMapper;


    @Override
    public MoodTracker createMood(MoodDto moodDto) {
        AppUser foundUser = userService.findUserByUsername(moodDto.getOwner());
        MoodTracker moodTracker = new MoodTracker();
       moodTracker.setMood(moodDto.getMood());
       moodTracker.setComment(moodDto.getComment());
       moodTracker.setDateTimeCreated(LocalDateTime.now().toString());
       moodTracker.setDateTimeUpdated(LocalDateTime.now().toString());
       moodTracker.setOwner(foundUser.getUsername());
       //moodTracker.setUser(foundUser);
        switch (moodTracker.getMood()) {
            case "SAD" -> moodTracker.setRatings(1.0);
            case "ANXIOUS" -> moodTracker.setRatings(2.0);
            case "NEUTRAL" -> moodTracker.setRatings(3.0);
            case "CALM" -> moodTracker.setRatings(4.0);
            default -> moodTracker.setRatings(5.0);
        }
        trackerRepository.save(moodTracker);
        foundUser.getMoodTrackers().add(moodTracker);
        userService.saveUser(foundUser);
        List<Double> ratingList = foundUser.getMoodRatings();
        foundUser.getMoodTrackers().forEach(tracker -> ratingList.add(tracker.getRatings()));
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
    public List<MoodTracker> findUserMoodTrackers(String username) {
        AppUser foundUser = userService.findUserByUsername(username);
        return foundUser.getMoodTrackers();
    }

    @Override
    public void summarizeMoodRating() {
        List<AppUser> allUsers = userService.findAllUsers();

        for (AppUser user: allUsers ) {
            List<Double> listOfRatings = user.getMoodRatings();
            double average =  listOfRatings.stream()
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0.0);
            listOfRatings.clear();
            userService.saveUser(user);
//            if(average > 0.0 && listOfRatings.size() > 0 ) {
//                WeeklyMoodTracker moodTracker = new WeeklyMoodTracker();
//                moodTracker.setRatings(average);
//                //moodTracker.setUser(user);
//                weeklyMoodTrackerRepository.save(moodTracker);
//                user.getWeeklyMoodTrackers().add(moodTracker);
//                user.setDailyMoodTrackers(user.getDailyMoodTrackers());
//                userService.saveUser(user);
//            }
            if(average > 4.0 && average <= 5.0) {
                user.setMoodTrackerMessage("Dear "+user.getUsername()+", from the mood entries you recorded last month," +
                        "it can be concluded that you had an excellent mood change");
            } else if (average > 3.0 && average <= 4.0) {
                user.setMoodTrackerMessage("Dear "+user.getUsername()+", from the mood entries you recorded last month," +
                        "it can be concluded that you had a very good mood change");
            } else if (average > 2.0 && average <= 3.0 ) {
                user.setMoodTrackerMessage("Dear "+user.getUsername()+", from the mood entries you recorded last month," +
                        "it can be concluded that you had a good mood change");
            } else if (average > 1.0 && average <= 2.0) {
                user.setMoodTrackerMessage("Dear "+user.getUsername()+", from the mood entries you recorded last month," +
                        "it can be concluded that you had a fair mood change");
            } else if (average > 0.0 && average <= 1.0){
                user.setMoodTrackerMessage("Dear "+user.getUsername()+", from the mood entries you recorded last month," +
                        "it can be concluded that you had a poor mood change");
            } else {
                user.setMoodTrackerMessage("Dear "+user.getUsername()+", No mood was available to track last month");
            }
            userService.saveUser(user);
            System.out.println(user.getMoodTrackerMessage());
        }

    }

    @Override
    public void plotMoodGraph() {
        AtomicInteger dayCounter = new AtomicInteger(1);
        List<AppUser> allUsers = userService.findAllUsers();
       MoodGraph moodGraph = new MoodGraph();
        for (AppUser user: allUsers) {
            List<Double> listOfRatings = user.getMoodRatings();
            listOfRatings.clear();
            user.getMoodGraphs().clear();
            userService.saveUser(user);
            user.getMoodTrackers().forEach(tracker -> listOfRatings.add(tracker.getRatings()));
            listOfRatings.forEach(rating ->{
                if(rating == 5.0){
                    moodGraph.setRate(100);
                } else if (rating == 4.0) {
                    moodGraph.setRate(80);
                } else if (rating == 3.0) {
                    moodGraph.setRate(60);
                } else if (rating == 2.0) {
                    moodGraph.setRate(40);
                } else if(rating == 1.0){
                    moodGraph.setRate(20);
                } else {
                    moodGraph.setRate(0);
                }
                moodGraph.setDayOfTheMonth(dayCounter.get());
                dayCounter.getAndIncrement();
                moodGraph.setOwnedBy(user.getUsername());
               moodGraphRepository.save(moodGraph);
               user.getMoodGraphs().add(moodGraph);
               userService.saveUser(user);
            });
        }
        //return moodGraph;
    }
}
