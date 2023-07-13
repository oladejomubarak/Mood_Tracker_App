package africa.irespond.moodtracker.service;

import africa.irespond.moodtracker.dto.EntryDto;
import africa.irespond.moodtracker.model.Entry;
import africa.irespond.moodtracker.model.AppUser;
import africa.irespond.moodtracker.repository.EntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EntryServiceImpl implements EntryService{
    @Autowired
    private UserService userService;

    @Autowired
    private EntryRepository entryRepository;
    @Autowired
    private ModelMapper modelMapper;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final String formattedDate = LocalDate.now().format(dateFormatter);
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private final String formattedTime = LocalTime.now().format(timeFormatter);





    @Override
    public Entry createEntry(EntryDto entryDto) {
        String modifiedTitle = getTitleFormat(entryDto);

      AppUser foundUser = userService.findUserByUsername(entryDto.getUsername());


        Entry entry = new Entry();
        entry.setTitle(modifiedTitle);
        entry.getCategories().add(entryDto.getCategory());
        entry.setBodyWithText(entryDto.getBodyWithText());
        entry.setBodyWithVoice(entryDto.getBodyWithVoice());
        entry.setCreatedDate(formattedDate);
        entry.setCreatedTime(formattedTime);
        entry.setUpdatedDate(formattedDate);
        entry.setUpdatedTime(formattedTime);
        entry.setCreatedBy(foundUser.getUsername());
        Entry savedEntry = entryRepository.save(entry);
        foundUser.getEntries().add(savedEntry);
        userService.saveUser(foundUser);
        return savedEntry;
    }

    private String getTitleFormat(EntryDto entryDto) {
        String[] words = entryDto.getTitle().split(" ");

        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            String firstLetter = word.substring(0, 1).toUpperCase();
            String restOfWord = word.substring(1);
            String capitalizedWord = firstLetter + restOfWord;
            sb.append(capitalizedWord).append(" ");
        }
        return sb.toString().trim();
    }

    @Override
    public Entry findEntryById(Long entryId) {

        return entryRepository.findById(entryId).orElseThrow(()-> new IllegalStateException("entry not found"));
    }

    @Override
    public Entry updateEntry(Long entryId, EntryDto entryDto) {
        Entry foundEntry = findEntryById(entryId);
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(entryDto, foundEntry);
        foundEntry.setUpdatedDate(formattedDate);
        foundEntry.setUpdatedTime(formattedTime);
        entryRepository.save(foundEntry);
        return foundEntry;
    }

    @Override
    public void deleteEntry(Long id) {
    entryRepository.deleteById(id);
    }
    @Override
    public List<Entry> getAllEntries() {
        return entryRepository.findAll();
    }

    @Override
    public List<Entry> findEntryByKeyword(String keyword) {
        boolean isValidKeyword = keyword.length() > 3;
        List <Entry> foundEntries = new ArrayList<>();
        for (Entry entry: getAllEntries()) {
            if(isValidKeyword && (entry.getTitle().contains(keyword) ||
                    entry.getBodyWithText().contains(keyword) ||
                    entry.getBodyWithVoice().contains(keyword)))
                foundEntries.add(entry);
        }
        return foundEntries;
    }

    @Override
    public List<Entry> findEntryByDateCreated(String createdDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        var formattedDate = LocalDateTime.parse(createdDate, dateTimeFormatter);
        String dateInString = formattedDate.toString();
        List <Entry> foundEntries = new ArrayList<>();
        for ( Entry entry: getAllEntries()) {
            if(entry.getCreatedDate().equals(dateInString))
                foundEntries.add(entry);
        }
        return foundEntries;
    }

    @Override
    public List<Entry> findEntryByTitle(String entryTitle) {
        List <Entry>foundEntries = new ArrayList<>();
        for (Entry entry: getAllEntries()
        ) { if (entry.getTitle().equalsIgnoreCase(entryTitle)) foundEntries.add(entry);

        }
        return foundEntries;
    }

}
