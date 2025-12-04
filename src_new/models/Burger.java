package models;

public class Burger extends Food{
	private boolean hasCheese;
	private boolean hasPepperoni;
	
	public Burger(String name, double price, String description, boolean hasCheese, boolean hasPepperoni) {
		super(name, price, description);
		this.hasCheese = hasCheese;
		this.hasPepperoni = hasPepperoni;
	}
	
	public String toString() {
		String extras = "";
		if(hasCheese) {
			extras += "Cheese ";
		}
		
		if(hasPepperoni) {
			extras += "Pepperoni ";
		}
		
		return super.toString() + (extras.isEmpty() ? "": " [" + extras.trim() + "]");
	}
}
