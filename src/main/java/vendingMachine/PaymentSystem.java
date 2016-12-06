package vendingMachine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static vendingMachine.VendingMachineConstants.*;

public abstract class PaymentSystem {
	protected List<Money> currentBalance = new ArrayList<Money>();
	protected List<Money> returnBalance = new ArrayList<Money>();

	public double currentBalance() {
		double total = 0.0;
		for (Money m : currentBalance) {
			total += m.getValue();
		}
		return total;
	}
	
	public Money returnInvalidCurrency(Money m) {
		returnBalance.add(m);
		return m;
	}
	
	public void depositCurrentBalance(double price) {
		Iterator<Money> it = currentBalance.listIterator();
		subtractCostFromCurrentBalance(it, price);
		if (!currentBalance.isEmpty()) makeChange(getCurrentBalance());
		
		it = currentBalance.listIterator();
		depositRemainingBalanceIntoChange(it);
	}
	
	public void depositRemainingBalanceIntoChange(Iterator<Money> it) {
		while (it.hasNext()) {
			Money m = it.next();
			moveCurrencyToChangeBin(m);
			it.remove();
			break;
		}
	}
	
	public void subtractCostFromCurrentBalance(Iterator<Money> it, double price) {
		while(it.hasNext()) {
			Money m = it.next();
			if (m.getValue() <= price) {
				price = formattedDouble(price - m.getValue());
				moveCurrencyToChangeBin(m);
				it.remove();
			}
		}		
	}
		
	public void emptyChangeReturn() {
		returnBalance.clear();
	}
	
	public double getCurrentBalance() {
		return formattedDouble(currentBalance());
	}
	
	public double getCoinReturnbalance() {
		double total = 0.0;
		for (Money m : returnBalance) {
			total = m != null ? total + m.getValue() : total;
		}
		return total;
	}
	
	public void returnAllCurrency() {
		returnBalance.addAll(currentBalance);
		currentBalance.clear();
	}
	
	public abstract boolean insertMoney(Money m);
	public abstract void fillChangeBins(int num);
	public abstract boolean exactChangeIsNeeded(double price);
	public abstract void moveCurrencyToChangeBin(Money m);
	public abstract void makeChange(double price);
	public abstract Money returnMoneyForChange(int i, double price);

}
