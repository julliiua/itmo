package human;

import enums.Emotion;

public class Hero extends Human{
    public Hero(String name, int age, int power, Emotion emotion){
        super(name, age, power, emotion);
    }
    public boolean shake(Hero human){
        if (this.getPower() == 0) {
            System.out.println(this.getName() + " не может оторвать руку, так как у него нет силы!");
        }

        if ((Math.random() < 0.5) || this.getPower() < human.getPower() * 1.2) {
            System.out.println(this.getName() + " хотел оторвать руку " + human.getName());
            return false;
        }
        System.out.println(this.getName() + " оторвал руку " + human.getName());
        human.addHealh((int) (-human.getHealth() * 0.5));

        return true;
    }
    @Override
    public String toString() {
        return "Герой: " + getName();
    }
}
