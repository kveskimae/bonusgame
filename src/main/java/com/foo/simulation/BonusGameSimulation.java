package com.foo.simulation;

import com.foo.bonus.BonusGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BonusGameSimulation {

	@Autowired
	BonusGame bonusGame;

	private long totalWinnings = 0;

	@Value("${simulation.rounds}")
	private Integer rounds;

	public void run() {
		int onePct = rounds / 100;

		for (int i = 1; i <= rounds; i++) {
			bonusGame.reset();

			while (!bonusGame.isGameOver()) {
				bonusGame.pickReward();
			}

			int winnings = bonusGame.getWinnings();

			totalWinnings += winnings;

			if (i > 1 && i % onePct == 0) {
				System.out.println(String.format(".. %d%%", (i / onePct)));
			}
		}
	}

	public double getAverageWinnings() {
		double avgWinnings = (double) totalWinnings / (double) rounds;

		return avgWinnings;
	}

}
