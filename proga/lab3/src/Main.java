import human.*;
import enums.*;
import thing.*;
import stories.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Hero skuperdyay = new Hero("Скупердяй", 50, (int)(Math.random()*101), Emotion.DEFAULT);
        Hero korotyshka = new Hero("Коротышка", 25, (int)(Math.random()*101), Emotion.DEFAULT);
        Seller seller = new Seller("Продавщица", 30, (int)(Math.random()*101), Emotion.KIND);
        Worker admin = new Worker("Управляющий", 40, (int)(Math.random()*101), Emotion.DEFAULT);
        Worker workers = new Worker("Работяги", 35, (int)(Math.random()*101), Emotion.DEFAULT);

        ArrayList<Food> products = new ArrayList<Food>() {{
            add(new Food("бутерброд с сосиской", (int)(Math.random()*35)));
            add(new Food("круассан", (int)(Math.random()*60)));
            add(new Food("шанежка", (int)(Math.random()*25)));
            add(new Food("кусок пиццы", (int)(Math.random()*45)));
            add(new Food("шоколадный торт", (int)(Math.random()*70)));
        }};
        seller.setProducts(products);

        ArrayList<Thing> things = new ArrayList<>() {{
            add(new Thing("монеты", (int)(Math.random()*100)));
            add(new Book("расписка", 1));
            add(new Book("книга", 1));
        }};

        String searchFood = "бутерброд с сосиской";
        Food sandwich = null;
        for (Food food : products) {
            if (food.foodName().equals(searchFood)) {
                sandwich = food;
                break;
            }
        }
        String searchBook = "книга";
        Book book = null;
        for (Thing thing : things){
            if (thing.getThingName().equals(searchBook)){
                book = (Book) thing;
                break;
            }
        }
        String searchNote = "расписка";
        Book note = null;
        for (Thing thing : things){
            if (thing.getThingName().equals(searchNote)){
                note = (Book) thing;
                break;
            }
        }
        String searchMoney = "монеты";
        Thing money = null;
        for (Thing thing : things){
            if (thing.getThingName().equals(searchMoney)){
                money = thing;
                break;
            }
        }
        Story story;
        story = new FirstStory(skuperdyay, korotyshka);
        story.start();
        story = new SecondStory(skuperdyay, korotyshka,seller,admin,workers, book, note, sandwich,money);
        story.start();

    }
}