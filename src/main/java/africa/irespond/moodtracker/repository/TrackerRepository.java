package africa.irespond.moodtracker.repository;

import africa.irespond.moodtracker.model.Tracker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface TrackerRepository extends JpaRepository<Tracker, Long> {
    Optional<Tracker> findTrackerByTodayDate(LocalDate date);
}
