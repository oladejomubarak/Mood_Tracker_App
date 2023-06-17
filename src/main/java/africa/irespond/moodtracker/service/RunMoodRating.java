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
   // @Scheduled(cron = "0 */3 * * * *") // Runs every 30 minutes for testing
   //@Scheduled(cron = "0 */3 * * * *") // Runs monthly
    public void runMonthlyRatingSchedule(){
        String moodSummary = moodTrackerService.calculateMonthlyMoodRate();
        System.out.println(moodSummary);
        System.out.println("weekly rating scheduler ran");
    }
    // @Scheduled(cron = "0 */3 * * * *") // Runs every 2 hours for testing
    //@Scheduled(cron = "0 */3 * * * *") // Runs annually
    public void runAnnualRatingSchedule(){
        String moodSummary = moodTrackerService.calculateAnnualMoodRate();
        System.out.println(moodSummary);
        System.out.println("weekly rating scheduler ran");
    }
}
