package human;

import enums.Emotion;
import thing.*;

public class Worker extends Human{
    public Worker(String name, int age, int power, Emotion emotion){
        super(name, age, power, emotion);
    }
    private boolean believe;
    public boolean getBelieve(){
        return this.believe;
    }
    public void addWrite(Book book) {
        if (book != null) {
            book.addText("Имя: " + this.getName());
        }
    }
    @Override
    public String toString() {
        return "Работник " + getName();
    }
    public void giveThing(Human human, Thing thing){
        human.addThing(thing);
        System.out.println(this.getName() + " дал " + thing.getThingName() + human.getName());
    }
    public void takeNote(Human human, Book book) {
        human.addThing(book);
        System.out.println(this.getName() + " дал расписку " + human.getName());
    }
}
