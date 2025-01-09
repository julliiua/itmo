package enums;

public enum Emotion {
    DEFAULT("Нормальный"),
    KIND("Добрый" ),
    EVIL("Злой"),
    ANGRY("Гневный");


    private String description;

    Emotion(String text){
        this.description = text;
    }
    public String getDescription() {
        return description;
    }

}