package africa.irespond.moodtracker.model;

public enum WeatherMoodInfluence {
    SUNNY("sunny"),
    CLOUDY("cloudy"),
    RAINY("cloudy"),
    WINDY("windy"),
    SNOWY("snowy");

    private String weatherMoodInfluence;
    WeatherMoodInfluence(String weatherMoodInfluence){
        this.weatherMoodInfluence = weatherMoodInfluence;
    }
}
