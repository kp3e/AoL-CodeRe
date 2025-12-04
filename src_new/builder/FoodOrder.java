package builder;

import java.util.ArrayList;
import java.util.List;

import models.Food;

public class FoodOrder {
	private ArrayList<Food> items;
	private String customerName;
    private String address;
    private String specialInstructions;
    private double totalPrice;
    private String orderId;
    
    public FoodOrder(OrderBuilder builder) {
    	this.items = new ArrayList<>(builder.getItems());
        this.customerName = builder.getCustomerName();
        this.address = builder.getAddress();
        this.specialInstructions = builder.getSpecialInstructions();
        this.totalPrice = calculateTotal();
        this.orderId = "ORD-" + System.currentTimeMillis();
    }
	
    private double calculateTotal() {
        return items.stream().mapToDouble(Food::getPrice).sum();
    }

	public ArrayList<Food> getItems() {
		return items;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getAddress() {
		return address;
	}

	public String getSpecialInstructions() {
		return specialInstructions;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public String getOrderId() {
		return orderId;
	}
	
	@Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: ").append(orderId).append("\n");
        sb.append("Customer: ").append(customerName).append("\n");
        sb.append("Address: ").append(address).append("\n");
        sb.append("Items:\n");
        for (Food item : items) {
            sb.append("  - ").append(item.toString()).append("\n");
        }
        sb.append("Special Instructions: ").append(specialInstructions).append("\n");
        sb.append("Total: $").append(String.format("%.2f", totalPrice));
        return sb.toString();
    }
	
	public static class OrderBuilder {
		private List<Food> items = new ArrayList<>();
		private String customerName;
	    private String address;
	    private String phone;
	    private String specialInstructions = "";
	    
	    public OrderBuilder setCustomerName(String customerName) {
	        this.customerName = customerName;
	        return this;
	    }
	    
	    public OrderBuilder setAddress(String address) {
	        this.address = address;
	        return this;
	    }
	    
	    public OrderBuilder setPhone(String phone) {
	        this.phone = phone;
	        return this;
	    }
	    
	    public OrderBuilder addItem(Food food) {
	        this.items.add(food);
	        return this;
	    }
	    
	    public OrderBuilder setSpecialInstructions(String instructions) {
	        this.specialInstructions = instructions;
	        return this;
	    }
	    
	    public FoodOrder build() {
	        if (customerName == null || phone == null || address == null || items.isEmpty()) {
	            throw new IllegalStateException("Customer info and at least one item are required");
	        }
	        return new FoodOrder(this);
	    }
	    
	    
	    public List<Food> getItems() {
	        return items;
	    }

	    public String getCustomerName() {
	        return customerName;
	    }

	    public String getAddress() {
	        return address;
	    }

	    public String getSpecialInstructions() {
	        return specialInstructions;
	    }
	    
	    public String getPhone() {
	        return phone;
	    }
	}
    
}
