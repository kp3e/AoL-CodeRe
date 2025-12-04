package menu;

import java.util.HashMap;
import java.util.Map;
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
    private Database db;
    private InputValidator validator;
    private Renderer printer;
    private MenuSelector menuSelector;
    private Map<Integer, MenuConfig> menuConfigs;
    
    public MenuSystem() {
        scanner = new Scanner(System.in);
        db = Database.getInstance();
        validator = new InputValidator(scanner);
        printer = new Renderer();
        menuSelector = new MenuSelector(validator);
        
        initializeMenus();
    }
    
    private void initializeMenus() {
        PizzaFactory pizzaFactory = new PizzaFactory();
        BurgerFactory burgerFactory = new BurgerFactory();
        PastaFactory pastaFactory = new PastaFactory();
        
        menuConfigs = new HashMap<>();
        
        menuConfigs.put(1, new MenuConfig("Pizza",
            new String[] {
                "Margherita Pizza - $12.99",
                "Pepperoni Pizza - $14.99",
                "Hawaiian Pizza - $13.99",
                "Supreme Pizza - $16.99",
                "Veggie Pizza - $13.49"
            },
            new String[] {"margherita", "pepperoni", "hawaiian", "supreme", "veggie"},
            pizzaFactory
        ));
        
        menuConfigs.put(2, new MenuConfig("Burger",
            new String[] {
                "Classic Burger - $8.99",
                "Cheeseburger - $9.99",
                "Bacon Burger - $11.99",
                "Veggie Burger - $9.49",
                "Double Cheeseburger - $13.99"
            },
            new String[] {"classic", "cheeseburger", "bacon", "veggie", "double"},
            burgerFactory
        ));
        
        menuConfigs.put(3, new MenuConfig("Pasta",
            new String[] {
                "Carbonara - $13.99",
                "Bolognese - $12.99",
                "Alfredo - $11.99",
                "Marinara - $10.99",
                "Pesto Pasta - $12.49"
            },
            new String[] {"carbonara", "bolognese", "alfredo", "marinara", "pesto"},
            pastaFactory
        ));
    }
    
    public void start() {
        System.out.println("WELCOME TO FOOD DELIVERY SYSTEM");
        System.out.println("═══════════════════════════════════════════════");
        
        while (true) {
            printer.displayMainMenu();
            int choice = validator.getChoice();
            
            switch (choice) {
                case 1:
                    createOrder();
                    break;
                case 2:
                    printer.viewAllOrders(db, validator);
                    break;
                case 3:
                    printer.trackOrder(db);
                    break;
                case 4:
                    System.out.println("Thank you for using Food Delivery System!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    
    
    
    private void createOrder() {
        System.out.println("\nCREATE NEW ORDER");
        System.out.println("═══════════════════════════════════════");
        
        CustomerInfo customerInfo = collectCustomerInfo();
        OrderBuilder orderBuilder = createOrderBuilder(customerInfo);
        addItemsToOrder(orderBuilder);
        addSpecialInstructions(orderBuilder);
        finalizeOrder(orderBuilder, customerInfo.getName());
    }
    
    private CustomerInfo collectCustomerInfo() {
        return new CustomerInfo(
            validator.getValidName(),
            validator.getValidPhone(),
            validator.getValidAddress()
        );
    }
    
    private OrderBuilder createOrderBuilder(CustomerInfo info) {
        return new FoodOrder.OrderBuilder()
            .setCustomerName(info.getName())
            .setPhone(info.getPhone())
            .setAddress(info.getAddress());
    }
    
    private Food selectFoodItem() {
        printer.displayFoodMenu();
        int category = validator.getChoice();
        
        if (category == 4) {
            return null; // Finish adding items
        }
        
        MenuConfig config = menuConfigs.get(category);
        if (config != null) {
            return menuSelector.selectFood(config);
        } else {
            System.out.println("Invalid choice.");
            return null;
        }
    }
    
    private void addItemsToOrder(OrderBuilder orderBuilder) {
        boolean addingItems = true;
        while (addingItems) {
            Food selectedFood = selectFoodItem();
            
            if (selectedFood != null) {
                orderBuilder.addItem(selectedFood);
                System.out.println("Added: " + selectedFood.getName());
                addingItems = validator.getYesNoChoice("Add another item? (y/n): ");
            } else {
                addingItems = false;
            }
        }
    }
    
    private void addSpecialInstructions(OrderBuilder orderBuilder) {
        System.out.print("Any special instructions? (press Enter to skip): ");
        String instructions = scanner.nextLine();
        if (!instructions.isEmpty()) {
            orderBuilder.setSpecialInstructions(instructions);
        }
    }
    
    private void finalizeOrder(OrderBuilder orderBuilder, String customerName) {
        try {
            FoodOrder order = orderBuilder.build();
            db.saveOrder(order);
            
            OrderContext context = new OrderContext(order);
            context.addObserver(new CustomerNotification(customerName));
            db.saveOrderContext(context);
            
            System.out.println("\nORDER PLACED SUCCESSFULLY!");
            System.out.println(order);
            System.out.println("\nYou will receive SMS updates about your order status.");
            
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    
    
    
}
