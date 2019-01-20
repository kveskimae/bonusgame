package com.foo.random;

import java.util.List;

public interface IRandomService {

	<T> T popOne(List<T> list);

}
