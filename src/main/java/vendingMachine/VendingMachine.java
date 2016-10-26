package vendingMachine;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class VendingMachine {
	private double currentBalance;
	private double coinReturnBalance;
	private String display;
	
	// Default constructor
	VendingMachine() {
		currentBalance = 0.0;
		coinReturnBalance = 0.0;
		setDisplay("INSERT COIN");
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
