package menu;

import java.util.ArrayList;

import builder.FoodOrder;
import singleton.Database;

public class Renderer {
	
	
	public Renderer() {
		super();
	}

	public void displayMainMenu() {
        System.out.println("\nMAIN MENU");
        System.out.println("═══════════════════════════════════════");
        System.out.println("1. Place New Order");
        System.out.println("2. View All Orders");
        System.out.println("3. Track Order");
        System.out.println("4. Exit");
        System.out.print("Enter your choice (1-4): ");
    }
	
	public void displayFoodMenu() {
        System.out.println("\nFOOD CATEGORIES");
        System.out.println("═══════════════════════════════════════");
        System.out.println("1. Pizzas");
        System.out.println("2. Burgers");
        System.out.println("3. Pasta");
        System.out.println("4. Finish Adding Items");
        System.out.print("Select category (1-4): ");
    }
    
    public void viewAllOrders(Database db, InputValidator validator) {
        System.out.println("\nALL ORDERS");
        System.out.println("═══════════════════════════════════════");
        
        ArrayList<FoodOrder> orders = db.getAllOrders();
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }
        
        for (int i = 0; i < orders.size(); i++) {
            FoodOrder order = orders.get(i);
            System.out.println((i + 1) + ". " + order.getOrderId() + " - " + 
                             order.getCustomerName() + " - $" + 
                             String.format("%.2f", order.getTotalPrice()));
        }
        
        System.out.print("\nEnter order number for details (or 0 to go back): ");
        int choice = validator.getChoice();
        
        if (choice > 0 && choice <= orders.size()) {
            System.out.println("\n" + orders.get(choice - 1));
        }
    }
    
    public void trackOrder(Database db) {
        db.displayOrderStatuses();
    }
}
