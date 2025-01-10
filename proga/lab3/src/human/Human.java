package human;
import enums.Emotion;

import java.util.ArrayList;
import java.util.Objects;
import thing.Thing;
public abstract class Human implements People{
    private String name;
    private int age;
    private  int power=0;
    private Emotion emotion;
    private int healh;
    private ArrayList<Thing> things;
    public Human(String name, int age, int power, Emotion emotion) {
        this.name = name;
        this.age = age;
        this.power = power;
        this.emotion = emotion;
        this.healh = 100;
        this.things = new ArrayList<Thing>();

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
    public int getHealth() {
        return this.healh;
    }
    public Emotion getEmotion() {
        return emotion;
    }

    public void setEmotion(Emotion emotion) {
        this.emotion = emotion;
    }

    public void addHealh(int amount) {
        this.healh += amount;
            if (this.healh < 0) {
                this.healh = 0;

            }
    }
    public void addPower(int amount) {
        if (amount > 0) {
            this.power += amount;
            if (this.power < 0) {
                this.power = 0;
            }

        }
    }
    public void say(String message) {
        System.out.println(this.name + " сказал: " + message);
    }
    public ArrayList<Thing> getThings() {
        return new ArrayList<>(things); // Создаем копию массива вещей, чтобы не менять вещи у обьекта
    }

    public void addThing(Thing thing) {
        if (thing != null) {
            things.add(thing);
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Human person = (Human) o;
        return Objects.equals(name, person.name) && Objects.equals(age, person.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}

