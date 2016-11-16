package vendingMachine;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class VendingMachineConstants {
	public static final String DISPLAY_SOLDOUT = "SOLD OUT";
	public static final String DISPLAY_EXACTCHANGE = "EXACT CHANGE ONLY";
	public static String DISPLAY_DEFAULT = "INSERT COIN";
	public enum Button {
		COLA(ProductConstants.NAME_COLA), 
		CHIPS(ProductConstants.NAME_CHIPS), 
		CANDY(ProductConstants.NAME_CANDY);
		
		private String value;
		Button(String v) {
			this.value = v;
		}
		
		public String Value() {
			return this.value;
		}
	}
	
	public static double formattedDouble(double d) {
		return BigDecimal.valueOf(d).setScale(3, RoundingMode.HALF_UP).doubleValue();
	}

}
