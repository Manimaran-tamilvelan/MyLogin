package com.fullcreative.demo.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

public class MockitoDemo {

	@Test
	public void test1() {
		// create mock
		UserA test = mock(UserA.class);

		// define return value for method getUniqueId()
		when(test.demo()).thenReturn(43);

		// use mock in test....
		assertEquals(test.demo(), 43);
	}

}

class UserA {

	public int demo() {
		return 0;
	}

}
