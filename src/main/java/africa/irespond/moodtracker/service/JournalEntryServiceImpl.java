package africa.irespond.moodtracker.service;

import africa.irespond.moodtracker.dto.EntryDto;
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
import java.util.Objects;

@Service
@Slf4j
public class JournalEntryServiceImpl implements JournalEntryService {
    @Autowired
    private UserService userService;

    @Autowired
    private JournalEntryRepository entryRepository;
    @Autowired
    private ModelMapper modelMapper;
    //private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final String formattedDate = LocalDate.now().toString();
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private final String formattedTime = LocalTime.now().format(timeFormatter);





    @Override
    public JournalEntry createJournalEntry(EntryDto entryDto) {
        //String modifiedTitle = getTitleFormat(entryDto);

      AppUser foundUser = userService.findUserByUsername(entryDto.getUsername());
        JournalEntry entry = new JournalEntry();
        entry.setTitle(entryDto.getTitle());
        entry.getCategories().add(entryDto.getCategory());
        entry.setText(entryDto.getText());
        entry.setVoiceUrl(entryDto.getVoiceUrl());
        entry.setCreatedOn(formattedDate);
        entry.setCreatedTime(formattedTime);
        entry.setUpdatedOn(formattedDate);
        JournalEntry savedEntry = entryRepository.save(entry);
        //generateTitle(foundUser, savedEntry);
        foundUser.getEntries().add(savedEntry);
        userService.saveUser(foundUser);
        return savedEntry;
    }

    @Override
    public JournalEntry createJournalEntryTwo(EntryDto entryDto) {

        AppUser foundUser = userService.findUserByUsername(entryDto.getUsername());
        JournalEntry entry = new JournalEntry();
        entry.setTitle(entryDto.getTitle());
        entry.getCategories().add(entryDto.getCategory());
        entry.setText(entryDto.getText());
        entry.setVoiceUrl(entryDto.getVoiceUrl());
        entry.setCreatedOn(formattedDate);
        entry.setCreatedTime(formattedTime);
        entry.setUpdatedOn(formattedDate);
        JournalEntry savedEntry = entryRepository.save(entry);
        generateTitle(foundUser, savedEntry);
        foundUser.getEntries().add(savedEntry);
        userService.saveUser(foundUser);
        return savedEntry;
    }

    private void generateTitle(AppUser foundUser, JournalEntry savedEntry) {
        if((Objects.equals(savedEntry.getTitle(), "") || savedEntry.getTitle() == null)
                && savedEntry.getText() != null ){
             String[] words = savedEntry.getText().split("\\s+");
             String firstWord = words[0];
             savedEntry.setTitle(firstWord);
             entryRepository.save(savedEntry);
            foundUser.getEntries().add(savedEntry);
            userService.saveUser(foundUser);
            }
    }

//    private String getTitleFormat(EntryDto entryDto) {
//        String[] words = entryDto.getTitle().split(" ");
//
//        StringBuilder sb = new StringBuilder();
//        for (String word : words) {
//            String firstLetter = word.substring(0, 1).toUpperCase();
//            String restOfWord = word.substring(1);
//            String capitalizedWord = firstLetter + restOfWord;
//            sb.append(capitalizedWord).append(" ");
//        }
//        return sb.toString().trim();
//    }

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
    public List<JournalEntry> findJournalEntryByTitleKeyword(String keyword) {
        boolean isValidKeyword = keyword.length() > 3;
        List <JournalEntry> foundEntries = new ArrayList<>();
        for (JournalEntry entry: getAllJournalEntries()) {
            if(isValidKeyword && entry.getTitle().contains(keyword)) {
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
    public List<JournalEntry> findJournalEntryByTitle(String entryTitle) {

        return entryRepository.findJournalEntriesByTitleIgnoreCase(entryTitle);
    }

    @Override
    public List<JournalEntry> findJournalEntryByCategory(String category) {
        return entryRepository.findJournalEntriesByCategoriesContainingIgnoreCase(category);
    }

}
