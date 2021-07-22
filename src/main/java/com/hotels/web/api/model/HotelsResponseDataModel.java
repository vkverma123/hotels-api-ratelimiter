package com.hotels.web.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * HotelsResponseDataModel Class.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelsResponseDataModel {
    private List<Hotels> hotelsResponseList;
}
