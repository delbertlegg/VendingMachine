package vendingMachine;

public class VendingMachineConstants {
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

}
