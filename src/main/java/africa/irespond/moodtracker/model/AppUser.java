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
    private List<MoodTracker> moodTrackers = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    @ToString.Exclude
    private List<DailyMoodTracker> dailyMoodTrackers = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    @ToString.Exclude
    private List<WeeklyMoodTracker> weeklyMoodTrackers = new ArrayList<>();
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    @ToString.Exclude
    private List<MonthlyMoodTracker> monthlyMoodTrackers = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    @ToString.Exclude
    private List<AnnualMoodTracker> annualMoodTrackers = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    @ToString.Exclude
    private List<Entry> entries = new ArrayList<>();

    private String moodTrackerMessage;
}
