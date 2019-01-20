package com.foo;

import com.foo.simulation.BonusGameSimulation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class SimulationRunner {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(SimulationRunner.class, args);

		BonusGameSimulation simulation = ctx.getBean(BonusGameSimulation.class);

		System.out.println("Starting simulation...");

		long start = System.currentTimeMillis();

		simulation.run();

		long time = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - start);

		System.out.println(String.format("Simulation total time: %ds", time));

		System.out.println(String.format("Average winnings: %.2f", simulation.getAverageWinnings()));
	}

}