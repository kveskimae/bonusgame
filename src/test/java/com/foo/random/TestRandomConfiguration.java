package com.foo.random;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class TestRandomConfiguration {

	@Bean
	@Primary
	public RandomService getRandomService() {
		return new TestRandomService();
	}

}
