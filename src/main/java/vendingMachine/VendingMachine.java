package vendingMachine;

import java.util.ArrayList;
import java.util.List;

import static vendingMachine.VendingMachineConstants.*;

public class VendingMachine {	

	private String display;
	private CoinPaymentSystem paymentSystem;
	public List<Product> products = new ArrayList<Product>();
	
	// Default constructor
	VendingMachine() {
		paymentSystem = new CoinPaymentSystem();
		setDefaultDisplay();
	}

	public boolean insertCoin(Coin c) {
		if (paymentSystem.insertMoney(c)) {
			setDisplay(String.format("%.2f", paymentSystem.getCurrentBalance()));
			return true;
		}
		else return false;
	}
	
	public void fillChangeBins(int num) {
		paymentSystem.fillChangeBins(num);
		setDefaultDisplay();
	}
	
	public Product pushButton(Button button) throws InterruptedException {
		for (Product product : products) {
			if (button.Value() == product.getName() && product.getQuantity() > 0) {
				if (!exactChangeIsNeededFor(product)) {
					return vendProductIfEnoughChangeIsInserted(product);
				}
			}
			else if (button.Value() == product.getName() && product.getQuantity() == 0) {
				setDisplay(DISPLAY_SOLDOUT);
				resetDisplay();
			}
		}
		return null;
	}
	
	private Product vendProductIfEnoughChangeIsInserted(Product product) {
		if (paymentSystem.currentBalance() >= product.getPrice()) {
			return vendProduct(product);
		}				
		else setDisplay("PRICE " + product.getPrice());				
		resetDisplay();				
		return null;
	}
	
	private boolean exactChangeIsNeededFor(Product product) {
		if (paymentSystem.exactChangeIsNeeded(product.getPrice())) {
			setDisplay(DISPLAY_EXACTCHANGE);
			resetDisplay();
			return true;
		}		
		return false;
	}
	private Product vendProduct(Product product) {
		product.setQuantity(product.getQuantity() - 1);
		paymentSystem.depositCurrentBalance(product.getPrice());
		setDefaultDisplay();
		return product;
	}

	public void pushReturnButton() {
		paymentSystem.returnAllCurrency();
		setDefaultDisplay();
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
		display = paymentSystem.exactChangeIsNeeded(0) ? DISPLAY_EXACTCHANGE :DISPLAY_DEFAULT;
	}
	
	public void resetDisplay() {
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		        		if (paymentSystem.currentBalance() == 0.0) setDefaultDisplay();
		        		else setDisplay(String.format("%.2f", 
		        				paymentSystem.getCurrentBalance()));
		            }
		        }, 
		       3000
		);
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public double getCurrentBalance() {
		return paymentSystem.getCurrentBalance();
	}

	public double getCoinReturnBalance() {
		return paymentSystem.getCoinReturnbalance();
	}

	public void emptyCoinReturn() {
		paymentSystem.emptyChangeReturn();
	}

}
