package com.foo.random;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RandomServiceTests {

	@Test
	void failToPopIfEmpty() {
		RandomService randomService = new RandomService();

		assertThrows(IllegalArgumentException.class,
				() -> {
					randomService.popOne(Collections.emptyList());
				});
	}

	@Test
	void removeReturnedWhenPop() {
		RandomService randomService = new RandomService();

		List<Integer> numbers = new ArrayList<>();

		numbers.add(1);
		numbers.add(2);
		numbers.add(3);
		numbers.add(4);

		Integer popped = randomService.popOne(numbers);

		assertEquals(3, numbers.size());

		assertFalse(numbers.contains(popped));
	}

}