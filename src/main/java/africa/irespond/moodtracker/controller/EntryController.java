package africa.irespond.moodtracker.controller;

import africa.irespond.moodtracker.dto.EntryDto;
import africa.irespond.moodtracker.service.EntryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class EntryController {
    @Autowired
    private EntryServiceImpl entryService;

    @PostMapping("/create-entry")
    public ResponseEntity<?> createEntry(@RequestBody EntryDto entryDto){
        try{
            return ResponseEntity.ok(entryService.createEntry(entryDto));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/entry/{id}")
    public ResponseEntity<?> findEntry(@PathVariable Long id){
        try{
            return ResponseEntity.ok(entryService.findEntryById(id));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @PatchMapping("edit-entry/{id}")
    public ResponseEntity<?> editEntry(@PathVariable Long id, @RequestBody EntryDto entryDto){
        try{
            return ResponseEntity.ok(entryService.updateEntry(id, entryDto));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteEntry(@PathVariable Long id){
        entryService.deleteEntry(id);
        return ResponseEntity.ok("entry deleted");
    }
    @GetMapping("entry-by-keyword")
    public ResponseEntity<?> findEntryByKeyword(@RequestParam String keyword){
    return ResponseEntity.ok(entryService.findEntryByKeyword(keyword));
    }
    @GetMapping("entry-by-date")
    public ResponseEntity<?> findEntryByDate(@RequestParam String date){
        return ResponseEntity.ok(entryService.findEntryByKeyword(date));
    }
    @GetMapping("entry-by-title")
    public ResponseEntity<?> findEntryByTitle(@RequestParam String title){
        return ResponseEntity.ok(entryService.findEntryByKeyword(title));
    }
}
