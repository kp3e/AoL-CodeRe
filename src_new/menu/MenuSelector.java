package menu;

import models.Food;

public class MenuSelector {
	private InputValidator validator;
    
    public MenuSelector(InputValidator validator) {
        this.validator = validator;
    }
    
    public Food selectFood(MenuConfig config) {
        System.out.println("\n" + config.getCategoryName().toUpperCase() + " MENU");
        System.out.println("═══════════════════════════════════════");
        
        String[] items = config.getMenuItems();
        for (int i = 0; i < items.length; i++) {
            System.out.println((i + 1) + ". " + items[i]);
        }
        
        System.out.print("Select " + config.getCategoryName().toLowerCase() + 
                        " (1-" + items.length + "): ");
        
        int choice = validator.getChoice();
        String[] types = config.getFactoryTypes();
        
        if (choice >= 1 && choice <= types.length) {
            return config.getFactory().createFood(types[choice - 1]);
        }
        return null;
    }
}
