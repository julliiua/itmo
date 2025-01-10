package stories;

import enums.Emotion;
import human.*;
import thing.*;

public class SecondStory extends Story {
    private Hero firstActor;
    private Hero secondActor;
    private Seller seller;
    private Worker admin;
    private Worker workers;
    private Book book;
    private Book note;
    private Food food;
    private Thing money;


    final private String storyName = "Вторая история";

    public SecondStory(Hero firstActor, Hero secondActor, Seller seller, Worker admin, Worker workers, Book book, Book note, Food food, Thing money) {
        this.firstActor = firstActor;
        this.secondActor = secondActor;
        this.seller = seller;
        this.admin = admin;
        this.workers = workers;
        this.book = book;
        this.note = note;
        this.food = food;
        this.money = money;
    }

    @Override
    public void start() {
        System.out.println("\n" + "Начало второй истории"+ "\n");

        System.out.println(firstActor.getName() + " говорит: ");
        this.firstAction();
    }

    private void firstAction() {
        System.out.println(seller.getName() + " из передвижного ларька торговала булочными изделиями. ");

        System.out.println(seller.getName()+"  чтобы Вы хотели? Вот моя витрина: " + "\n"+ seller.getProducts());
        System.out.println(secondActor.getName()+ " имеет " + money.getCount()+ " монет.");
        System.out.println(secondActor.getName() + " хотел купить " + food.foodName());
        if (Math.random() > 0.15) {
            System.out.println(seller.getName() + " вручила " + secondActor.getName() + " " + food.foodName() +
                    " за " + food.price() + " монет.");
            money.addCount(-food.price());
            System.out.println(admin.getName() + " заносил его имя в " + book.getThingName());

            book.addText(secondActor.getName());

            System.out.println(admin.getName() + " брал " + note.getThingName() + "  в том, что он получил " +
                    food.foodName() + ".");
            admin.takeNote(secondActor, note);

            System.out.println("Вся эта комедия с " + food.foodName() + " была придумана " + firstActor.getName() +
                    " для того, чтобы новые ");
            System.out.println(workers.getName() + " увидели, какой он добрый, и получше работали на него.");
            System.out.println("Получилось это у него или нет??"+ "\n");
            if (Math.random() > 0.4) {
                System.out.println("ДА"+ "\n");
                firstActor.setEmotion(Emotion.KIND);
                workers.setEmotion(Emotion.KIND);
                System.out.println(firstActor.getName() + " "+ firstActor.getEmotion().getDescription() + " потому что " +
                        workers.getName() + " имеют рабочую силу " + workers.getPower());
                System.out.println("Это позволяет увеличить производство в 3 раза");
            } else {
                System.out.println("Несовсем"+ "\n");
                firstActor.setEmotion(Emotion.DEFAULT);
                workers.setEmotion(Emotion.DEFAULT);
                workers.addPower(-50);
                System.out.println(firstActor.getName() + " "+ firstActor.getEmotion().getDescription() + " потому что " +
                        workers.getName() + " имеют рабочую силу " + workers.getPower());
                System.out.println("Этого недостаточно для наилучшего результата, но " + firstActor.getName() +
                        " надеетcя на лучшее. " + "\n"+
                        "Сила работяг позволяет увеличить производство только но 50%");
            }
            System.out.println(firstActor.getName() + " довольный и " +
                    "таким образом обтяпать попутно еще одно выгодное дельце.");
            this.secondAction();
        } else {
            System.out.println("НЕТ"+ "\n");
            firstActor.setEmotion(Emotion.ANGRY);
            workers.setEmotion(Emotion.ANGRY);
            workers.addPower(-100);
            System.out.println("Вся эта комедия с " + food.foodName() + " была придумана " + firstActor.getName() +
                    " для того, чтобы новые ");
            System.out.println(workers.getName() + " увидели, какой он добрый, и получше работали на него." +
                    "Но все пошло не по плану.");
            System.out.println(firstActor.getName()+ " " + firstActor.getEmotion().getDescription() + " потому что " +
                    workers.getName() + " имеют рабочую силу " + workers.getPower());
            System.out.println(firstActor.getName() + " работает в минус, теряет много денег... "+"\n"+" и разоряется" +
                    " таким образом обтяпать попутно еще одно выгодное дельце не получилось" + "\n"+
                    firstActor.getName() + " разочарован.");
            this.secondAction();
        }
    }

    private void secondAction() {
        System.out.println("\n"+ "Конец истории");
    }
}
