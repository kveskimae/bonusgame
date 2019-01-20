package com.foo.random;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestRandomService extends RandomService {

	private Object pick;

	public void setNextElementToPick(Object pick) {
		this.pick = pick;
	}

	@Override
	protected int getPickIndex(List list) {
		if (!list.contains(pick)) {
			throw new IllegalArgumentException("Parameter list does not contain pick: " + pick);
		}

		int idx = list.indexOf(pick);

		return idx;
	}

}
