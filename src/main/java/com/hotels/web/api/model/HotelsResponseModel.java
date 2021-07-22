package com.hotels.web.api.model;

import java.util.List;

/**
 * HotelsResponseModel Class.
 */
public class HotelsResponseModel {
    private List<Hotels> hotelsResponseList;
    private int statusCode;
    private String message;

    /**
     * @return the statusCode
     */
    public int getStatusCode() {
        return statusCode;
    }
    /**
     * @param statusCode the statusCode to set
     */
    public HotelsResponseModel setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }
    /**
     * @param message the message to set
     */
    public HotelsResponseModel setMessage(final String message) {
        this.message = message;
        return this;
    }
    /**
     * @return the data
     */
    public List<Hotels> getHotelsResponseList() {
        return hotelsResponseList;
    }
    /**
     * @param hotelsResponseList the HotelsResponseList to set
     */
    public HotelsResponseModel setHotelsResponseList(List<Hotels> hotelsResponseList) {
        this.hotelsResponseList = hotelsResponseList;
        return this;
    }

}
