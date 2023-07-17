package africa.irespond.moodtracker.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EntryDto {
    private String title;
    private String text;
    private String voiceUrl;
    private String category;
    @NotBlank
    private String username;
}
