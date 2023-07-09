package africa.irespond.moodtracker.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
@Entity
@Data
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String title;
    @Column(length = 30000)
    private String bodyWithText;
    @Column(length = 30000)
    private String bodyWithVoice;
    private String createdDateAndTime;
    private String updatedDateAndTime;
    private String createdBy;
    @ElementCollection
    private Set<String> categories = new HashSet<>();
}
