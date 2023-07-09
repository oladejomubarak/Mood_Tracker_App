package africa.irespond.moodtracker.dto;

import lombok.Data;

@Data
public class MoodDto {
    private String mood;
//    private SocialMoodInfluence socialMoodInfluence;
//    private WeatherMoodInfluence weatherMoodInfluence;
    private String comment;
    private String owner;
}
