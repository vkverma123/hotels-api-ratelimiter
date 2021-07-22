package com.hotels.web.api.controller;

import com.hotels.web.api.common.ApplicationErrorType;
import com.hotels.web.api.common.SortingOptions;
import com.hotels.web.api.context.CityApiRateLimiter;
import com.hotels.web.api.context.IRateLimiter;
import com.hotels.web.api.context.RoomApiRateLimiter;
import com.hotels.web.api.model.ErrorDetailsType;
import com.hotels.web.api.model.HotelsResponseModel;
import com.hotels.web.api.service.HotelService;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayDeque;

/**
 * Hotels Controller.
 */
@RestController
public class HotelsController {

    private static final Logger LOGGER = LogManager.getLogger(HotelsController.class);
    private static final String ERROR_MESSAGE = "Sorry, Cannot process this request";
    private final HotelService hotelService;

    private final CityApiRateLimiter cityApiRateLimiter;
    private final RoomApiRateLimiter roomApiRateLimiter;

    /**
     * HotelsController constructor.
     * @param hotelService {@link HotelService}.
     */
    @Autowired
    public HotelsController(
            final HotelService hotelService,
            @Qualifier("cityApiRateLimiter") IRateLimiter cityApiRateLimiter,
            @Qualifier("roomApiRateLimiter") IRateLimiter roomApiRateLimiter) {
        this.hotelService = hotelService;
        this.cityApiRateLimiter = (CityApiRateLimiter) cityApiRateLimiter;
        this.roomApiRateLimiter = (RoomApiRateLimiter) roomApiRateLimiter;
    }

    /**
     * Gets Hotel Details By CityName.
     * @param cityName String.
     * @return ResponseEntity HotelsResponseDataModel.
     */
    @ApiOperation(value = "Get Hotels Data By City Name")
    @GetMapping("/city/{cityName}")
    @ResponseBody
    public ResponseEntity getHotelByCityName(@PathVariable("cityName") String cityName,
                                             @RequestParam(value = "sortOption", required = false) SortingOptions sortOption) {
        try {
            synchronized (CityApiRateLimiter.class) {
                long currentTime = System.currentTimeMillis();
                // If the suspensionEndTime is greater than -1, it means that the API is suspended
                if (cityApiRateLimiter.getApiSuspensionFinishTime() > -1 && cityApiRateLimiter.getApiSuspensionFinishTime() >= currentTime) {
                    return apiSuspendMessage(cityApiRateLimiter);
                }
                // Finding the window starting time
                long windowStartTime = currentTime - cityApiRateLimiter.getAllowedTimeInterval();
                ArrayDeque<Long> requestQueue = cityApiRateLimiter.getRequestsQueue();

                // Looping through the queue to remove the requests that fall outside the current window.
                while (!requestQueue.isEmpty() && windowStartTime >= requestQueue.peekFirst()) {
                    requestQueue.poll();
                }
                // If the requests size is more than the maximum acceptable limit, suspend API.
                if (requestQueue.size() >= cityApiRateLimiter.getMaxNumberOfRequests()) {
                    cityApiRateLimiter.setApiSuspensionFinishTime(currentTime + cityApiRateLimiter.getSuspensionTimeInterval());
                    return apiSuspendMessage(cityApiRateLimiter);
                }
                cityApiRateLimiter.setApiSuspensionFinishTime(-1);
                // Adding the current request to the queue.
                requestQueue.add(currentTime);
            }
            return getHotelListByCity(cityName, sortOption);
        } catch (final Exception exception) {
            LOGGER.error(exception);
            return getErrorResponse(exception);
        }
    }

    /**
     * Gets Hotel Details By RoomType.
     * @param roomType String.
     * @return ResponseEntity HotelsResponseDataModel.
     */
    @ApiOperation(value = "Get Hotels Data By Room Type")
    @GetMapping(value = "/room/{roomType}")
    @ResponseBody
    public ResponseEntity getHotelByRoomType(@PathVariable("roomType") String roomType,
                                             @RequestParam(value = "sortOption", required = false) SortingOptions sortOption) {
        try {
            synchronized (RoomApiRateLimiter.class) {
                long currentTime = System.currentTimeMillis();

                // If the suspensionEndTime is greater than -1, it means that the API is suspended
                if (roomApiRateLimiter.getApiSuspensionFinishTime() > -1 && roomApiRateLimiter.getApiSuspensionFinishTime() >= currentTime) {
                    return apiSuspendMessage(roomApiRateLimiter);
                }

                // Finding the window starting time
                long windowStartTime = currentTime - roomApiRateLimiter.getAllowedTimeInterval();
                ArrayDeque<Long> requestQueue = roomApiRateLimiter.getRequestsQueue();

                // Looping through the queue to remove the requests that fall outside the current window.
                while (!requestQueue.isEmpty() && windowStartTime >= requestQueue.peekFirst()) {
                    requestQueue.poll();
                }
                // If the requests size is more than the maximum acceptable limit, suspend the API.
                if (requestQueue.size() >= roomApiRateLimiter.getMaxNumberOfRequests()) {
                    roomApiRateLimiter.setApiSuspensionFinishTime(currentTime + roomApiRateLimiter.getSuspensionTimeInterval());
                    return apiSuspendMessage(roomApiRateLimiter);
                }
                roomApiRateLimiter.setApiSuspensionFinishTime(-1);
                // Adding the current request to the queue.
                requestQueue.add(currentTime);
            }
            return getHotelListByRoom(roomType, sortOption);
        } catch (Exception exception) {
            LOGGER.error(exception);
            return getErrorResponse(exception);
        }
    }

    /**
     * Gets Error Response in case of Exception.
     * @param exception Exception.
     * @return ResponseEntity.
     */
    private ResponseEntity getErrorResponse(final Exception exception) {

        final ErrorDetailsType errorDetailsType = new ErrorDetailsType();
        errorDetailsType.setErrorCode(ApplicationErrorType.INTERNAL_SERVER_ERROR.name());
        errorDetailsType.setErrorMessage(ERROR_MESSAGE);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
                .body(errorDetailsType);
    }

    /**
     * Gets HotelsResponse.
     * @param cityId String.
     * @param sortOrder SortingOptions
     * @return ResponseEntity.
     */
    private ResponseEntity<HotelsResponseModel> getHotelListByCity(String cityId, SortingOptions sortOrder) throws IOException {
        HotelsResponseModel hotelsResponseModel = hotelService.getHotelsDataByCityName(cityId, sortOrder);
        return ResponseEntity.status(HttpStatus.OK).body(hotelsResponseModel);
    }

    /**
     * Gets HotelsResponse.
     * @param roomType String.
     * @param sortOrder SortingOptions
     * @return ResponseEntity.
     */
    private ResponseEntity<HotelsResponseModel> getHotelListByRoom(String roomType, SortingOptions sortOrder) throws IOException {
        HotelsResponseModel hotelsResponseModel = hotelService.getHotelsDataByRoomType(roomType, sortOrder);
        return ResponseEntity.status(HttpStatus.OK).body(hotelsResponseModel);
    }

    /**
     * Gets HotelsResponse when API is suspended.
     * @param limiter IRateLimiter.
     * @return ResponseEntity.
     */
    private ResponseEntity<HotelsResponseModel> apiSuspendMessage(IRateLimiter limiter) {

        HotelsResponseModel hotelsResponseModel =new HotelsResponseModel();
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .body(hotelsResponseModel
                        .setStatusCode(HttpStatus.TOO_MANY_REQUESTS.value())
                        .setMessage("Too many requests. Api is suspended for the next "
                                + (limiter.getApiSuspensionFinishTime() - System.currentTimeMillis()) / 1000 + " seconds"));

    }
}
