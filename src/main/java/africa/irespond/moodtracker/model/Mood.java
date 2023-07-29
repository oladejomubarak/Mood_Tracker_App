package africa.irespond.moodtracker.model;

public enum Mood {
    SAD("SAD", 1.0),
    ANXIOUS("ANXIOUS", 2.0),
    NEUTRAL("NEUTRAL", 3.0),
    CALM("CALM", 4.0),
    HAPPY("HAPPY", 5.0);
    private final String moodValue;
    private final double ratingValue;

    Mood(String moodValue, double ratingValue) {
        this.moodValue = moodValue;
        this.ratingValue = ratingValue;
    }
    public String getStringValue() {
        return moodValue;
    }
    public double getDoubleValue() {
        return ratingValue;
    }

    public static Mood fromStringValue(String stringValue) {
        for (Mood enumValue : Mood.values()) {
            if (enumValue.moodValue.equalsIgnoreCase(stringValue)) {
                return enumValue;
            }
        }
        throw new IllegalArgumentException("Invalid string value: " + stringValue);
    }
}
