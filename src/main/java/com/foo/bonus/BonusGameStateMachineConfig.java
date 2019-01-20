package com.foo.bonus;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachine
@Scope(value = "prototype")
public class BonusGameStateMachineConfig extends EnumStateMachineConfigurerAdapter<BonusGameState, BonusGameRewardEvent> {

	@Override
	public void configure(StateMachineStateConfigurer<BonusGameState, BonusGameRewardEvent> states) throws Exception {
		states
				.withStates()
				.initial(BonusGameState.OPENING_BOXES)
				.states(EnumSet.allOf(BonusGameState.class));
	}

	@Override
	public void configure(StateMachineTransitionConfigurer<BonusGameState, BonusGameRewardEvent> transitions)
			throws Exception {
		transitions
				.withExternal().source(BonusGameState.OPENING_BOXES).target(BonusGameState.OPENING_BOXES).event(BonusGameRewardEvent.WIN_5)
				.and().withExternal().source(BonusGameState.OPENING_BOXES).target(BonusGameState.OPENING_BOXES).event(BonusGameRewardEvent.WIN_20)
				.and().withExternal().source(BonusGameState.OPENING_BOXES).target(BonusGameState.OPENING_BOXES).event(BonusGameRewardEvent.WIN_100)
				.and().withExternal().source(BonusGameState.OPENING_BOXES).target(BonusGameState.OPENING_BOXES_WITH_EXTRA_LIFE).event(BonusGameRewardEvent.WIN_EXTRA_LIFE)
				.and().withExternal().source(BonusGameState.OPENING_BOXES).target(BonusGameState.OPENING_ADDITIONAL_REWARD).event(BonusGameRewardEvent.SHOW_GAME_OVER)
				.and().withExternal().source(BonusGameState.OPENING_BOXES_WITH_EXTRA_LIFE).target(BonusGameState.OPENING_BOXES).event(BonusGameRewardEvent.SHOW_GAME_OVER)
				.and().withExternal().source(BonusGameState.OPENING_ADDITIONAL_REWARD).target(BonusGameState.GAME_OVER).event(BonusGameRewardEvent.WIN_5)
				.and().withExternal().source(BonusGameState.OPENING_ADDITIONAL_REWARD).target(BonusGameState.GAME_OVER).event(BonusGameRewardEvent.WIN_10)
				.and().withExternal().source(BonusGameState.OPENING_ADDITIONAL_REWARD).target(BonusGameState.GAME_OVER).event(BonusGameRewardEvent.WIN_20)
				.and().withExternal().source(BonusGameState.OPENING_ADDITIONAL_REWARD).target(BonusGameState.OPENING_BOXES).event(BonusGameRewardEvent.WIN_SECOND_CHANCE);
	}

}