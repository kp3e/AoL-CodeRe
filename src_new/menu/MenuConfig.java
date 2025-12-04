package menu;

import factory.FoodFactory;

public class MenuConfig {
	private final String categoryName;
    private final String[] menuItems;
    private final String[] factoryTypes;
    private final FoodFactory factory;
    
    public MenuConfig(String categoryName, String[] menuItems, String[] factoryTypes, FoodFactory factory) {
        this.categoryName = categoryName;
        this.menuItems = menuItems;
        this.factoryTypes = factoryTypes;
        this.factory = factory;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public String[] getMenuItems() {
        return menuItems;
    }
    
    public String[] getFactoryTypes() {
        return factoryTypes;
    }
    
    public FoodFactory getFactory() {
        return factory;
    }
}
