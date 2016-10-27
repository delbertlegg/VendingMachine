package vendingMachine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import vendingMachine.VendingMachineConstants.Button;

public class VendingMachineCoinReturnTest {
	private VendingMachine vend;
	private static Coin nickel, dime, quarter;
	private static Product candy;
		
	@BeforeClass
	public static void runOnceBeforeClass() {
		nickel = new Coin(CoinConstants.WEIGHT_NICKEL, CoinConstants.EDGE_NICKEL);
		dime = new Coin(CoinConstants.WEIGHT_DIME, CoinConstants.EDGE_DIME);
		quarter = new Coin(CoinConstants.WEIGHT_QUARTER, CoinConstants.EDGE_QUARTER);
		
		candy = new Product(ProductConstants.NAME_CANDY, ProductConstants.COST_CANDY);
	}

	@Before
	public void setUp() throws Exception {
		this.vend = new VendingMachine();
		vend.addProduct(candy);
		
		for (int i = 0; i < 3; ++i) {
			vend.insertCoin(quarter);
		}
		vend.insertCoin(dime);
		vend.insertCoin(nickel);
	}
	
//	Make Change
//
//	As a vendor
//	I want customers to receive correct change
//	So that they will use the vending machine again
//
//	When a product is selected that costs less than the amount of money in the machine, then the remaining amount is placed in the coin return.
//
//	Return Coins
//
//	As a customer
//	I want to have my money returned
//	So that I can change my mind about buying stuff from the vending machine
//
//	When the return coins button is pressed, the money the customer has placed in the machine is returned and the display shows INSERT COIN.
	
	@Test
	public void VendingMachineReturnsChangeWhenProductCostsLessThanCurrentBalance() throws InterruptedException {
		double val = vend.getCurrentBalance();
		vend.pushButton(Button.CANDY);
		assertEquals(val - candy.getPrice(), vend.getCoinReturnBalance(), .01);
	}
	
	@Test
	public void VendingMachineReturnsCurrentBalanceWhenReturnButtonIsPressed() {
		double val = vend.getCurrentBalance();
		vend.pushReturnButton();
		assertEquals(val, vend.getCoinReturnBalance(), .01);
		assertEquals(VendingMachineConstants.DISPLAY_DEFAULT, vend.getDisplay());
	}
}
