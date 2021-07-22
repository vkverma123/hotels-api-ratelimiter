package com.hotels.web.api.context;

import java.util.ArrayDeque;

/**
 * ApiRateLimiter Class.
 */
public abstract class ApiRateLimiter implements IRateLimiter {

	private final int maxNumberOfRequests;
	private final long allowedTimeInterval;
	private final long suspensionTimeInterval;

	private final ArrayDeque<Long> requestQueue;
	private long apiSuspensionFinishTime;

	public ApiRateLimiter(int maxNumberOfRequests, long allowedTimeInterval, long suspensionTimeInterval) {
		this.maxNumberOfRequests = maxNumberOfRequests;
		this.allowedTimeInterval = allowedTimeInterval;
		this.suspensionTimeInterval = suspensionTimeInterval;
		requestQueue = new ArrayDeque<>();
	}

	@Override
	public int getMaxNumberOfRequests() {
		return maxNumberOfRequests;
	}

	@Override
	public long getAllowedTimeInterval() {
		return allowedTimeInterval;
	}

	@Override
	public long getSuspensionTimeInterval() {
		return suspensionTimeInterval;
	}

	@Override
	public ArrayDeque<Long> getRequestsQueue() {
		return requestQueue;
	}

	@Override
	public long getApiSuspensionFinishTime() {
		return apiSuspensionFinishTime;
	}

	@Override
	public void setApiSuspensionFinishTime(long apiSuspensionFinishTime) {
		this.apiSuspensionFinishTime = apiSuspensionFinishTime;
	}
}
