package africa.irespond.moodtracker.repository;

import africa.irespond.moodtracker.model.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntryRepository extends JpaRepository<Entry, Long> {
    List<Entry> findEntryByTitleIgnoreCase(String title);

    List<Entry> findEntryByCreatedDate(String createdDate);
}
