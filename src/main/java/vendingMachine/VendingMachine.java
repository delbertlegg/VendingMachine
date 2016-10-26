package vendingMachine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import vendingMachine.VendingMachineConstants.*;

public class VendingMachine {

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
					this.setCurrentBalance(0.0);
					return product;
				}
				else this.setDisplay("PRICE " + product.getPrice());
			}
		}
		return null;
	}
	
	/**
	 * Getters and setters
	 */
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

}
