package human;

import enums.Emotion;
import java.util.ArrayList;
import thing.Food;
import thing.Thing;

public class Seller extends Human {
    public Seller(String name, int age, int power, Emotion emotion) {
        super(name, age, power, emotion);
        this.products = new ArrayList<Food>();
    }

    private ArrayList<Food> products;


    public void setProducts(ArrayList<Food> products) {
        this.products = products;
    }
    public String getProducts() {
        StringBuilder result = new StringBuilder();  // Используем StringBuilder для эффективной конкатенации строк

        for (Food product : products) {
            result.append("\tТовар: ").append(product.foodName()).append(", цена: ").append(product.price()).append("\n");
        }

        return result.toString();
    }

    public Food food(String request) {
        for (Food a : products) {
            if (a.foodName().equals(request)) {
                return a;
            }
        }
        return null;
    }
}
