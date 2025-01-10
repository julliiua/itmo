package stories;

import human.*;
import java.util.Objects;
import java.util.Random;

public class Story {
    public Story(){};
    private final String storyName = "Базовая история";

    public void start(){
        System.out.println("Начало истории: ");
    }
    @Override
    public String toString() {
        return "Story {" + "storyName='" + storyName + '\'' + '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Story story = (Story) o;
        return Objects.equals(storyName, story.storyName);
    }
    @Override
    public int hashCode() {
        return Objects.hash(storyName);
    }
}
