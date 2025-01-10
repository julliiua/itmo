package stories;

import enums.Emotion;
import human.*;

public class FirstStory extends Story{
    private Hero firstActor;
    private Hero secondActor;

    final private String storyName = "Первая история";
    public FirstStory(Hero firstActor, Hero secondActor) {
        this.firstActor = firstActor;
        this.secondActor = secondActor;
    }
    @Override
    public void start() {
        System.out.println("Начало первой истории"+ "\n");
        this.firstAction();
    }
    private void firstAction() {
        System.out.println(firstActor.getName() + " осматривает " + secondActor.getName() + " со всех сторон.");
        System.out.println("Oн изо всех сил хлопал его рукой по спине, словно пытаясь сбить с ног, " +
                "тряс ему руку с такой энергией, будто задумал оторвать ее");


        if (firstActor.getPower() > secondActor.getPower()) {
            System.out.println("Слишком сильное рукопожатие! Рука оторвана!");
            secondActor.addHealh(-(int)(Math.random()*70));
            secondActor.setEmotion(Emotion.ANGRY);
        } else {
            System.out.println("Рука осталась цела, но " + secondActor.getName() + " испытывает боль.");
            secondActor.addHealh((int)(-Math.random()*25));
            secondActor.setEmotion(Emotion.EVIL);
        }


        System.out.println(secondActor.getName() + " кричит потому что " + this.secondActor.getEmotion().getDescription()+
                ". Его здоровье теперь: " + secondActor.getHealth());
        this.secondAction();
    }

    private void secondAction() {
        System.out.println("После небольшой стычки герои пошли к ларьку");
    }
}
