package africa.irespond.moodtracker.service;

import africa.irespond.moodtracker.model.EntryCategory;
import africa.irespond.moodtracker.repository.EntryCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EntryCategoryService {
    @Autowired
    private EntryCategoryRepository entryCategoryRepository;

    List<EntryCategory> findAllCategories(){
        return entryCategoryRepository.findAll();
    }
    void saveEntryCategory(EntryCategory entryCategory){
        entryCategoryRepository.save(entryCategory);
    }
}
