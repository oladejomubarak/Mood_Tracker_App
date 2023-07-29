package africa.irespond.moodtracker.service;

import africa.irespond.moodtracker.dto.MoodDto;
import africa.irespond.moodtracker.model.*;
import africa.irespond.moodtracker.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
@RequiredArgsConstructor
public class MoodEntryServiceImpl implements MoodEntryService {

    private final MoodEntryRepository trackerRepository;

    private final MoodGraphRepository moodGraphRepository;

    private final UserServiceImpl userService;

    private final ModelMapper modelMapper;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final String formattedDate = LocalDate.now().format(dateFormatter);



    @Override
    public MoodEntry  createMood(MoodDto moodDto) {
        AppUser foundUser = userService.findUserByUsername(moodDto.getUsername());
        LocalDate date = LocalDate.parse(formattedDate, dateFormatter);
        int day = date.getDayOfMonth();
        Mood mood = Mood.valueOf(moodDto.getMood().toUpperCase());


        for (Integer presentDay: foundUser.getMoodRatings().keySet()) {
            if(presentDay.equals(day)) throw new RuntimeException("You have already created mood entry for today" +
                    "wait till tomorrow");
        }



        MoodEntry moodEntry = new MoodEntry();
        moodEntry.setMood(Mood.valueOf(mood.getStringValue()));
        moodEntry.setRatings(mood.getDoubleValue());
        moodEntry.setComment(moodDto.getComment());
        moodEntry.setCreatedOn(formattedDate);
        moodEntry.setUpdatedOn(formattedDate);
        moodEntry.setCreatedAt(LocalTime.now().toString());
        // moodEntry.setOwner(foundUser.getUsername());
        moodEntry.setUser(foundUser);
        trackerRepository.save(moodEntry);
        Map<Integer, Double> moodRatings = new HashMap<>();
        moodRatings.put(day, moodEntry.getRatings());
        foundUser.setMoodRatings(moodRatings);
        userService.saveUser(foundUser);
        return moodEntry;
    }

    @Override
    public MoodEntry findMood(Long moodTrackerId) {

        return trackerRepository.findById(moodTrackerId).orElseThrow(()-> new RuntimeException("Mood entry not found"));
    }

    @Override
    public MoodEntry editMoodTracker(Long moodTrackerId, MoodDto moodDto) {
        MoodEntry foundTracker = findMood(moodTrackerId);
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(moodDto, foundTracker);
        foundTracker.setUpdatedOn(formattedDate);
        trackerRepository.save(foundTracker);
        return foundTracker;
    }

    @Override
    public void deleteMoodTracker(Long moodTrackerId) {
        MoodEntry foundTracker = findMood(moodTrackerId);
        trackerRepository.delete(foundTracker);
    }

    @Override
    public List<MoodEntry> findAllMoodEntriesForUser(String username) {
        AppUser foundUser = userService.findUserByUsername(username);
        return trackerRepository.findMoodEntriesByUser(foundUser);
    }

    @Override
    public List<MoodEntry> findAllMoodEntriesForUserByDate(String username, String date) {
        AppUser foundUser = userService.findUserByUsername(username);
        return trackerRepository.findMoodEntriesByUserAndCreatedOn(foundUser, date);
    }

    @Override
    public List<MoodEntry> findAllMoodEntriesForUserByMood(String username, String mood) {
        AppUser foundUser = userService.findUserByUsername(username);
        Mood moodValue = Mood.valueOf(mood.toUpperCase());
        return trackerRepository.findMoodEntriesByUserAndMood(foundUser, moodValue);
    }

    @Override
    public void summarizeMoodRating() {
        for (AppUser user: userService.findAllUsers()) {
            List<Double> listOfRatings = new ArrayList<>(user.getMoodRatings().values());
            double average =  listOfRatings.stream()
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0.0);
            listOfRatings.clear();
            userService.saveUser(user);

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

//    @Override
//    public void plotMoodGraph() {
//        AtomicInteger dayCounter = new AtomicInteger(1);
//        List<AppUser> allUsers = userService.findAllUsers();
//        for (AppUser user: allUsers) {
//            user.getMoodRatings().forEach(rating ->{
//                MoodGraph moodGraph = new MoodGraph();
//                if(rating == 5.0){
//                    moodGraph.setRate(100);
//                } else if (rating == 4.0) {
//                    moodGraph.setRate(80);
//                } else if (rating == 3.0) {
//                    moodGraph.setRate(60);
//                } else if (rating == 2.0) {
//                    moodGraph.setRate(40);
//                } else if(rating == 1.0){
//                    moodGraph.setRate(20);
//                } else {
//                    moodGraph.setRate(0);
//                }
//                moodGraph.setUser(user);
//                moodGraph.setDayOfTheMonth(dayCounter.get());
//                dayCounter.getAndIncrement();
//               moodGraphRepository.save(moodGraph);
//            });
//        }
//        //return moodGraph;
//    }

    @Override
    public void plotMoodGraph() {

    }

    @Override
    public List<MoodGraph> findGraphsByUser(String username) {

        return moodGraphRepository.findMoodGraphsByUser(userService.findUserByUsername(username));
    }

    @Override
    public List<MoodGraph> findAllMoodGraphs() {
        return moodGraphRepository.findAll();
    }
}
