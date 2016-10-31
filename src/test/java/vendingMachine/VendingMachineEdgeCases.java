package vendingMachine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import vendingMachine.VendingMachineConstants.Button;

public class VendingMachineEdgeCases {
	private VendingMachine vend;
	private static Coin nickel, dime, quarter;
	private static Product cola, chips, candy;
	
	@BeforeClass
	public static void runOnceBeforeClass() {
		nickel = new Coin(CoinConstants.WEIGHT_NICKEL, CoinConstants.EDGE_NICKEL);
		dime = new Coin(CoinConstants.WEIGHT_DIME, CoinConstants.EDGE_DIME);
		quarter = new Coin(CoinConstants.WEIGHT_QUARTER, CoinConstants.EDGE_QUARTER);
		
		cola = new Product(ProductConstants.NAME_COLA, ProductConstants.COST_COLA);
		chips = new Product(ProductConstants.NAME_CHIPS, ProductConstants.COST_CHIPS);
		candy = new Product(ProductConstants.NAME_CANDY, ProductConstants.COST_CANDY);
	}

	@Before
	public void setUp() throws Exception {
		this.vend = new VendingMachine();
		vend.addProduct(cola);
		vend.addProduct(chips);
		vend.addProduct(candy);
	}
	
//	Sold Out
//
//	As a customer
//	I want to be told when the item I have selected is not available
//	So that I can select another item
//
//	When the item selected by the customer is out of stock, the machine displays SOLD OUT. If the display is checked again, it will display the amount of 
//	money remaining in the machine or INSERT COIN if there is no money in the machine.
//
//	Exact Change Only
//
//	As a customer
//	I want to be told when exact change is required
//	So that I can determine if I can buy something with the money I have before inserting it
//
//	When the machine is not able to make change with the money in the machine for any of the items that it sells, it will display EXACT CHANGE 
//	ONLY instead of INSERT COIN
	
	@Test
	public void VendingMachineDisplaysSOLDOUTWhenProductIsOutOfStock() throws InterruptedException {
		for (int i = 0; i < 4; ++i) {
			vend.insertCoin(quarter);
		}		
		assertEquals(cola, vend.pushButton(Button.COLA));
		for (int i = 0; i < 4; ++i) {
			vend.insertCoin(quarter);
		}
		assertNull(vend.pushButton(Button.COLA));
		assertEquals(VendingMachineConstants.DISPLAY_SOLDOUT, vend.getDisplay());
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		        		assertEquals(ProductConstants.COST_COLA,  vend.getDisplay());
		            }
		        }, 
		        5000
		);
	}
	
	@Test
	public void VendingMachineDisplaysEXACTCHANGEONLYWhenBinsAreEmpty() {
		assertEquals(VendingMachineConstants.DISPLAY_EXACTCHANGE, vend.getDisplay());		
	}
	
	@Test
	public void VendingMachineDisplaysEXACTCHANGEONLYWhenNotEnoughChangeForItem() throws InterruptedException {
		vend.fillChangeBins(1);
		for (int i = 0; i < 4; ++i) vend.insertCoin(quarter);
		vend.pushButton(Button.CHIPS);
		assertEquals(VendingMachineConstants.DISPLAY_EXACTCHANGE, vend.getDisplay());
	}
	
	@Test
	public void VendingMachineGivesCorrectChange() throws InterruptedException {
		vend.fillChangeBins(1);
		for (int i = 0; i < 2; ++i) {
			vend.insertCoin(quarter);
			vend.insertCoin(dime);
		}
		vend.insertCoin(nickel);
		vend.pushButton(Button.CANDY);
		assertEquals(CoinConstants.VALUE_DIME, vend.getCoinReturnBalance(), .01);
	}
}


