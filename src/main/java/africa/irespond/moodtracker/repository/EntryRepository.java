package africa.irespond.moodtracker.repository;

import africa.irespond.moodtracker.model.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntryRepository extends JpaRepository<JournalEntry, Long> {
    List<JournalEntry> findJournalEntriesByTitle(String title);

    List<JournalEntry> findJournalEntriesByCreatedOn (String createdDate);

    List<JournalEntry> findJournalEntriesByCategoriesContainingIgnoreCase(String category);
}
