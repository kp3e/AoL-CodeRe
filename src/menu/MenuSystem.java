package menu;

import java.util.ArrayList;
import java.util.Scanner;

import builder.FoodOrder;
import builder.FoodOrder.OrderBuilder;
import factory.*;
import singleton.Database;
import state.*;
import models.*;
import observer.*;

public class MenuSystem {
	private Scanner scanner;
    private PizzaFactory pizzaFactory;
    private BurgerFactory burgerFactory;
    private PastaFactory pastaFactory;
    private Database db;
    
    public MenuSystem() {
        scanner = new Scanner(System.in);
        pizzaFactory = new PizzaFactory();
        burgerFactory = new BurgerFactory();
        pastaFactory = new PastaFactory();
        db = Database.getInstance();
    }
    
    public void start() {
        System.out.println("WELCOME TO FOOD DELIVERY SYSTEM");
        System.out.println("═══════════════════════════════════════════════");
        
        while (true) {
            displayMainMenu();
            int choice = getChoice();
            
            switch (choice) {
                case 1:
                    createOrder();
                    break;
                case 2:
                    viewAllOrders();
                    break;
                case 3:
                    trackOrder();
                    break;
                case 4:
                    System.out.println("Thank you for using Food Delivery System!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void displayMainMenu() {
        System.out.println("\nMAIN MENU");
        System.out.println("═══════════════════════════════════════");
        System.out.println("1. Place New Order");
        System.out.println("2. View All Orders");
        System.out.println("3. Track Order");
        System.out.println("4. Exit");
        System.out.print("Enter your choice (1-4): ");
    }
    
    private String getValidName() {
        while (true) {
            System.out.print("Enter your name: ");
            String name = scanner.nextLine().trim();
            
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty. Please enter your name.");
            } else if (name.length() < 2) {
                System.out.println("Name must be at least 2 characters long. Please try again.");
            } else {
                return name;
            }
        }
    }

    private String getValidAddress() {
        while (true) {
            System.out.print("Enter your address: ");
            String address = scanner.nextLine().trim();
            
            if (address.isEmpty()) {
                System.out.println("Address cannot be empty. Please enter your address.");
            } else if (address.length() < 5) {
                System.out.println("Address must be at least 5 characters long. Please try again.");
            } else {
                return address;
            }
        }
    }
    
    private String getValidPhone() {
        while (true) {
            System.out.print("Enter your phone: ");
            String phone = scanner.nextLine().trim();
            
            if (phone.isEmpty()) {
                System.out.println("Phone number cannot be empty. Please try again.");
                continue;
            }
            
            // Check if phone contains only digits and common phone characters
            if (phone.matches("[0-9+\\-\\s()]+")) {
                // Remove all non-digit characters for final validation
                String digitsOnly = phone.replaceAll("[^0-9]", "");
                
                if (digitsOnly.length() >= 8 && digitsOnly.length() <= 15) {
                    return phone; // Return original format with formatting
                } else {
                    System.out.println("Phone number must be 8-15 digits long. Please try again.");
                }
            } else {
                System.out.println("Phone number can only contain numbers, spaces, hyphens, parentheses, and +. Please try again.");
            }
        }
    }
    
    private boolean getYesNoChoice(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            
            if (input.equals("y") || input.equals("yes")) {
                return true;
            } else if (input.equals("n") || input.equals("no")) {
                return false;
            } else {
                System.out.println("Please enter 'y' for yes or 'n' for no.");
            }
        }
    }
    
    private void createOrder() {
        System.out.println("\nCREATE NEW ORDER");
        System.out.println("═══════════════════════════════════════");
        
        // Get customer information
        String name = getValidName();
        String phone = getValidPhone();
        String address = getValidAddress();
        
        // Build order
        OrderBuilder orderBuilder = new FoodOrder.OrderBuilder()
        		.setCustomerName(name)
                .setPhone(phone)
                .setAddress(address);
        
        // Add items to order
        boolean addingItems = true;
        while (addingItems) {
            displayFoodMenu();
            int category = getChoice();
            
            Food selectedFood = null;
            switch (category) {
                case 1:
                    selectedFood = selectPizza();
                    break;
                case 2:
                    selectedFood = selectBurger();
                    break;
                case 3:
                    selectedFood = selectPasta();
                    break;
                case 4:
                    addingItems = false;
                    continue;
                default:
                    System.out.println("Invalid choice.");
                    continue;
            }
            
            if (selectedFood != null) {
                orderBuilder.addItem(selectedFood);
                System.out.println("Added: " + selectedFood.getName());
            }
            
            if (addingItems) {
            	addingItems = getYesNoChoice("Add another item? (y/n): ");
            }
        }
        
        // Special instructions
        System.out.print("Any special instructions? (press Enter to skip): ");
        String instructions = scanner.nextLine();
        if (!instructions.isEmpty()) {
            orderBuilder.setSpecialInstructions(instructions);
        }
        
        // Build and save order
        try {
            FoodOrder order = orderBuilder.build();
            db.saveOrder(order);
            
            // Create order context for tracking
            OrderContext context = new OrderContext(order);
            context.addObserver(new CustomerNotification(name));
            db.saveOrderContext(context);
            
            System.out.println("\nORDER PLACED SUCCESSFULLY!");
            System.out.println(order);
            System.out.println("\nYou will receive SMS updates about your order status.");
            
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void displayFoodMenu() {
        System.out.println("\nFOOD CATEGORIES");
        System.out.println("═══════════════════════════════════════");
        System.out.println("1. Pizzas");
        System.out.println("2. Burgers");
        System.out.println("3. Pasta");
        System.out.println("4. Finish Adding Items");
        System.out.print("Select category (1-4): ");
    }
    
    private Food selectPizza() {
        System.out.println("\nPIZZA MENU");
        System.out.println("═══════════════════════════════════════");
        System.out.println("1. Margherita Pizza - $12.99");
        System.out.println("2. Pepperoni Pizza - $14.99");
        System.out.println("3. Hawaiian Pizza - $13.99");
        System.out.println("4. Supreme Pizza - $16.99");
        System.out.println("5. Veggie Pizza - $13.49");
        System.out.print("Select pizza (1-5): ");
        
        int choice = getChoice();
        String[] types = {"margherita", "pepperoni", "hawaiian", "supreme", "veggie"};
        
        if (choice >= 1 && choice <= 5) {
            return pizzaFactory.createFood(types[choice - 1], "");
        }
        return null;
    }
    
    private Food selectBurger() {
        System.out.println("\nBURGER MENU");
        System.out.println("═══════════════════════════════════════");
        System.out.println("1. Classic Burger - $8.99");
        System.out.println("2. Cheeseburger - $9.99");
        System.out.println("3. Bacon Burger - $11.99");
        System.out.println("4. Veggie Burger - $9.49");
        System.out.println("5. Double Cheeseburger - $13.99");
        System.out.print("Select burger (1-5): ");
        
        int choice = getChoice();
        String[] types = {"classic", "cheeseburger", "bacon", "veggie", "double"};
        
        if (choice >= 1 && choice <= 5) {
            return burgerFactory.createFood(types[choice - 1], "");
        }
        return null;
    }
    
    private Food selectPasta() {
        System.out.println("\nPASTA MENU");
        System.out.println("═══════════════════════════════════════");
        System.out.println("1. Carbonara - $13.99");
        System.out.println("2. Bolognese - $12.99");
        System.out.println("3. Alfredo - $11.99");
        System.out.println("4. Marinara - $10.99");
        System.out.println("5. Pesto Pasta - $12.49");
        System.out.print("Select pasta (1-5): ");
        
        int choice = getChoice();
        String[] types = {"carbonara", "bolognese", "alfredo", "marinara", "pesto"};
        
        if (choice >= 1 && choice <= 5) {
            return pastaFactory.createFood(types[choice - 1], "");
        }
        return null;
    }
    
    private void viewAllOrders() {
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
        int choice = getChoice();
        
        if (choice > 0 && choice <= orders.size()) {
            System.out.println("\n" + orders.get(choice - 1));
        }
    }
    
    private void trackOrder() {
        db.displayOrderStatuses();
    }
    
    private int getChoice() {
        while (true) {
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume the newline
                return choice;
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // consume the invalid input
            }
        }
    }
}
