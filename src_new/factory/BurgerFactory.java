package factory;

import models.Burger;
import models.Food;

public class BurgerFactory extends FoodFactory{

	@Override
	public Food createFood(String type) {
		switch (type.toLowerCase()) {
        case "classic":
            return new Burger("Classic Burger", 8.99, "Beef patty with lettuce and tomato", false, false);
        case "cheeseburger":
            return new Burger("Cheeseburger", 9.99, "Beef patty with cheese", true, false);
        case "bacon":
            return new Burger("Bacon Burger", 11.99, "Beef patty with bacon and cheese", true, true);
        default:
            return new Burger("Double CheeseBurger", 10.99, "Beef patty with extra cheese", true, false);
		}
	}

}
