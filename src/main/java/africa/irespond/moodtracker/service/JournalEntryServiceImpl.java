package africa.irespond.moodtracker.service;

import africa.irespond.moodtracker.dto.EntryDto;
import africa.irespond.moodtracker.model.EntryCategory;
import africa.irespond.moodtracker.model.JournalEntry;
import africa.irespond.moodtracker.model.AppUser;
import africa.irespond.moodtracker.repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class JournalEntryServiceImpl implements JournalEntryService {
    @Autowired
    private UserService userService;

    @Autowired
    private EntryCategoryService entryCategoryService;

    @Autowired
    private JournalEntryRepository entryRepository;
    @Autowired
    private ModelMapper modelMapper;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final String formattedDate = LocalDate.now().format(dateFormatter);

    @Override
    public JournalEntry createJournalEntry(EntryDto entryDto) {
        if(!entryCategoryService.findAllCategories().contains(entryDto.getCategory().toLowerCase())){
            entryCategoryService.createCategory(entryDto.getCategory().toLowerCase());
        }
        AppUser foundUser = userService.findUserByUsername(entryDto.getUsername());
        JournalEntry entry = new JournalEntry();
        entry.setTitle(entryDto.getTitle());
        entry.setCategory(entryCategoryService.getCategoryByName(entryDto.getCategory().toLowerCase()));
        entry.setText(entryDto.getText());
        entry.setVoiceUrl(entryDto.getVoiceUrl());
        entry.setCreatedOn(formattedDate);
        entry.setCreatedTime(LocalTime.now().toString());
        entry.setUpdatedOn(formattedDate);
        entry.setUser(foundUser);

        return entryRepository.save(entry);
    }

    @Override
    public JournalEntry createJournalEntryTwo(EntryDto entryDto) {
        if(!entryCategoryService.findAllCategories().contains(entryDto.getCategory().toLowerCase())){
            entryCategoryService.createCategory(entryDto.getCategory().toLowerCase());
        }
        AppUser foundUser = userService.findUserByUsername(entryDto.getUsername());
        JournalEntry entry = new JournalEntry();
        entry.setText(entryDto.getText());
        entry.setTitle(entryDto.getTitle());
        setDefaultTitle(entryDto, entry);
        entry.setVoiceUrl(entryDto.getVoiceUrl());
        entry.setCreatedOn(formattedDate);
        entry.setCreatedTime(LocalTime.now().toString());
        entry.setUpdatedOn(formattedDate);
        entry.setCategory(entryDto.getCategory().toLowerCase());
        entry.setUser(foundUser);
        return entry;
    }
    private void setDefaultTitle(EntryDto entryDto, JournalEntry entry) {
        if((entryDto.getTitle().equals("") || entryDto.getTitle() == null)
                && (!entryDto.getText().equals("") || entryDto.getText() != null)) {
            String[] words = entryDto.getText().split("\\s+");
            String firstWord = words[0];
            entry.setTitle(firstWord);
        }
    }

    @Override
    public JournalEntry findJournalEntryById(Long entryId) {

        return entryRepository.findById(entryId).orElseThrow(()-> new IllegalStateException("entry not found"));
    }

    @Override
    public JournalEntry updateJournalEntry(Long entryId, EntryDto entryDto) {
        JournalEntry foundEntry = findJournalEntryById(entryId);
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(entryDto, foundEntry);
        foundEntry.setUpdatedOn(formattedDate);
        entryCategoryService.findAllCategoryClasses().forEach(entryCategory -> {
            if(!entryCategory.getName().equalsIgnoreCase(entryDto.getCategory())) {
                entryCategoryService.createCategory(entryDto.getCategory());
                foundEntry.setCategory(entryCategory.getName().toLowerCase());
                entryRepository.save(foundEntry);
            }
        });
        entryRepository.save(foundEntry);
        return foundEntry;
    }

    @Override
    public void deleteJournalEntry(Long id) {
    entryRepository.deleteById(id);
    }
    @Override
    public List<JournalEntry> getAllJournalEntries() {
        return entryRepository.findAll();
    }
    @Override
    public List<JournalEntry> findAllEntriesByUser(String username) {
        AppUser foundUser = userService.findUserByUsername(username);
        return entryRepository.findJournalEntriesByUser(foundUser);
    }

    @Override
    public List<JournalEntry> findJournalEntryByTitleKeyword(String keyword) {
        boolean isValidKeyword = keyword.length() > 3;
        List <JournalEntry> foundEntries = new ArrayList<>();
        for (JournalEntry entry: getAllJournalEntries()) {
            if(isValidKeyword && entry.getTitle() != null && entry.getTitle().contains(keyword)) {
                foundEntries.add(entry);
            }
        }
        return foundEntries;
    }
    @Override
    public List<JournalEntry> findJournalEntryByTitleKeywordForUser(String username, String keyword) {
        boolean isValidKeyword = keyword.length() > 3;
        List <JournalEntry> foundEntries = new ArrayList<>();
        for (JournalEntry entry: findAllEntriesByUser(username)) {
            if(isValidKeyword && entry.getTitle() != null && entry.getTitle().contains(keyword)) {
                foundEntries.add(entry);
            }
        }
        return foundEntries;
    }

    @Override
    public List<JournalEntry> findJournalEntryByDateCreated(String createdDate) {
        return entryRepository.findJournalEntriesByCreatedOn(createdDate);
    }

    @Override
    public List<JournalEntry> findJournalEntryByDateCreatedForUser(String username, String createdDate) {
        List <JournalEntry> foundEntries = new ArrayList<>();
        List <JournalEntry> userEntries = findAllEntriesByUser(username);
        userEntries.forEach( entry -> {
            if(entry.getCreatedOn().equals(createdDate)) foundEntries.add(entry);
        });
        return foundEntries;
    }

    @Override
    public List<JournalEntry> findJournalEntryByTitle(String entryTitle) {
        return entryRepository.findJournalEntriesByTitleIgnoreCase(entryTitle);
    }

    @Override
    public List<JournalEntry> findJournalEntryByTitleForUser(String username, String entryTitle) {
        List <JournalEntry> foundEntries = new ArrayList<>();
        List <JournalEntry> userEntries = findAllEntriesByUser(username);
        userEntries.forEach( entry -> {
            if(entry.getTitle() != null && entry.getTitle().equalsIgnoreCase(entryTitle)) foundEntries.add(entry);
        });
        return foundEntries;
    }

    @Override
    public List<JournalEntry> findJournalEntryByCategory(String category) {
        List<JournalEntry> entryList = new ArrayList<>();
    getAllJournalEntries().forEach( journalEntry -> {
        if(journalEntry.getCategory() != null && journalEntry.getCategory().equalsIgnoreCase(category)) entryList.add(journalEntry);
    });
        return entryList;
    }

    @Override
    public List<JournalEntry> findJournalEntryByCategoryForUser(String username, String category) {
        List <JournalEntry> foundEntries = new ArrayList<>();
        List <JournalEntry> userEntries = findAllEntriesByUser(username);
        userEntries.forEach( entry -> {
            if(entry.getCategory() != null && entry.getCategory().equalsIgnoreCase(category)) foundEntries.add(entry);
        });
        return foundEntries;
    }

}
