package models;


public class Pasta extends Food{
	private String sauce;
    private String pastaType;
    
	public Pasta(String name, double price, String description, String sauce, String pastaType) {
		super(name, price, description);
		this.sauce = sauce;
		this.pastaType = pastaType;
	}
    
	public String toString() {
        return super.toString() + " [" + pastaType + " with " + sauce + " sauce]";
    }

	
}
