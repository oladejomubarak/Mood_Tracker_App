package africa.irespond.moodtracker.service;

import africa.irespond.moodtracker.model.EntryCategory;
import africa.irespond.moodtracker.repository.EntryCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class EntryCategoryServiceImpl implements EntryCategoryService{

    private final EntryCategoryRepository entryCategoryRepository;

    public List<EntryCategory> findAllCategoryClasses(){
        return entryCategoryRepository.findAll();
    }

    public Set<String> findAllCategories(){
        Set<String> allCategories = new HashSet<>();
        findAllCategoryClasses().forEach(entryCategory -> allCategories.add(entryCategory.getName()));
        return allCategories;
    }

    @Override
    public void createCategory(String category) {
        EntryCategory entryCategory = new EntryCategory();
        entryCategory.setName(category);
        entryCategoryRepository.save(entryCategory);
    }
}
