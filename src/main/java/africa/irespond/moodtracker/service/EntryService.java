package africa.irespond.moodtracker.service;

import africa.irespond.moodtracker.dto.EntryDto;
import africa.irespond.moodtracker.model.JournalEntry;

import java.util.List;

public interface EntryService {
    JournalEntry createEntry(EntryDto entryDto);
    JournalEntry findEntryById(Long entryId);
    JournalEntry updateEntry(Long id, EntryDto entryDto);
    void deleteEntry(Long id);

    List<JournalEntry> getAllEntries();

    List<JournalEntry> findEntryByKeyword(String keyword);
    List <JournalEntry> findEntryByDateCreated(String createdDate);
    List <JournalEntry> findEntryByTitle(String entryTitle);
}
