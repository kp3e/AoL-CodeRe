package singleton;

import java.util.ArrayList;
import java.util.Iterator;

import builder.FoodOrder;
import state.OrderContext;

public class Database {
	private static Database instance;
	private ArrayList<FoodOrder> orders;
	private ArrayList<OrderContext> orderContexts;

	private Database() {
		orders = new ArrayList<>();
		orderContexts = new ArrayList<>();
		System.out.println("Database Connection Extablished!");
	}
	
	public static Database getInstance() {
		if(instance == null) {
			synchronized (Database.class) {
				if(instance == null) {
					instance = new Database();
				}
			}
		}
		
		return instance;
	}
	
	public void saveOrder(FoodOrder order) {
        orders.add(order);
        System.out.println("Order saved to database: " + order.getOrderId());
    }
    
    public void saveOrderContext(OrderContext context) {
        orderContexts.add(context);
    }
    
    public FoodOrder getOrder(String orderID) {
    	for(FoodOrder order : orders) {
    		if(order.getOrderId().equalsIgnoreCase(orderID)) {
    			return order;
    		}
    	}
    	return null;
    }
	
    public ArrayList<FoodOrder> getAllOrders(){
    	return new ArrayList<FoodOrder>(orders);
    }
    
    public void displayAllOrders() {
        System.out.println("\nAll Orders in Database:");
        for (FoodOrder order : orders) {
            System.out.println("- " + order.getOrderId() + " (" + order.getCustomerName() + ") - $" + order.getTotalPrice());
        }
    }
    
    public void removeDeliveredOrders() {
        
        Iterator<OrderContext> contextIterator = orderContexts.iterator();
        while (contextIterator.hasNext()) {
            OrderContext context = contextIterator.next();
            String orderId = context.getOrder().getOrderId();
            boolean isDelivered = context.isDelivered();
            
            if (isDelivered) {
                System.out.println("Removing completed order: " + orderId);
                
                // Remove from orders list
                orders.removeIf(order -> order.getOrderId().equals(orderId));
                
                // Cancel timer and remove context
                context.cancelTimer();
                contextIterator.remove();
            }
        }
    }
    
    
    public void displayOrderStatuses() {
        System.out.println("\nCURRENT ORDER STATUSES");
        System.out.println("═══════════════════════════════════════");
        
        if (orderContexts.isEmpty()) {
            System.out.println("No active orders.");
            return;
        }
        
        for (OrderContext context : orderContexts) {
            System.out.println(context.getOrder().getOrderId() + 
                             " (" + context.getOrder().getCustomerName() + ") - " + 
                             context.getStatus());
        }
    }
	
}

