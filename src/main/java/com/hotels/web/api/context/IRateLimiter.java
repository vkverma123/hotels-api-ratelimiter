package com.hotels.web.api.context;

import java.util.ArrayDeque;

public interface IRateLimiter {

	int getMaxNumberOfRequests();

	long getAllowedTimeInterval();

	long getSuspensionTimeInterval();

	ArrayDeque<Long> getRequestsQueue();

	long getApiSuspensionFinishTime();

	void setApiSuspensionFinishTime(long apiSuspendedFinishTime);
}