package com.foo.bonus;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BonusGameDataIntegrationTests {

	@Autowired
	private BonusGameData data;

	@Test
	public void contextLoads() {
		assertNotNull(data);

		assertNotNull(data.getStateMachine());
	}

	@Test
	public void stateInfoIsCorrectAfterReset() {
		data.resetRewards();

		assertEquals(0, data.getPickedRewards().size());
		assertEquals(4, data.getAdditionalRewards().size());
		assertEquals(12, data.getUnopenedBoxes().size());
	}

	@Test
	public void rewardsRetrievesCorrectCollection() {
		data.resetRewards();

		data.getStateMachine().start();

		assertEquals(BonusGameState.OPENING_BOXES, data.getState());
		List<BonusGameRewardEvent> rewards = data.getRewardsToPickFrom();
		assertEquals(data.getUnopenedBoxes(), rewards);

		data.getStateMachine().sendEvent(BonusGameRewardEvent.WIN_EXTRA_LIFE);
		assertEquals(BonusGameState.OPENING_BOXES_WITH_EXTRA_LIFE, data.getState());
		rewards = data.getRewardsToPickFrom();
		assertEquals(data.getUnopenedBoxes(), rewards);

		data.getStateMachine().sendEvent(BonusGameRewardEvent.SHOW_GAME_OVER);
		assertEquals(BonusGameState.OPENING_BOXES, data.getState());
		rewards = data.getRewardsToPickFrom();
		assertEquals(data.getUnopenedBoxes(), rewards);

		data.getStateMachine().sendEvent(BonusGameRewardEvent.SHOW_GAME_OVER);
		assertEquals(BonusGameState.OPENING_ADDITIONAL_REWARD, data.getState());
		rewards = data.getRewardsToPickFrom();
		assertEquals(data.getAdditionalRewards(), rewards);

		data.getStateMachine().sendEvent(BonusGameRewardEvent.WIN_5);
		assertEquals(BonusGameState.GAME_OVER, data.getState());
		assertThrows(IllegalStateException.class,
				() -> {
					data.getRewardsToPickFrom();
				});
	}

}
