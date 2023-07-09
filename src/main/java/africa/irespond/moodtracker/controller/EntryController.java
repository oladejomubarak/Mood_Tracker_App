package africa.irespond.moodtracker.controller;

import africa.irespond.moodtracker.dto.EntryDto;
import africa.irespond.moodtracker.service.EntryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
