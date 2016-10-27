package vendingMachine;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
   VendingMachineAcceptsCoinsTest.class,
   VendingMachineSelectsProductTest.class,
   VendingMachineCoinReturnTest.class,
   VendingMachineEdgeCases.class
})

	

public class VendingMachineTestSuite {

}
