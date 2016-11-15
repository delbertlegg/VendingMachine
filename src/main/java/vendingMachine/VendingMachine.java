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
				if (!exactChangeIsNeededFor(product)) {
					return vendProductIfEnoughChangeIsInserted(product);
				}
			}
			else if (button.Value() == product.getName() && product.getQuantity() == 0) {
				setDisplay(VendingMachineConstants.DISPLAY_SOLDOUT);
				resetDisplay();
			}
		}
		return null;
	}
	
	private Product vendProductIfEnoughChangeIsInserted(Product product) {
		if (currentBalance() >= product.getPrice()) {
			return vendProduct(product);
		}				
		else setDisplay("PRICE " + product.getPrice());				
		resetDisplay();				
		return null;
	}
	
	private boolean exactChangeIsNeededFor(Product product) {
		if (exactChangeIsNeeded(product.getPrice())) {
			setDisplay(VendingMachineConstants.DISPLAY_EXACTCHANGE);
			resetDisplay();
			return true;
		}		
		return false;
	}
	private Product vendProduct(Product product) {
		product.setQuantity(product.getQuantity() - 1);
		depositCurrentBalance(product.getPrice());
		setDefaultDisplay();
		return product;
	}
	
	private void depositCurrentBalance(double price) {
		Iterator<Coin> it = currentCoins.listIterator();
		subtractCostFromCurrentCoins(it, price);		
			
		if (!currentCoins.isEmpty()) makeChange(getCurrentBalance());
		
		it = currentCoins.listIterator();
		depositRemainingCoinsIntoChangeBins(it);
		
	}

	private void depositRemainingCoinsIntoChangeBins(Iterator<Coin> it) {
		while (it.hasNext()) {
			Coin coin = it.next();
			moveCoinToChangeBin(coin);
			it.remove();
			break;
		}				
	}
	
	private void subtractCostFromCurrentCoins(Iterator<Coin> it, double price) {
		while(it.hasNext()) {
			Coin coin = it.next();
			if (coin.getValue() <= price) {
				price = formattedDouble(price - coin.getValue());
				moveCoinToChangeBin(coin);
				it.remove();
			}
		}		
	}
	private void moveCoinToChangeBin(Coin coin) {
		if(coin.isQuarter()) changeBins[INV_QUARTERS]++;
		else if(coin.isNickel()) changeBins[INV_NICKELS]++;
		else changeBins[INV_DIMES]++;
	}

	private void makeChange(double price) {
		Coin coin = new Coin();
		while (price > 0) {
			for (int i = 0; i < NUM_COINS; ++i) {
				for (int j = 0; j < changeBins[i]; ++j) {
					coin = returnCoinsForChange(i, price);
					price =  coin != null ? formattedDouble(price - coin.getValue()): price;
					returnCoins.add(coin);
				}			
			}
		}
	}
	
	private Coin returnCoinsForChange(int i, double price) {
		if (i == INV_QUARTERS && price >= CoinConstants.VALUE_QUARTER) {
			return new Coin(CoinConstants.WEIGHT_QUARTER, CoinConstants.EDGE_QUARTER);
		}
		else if (i == INV_DIMES && price >= CoinConstants.VALUE_DIME) {
			return new Coin(CoinConstants.WEIGHT_DIME, CoinConstants.EDGE_DIME);
		}
		else if (i == INV_NICKELS && price >= CoinConstants.VALUE_NICKEL) {
			return new Coin(CoinConstants.WEIGHT_NICKEL, CoinConstants.EDGE_NICKEL);
		}
		return null;
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
		Coin coin = new Coin();
		double neededChange = formattedDouble(currentBalance() - price);
		for (int i = 0; i < NUM_COINS; ++i) {
			for (int j = 0; j < changeBins[i]; ++j) {
				coin = returnCoinsForChange(i, neededChange);
				neededChange =  coin != null ? formattedDouble(neededChange - coin.getValue()) : neededChange;
			}
		}
		if (neededChange > 0) {
			return true;
		}
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
			total = coin != null ? total + coin.getValue() : total;
		}
		return total;
	}
}
