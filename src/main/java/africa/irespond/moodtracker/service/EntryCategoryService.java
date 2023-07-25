package africa.irespond.moodtracker.service;

import africa.irespond.moodtracker.model.EntryCategory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface EntryCategoryService {
    List<EntryCategory> findAllCategoryClasses();

    Set<String> findAllCategories();

    void createCategory(String category);

}
