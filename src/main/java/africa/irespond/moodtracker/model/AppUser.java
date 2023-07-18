package africa.irespond.moodtracker.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "app_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String username;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    @ToString.Exclude
    private List<MoodTracker> moodTrackers;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    @ToString.Exclude
    private List<JournalEntry> entries;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    @ToString.Exclude
    private List<MoodGraph> moodGraphs;
    private String moodTrackerMessage;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Double> moodRatings = new ArrayList<>();

//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
//    @ToString.Exclude
//    private List<DailyMoodTracker> dailyMoodTrackers;
//    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
//    @ToString.Exclude
//    private List<WeeklyMoodTracker> weeklyMoodTrackers;
//    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
//    @ToString.Exclude
//    private List<MonthlyMoodTracker> monthlyMoodTrackers;
//
//    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
//    @ToString.Exclude
//    private List<AnnualMoodTracker> annualMoodTrackers;

}
