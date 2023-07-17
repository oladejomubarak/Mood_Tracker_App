package africa.irespond.moodtracker.repository;

import africa.irespond.moodtracker.model.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {
    List<JournalEntry> findJournalEntriesByTitleIgnoreCase(String title);

    List<JournalEntry> findJournalEntriesByCreatedOn (String createdDate);

    List<JournalEntry> findJournalEntriesByCategoriesContainingIgnoreCase(String category);
}
