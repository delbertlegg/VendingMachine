package vendingMachine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import vendingMachine.VendingMachineConstants.*;

public class VendingMachine {	
	private double currentBalance;
	private double coinReturnBalance;
	private String display;
	public List<Product> products = new ArrayList<Product>();
	private Map<Coin, Integer> change = new HashMap<Coin, Integer>();
	private List<Coin> currentCoins = new ArrayList<Coin>();

	
	// Default constructor
	VendingMachine() {
		currentBalance = 0.0;
		coinReturnBalance = 0.0;
		setDisplay(exactChangeIsNeeded() ? VendingMachineConstants.DISPLAY_EXACTCHANGE : VendingMachineConstants.DISPLAY_DEFAULT);
		change.put(new Coin(CoinConstants.WEIGHT_DIME, CoinConstants.EDGE_DIME), 0);
		change.put(new Coin(CoinConstants.WEIGHT_NICKEL, CoinConstants.EDGE_NICKEL), 0);
		change.put(new Coin(CoinConstants.WEIGHT_QUARTER, CoinConstants.EDGE_QUARTER), 0);
	}
	
	public boolean insertCoin(Coin c) {
		if (c.isValidCoin()) {
			currentBalance += c.getValue();
			setDisplay(String.format("%.2f", getCurrentBalance()));
			return true;
		}
		else {
			returnInvalidCoin(c);
			return false;
		}
	}

	public double returnInvalidCoin(Coin c) {
		coinReturnBalance += c.getValue();
		return BigDecimal.valueOf(coinReturnBalance).setScale(3, RoundingMode.HALF_UP).doubleValue();
	}
	
	public Product pushButton(Button button) throws InterruptedException {
		for (Product product : products) {
			if (button.Value() == product.getName() && product.getQuantity() > 0) {
				if (currentBalance >= product.getPrice()) {
					product.setQuantity(product.getQuantity() - 1);
					setCoinReturnBalance(currentBalance - product.getPrice());
					setCurrentBalance(0.0);
					setDisplay(VendingMachineConstants.DISPLAY_DEFAULT);
					return product;
				}				
				else setDisplay("PRICE " + product.getPrice());				
				resetDisplay();				
			}
			else if (button.Value() == product.getName() && product.getQuantity() == 0) {
				setDisplay(VendingMachineConstants.DISPLAY_SOLDOUT);
				resetDisplay();
			}
		}
		return null;
	}
	
	public void pushReturnButton() {
		coinReturnBalance = currentBalance;
		currentBalance = 0.0;
		display = VendingMachineConstants.DISPLAY_DEFAULT;		
	}
	
	// Simulating emptying of coin return for this exercise
	public void emptyCoinReturn() {
		coinReturnBalance = 0.0;
	}
	
	public void addProduct(Product product) {
		for (Product p : products) {
			if (p.getName().equals(product.getName())) {
				p.setQuantity(p.getQuantity() + 1);
				return;
			}			
		}
		product.setQuantity(1);
		products.add(product);		
	}
	
	public void resetDisplay() {
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		        		if (currentBalance == 0.0) setDisplay(VendingMachineConstants.DISPLAY_DEFAULT);
		        		else setDisplay(String.format("%.2f", getCurrentBalance()));
		            }
		        }, 
		       3000
		);
	}
	
	public boolean exactChangeIsNeeded() {
		Collection<Integer> coins = change.values();
		for (int num : coins) {
			if (num > 0) return false;
		}
		return true;
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
