package com.hotels.web.api.common;

/**
 * Application Error Types.
 */
public enum ApplicationErrorType {

    INVALID_REQUEST_VALUE,
    INVALID_EXTERNAL_RESPONSE,
    INTERNAL_SERVER_ERROR,
    TOO_MANY_REQUEST,
    MISSING_RESPONSE_DATA,
    TIMEOUT,
    RETRY_REISSUE,
    NO_RESULTS;

    public static ApplicationErrorType fromValue(String val) {
        return valueOf(val);
    }
}


