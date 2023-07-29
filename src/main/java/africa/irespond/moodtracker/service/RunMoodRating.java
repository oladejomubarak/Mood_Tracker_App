package africa.irespond.moodtracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class RunMoodRating {
    private final MoodEntryServiceImpl moodTrackerService;

    @Scheduled(cron = "0 0/3 * * * ?") // Runs every 3 minutes for testing
    //@Scheduled(cron = "0 0 0 1 * ?") // Runs monthly (at 12:00am of every first day of the month)
    public void runMoodGraph(){
        moodTrackerService.plotMoodGraph();
        System.out.println("mood graph scheduler ran");
    }

    @Scheduled(cron = "0 0/4 * * * ?") // Runs every 4 minutes for testing
   //@Scheduled(cron = "0 2 0 1 * ?") // Runs monthly (at 12:02am of every first day of the month)
    public void runMonthlyRatingSchedule(){
      moodTrackerService.summarizeMoodRating();
        System.out.println("monthly rating scheduler ran");
    }


}
