package africa.irespond.moodtracker.dto;

import africa.irespond.moodtracker.model.Mood;
import africa.irespond.moodtracker.model.SocialMoodInfluence;
import africa.irespond.moodtracker.model.WeatherMoodInfluence;
import lombok.Data;

@Data
public class MoodDto {
    private Mood mood;
    private SocialMoodInfluence socialMoodInfluence;
    private WeatherMoodInfluence weatherMoodInfluence;
    private String comment;
}
