package models;

public class CustomerInfo {
	private String name;
	private String phone;
	private String address;
	
	public CustomerInfo(String name, String phone, String address) {
		super();
		this.name = name;
		this.phone = phone;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public String getAddress() {
		return address;
	}
	
	
	
	
}
