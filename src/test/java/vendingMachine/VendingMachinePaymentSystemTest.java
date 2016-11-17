package vendingMachine;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class VendingMachinePaymentSystemTest {
	private CoinPaymentSystem pay = new CoinPaymentSystem();
private static Coin nickel, dime, quarter, cent;
	
	@BeforeClass
	public static void runOnceBeforeClass() {
		nickel = new Coin(CoinConstants.WEIGHT_NICKEL, CoinConstants.EDGE_NICKEL);
		dime = new Coin(CoinConstants.WEIGHT_DIME, CoinConstants.EDGE_DIME);
		quarter = new Coin(CoinConstants.WEIGHT_QUARTER, CoinConstants.EDGE_QUARTER);
		cent = new Coin(CoinConstants.WEIGHT_CENT, CoinConstants.EDGE_CENT);	
	}
	
	@Test
	public void VendingMachinePlacesRejectedCoinsInCoinReturn() {
		assertEquals(cent, pay.returnInvalidCurrency(cent));
	}

}
