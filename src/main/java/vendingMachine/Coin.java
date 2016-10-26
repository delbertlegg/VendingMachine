package vendingMachine;

import vendingMachine.CoinConstants.*;

public class Coin {
	private double weight;	
	private CoinEdge edge;
	
	// Provided constants for thickness and diameter, but for the purposes of validation in this exercise,
	// weight and edge type will be sufficient. Will keep constants in case further validation is needed.
	
	// Default constructor
	Coin() {
		this.weight = 0;
		this.edge = null;
	}
	
	Coin(double w, CoinEdge e) {
		this.weight = w;
		this.edge = e;
	}
	
	public boolean isValidCoin() {
		if (this.edge == CoinEdge.SMOOTH) {
			if (this.weight <= CoinConstants.WEIGHT_CENT) return false;
			else return true;
		}
		else {
			if (this.weight > CoinConstants.WEIGHT_QUARTER) return false;
			else return true;			
		}
	}
	
	// Note: For the purpose of this exercise, I'm using an equality check against a constant double. In a real world application, I may add a
	// lower and upper bound to this check in case of measurement fluctuations (wear, girt/debris, etc.)
	public double coinValue() {
		if (this.weight < CoinConstants.WEIGHT_CENT) return 0.1;
		else if (this.weight < CoinConstants.WEIGHT_NICKEL) return 0.01;
		else if (this.weight < CoinConstants.WEIGHT_QUARTER) return 0.05;
		else return 0.25;
	}
}
