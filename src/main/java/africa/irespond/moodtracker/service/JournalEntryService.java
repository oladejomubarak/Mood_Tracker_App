package africa.irespond.moodtracker.service;

import africa.irespond.moodtracker.dto.EntryDto;
import africa.irespond.moodtracker.model.JournalEntry;

import java.util.List;

public interface JournalEntryService {
    JournalEntry createJournalEntry(EntryDto entryDto);
    JournalEntry createJournalEntryTwo(EntryDto entryDto);
    JournalEntry findJournalEntryById(Long entryId);
    JournalEntry updateJournalEntry(Long id, EntryDto entryDto);
    void deleteJournalEntry(Long id);

    List<JournalEntry> getAllJournalEntries();

    List<JournalEntry> findJournalEntryByTitleKeyword(String keyword);
    List <JournalEntry> findJournalEntryByDateCreated(String createdDate);
    List <JournalEntry> findJournalEntryByTitle(String entryTitle);

    List<JournalEntry> findJournalEntryByCategory(String category);

    List<JournalEntry> findJournalEntryByTitleKeywordForUser(String username,String keyword);
    List <JournalEntry> findJournalEntryByDateCreatedForUser(String username, String createdDate);
    List <JournalEntry> findJournalEntryByTitleForUser(String username,String entryTitle);

    List<JournalEntry> findJournalEntryByCategoryForUser(String username,String category);
    List<JournalEntry> findAllEntriesByUser(String username);
}
