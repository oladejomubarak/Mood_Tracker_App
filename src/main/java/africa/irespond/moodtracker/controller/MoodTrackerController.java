package africa.irespond.moodtracker.controller;

import africa.irespond.moodtracker.dto.MoodDto;
import africa.irespond.moodtracker.service.MoodTrackerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class MoodTrackerController {
    @Autowired
    private MoodTrackerServiceImpl moodTrackerService;
    @PostMapping("/create-mood")
    public ResponseEntity<?> createMoodTracker(@RequestBody MoodDto moodDto){
        return new ResponseEntity<>(moodTrackerService.createMood(moodDto), HttpStatus.OK);
    }
    @GetMapping("/find-mood/{moodTrackerId}")
    public ResponseEntity<?> getMood(@ PathVariable Long moodTrackerId){
        return ResponseEntity.ok(moodTrackerService.findMood(moodTrackerId));
    }

}
