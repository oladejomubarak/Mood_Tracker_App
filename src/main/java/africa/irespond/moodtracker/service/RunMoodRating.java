package africa.irespond.moodtracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class RunMoodRating {
    @Autowired
    private MoodTrackerServiceImpl moodTrackerService;
}
