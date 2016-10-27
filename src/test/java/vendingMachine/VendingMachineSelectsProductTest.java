package vendingMachine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import vendingMachine.VendingMachineConstants.*;

public class VendingMachineSelectsProductTest {
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
	
//
//		Select Product
//
//		As a vendor
//		I want customers to select products
//		So that I can give them an incentive to put money in the machine
//
//		There are three products: cola for $1.00, chips for $0.50, and candy for $0.65. When the respective button is pressed and enough money has been inserted, 
//		the product is dispensed and the machine displays THANK YOU. If the display is checked again, it will display INSERT COIN and the current amount will be 
//		set to $0.00. If there is not enough money inserted then the machine displays PRICE and the price of the item and subsequent checks of the display will 
//		display either INSERT COIN or the current amount as appropriate.
		
	@Test
	public void VendColaWhenEnoughMoneyIsInsertedAndButtonIsPushed() throws InterruptedException {
		for (int i = 0; i < 4; ++i) {
			vend.insertCoin(quarter);
		}
		assertEquals(cola, vend.pushButton(Button.COLA));		
	}
	
	@Test
	public void VendChipsWhenEnoughMoneyIsInsertedAndButtonIsPushed() throws InterruptedException {
		for (int i = 0; i < 2; ++i) {
			vend.insertCoin(quarter);
		}
		assertEquals(chips, vend.pushButton(Button.CHIPS));
	}
	
	@Test
	public void VendCandyWhenEnoughMoneyIsInsertedAndButtonIsPushed() throws InterruptedException {
		for (int i = 0; i < 2; ++i) {
			vend.insertCoin(quarter);
		}
		vend.insertCoin(dime);
		vend.insertCoin(nickel);
		assertEquals(candy, vend.pushButton(Button.CANDY));
	}
	
	@Test
	public void DisplayReadsInsertCoinAndCurrentAmountResetsWhenProductIsVended() throws InterruptedException {
		for (int i = 0; i < 2; ++i) {
			vend.insertCoin(quarter);
		}
		vend.insertCoin(dime);
		vend.insertCoin(nickel);
		vend.pushButton(Button.CANDY);
		assertEquals(VendingMachineConstants.DISPLAY_DEFAULT, vend.getDisplay());
		assertEquals(0.0, vend.getCurrentBalance(), .01);
	}
	
	@Test
	public void DisplayReadsPRICEandPriceOfItemWhenNotEnoughMoneyIsInsertedAndButtonIsPressed() throws InterruptedException {
		vend.insertCoin(quarter);
		vend.insertCoin(dime);
		vend.insertCoin(nickel);
		vend.pushButton(Button.CANDY);
		
		assertEquals("PRICE "+ candy.getPrice(), vend.getDisplay());		
	}
}
