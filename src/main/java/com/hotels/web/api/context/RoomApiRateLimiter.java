package com.hotels.web.api.context;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * RoomApiRateLimiter Class.
 */
@Component
public class RoomApiRateLimiter extends ApiRateLimiter {

	public RoomApiRateLimiter(@Value("${room.maxNumberOfRequests}") int maxNumberOfRequests,
							  @Value("${room.allowedTimeInterval}") long allowedTimeInterval,
							  @Value("${room.suspensionTimeInterval}") long suspensionTimeInterval) {
		super(maxNumberOfRequests, allowedTimeInterval, suspensionTimeInterval);
	}
}
