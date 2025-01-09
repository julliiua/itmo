package human;
import enums.Emotion
public abstract class Human implements People{
    private String name;
    private int age;
    private  int power=0;
    private Emotion emotion;
    private int healh=100
    private ArrayList<Thing> things;
    public Human(String name, int age, int power, Emotion emotion) {
        this.name = name;
        this.age = age;
        this.power = power;
        this.emotion = emotion;
        this.things = new ArrayList<>();
    }
    @Override
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }

    public int getPower() {
        return this.power;
    }


}

+Human(String, int, int, Emotion)
      + hashCode(): int
      + equals(Object): boolean


      + getEmotion(): Emotion

      + getHealth(): int
      + addHealth(): void
      + getThings(): ArrayList<Thing>
      + addThing(thing): void
      + say(String): void
      + isMale(): boolean