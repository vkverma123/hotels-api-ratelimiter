package com.hotels.web.api.controller;

import com.hotels.web.api.common.SortingOptions;
import com.hotels.web.api.context.CityApiRateLimiter;
import com.hotels.web.api.context.RoomApiRateLimiter;
import com.hotels.web.api.model.HotelsResponseModel;
import com.hotels.web.api.service.HotelService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayDeque;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HotelsControllerUnitTests {

    @Mock
    private HotelService hotelService;

    @Mock
    private CityApiRateLimiter cityApiRateLimiter;

    @Mock
    private RoomApiRateLimiter roomApiRateLimiter;

    private HotelsController hotelsController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(cityApiRateLimiter.getRequestsQueue()).thenReturn(new ArrayDeque<>(5));
        when(cityApiRateLimiter.getApiSuspensionFinishTime()).thenReturn(100000L);
        when(cityApiRateLimiter.getAllowedTimeInterval()).thenReturn(5000L);
        when(cityApiRateLimiter.getMaxNumberOfRequests()).thenReturn(10);
        when(cityApiRateLimiter.getSuspensionTimeInterval()).thenReturn(4000L);
        when(roomApiRateLimiter.getRequestsQueue()).thenReturn(new ArrayDeque<>(5));
        when(roomApiRateLimiter.getApiSuspensionFinishTime()).thenReturn(100000L);
        when(roomApiRateLimiter.getAllowedTimeInterval()).thenReturn(5000L);
        when(roomApiRateLimiter.getMaxNumberOfRequests()).thenReturn(10);
        when(roomApiRateLimiter.getSuspensionTimeInterval()).thenReturn(4000L);

        hotelsController = new HotelsController(hotelService, cityApiRateLimiter, roomApiRateLimiter);
    }

    @Test
    public void cityApiHotelControllerBuildsResponse() throws IOException {
        // Act
        final ResponseEntity<HotelsResponseModel> response = hotelsController.getHotelByCityName("Bangkok", SortingOptions.ASC);

        // Assert
        assertNotNull(response);
        verify(hotelService).getHotelsDataByCityName(anyString(), any());
    }

    @Test
    public void roomApiHotelControllerBuildsResponse() throws IOException {
        // Act
        final ResponseEntity<HotelsResponseModel> response = hotelsController.getHotelByRoomType("Deluxe", SortingOptions.ASC);

        // Assert
        assertNotNull(response);
        verify(hotelService).getHotelsDataByRoomType(anyString(), any());
    }
}
