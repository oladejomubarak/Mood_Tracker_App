package africa.irespond.moodtracker.dto;

import africa.irespond.moodtracker.model.SocialMoodInfluence;
import africa.irespond.moodtracker.model.WeatherMoodInfluence;
import lombok.Data;

@Data
public class MoodDto {
    private String mood;
    private String socialMoodInfluence;
    private String weatherMoodInfluence;
    private String comment;
}
