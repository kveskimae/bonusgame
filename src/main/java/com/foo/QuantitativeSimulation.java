package com.foo;

import com.foo.quant.BonusGameQuantitativeSimulation;

public class QuantitativeSimulation {

	public static void main(String[] args) {
		double expectedAvgWinnings = BonusGameQuantitativeSimulation.calcExpectedAvgWinnings();

		System.out.println(
				String.format(
						"Sanity check, expected average winnings by calculation: %.2f",
						expectedAvgWinnings
				)
		);
	}

}

