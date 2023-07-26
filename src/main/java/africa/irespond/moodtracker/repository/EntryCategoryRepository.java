package africa.irespond.moodtracker.repository;

import africa.irespond.moodtracker.model.EntryCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryCategoryRepository extends JpaRepository<EntryCategory, Long> {
    EntryCategory findByNameIgnoreCase(String categoryName);

}
