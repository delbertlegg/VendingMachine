package vendingMachine;

import static vendingMachine.VendingMachineConstants.*;
import static vendingMachine.CoinConstants.*;

public class CoinPaymentSystem extends PaymentSystem {
	private static final int INV_QUARTERS = 0;
	private static final int INV_DIMES = 1;
	private static final int INV_NICKELS = 2;
	private static final int NUM_COINS = 3;
	
	private int[] changeBins = new int[NUM_COINS];
	
	public CoinPaymentSystem() {
		// TODO Auto-generated constructor stub
	} 

	@Override
	public boolean insertMoney(Money m) {
		if (m.isValid()) {
			currentBalance.add(m);
			return true;
		}
		else {
			returnInvalidCurrency(m);
			return false;
		}
	}

	@Override
	public void fillChangeBins(int num) {
		for (int i = 0; i < NUM_COINS; ++i) {
			changeBins[i] = num;
		}
	}

	@Override
	public boolean exactChangeIsNeeded(double price) {
		if (price == 0) {
			for (int num : changeBins) if (num > 0) return false;
			return true;
		}
		Coin coin = new Coin();
		double neededChange = formattedDouble(currentBalance() - price);
		for (int i = 0; i < NUM_COINS; ++i) {
			for (int j = 0; j < changeBins[i]; ++j) {
				coin = (Coin) returnMoneyForChange(i, neededChange);
				neededChange =  coin != null ? formattedDouble(neededChange - coin.getValue()) : neededChange;
			}
		}
		if (neededChange > 0) {
			return true;
		}
		return false;
	}

	@Override
	public void moveCurrencyToChangeBin(Money m) {
		if(((Coin)m).isQuarter()) changeBins[INV_QUARTERS]++;
		else if(((Coin)m).isNickel()) changeBins[INV_NICKELS]++;
		else changeBins[INV_DIMES]++;
	}

	@Override
	public void makeChange(double price) {
		Coin coin = new Coin();
		while (price > 0) {
			for (int i = 0; i < NUM_COINS; ++i) {
				for (int j = 0; j < changeBins[i]; ++j) {
					coin = (Coin) returnMoneyForChange(i, price);
					price =  coin != null ? formattedDouble(price - coin.getValue()): price;
					returnBalance.add(coin);
				}			
			}
		}
	}

	@Override
	public Money returnMoneyForChange(int i, double price) {
		if (i == INV_QUARTERS && price >= VALUE_QUARTER) {
			return new Coin(WEIGHT_QUARTER, EDGE_QUARTER);
		}
		else if (i == INV_DIMES && price >= VALUE_DIME) {
			return new Coin(WEIGHT_DIME, EDGE_DIME);
		}
		else if (i == INV_NICKELS && price >= VALUE_NICKEL) {
			return new Coin(WEIGHT_NICKEL, EDGE_NICKEL);
		}
		return null;
	}

}
