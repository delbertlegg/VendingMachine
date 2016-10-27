package vendingMachine;

public class CoinConstants {
	public enum CoinEdge {
		SMOOTH, RIDGED
	}
	
	public enum CoinWeight {
		CENT(2.500),
		NICKEL(5.000),
		DIME(2.268),
		QUARTER(5.670);		
		
		private double value;
		CoinWeight(double v) {
			this.value = v;
		}
	}
		
	public enum CoinDiam {
		CENT(19.05),
		NICKEL(21.21),
		DIME(17.91),
		QUARTER(24.26);	
		
		private double value;
		CoinDiam(double v) {
			this.value = v;
		}
	}
	
	public enum CoinThick {
		CENT(1.55),
		NICKEL(1.95),
		DIME(1.35),
		QUARTER(1.75);	
		
		private double value;
		CoinThick(double v) {
			this.value = v;
		}
	}		
	
	// Coin weights (g) from least to most	
	public static final double WEIGHT_DIME = 2.268;
	public static final double WEIGHT_CENT = 2.500;
	public static final double WEIGHT_NICKEL = 5.000;
	public static final double WEIGHT_QUARTER = 5.670;
	
	// Coin Diameters (mm)
	public static final double DIAM_CENT = 19.05;
	public static final double DIAM_NICKEL = 21.21;
	public static final double DIAM_DIME = 17.91;
	public static final double DIAM_QUARTER = 24.26;
	
	// Coin thicnkess (mm)
	public static final double THICK_CENT = 1.55;
	public static final double THICK_NICKEL = 1.95;
	public static final double THICK_DIME = 1.35;
	public static final double THICK_QUARTER = 1.75;
	
	// Coin Edge Style
	public static final CoinEdge EDGE_CENT = CoinEdge.SMOOTH;
	public static final CoinEdge EDGE_NICKEL = CoinEdge.SMOOTH;
	public static final CoinEdge EDGE_DIME = CoinEdge.RIDGED;
	public static final CoinEdge EDGE_QUARTER = CoinEdge.RIDGED;
	
	// Coin Values
	public static final double VALUE_CENT = .01;
	public static final double VALUE_NICKEL = .05;
	public static final double VALUE_DIME = .1;
	public static final double VALUE_QUARTER = .25;
	

}
