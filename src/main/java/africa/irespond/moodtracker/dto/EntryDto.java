package africa.irespond.moodtracker.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EntryDto {
    private String title;
    private String body;
    private String bodyWithText;
    private String bodyWithVoice;
    private String category;
    @NotBlank
    private String username;
}
