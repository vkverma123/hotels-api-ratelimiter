package com.hotels.web.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ErrorDetailsType DataModel Class.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDetailsType {
    private String errorCode;
    private String errorMessage;

    @Override
    public String toString() {
        return (getErrorCode() == null ? "" : getErrorCode().concat(": ")).concat(getErrorMessage());
    }
}

