package com.hotels.web.api.mapper;

import com.hotels.web.api.model.Hotels;
import com.hotels.web.api.model.HotelsResponseModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit tests for {@link HotelResponseDataModelMapper}.
 */
public class HotelResponseDataModelMapperUnitTests {

    private static final String CITY = "Bangkok";
    private static final Integer HOTEL_ID = 2345;
    private static final String ROOM = "Deluxe";
    private static final Integer PRICE = 654;

    private HotelResponseDataModelMapper mapper;
    private List<Hotels> hotelsList;
    private Hotels hotel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mapper = new HotelResponseDataModelMapper();

        hotel = new Hotels();
        hotel.setCity(CITY);
        hotel.setRoom(ROOM);
        hotel.setPrice(PRICE);
        hotel.setHotelId(HOTEL_ID);

        hotelsList = new ArrayList<>();
        hotelsList.add(hotel);
    }

    @Test
    public void mapWithHotelListSuccessfulHotelsResponse() {
        // Act
        final HotelsResponseModel hotelsResponseModel = mapper.map(hotelsList);

        // Assert
        assertNotNull(hotelsResponseModel);
        assertEquals(CITY, hotelsResponseModel.getHotelsResponseList().get(0).getCity());
        assertEquals(HOTEL_ID, hotelsResponseModel.getHotelsResponseList().get(0).getHotelId());
        assertEquals(ROOM, hotelsResponseModel.getHotelsResponseList().get(0).getRoom());
        assertEquals(PRICE, hotelsResponseModel.getHotelsResponseList().get(0).getPrice());
    }
}

