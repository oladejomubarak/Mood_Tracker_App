package africa.irespond.moodtracker.model;

public enum Mood {
    VERY_SAD("very sad"), SAD("sad"), FAIR("fair"), HAPPY("happy"), VERY_HAPPY("very happy");

    private String mood;
    Mood(String mood){
        this.mood = mood;
    }
}
