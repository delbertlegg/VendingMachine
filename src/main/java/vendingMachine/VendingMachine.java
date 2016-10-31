package vendingMachine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import vendingMachine.VendingMachineConstants.*;


public class VendingMachine {	
	private static final int INV_QUARTERS = 0;
	private static final int INV_DIMES = 1;
	private static final int INV_NICKELS = 2;
	private static final int NUM_COINS = 3;
	private String display;
	public List<Product> products = new ArrayList<Product>();
	private int[] changeBins = new int[NUM_COINS];
	private List<Coin> currentCoins = new ArrayList<Coin>();
	private List<Coin> returnCoins = new ArrayList<Coin>();

	
	// Default constructor
	VendingMachine() {
		setDefaultDisplay();
	}
	public void fillChangeBins(int num) {
		for (int i = 0; i < NUM_COINS; ++i) {
			changeBins[i] = num;
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
					return vendProduct(product);
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
	
	private Product vendProduct(Product product) {
		product.setQuantity(product.getQuantity() - 1);
		depositCurrentBalance(product.getPrice());
		setDefaultDisplay();
		return product;
	}
	
	private void depositCurrentBalance(double price) {
		Iterator<Coin> it = currentCoins.listIterator();
		while(it.hasNext()) {
			Coin coin = it.next();
				if (coin.getValue() <= price) {
					price = formattedDouble(price - coin.getValue());
					moveCoinToChangeBin(coin);
					it.remove();
				}
			}
			
		if (!currentCoins.isEmpty()) makeChange(getCurrentBalance());
		
		it = currentCoins.listIterator();
		while (it.hasNext()) {
				Coin coin = it.next();
				moveCoinToChangeBin(coin);
				it.remove();
				break;
			}		
	}

	private void moveCoinToChangeBin(Coin coin) {
		if(coin.isQuarter()) changeBins[INV_QUARTERS]++;
		else if(coin.isNickel()) changeBins[INV_NICKELS]++;
		else changeBins[INV_DIMES]++;
	}

	private void makeChange(double price) {
		while (price > 0) {
			for (int i = 0; i < NUM_COINS; ++i) {
				for (int j = 0; j < changeBins[i]; ++j) {
					if (i == INV_QUARTERS && price >= CoinConstants.VALUE_QUARTER) {
						price =  formattedDouble(price - CoinConstants.VALUE_QUARTER);
						returnCoins.add(new Coin(CoinConstants.WEIGHT_QUARTER, CoinConstants.EDGE_QUARTER));
					}
					else if (i == INV_DIMES && price >= CoinConstants.VALUE_DIME) {
						price = formattedDouble(price - CoinConstants.VALUE_DIME);
						returnCoins.add(new Coin(CoinConstants.WEIGHT_DIME, CoinConstants.EDGE_DIME));
					}
					else if (i == INV_NICKELS && price >= CoinConstants.VALUE_NICKEL) {
						price = formattedDouble(price - CoinConstants.VALUE_NICKEL);
						returnCoins.add(new Coin(CoinConstants.WEIGHT_NICKEL, CoinConstants.EDGE_NICKEL));
						}
				}			
			}
		}
	}

	public void pushReturnButton() {
		returnCoins.addAll(currentCoins);
		currentCoins.clear();
		setDefaultDisplay();
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
	
	public void setDefaultDisplay() {
		display = exactChangeIsNeeded(0) ? VendingMachineConstants.DISPLAY_EXACTCHANGE :VendingMachineConstants.DISPLAY_DEFAULT;
	}
	
	public void resetDisplay() {
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		        		if (currentBalance() == 0.0) setDefaultDisplay();
		        		else setDisplay(String.format("%.2f", getCurrentBalance()));
		            }
		        }, 
		       3000
		);
	}
	
	public boolean exactChangeIsNeeded(double price) {
		if (price == 0) {
			for (int num : changeBins) if (num > 0) return false;
			return true;
		}
		double neededChange = currentBalance() - price;
		for (int i = 0; i < NUM_COINS; ++i) {
			for (int j = 0; j < changeBins[i]; ++j) {
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
