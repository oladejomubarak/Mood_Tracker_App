package africa.irespond.moodtracker.model;

public enum SocialMoodInfluence {
    FAMILY("family"),
    FRIEND("friend"),
    PARTNER("partner"),
    COLLEAGUE("colleague"),
    STRANGER("stranger");
    private String socialMoodInfluence;
    SocialMoodInfluence(String socialMoodInfluence){
        this.socialMoodInfluence = socialMoodInfluence;
    }
}
