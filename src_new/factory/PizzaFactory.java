package factory;

import models.Food;
import models.Pizza;

public class PizzaFactory extends FoodFactory{

	@Override
	public Food createFood(String type) {
		switch (type.toLowerCase()) {
        case "margherita":
            return new Pizza("Margherita Pizza", 12.99, "Classic tomato and mozzarella", "Medium", "Thin");
        case "pepperoni":
            return new Pizza("Pepperoni Pizza", 14.99, "Pepperoni with mozzarella", "Large", "Thick");
        case "hawaiian":
            return new Pizza("Hawaiian Pizza", 13.99, "Ham and pineapple", "Medium", "Thin");
        default:
            return new Pizza("Veggie Pizza", 15.99, "Tomato sauce with bell peppers and basil", "Medium", "Regular");
		}
	}

}
