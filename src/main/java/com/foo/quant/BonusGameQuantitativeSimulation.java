package com.foo.quant;

import com.foo.bonus.BonusGameRewardEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class BonusGameQuantitativeSimulation {

	public static double calcExpectedAvgWinnings() {
		Map<BonusGameRewardEvent, Integer> reward2Count = new TreeMap<>();

		reward2Count.put(BonusGameRewardEvent.WIN_100, 1);

		reward2Count.put(BonusGameRewardEvent.WIN_20, 2);

		reward2Count.put(BonusGameRewardEvent.WIN_5, 5);

		reward2Count.put(BonusGameRewardEvent.WIN_EXTRA_LIFE, 1);

		reward2Count.put(BonusGameRewardEvent.SHOW_GAME_OVER, 3);

		Collection<BonusGameWalkPath> pathsOpeningBoxes = walkAllPathsOpeningBoxes(
				new BonusGameWalkPath(1., 0, 1, 0),
				reward2Count
		);

		double expectedWinningsFromBoxes = pathsOpeningBoxes.stream().mapToDouble(path -> path.probability * path.winnings).sum();

		double ret = 0;

		ret += 0.25 * (expectedWinningsFromBoxes + 5);
		ret += 0.25 * (expectedWinningsFromBoxes + 10);
		ret += 0.25 * (expectedWinningsFromBoxes + 20);

		ret += 0.25 * (2 * expectedWinningsFromBoxes + (1. / 3. * 5 + 1. / 3. * 10 + 1. / 3. * 20));

		return ret;
	}

	static Collection<BonusGameWalkPath> walkAllPathsOpeningBoxes(final BonusGameWalkPath pathThisFar,
																  final Map<BonusGameRewardEvent, Integer> reward2Count) {
		int noOfRewardsRemaining = reward2Count.values().stream().mapToInt(i -> i).sum();

		if (noOfRewardsRemaining <= 1) {

			throw new IllegalStateException(String.format("Should have more rewards remaining: %d", noOfRewardsRemaining));

		}

		Collection<BonusGameWalkPath> ret = new ArrayList<>();

		for (Map.Entry<BonusGameRewardEvent, Integer> entry : reward2Count.entrySet()) {
			BonusGameRewardEvent reward = entry.getKey();

			double probabilityDownNewPath = (double) entry.getValue() / (double) noOfRewardsRemaining;

			int newDeaths = BonusGameRewardEvent.SHOW_GAME_OVER.equals(reward) ? pathThisFar.deaths + 1 : pathThisFar.deaths;
			int newLives = BonusGameRewardEvent.WIN_EXTRA_LIFE.equals(reward) ? pathThisFar.lives + 1 : pathThisFar.lives;

			BonusGameWalkPath newPath = new BonusGameWalkPath(
					pathThisFar.probability * probabilityDownNewPath,
					pathThisFar.winnings + reward.getWin(),
					newLives,
					newDeaths
			);

			if (newPath.isGameOver()) {

				ret.add(newPath);

			} else {

				Map<BonusGameRewardEvent, Integer> newReward2Count = new TreeMap<>(reward2Count);

				Integer newCount = reward2Count.get(reward) - 1;

				if (newCount < 1) {
					newReward2Count.remove(reward);
				} else {
					newReward2Count.put(reward, newCount);
				}

				ret.addAll(walkAllPathsOpeningBoxes(newPath, newReward2Count));
			}
		}

		return ret;
	}

}
