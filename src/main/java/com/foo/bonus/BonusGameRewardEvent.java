package com.foo.bonus;

public enum BonusGameRewardEvent {

	WIN_100(100),

	WIN_20(20),

	WIN_10(10),

	WIN_5(5),

	WIN_EXTRA_LIFE(0),

	SHOW_GAME_OVER(0),

	WIN_SECOND_CHANCE(0);

	private final int win;

	BonusGameRewardEvent(final int win) {
		this.win = win;
	}

	public int getWin() {
		return win;
	}

}
