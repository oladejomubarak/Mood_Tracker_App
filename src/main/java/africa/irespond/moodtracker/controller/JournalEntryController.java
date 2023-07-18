package africa.irespond.moodtracker.controller;

import africa.irespond.moodtracker.dto.EntryDto;
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

    @PostMapping("/create-entry")
    public ResponseEntity<?> createEntry(@RequestBody EntryDto entryDto){
        try{
            return ResponseEntity.ok(entryService.createJournalEntry(entryDto));
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

    @GetMapping("entry-by-keyword")
    public ResponseEntity<?> findEntryByKeyword(@RequestParam String keyword){
    return ResponseEntity.ok(entryService.findJournalEntryByTitleKeyword(keyword));
    }
    @GetMapping("entry-by-date")
    public ResponseEntity<?> findEntryByDate(@RequestParam String date){
        return ResponseEntity.ok(entryService.findJournalEntryByDateCreated(date));
    }
    @GetMapping("entry-by-title")
    public ResponseEntity<?> findEntryByTitle(@RequestParam String title){
        return ResponseEntity.ok(entryService.findJournalEntryByTitle(title));
    }
    @GetMapping("entry-by-category")
    public ResponseEntity<?> findEntryByCategory(@RequestParam String category){
        return ResponseEntity.ok(entryService.findJournalEntryByCategory(category));
    }
    @GetMapping("/entries-by-user")
    public ResponseEntity<?> findAllEntriesForUser(@RequestParam String username){
        try{
            return ResponseEntity.ok(entryService.findAllEntriesByUser(username));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
