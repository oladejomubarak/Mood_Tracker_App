package africa.irespond.moodtracker.repository;

import africa.irespond.moodtracker.model.AppUser;
import africa.irespond.moodtracker.model.MoodGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoodGraphRepository extends JpaRepository<MoodGraph, Long> {
    List<MoodGraph> findMoodGraphsByUser(AppUser user);

}
