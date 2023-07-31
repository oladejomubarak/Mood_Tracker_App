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
        List<MoodEntry> moodEntryList = findAllMoodEntriesForUser(moodDto.getUsername());

        for (MoodEntry moodEntry:moodEntryList) {
            if (moodEntry.getCreatedOn().equals(formattedDate)) throw new RuntimeException(
                    "You have already created mood entry for today, wait till tomorrow");
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
        for (Map.Entry<Integer, Double> entrySet : foundUser.getMoodRatings().entrySet()) {
            if (entrySet.getKey().equals(day)) foundUser.getMoodRatings().remove(entrySet.getKey(), entrySet.getValue());
        }
        foundUser.getMoodRatings().put(day, moodEntry.getRatings());
        userService.saveUser(foundUser);
        return moodEntry;
    }

    @Override
    public MoodEntry findMood(Long moodTrackerId) {
        return trackerRepository.findById(moodTrackerId).orElseThrow(()-> new RuntimeException("Mood entry not found"));
    }

    @Override
    public MoodEntry editMoodEntry(Long moodTrackerId, MoodDto moodDto) {
        MoodEntry foundTracker = findMood(moodTrackerId);
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(moodDto, foundTracker);
        foundTracker.setUpdatedOn(formattedDate);
        trackerRepository.save(foundTracker);
        return foundTracker;
    }

    @Override
    public void deleteMoodEntry(Long moodTrackerId) {
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
            user.getMoodRatings().clear();
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

    @Override
    public void plotMoodGraph() {
        List<AppUser> allUsers = userService.findAllUsers();
        for (AppUser user: allUsers) {
            MoodGraph moodGraph = new MoodGraph();
            Map<Integer, Double> moodGraphRating = new HashMap<>( user.getMoodRatings());
            moodGraph.setGraph(moodGraphRating);
            moodGraph.setUser(user);
            moodGraphRepository.save(moodGraph);
        }
        System.out.println(allUsers);
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
