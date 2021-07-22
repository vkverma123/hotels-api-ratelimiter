package com.hotels.web.api.context;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * CityApiRateLimiter Class.
 */
@Component
public class CityApiRateLimiter extends ApiRateLimiter {

	public CityApiRateLimiter(@Value("${city.maxNumberOfRequests}") int maxNumberOfRequests,
			@Value("${city.allowedTimeInterval}") long allowedTimeInterval,
			@Value("${city.suspensionTimeInterval}") long suspensionTimeInterval) {
		super(maxNumberOfRequests, allowedTimeInterval, suspensionTimeInterval);
	}
}
