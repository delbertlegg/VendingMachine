package vendingMachine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vendingMachine.VendingMachineConstants.*;


public class VendingMachine {	
	private static final int INV_QUARTERS = 0;
	private static final int INV_DIMES = 1;
	private static final int INV_NICKELS = 2;
	private static final int NUM_COINS = 3;
	private String display;
	public List<Product> products = new ArrayList<Product>();
	private int[] change = new int[NUM_COINS];
	private List<Coin> currentCoins = new ArrayList<Coin>();
	private List<Coin> returnCoins = new ArrayList<Coin>();

	
	// Default constructor
	VendingMachine() {
		setDisplay(exactChangeIsNeeded(0) ? VendingMachineConstants.DISPLAY_EXACTCHANGE : VendingMachineConstants.DISPLAY_DEFAULT);
	}
	public void fillChangeBins(int num) {
		for (int i = 0; i < NUM_COINS; ++i) {
			change[i] = num;
		}
	}
	
	public boolean insertCoin(Coin c) {
		if (c.isValidCoin()) {
			currentCoins.add(c);
			setDisplay(String.format("%.2f", getCurrentBalance()));
			return true;
		}
		else {
			returnInvalidCoin(c);
			return false;
		}
	}
	
	public double currentBalance() {
		double total = 0.0;
		for (Coin coin : currentCoins) {
			total+= coin.getValue();
		}
		return total;
	}

	public Coin returnInvalidCoin(Coin c) {
		returnCoins.add(c);
		return c;
	}
	
	public Product pushButton(Button button) throws InterruptedException {
		for (Product product : products) {
			if (button.Value() == product.getName() && product.getQuantity() > 0) {
				if (exactChangeIsNeeded(product.getPrice())) {
					setDisplay(VendingMachineConstants.DISPLAY_EXACTCHANGE);
					resetDisplay();
				}
				else if (currentBalance() >= product.getPrice()) {
					product.setQuantity(product.getQuantity() - 1);
					depositCurrentBalance(product.getPrice());
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
	
	private void depositCurrentBalance(double price) {
		Collections.sort(currentCoins);
		while (price > 0) {
			for (Coin coin : currentCoins) {
				if (coin.getValue() <= price) {
					price = formattedDouble(price - coin.getValue());
					moveCoinToChangeBin(coin);		
					break;
				}
			}			
		}
		if (!currentCoins.isEmpty()) makeChange();
	}

	private void moveCoinToChangeBin(Coin coin) {
		if(coin.isQuarter()) change[INV_QUARTERS]++;
		else if(coin.isNickel()) change[INV_NICKELS]++;
		else change[INV_DIMES]++;
		currentCoins.remove(coin);
	}

	private void makeChange() {
		returnCoins.addAll(currentCoins);
		currentCoins.clear();
	}

	public void pushReturnButton() {
		makeChange();
		display = exactChangeIsNeeded(0) ? VendingMachineConstants.DISPLAY_EXACTCHANGE :VendingMachineConstants.DISPLAY_DEFAULT;		
	}
	
	// Simulating emptying of coin return for this exercise
	public void emptyCoinReturn() {
		returnCoins.clear();
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
		        		if (currentBalance() == 0.0) setDisplay(VendingMachineConstants.DISPLAY_DEFAULT);
		        		else setDisplay(String.format("%.2f", getCurrentBalance()));
		            }
		        }, 
		       3000
		);
	}
	
	public boolean exactChangeIsNeeded(double price) {
		if (price == 0) {
			for (int num : change) if (num > 0) return false;
			else return true;
		}
		double neededChange = currentBalance() - price;
		for (int i = 0; i < NUM_COINS; ++i) {
			for (int j = 0; j < change[i]; ++j) {
				if (i == INV_QUARTERS && neededChange >= CoinConstants.VALUE_QUARTER) 
					neededChange =  formattedDouble(neededChange - CoinConstants.VALUE_QUARTER);
				else if (i == INV_DIMES && neededChange >= CoinConstants.VALUE_DIME)
					neededChange = formattedDouble(neededChange - CoinConstants.VALUE_DIME);
				else neededChange = formattedDouble(neededChange - CoinConstants.VALUE_NICKEL);
			}			
		}
		if (neededChange > 0) return true;
		return false;
	}
	
	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}
	
	public double getCurrentBalance() {
		return formattedDouble(currentBalance());
	}
	
	private double formattedDouble(double d) {
		return BigDecimal.valueOf(d).setScale(3, RoundingMode.HALF_UP).doubleValue();
	}
	
	public double getCoinReturnBalance() {
		double total = 0.0;
		for (Coin coin : returnCoins) {
			total += coin.getValue();
		}
		return total;
	}
}
