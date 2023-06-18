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
    //@Scheduled(cron = "0 */3 * * * *") // Runs every 3 minutes for testing
    //@Scheduled(cron = "0 */3 * * * *") // Runs weekly
    public void runWeeklyRatingSchedule(){
        String moodSummary = moodTrackerService.calculateWeeklyMoodRate();
        System.out.println(moodSummary);
        System.out.println("weekly rating scheduler ran");
    }
    @Scheduled(cron = "0 */20 * * * *") // Runs every 20 minutes for testing
   //@Scheduled(cron = "0 0 0 1 * *") // Runs monthly
    public void runMonthlyRatingSchedule(){
        String moodSummary = moodTrackerService.calculateMonthlyMoodRate();
        System.out.println(moodSummary);
        System.out.println("monthly rating scheduler ran");
    }
     @Scheduled(cron = "0 0 */1 * * *") // Runs every 1 hour for testing
    //@Scheduled(cron = "0 0 0 1 1 *") // Runs annually
    public void runAnnualRatingSchedule(){
        String moodSummary = moodTrackerService.calculateAnnualMoodRate();
        System.out.println(moodSummary);
        System.out.println("annual rating scheduler ran");
    }
}
