package africa.irespond.moodtracker.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Data
@Table(name = "app_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String username;
//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
//    @ToString.Exclude
//    private List<MoodGraph> moodGraphs;

    private String moodTrackerMessage;

    @ElementCollection(fetch = FetchType.EAGER)
    private Map<Integer, Double> moodRatings = new HashMap<>();

}
