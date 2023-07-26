package africa.irespond.moodtracker.controller;

import africa.irespond.moodtracker.dto.EntryDto;
import africa.irespond.moodtracker.model.EntryCategory;
import africa.irespond.moodtracker.service.EntryCategoryService;
import africa.irespond.moodtracker.service.JournalEntryService;
import africa.irespond.moodtracker.service.JournalEntryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class JournalEntryController {
    @Autowired
    private JournalEntryService entryService;

    @Autowired
    private EntryCategoryService entryCategoryService;

    @PostMapping("/create-entry")
    public ResponseEntity<?> createEntry(@RequestBody EntryDto entryDto){
        try{
            return ResponseEntity.ok(entryService.createJournalEntry(entryDto));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create-entry2")
    public ResponseEntity<?> createEntry2(@RequestBody EntryDto entryDto){
        try{
            return ResponseEntity.ok(entryService.createJournalEntryTwo(entryDto));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/entry/{id}")
    public ResponseEntity<?> findEntry(@PathVariable Long id){
        try{
            return ResponseEntity.ok(entryService.findJournalEntryById(id));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("edit-entry/{id}")
    public ResponseEntity<?> editEntry(@PathVariable Long id, @RequestBody EntryDto entryDto){
        try{
            return ResponseEntity.ok(entryService.updateJournalEntry(id, entryDto));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("delete-entry/{id}")
    public ResponseEntity<?> deleteEntry(@PathVariable Long id){
        entryService.deleteJournalEntry(id);
        return ResponseEntity.ok("entry deleted");
    }

    @GetMapping("all-entries")
    public ResponseEntity<?> getAllEntries(@RequestParam String keyword){
        return ResponseEntity.ok(entryService.getAllJournalEntries());
    }

    @GetMapping("/entries-by-user")
    public ResponseEntity<?> findAllEntriesForUser(@RequestParam String username){
        try{
            return ResponseEntity.ok(entryService.findAllEntriesByUser(username));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("entry-by-keyword")
    public ResponseEntity<?> findEntryByKeyword(@RequestParam String keyword){
    return ResponseEntity.ok(entryService.findJournalEntryByTitleKeyword(keyword));
    }

    @GetMapping("/entries-by-keyword/{username}")
    public ResponseEntity<?> findEntriesByKeywordForUser(@PathVariable String username, @RequestParam String keyword){
        try{
            return ResponseEntity.ok(entryService.findJournalEntryByTitleKeywordForUser(username, keyword));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("entry-by-date")
    public ResponseEntity<?> findEntryByDate(@RequestParam String date){
        return ResponseEntity.ok(entryService.findJournalEntryByDateCreated(date));
    }

    @GetMapping("/entries-by-date/{username}")
    public ResponseEntity<?> findEntriesByDateForUser(@PathVariable String username, @RequestParam String date){
        try{
            return ResponseEntity.ok(entryService.findJournalEntryByDateCreatedForUser(username, date));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("entry-by-title")
    public ResponseEntity<?> findEntryByTitle(@RequestParam String title){
        return ResponseEntity.ok(entryService.findJournalEntryByTitle(title));
    }

    @GetMapping("/entries-by-title/{username}")
    public ResponseEntity<?> findEntriesByTitleForUser(@PathVariable String username, @RequestParam String title){
        try{
            return ResponseEntity.ok(entryService.findJournalEntryByTitleForUser(username, title));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("entry-by-category")
    public ResponseEntity<?> findEntryByCategory(@RequestParam String category){
        return ResponseEntity.ok(entryService.findJournalEntryByCategory(category));
    }

    @GetMapping("/entries-by-category/{username}")
    public ResponseEntity<?> findEntriesByCategoryForUser(@PathVariable String username, @RequestParam String category){
        try{
            return ResponseEntity.ok(entryService.findJournalEntryByCategoryForUser(username, category));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create-category")
    public ResponseEntity<?> createCategory(@RequestParam String category){
        try{
            return ResponseEntity.ok(entryCategoryService.createCategory(category));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("categories")
    public ResponseEntity<?> findAllCategories(){
        return ResponseEntity.ok(entryCategoryService.findAllCategories());
    }

    @GetMapping("category-classes")
    public ResponseEntity<?> findAllCategoryClasses(){
        return ResponseEntity.ok(entryCategoryService.findAllCategoryClasses());
    }
}
