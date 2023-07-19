package africa.irespond.moodtracker.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

@Data
@Entity
public class MoodGraph {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int rate;
    private int dayOfTheMonth;
    private String ownedBy;
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    private AppUser user;
}
