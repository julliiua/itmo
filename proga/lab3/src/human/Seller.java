package human;

import enums.Emotion;
import java.util.ArrayList;
import thing.Food;
public class Seller extends Human {
    public Seller(String name, int age, int power, Emotion emotion){
        super(name, age, power, emotion);
    }
    private ArrayList<Food> products;
    public ArrayList<Food> getProducts() {
        return new ArrayList<>(products);
    }
    public Food food(String request){
        for (Food prod : products){
            if (prod.foodName() == request){
                return prod;
            }
        }
        return null;
    }
}
