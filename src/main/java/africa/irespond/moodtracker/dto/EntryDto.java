package africa.irespond.moodtracker.dto;

import lombok.Data;

@Data
public class EntryDto {
    private String title;
    private String body;
    private String bodyWithText;
    private String bodyWithVoice;
    private String category;
    private String username;
}
