package vendingMachine;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class VendingMachineAcceptsCoinsTest {
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

	@After
	public void tearDown() throws Exception {
	}		
				
//	Accept Coins
//
//	As a vendor
//	I want a vending machine that accepts coins
//	So that I can collect money from the customer
//
//	The vending machine will accept valid coins (nickels, dimes, and quarters) and reject invalid ones (pennies). When a valid coin is inserted the amount
//	of the coin will be added to the current amount and the display will be updated. When there are no coins inserted, the machine displays INSERT COIN. 
//	Rejected coins are placed in the coin return.
//
//	NOTE: The temptation here will be to create Coin objects that know their value. However, this is not how a real vending machine works. Instead, it 
//	identifies coins by their weight and size and then assigns a value to what was inserted. You will need to do something similar. This can be simulated 
//	using strings, constants, enums, symbols, or something of that nature.

	@Test
	public void VendingMachineAcceptsNickels() {
		assertTrue(vend.insertCoin(nickel));
	}
	
	@Test
	public void VendingMachineAcceptsDimes() {		
		assertTrue(vend.insertCoin(dime));
	}
	
	@Test
	public void VendingMachineAcceptsQuarters() {				
		assertTrue(vend.insertCoin(quarter));
	}
	
	@Test
	public void VendingMachineRejectsPennies() {
		
		assertFalse(vend.insertCoin(cent));
	}
	
	@Test
	public void VendingMachineAddsCoinValueToCurrentAmount() {
		vend.insertCoin(nickel);
		assertTrue(vend.getCurrentBalance() == .05);
		vend.insertCoin(dime);
		assertTrue(vend.getCurrentBalance() == .15);
		vend.insertCoin(quarter);
		assertTrue(vend.getCurrentBalance() == .4);
		vend.insertCoin(cent);
		assertTrue(vend.getCurrentBalance() == .4);
	}
	
	@Test
	public void VendingMachineUpdatesDisplayWhenCoinsAreAdded() {
		vend.insertCoin(nickel);
		assertEquals(vend.getDisplay(), "0.05");
		vend.insertCoin(dime);
		assertEquals(vend.getDisplay(), "0.15");
		vend.insertCoin(quarter);
		assertEquals(vend.getDisplay(), "0.40");
		vend.insertCoin(cent);
		assertEquals(vend.getDisplay(), "0.40");
	}
	
	@Test
	public void VendingMachineDisplaysINSERTCOINWhenNoCoinsAreInserted() {
		assertEquals(vend.getDisplay(), VendingMachineConstants.DISPLAY_DEFAULT);
	}
	
	@Test
	public void VendingMachinePlacesRejectedCoinsInCoinReturn() {
		assertEquals(.01, vend.returnInvalidCoin(cent), .001);
	}

}
