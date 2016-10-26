package vendingMachine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class VendingMachineEdgeCases {
	private VendingMachine vend;
	private static Coin nickel, dime, quarter, cent;
	
	@BeforeClass
	public static void runOnceBeforeClass() {
		nickel = new Coin(CoinConstants.WEIGHT_NICKEL, CoinConstants.EDGE_NICKEL);
		dime = new Coin(CoinConstants.WEIGHT_DIME, CoinConstants.EDGE_DIME);
		quarter = new Coin(CoinConstants.WEIGHT_QUARTER, CoinConstants.EDGE_QUARTER);
		cent = new Coin(CoinConstants.WEIGHT_CENT, CoinConstants.EDGE_CENT);	
	}

	@Before
	public void setUp() throws Exception {
		this.vend = new VendingMachine();		
	}
	
//

//
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
	public void VendingMachineDisplaysSOLDOUTWhenProductIsOutOfStock() {
		
	}
	
	@Test
	public void VendingMachineDisplaysEXACTCHANGEONLYWhenNotAbleToMakeChange() {
		
	}
}


