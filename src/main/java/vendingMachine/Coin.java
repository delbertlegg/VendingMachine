package vendingMachine;

import static vendingMachine.CoinConstants.*;

public class Coin extends Money {
	private double weight;	
	private CoinEdge edge;
	private double value;
	
	// Provided constants for thickness and diameter, but for the purposes of validation in this exercise,
	// weight and edge type will be sufficient. Will keep constants in case further validation is needed.
	
	// Default constructor
	Coin() {
		edge = null;
	}
	
	Coin(double w, CoinEdge e) {
		weight = w;
		edge = e;
		setValue();
	}
	

	private void setValue() {
		if (this.weight < WEIGHT_CENT) value =  VALUE_DIME;
		else if (this.weight < WEIGHT_NICKEL) value =  VALUE_CENT;
		else if (this.weight < WEIGHT_QUARTER) value = VALUE_NICKEL;
		else value =  VALUE_QUARTER;
	}
	
	// Note: For the purpose of this exercise, I'm using an equality check against a constant double. In a real world application, I may add a
	// lower and upper bound to this check in case of measurement fluctuations (wear, dirt/debris, etc.)
	public boolean isValid() {
		if (this.edge == CoinEdge.SMOOTH) {
			if (this.weight <= WEIGHT_CENT) return false;
			else return true;
		}
		else {
			if (this.weight > WEIGHT_QUARTER) return false;
			else return true;			
		}
	}
	
	public double getValue() {
		return value;
	}
	
	
	public boolean isQuarter() {
		return value == VALUE_QUARTER;
	}
	
	public boolean isDime() {
		return value == VALUE_DIME;
	}
	
	public boolean isNickel() {
		return value == VALUE_NICKEL;
	}
}
