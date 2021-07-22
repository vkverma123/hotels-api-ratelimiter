package com.hotels.web.api.context;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RoomApiRateLimiterUnitTests {

	@Test
	public void addTimeConstraintsAndVerify() {
		RoomApiRateLimiter roomApiRateLimiter = new RoomApiRateLimiter(10, 1000, 500);
		assertEquals(10, roomApiRateLimiter.getMaxNumberOfRequests());
		assertEquals(1000, roomApiRateLimiter.getAllowedTimeInterval());
		assertEquals(500, roomApiRateLimiter.getSuspensionTimeInterval());
	}

}
