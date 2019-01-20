package com.foo.random;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class RandomService implements IRandomService {

	private Random rand = new Random();

	public <T> T popOne(List<T> list) {
		int idx = getPickIndex(list);
		T randomElement = list.get(idx);
		list.remove(idx);
		return randomElement;
	}

	protected int getPickIndex(List list) {
		return rand.nextInt(list.size());
	}

}
