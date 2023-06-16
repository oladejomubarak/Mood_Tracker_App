package africa.irespond.moodtracker.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Tracker {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private LocalDate todayDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private Mood mood;
    private SocialMoodInfluence socialMoodInfluence;
    private WeatherMoodInfluence weatherMoodInfluence;
    @Column(length = 5000)
    private String comment;
}
