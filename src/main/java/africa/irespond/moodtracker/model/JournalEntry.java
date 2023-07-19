package africa.irespond.moodtracker.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
@Entity
@Data
public class JournalEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String title;
    @Column(length = 30000)
    private String text;
    private String voiceUrl;
    private String createdOn;
    private String createdTime;
    private String updatedOn;
   // private String createdBy;
//    @ElementCollection
//    private Set<String> categories = new HashSet<>();
    private String category;
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    private AppUser user;
}
