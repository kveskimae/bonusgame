package com.foo.quant;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class BonusGameWalkPath {
	public final double probability;
	public final int winnings, lives, deaths;

	public BonusGameWalkPath(final double probability, final int winnings, final int lives, final int deaths) {
		this.probability = probability;
		this.winnings = winnings;
		this.lives = lives;
		this.deaths = deaths;
	}

	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}

	public boolean isGameOver() {
		return deaths >= lives;
	}

}
