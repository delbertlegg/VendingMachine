package vendingMachine;

public class Product {
	private String name;
	private double price;
	
	// Default constructor
	Product() {
		this.setName("");
		this.setPrice(0.0);
	}
	
	Product(String n, double p) {
		this.setName(n);
		this.setPrice(p);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
