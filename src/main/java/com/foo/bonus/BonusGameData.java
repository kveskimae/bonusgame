package com.foo.bonus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Component
@Scope(value = "prototype")
@WithStateMachine
public class BonusGameData {

	@Autowired
	private StateMachine<BonusGameState, BonusGameRewardEvent> stateMachine;

	private List<BonusGameRewardEvent> unopenedBoxes = new ArrayList<>();

	private List<BonusGameRewardEvent> additionalRewards = new ArrayList<>();

	private List<BonusGameRewardEvent> pickedRewards = new ArrayList<>();

	public StateMachine<BonusGameState, BonusGameRewardEvent> getStateMachine() {
		requireNonNull(stateMachine);

		return stateMachine;
	}

	public List<BonusGameRewardEvent> getRewardsToPickFrom() {
		BonusGameState currentState = getState();

		switch (currentState) {
			case OPENING_ADDITIONAL_REWARD:
				return additionalRewards;
			case OPENING_BOXES:
			case OPENING_BOXES_WITH_EXTRA_LIFE:
				return unopenedBoxes;
			case GAME_OVER:
				throw new IllegalStateException("Game has already finished");
			default:
				throw new UnsupportedOperationException("Unknown state: " + currentState);
		}
	}

	public BonusGameState getState() {
		requireNonNull(stateMachine);
		requireNonNull(stateMachine.getState());
		requireNonNull(stateMachine.getState().getId());

		return stateMachine.getState().getId();
	}

	public int sumWinnings() {
		return pickedRewards.stream().mapToInt(box -> box.getWin()).sum();
	}

	public void addPickedReward(BonusGameRewardEvent pickedReward) {
		pickedRewards.add(pickedReward);
	}

	//<editor-fold desc="Helpers">
	List<BonusGameRewardEvent> getAdditionalRewards() {
		return additionalRewards;
	}

	List<BonusGameRewardEvent> getPickedRewards() {
		return pickedRewards;
	}

	List<BonusGameRewardEvent> getUnopenedBoxes() {
		return unopenedBoxes;
	}

	void resetRewards() {
		resetUnopenedBoxes();

		resetAdditionalRewards();

		pickedRewards.clear();
	}

	void resetAdditionalRewards() {
		additionalRewards.clear();

		additionalRewards.add(BonusGameRewardEvent.WIN_5);
		additionalRewards.add(BonusGameRewardEvent.WIN_10);
		additionalRewards.add(BonusGameRewardEvent.WIN_20);
		additionalRewards.add(BonusGameRewardEvent.WIN_SECOND_CHANCE);
	}

	void resetUnopenedBoxes() {
		unopenedBoxes.clear();

		unopenedBoxes.add(BonusGameRewardEvent.WIN_100);

		unopenedBoxes.add(BonusGameRewardEvent.WIN_20);
		unopenedBoxes.add(BonusGameRewardEvent.WIN_20);

		unopenedBoxes.add(BonusGameRewardEvent.WIN_5);
		unopenedBoxes.add(BonusGameRewardEvent.WIN_5);
		unopenedBoxes.add(BonusGameRewardEvent.WIN_5);
		unopenedBoxes.add(BonusGameRewardEvent.WIN_5);
		unopenedBoxes.add(BonusGameRewardEvent.WIN_5);

		unopenedBoxes.add(BonusGameRewardEvent.WIN_EXTRA_LIFE);

		unopenedBoxes.add(BonusGameRewardEvent.SHOW_GAME_OVER);
		unopenedBoxes.add(BonusGameRewardEvent.SHOW_GAME_OVER);
		unopenedBoxes.add(BonusGameRewardEvent.SHOW_GAME_OVER);
	}
	//</editor-fold>

}
