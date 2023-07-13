package africa.irespond.moodtracker.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.util.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;



@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class RunMoodRating {
    @Autowired
    private MoodTrackerServiceImpl moodTrackerService;

    @Scheduled(cron = "0 */5 * * * *") // Runs every 5 minutes for testing
   //@Scheduled(cron = "0 0 0 1 * *") // Runs monthly
    public void runMonthlyRatingSchedule(){
      moodTrackerService.summarizeMoodRating();
        System.out.println("monthly rating scheduler ran");
    }
}
