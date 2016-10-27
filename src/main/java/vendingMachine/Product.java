package vendingMachine;

public class Product {
	private String name;
	private double price;
	private int quantity;
	
	// Default constructor
	Product() {
		this.setName("");
		this.setPrice(0.0);
		this.setQuantity(1);
	}
	
	Product(String n, double p) {
		this.setName(n);
		this.setPrice(p);
		this.setQuantity(1);
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
