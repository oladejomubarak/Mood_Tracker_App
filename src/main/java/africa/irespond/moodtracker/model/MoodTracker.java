package africa.irespond.moodtracker.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class MoodTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String dateTimeCreated;
    private String dateTimeUpdated;
    private String mood;
//    private SocialMoodInfluence socialMoodInfluence;
//    private WeatherMoodInfluence weatherMoodInfluence;
    @Column(length = 5000)
    private String comment;
    private double ratings;
    //private String owner;
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private AppUser user;
}
