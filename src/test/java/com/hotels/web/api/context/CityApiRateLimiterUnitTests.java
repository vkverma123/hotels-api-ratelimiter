package com.hotels.web.api.context;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CityApiRateLimiterUnitTests {

	@Test
	public void addTimeConstraintsAndVerify() {
		CityApiRateLimiter cityApiRateLimiter = new CityApiRateLimiter(10, 1000, 500);
		assertEquals(10, cityApiRateLimiter.getMaxNumberOfRequests());
		assertEquals(1000, cityApiRateLimiter.getAllowedTimeInterval());
		assertEquals(500, cityApiRateLimiter.getSuspensionTimeInterval());
	}

}
