package com.hotels.web.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Hotels DataModel Class.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hotels implements Comparable<Hotels> {

    private String city;
    private Integer hotelId;
    private String room;
    private Integer price;

    @Override
    public int compareTo(Hotels secondHotel) {
        return this.getPrice() - secondHotel.getPrice();
    }
}