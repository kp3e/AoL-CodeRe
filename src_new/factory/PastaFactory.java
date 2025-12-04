package factory;

import models.Food;
import models.Pasta;

public class PastaFactory extends FoodFactory{
	public Food createFood(String type) {
        switch (type.toLowerCase()) {
            case "carbonara":
                return new Pasta("Carbonara", 13.99, "Creamy pasta with pancetta", "Carbonara", "Spaghetti");
            case "bolognese":
                return new Pasta("Bolognese", 12.99, "Pasta with meat sauce", "Bolognese", "Penne");
            case "alfredo":
                return new Pasta("Alfredo", 11.99, "Creamy white sauce pasta", "Alfredo", "Fettuccine");
            default:
                return new Pasta("Pesto Pasta", 12.99, "Green pasta with herbs", "Pesto", "Spaghetti");
        }
    }
}
