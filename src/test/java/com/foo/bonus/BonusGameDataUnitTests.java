package com.foo.bonus;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BonusGameDataUnitTests {

	@Test
	void hasInitialInfoAfterResetRewards() {
		BonusGameData data = new BonusGameData();

		data.resetRewards();

		assertEquals(12, data.getUnopenedBoxes().size());
		assertEquals(4, data.getAdditionalRewards().size());
		assertEquals(0, data.getPickedRewards().size());
	}

	@Test
	void winningsAreZeroAfterImmediateGameOver() {
		BonusGameData data = new BonusGameData();

		data.resetRewards();

		data.addPickedReward(BonusGameRewardEvent.SHOW_GAME_OVER);

		assertEquals(0, data.sumWinnings());
	}

	@Test
	void winningsAreSummedCorrectly() {
		BonusGameData data = new BonusGameData();

		data.resetRewards();

		assertEquals(0, data.sumWinnings());

		data.addPickedReward(BonusGameRewardEvent.WIN_5);
		data.addPickedReward(BonusGameRewardEvent.WIN_SECOND_CHANCE);
		data.addPickedReward(BonusGameRewardEvent.SHOW_GAME_OVER);
		data.addPickedReward(BonusGameRewardEvent.WIN_20);
		data.addPickedReward(BonusGameRewardEvent.WIN_20);
		data.addPickedReward(BonusGameRewardEvent.WIN_100);
		data.addPickedReward(BonusGameRewardEvent.SHOW_GAME_OVER);
		data.addPickedReward(BonusGameRewardEvent.WIN_10);

		assertEquals(155, data.sumWinnings());
	}

}
