package africa.irespond.moodtracker.service;

import africa.irespond.moodtracker.dto.EntryDto;
import africa.irespond.moodtracker.model.Entry;

import java.util.List;

public interface EntryService {
    Entry createEntry(EntryDto entryDto);
    Entry findEntryById(Long entryId);
    Entry updateEntry(Long id, EntryDto entryDto);
    void deleteEntry(Long id);

    List<Entry> getAllEntries();

    List<Entry> findEntryByKeyword(String keyword);
    List <Entry> findEntryByDateCreated(String createdDate);
    List <Entry> findEntryByTitle(String entryTitle);
}
