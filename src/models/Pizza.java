package models;

public class Pizza extends Food{
	private String size;
	private String crust;
	
	public Pizza(String name, double price, String description, String size, String crust) {
		super(name, price, description);
		this.size = size;
		this.crust = crust;
	}
	
	public String toString() {
        return super.toString() + " [" + size + ", " + crust + " crust]";
    }

}
