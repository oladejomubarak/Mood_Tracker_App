package africa.irespond.moodtracker.service;

import africa.irespond.moodtracker.model.MoodGraph;
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

    @Scheduled(cron = "0 0/30 * * * ?") // Runs every 30 minutes for testing
   //@Scheduled(cron = "0 0 0 1 * ?") // Runs monthly (at 12am of every first day of the month)
    public void runMonthlyRatingSchedule(){
      moodTrackerService.summarizeMoodRating();
        System.out.println("monthly rating scheduler ran");
    }
    @Scheduled(cron = "0 0/3 * * * ?") // Runs every 3 minutes for testing
    //@Scheduled(cron = "0 2 0 1 * ?") // Runs monthly (at 12:02am of every first day of the month)
    public void runMoodGraph(){
        moodTrackerService.plotMoodGraph();
        System.out.println("mood graph scheduler ran");
    }

}
