package vendingMachine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import vendingMachine.VendingMachineConstants.*;

public class VendingMachine {
	// TODO: Add method to addProduct that will increment quantity if already in list (refactoring)
	// TODO: Add a method to showDisplay to change values (ex. from SOLD OUT to INSERT COIN)
	
	private double currentBalance;
	private double coinReturnBalance;
	private String display;
	public List<Product> products = new ArrayList<Product>();

	
	// Default constructor
	VendingMachine() {
		currentBalance = 0.0;
		coinReturnBalance = 0.0;		
		setDisplay(VendingMachineConstants.DISPLAY_DEFAULT);
	}
	
	public boolean insertCoin(Coin c) {
		if (c.isValidCoin()) {
			this.currentBalance += c.coinValue();
			this.setDisplay(String.format("%.2f", this.getCurrentBalance()));
			return true;
		}
		else {
			this.returnInvalidCoin(c);
			return false;
		}
	}

	public double returnInvalidCoin(Coin c) {
		this.coinReturnBalance += c.coinValue();
		return BigDecimal.valueOf(coinReturnBalance).setScale(3, RoundingMode.HALF_UP).doubleValue();
	}
	
	public Product pushButton(Button button) {
		for (Product product : this.products) {
			if (button.Value() == product.getName()) {
				if (this.currentBalance >= product.getPrice()) {
					this.setDisplay(VendingMachineConstants.DISPLAY_DEFAULT);
					this.setCoinReturnBalance(this.currentBalance - product.getPrice());
					this.setCurrentBalance(0.0);
					return product;
				}
				else this.setDisplay("PRICE " + product.getPrice());
			}
		}
		return null;
	}
	
	public void pushReturnButton() {
		this.coinReturnBalance = this.currentBalance;
		this.currentBalance = 0.0;
		this.display = VendingMachineConstants.DISPLAY_DEFAULT;		
	}
	
	// Simulating emptying of coin return for this exercise
	public void emptyCoinReturn() {
		this.coinReturnBalance = 0.0;
	}
	
	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}
	
	public double getCurrentBalance() {
		return BigDecimal.valueOf(currentBalance).setScale(3, RoundingMode.HALF_UP).doubleValue();
	}

	public void setCurrentBalance(double currentBalance) {
		this.currentBalance = currentBalance;
	}
	
	public double getCoinReturnBalance() {
		return coinReturnBalance;
	}

	public void setCoinReturnBalance(double coinReturnBalance) {
		this.coinReturnBalance = coinReturnBalance;
	}

	

}
