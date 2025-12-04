package observer;

import state.OrderContext;

public class CustomerNotification implements OrderObserver{
	private String customerName;
    
    public CustomerNotification(String customerName) {
        this.customerName = customerName;
    }
    
	@Override
	public void update(OrderContext orderContext) {
		// TODO Auto-generated method stub
		 System.out.println("\nSMS to " + customerName + ": Your order " + 
                 orderContext.getOrder().getOrderId() + " is now " + 
                 orderContext.getStatus());
		 
		 if (orderContext.getStatus().equals("Delivered")) {
	            System.out.println("Order completed! Thank you for choosing our service!");
	     }
	}

}
