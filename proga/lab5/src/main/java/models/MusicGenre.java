package models;

/**
 * Перечисление, представляющее музыкальные жанры.
 */
public enum MusicGenre {
    RAP,
    HIP_HOP,
    BLUES,
    POP,
    POST_PUNK;

    public String getGenreName() {
        return this.name();
    }
}