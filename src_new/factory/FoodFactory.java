package factory;

import models.Food;

public abstract class FoodFactory {
	public abstract Food createFood(String type);
}
