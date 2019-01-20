package com.foo.bonus;

import com.foo.Simulation;
import com.foo.random.TestRandomConfiguration;
import com.foo.random.TestRandomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Simulation.class, TestRandomConfiguration.class})
class BonusGameIntegrationTests {

	@Autowired
	private BonusGame game;

	@Autowired
	private TestRandomService testRandomService;

	@BeforeEach
	void initGame() {
		assertNotNull(game); // check that context loads

		game.reset();
	}

	@Test
	void correctInfoAfterReset() {
		assertEquals(BonusGameState.OPENING_BOXES, game.getData().getState());

		assertEquals(12, game.getData().getUnopenedBoxes().size());
		assertEquals(4, game.getData().getAdditionalRewards().size());
		assertEquals(0, game.getData().getPickedRewards().size());
	}

	@Test
	void gameIsOverOnlyAfterAdditionalReward() {
		assertFalse(game.isGameOver());

		game.getData().getStateMachine().sendEvent(BonusGameRewardEvent.SHOW_GAME_OVER);

		assertFalse(game.isGameOver());

		game.getData().getStateMachine().sendEvent(BonusGameRewardEvent.WIN_5);

		assertTrue(game.isGameOver());
	}

	@Test
	void pickRewardAddsPickedRewards() {
		assertEquals(0, game.getData().getPickedRewards().size());

		testRandomService.setNextElementToPick(BonusGameRewardEvent.WIN_5);
		game.pickReward();

		assertEquals(1, game.getData().getPickedRewards().size());
		assertEquals(BonusGameRewardEvent.WIN_5, game.getData().getPickedRewards().get(0));

		testRandomService.setNextElementToPick(BonusGameRewardEvent.WIN_EXTRA_LIFE);
		game.pickReward();

		assertEquals(2, game.getData().getPickedRewards().size());
		assertEquals(BonusGameRewardEvent.WIN_EXTRA_LIFE, game.getData().getPickedRewards().get(1));
	}

	@Test
	void additionalRewardsCorrectAfterSecondChance() {
		assertEquals(BonusGameState.OPENING_BOXES, game.getData().getState());

		testRandomService.setNextElementToPick(BonusGameRewardEvent.SHOW_GAME_OVER);
		game.pickReward();
		assertEquals(BonusGameState.OPENING_ADDITIONAL_REWARD, game.getData().getState());

		testRandomService.setNextElementToPick(BonusGameRewardEvent.WIN_SECOND_CHANCE);
		game.pickReward();
		assertEquals(3, game.getData().getAdditionalRewards().size()); // should not contain second chance
		assertFalse(game.getData().getAdditionalRewards().contains(BonusGameRewardEvent.WIN_SECOND_CHANCE));
	}

	@Test
	void boxesCorrectAfterSecondChance() {
		assertEquals(BonusGameState.OPENING_BOXES, game.getData().getState());

		testRandomService.setNextElementToPick(BonusGameRewardEvent.SHOW_GAME_OVER);
		game.pickReward();
		assertEquals(BonusGameState.OPENING_ADDITIONAL_REWARD, game.getData().getState());

		testRandomService.setNextElementToPick(BonusGameRewardEvent.WIN_SECOND_CHANCE);
		game.pickReward();
		assertEquals(12, game.getData().getUnopenedBoxes().size()); // should have shuffled
	}

	@Test
	void pickRewardShiftsStates() {
		assertEquals(BonusGameState.OPENING_BOXES, game.getData().getState());

		testRandomService.setNextElementToPick(BonusGameRewardEvent.WIN_5);
		game.pickReward();
		assertEquals(BonusGameState.OPENING_BOXES, game.getData().getState());

		testRandomService.setNextElementToPick(BonusGameRewardEvent.WIN_EXTRA_LIFE);
		game.pickReward();
		assertEquals(BonusGameState.OPENING_BOXES_WITH_EXTRA_LIFE, game.getData().getState());

		testRandomService.setNextElementToPick(BonusGameRewardEvent.WIN_5);
		game.pickReward();
		assertEquals(BonusGameState.OPENING_BOXES_WITH_EXTRA_LIFE, game.getData().getState());

		testRandomService.setNextElementToPick(BonusGameRewardEvent.SHOW_GAME_OVER);
		game.pickReward();
		assertEquals(BonusGameState.OPENING_BOXES, game.getData().getState());

		testRandomService.setNextElementToPick(BonusGameRewardEvent.WIN_20);
		game.pickReward();
		assertEquals(BonusGameState.OPENING_BOXES, game.getData().getState());

		testRandomService.setNextElementToPick(BonusGameRewardEvent.WIN_100);
		game.pickReward();
		assertEquals(BonusGameState.OPENING_BOXES, game.getData().getState());

		testRandomService.setNextElementToPick(BonusGameRewardEvent.SHOW_GAME_OVER);
		game.pickReward();
		assertEquals(BonusGameState.OPENING_ADDITIONAL_REWARD, game.getData().getState());

		testRandomService.setNextElementToPick(BonusGameRewardEvent.WIN_SECOND_CHANCE);
		game.pickReward();
		assertEquals(BonusGameState.OPENING_BOXES, game.getData().getState());

		testRandomService.setNextElementToPick(BonusGameRewardEvent.WIN_20);
		game.pickReward();
		assertEquals(BonusGameState.OPENING_BOXES, game.getData().getState());

		testRandomService.setNextElementToPick(BonusGameRewardEvent.SHOW_GAME_OVER);
		game.pickReward();
		assertEquals(BonusGameState.OPENING_ADDITIONAL_REWARD, game.getData().getState());

		testRandomService.setNextElementToPick(BonusGameRewardEvent.WIN_10);
		game.pickReward();
		assertEquals(BonusGameState.GAME_OVER, game.getData().getState());

		assertEquals(160, game.getWinnings());
	}

}