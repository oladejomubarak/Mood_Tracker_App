package africa.irespond.moodtracker.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class MonthlyMoodTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String todayDate;
    private Mood mood;
    private SocialMoodInfluence socialMoodInfluence;
    private WeatherMoodInfluence weatherMoodInfluence;
    @Column(length = 5000)
    private String comment;
    private double ratings;
}
