package enums;

public enum Emotion {
    DEFAULT("нормальный"),
    KIND("добрый" ),
    EVIL("злой"),
    ANGRY("гневный");


    private String description;

    Emotion(String text){
        this.description = text;
    }
    public String getDescription() {
        return description;
    }

}