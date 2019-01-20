package com.foo.bonus;

import com.foo.random.IRandomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope(value = "prototype")
public class BonusGame {

	@Autowired
	private IRandomService randomService;

	@Autowired
	private BonusGameData data;

	public void reset() {
		data.resetRewards();

		data.getStateMachine().stop();

		data.getStateMachine().start();
	}

	public void pickReward() {
		List<BonusGameRewardEvent> rewards = data.getRewardsToPickFrom();

		BonusGameRewardEvent pickedReward = randomService.popOne(rewards);

		data.addPickedReward(pickedReward);

		data.getStateMachine().sendEvent(pickedReward);

		if (BonusGameRewardEvent.WIN_SECOND_CHANCE.equals(pickedReward)) {
			data.resetUnopenedBoxes();
		}
	}

	public boolean isGameOver() {
		return BonusGameState.GAME_OVER.equals(data.getStateMachine().getState().getId());
	}

	public int getWinnings() {
		return data.sumWinnings();
	}

	//<editor-fold desc="Testing">
	BonusGameData getData() {
		return data;
	}
	//</editor-fold>

}