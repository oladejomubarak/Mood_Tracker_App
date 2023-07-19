package africa.irespond.moodtracker.service;

import africa.irespond.moodtracker.model.EntryCategory;
import africa.irespond.moodtracker.repository.EntryCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class EntryCategoryServiceImpl implements EntryCategoryService{
    @Autowired
    private EntryCategoryRepository entryCategoryRepository;

    public List<EntryCategory> findAllCategoryClasses(){
        return entryCategoryRepository.findAll();
    }
   public void saveEntryCategory(EntryCategory entryCategory){
        entryCategoryRepository.save(entryCategory);
    }

    public Set<String> findAllCategories(){
        Set<String> allCategories = new HashSet<>();
        findAllCategoryClasses().forEach(entryCategory -> allCategories.add(entryCategory.getName()));
        return allCategories;
    }

    @Override
    public EntryCategory createCategory(String category) {
        EntryCategory entryCategory = new EntryCategory();
        entryCategory.setName(category);
        return entryCategoryRepository.save(entryCategory);
    }
}
