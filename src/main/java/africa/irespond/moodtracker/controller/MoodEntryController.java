package africa.irespond.moodtracker.controller;

import africa.irespond.moodtracker.dto.MoodDto;
import africa.irespond.moodtracker.service.MoodEntryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class MoodEntryController {
    @Autowired
    private MoodEntryServiceImpl moodTrackerService;
    @PostMapping("/create-mood")
    public ResponseEntity<?> createMoodTracker(@RequestBody MoodDto moodDto){
        try{
            return new ResponseEntity<>(moodTrackerService.createMood(moodDto), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/find-mood/{moodTrackerId}")
    public ResponseEntity<?> getMood(@PathVariable Long moodTrackerId){
        return ResponseEntity.ok(moodTrackerService.findMood(moodTrackerId));
    }
    @PatchMapping("/edit-mood{moodId}")
    public ResponseEntity<?> editMood(@PathVariable Long moodId, MoodDto moodDto){
        return ResponseEntity.ok(moodTrackerService.editMoodTracker(moodId, moodDto));
    }
    @DeleteMapping("delete-mood")
    public ResponseEntity<?> deleteMood(@RequestParam Long moodTrackerId){
        moodTrackerService.deleteMoodTracker(moodTrackerId);
        return ResponseEntity.ok("mood tracker deleted");
    }
    @GetMapping("trackers-by-username/{username}")
    public ResponseEntity<?> findTrackersByUsername(@PathVariable String username){
        return ResponseEntity.ok(moodTrackerService.findAllMoodEntriesForUser(username));
    }
    @GetMapping("/trackers-by-mood/{username}")
    public ResponseEntity<?> findEntriesByCategoryForUser(@PathVariable String username, @RequestParam String mood){
        try{
            return ResponseEntity.ok(moodTrackerService.findAllMoodEntriesForUserByMood(username, mood));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/trackers-by-date/{username}")
    public ResponseEntity<?> findEntriesByDateForUser(@PathVariable String username, @RequestParam String date){
        try{
            return ResponseEntity.ok(moodTrackerService.findAllMoodEntriesForUserByDate(username, date));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/graphs-by-user/{username}")
    public ResponseEntity<?> findAllMoodGraphsByUser(@PathVariable String username){
        try{
            return ResponseEntity.ok(moodTrackerService.findGraphsByUser(username));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all-graphs")
    public ResponseEntity<?> findAllMoodGraphs() {
        return ResponseEntity.ok(moodTrackerService.findAllMoodGraphs());
    }
}
