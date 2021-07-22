package com.hotels.web.api.service;

import com.hotels.web.api.common.SortingOptions;
import com.hotels.web.api.init.HotelRepository;
import com.hotels.web.api.mapper.HotelResponseDataModelMapper;
import com.hotels.web.api.model.Hotels;
import com.hotels.web.api.model.HotelsResponseDataModel;
import com.hotels.web.api.model.HotelsResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Hotels Service.
 */
@Component
public class HotelService {

    private final HotelRepository hotelRepository;
    private final HotelResponseDataModelMapper hotelsResponseDataModelMapper;

    /**
     * HotelsService constructor.
     * @param hotelRepository {@link HotelRepository}
     * @param hotelsResponseDataModelMapper {@link HotelResponseDataModelMapper}
     */
    @Autowired
    public HotelService(
            final HotelRepository hotelRepository,
            final HotelResponseDataModelMapper hotelsResponseDataModelMapper) {
        this.hotelRepository = hotelRepository;
        this.hotelsResponseDataModelMapper = hotelsResponseDataModelMapper;
    }

    /**
     * Gets Hotel Details By RoomType.
     * @param roomType String.
     * @return hotelsResponseModel HotelsResponseModel.
     */
    public HotelsResponseModel getHotelsDataByRoomType(final String roomType, final SortingOptions sortingOption) throws IOException {
        final HotelsResponseDataModel hotelsResponseDataModel = hotelRepository.parse();

        List<Hotels> hotelsList = hotelsResponseDataModel.getHotelsResponseList().stream().filter(hotel ->  roomType.equalsIgnoreCase(hotel.getRoom())
        ).collect(Collectors.toList());

        final HotelsResponseModel hotelsResponseModel = hotelsResponseDataModelMapper.map(hotelsList);
        sortHotels(hotelsResponseModel, sortingOption);
        return hotelsResponseModel;
    }

    /**
     * Gets Hotel Details By cityName.
     * @param cityName String.
     * @return hotelsResponseModel HotelsResponseModel.
     */
    public HotelsResponseModel getHotelsDataByCityName(final String cityName, final SortingOptions sortingOption) throws IOException {
        final HotelsResponseDataModel hotelsRequestDataModel = hotelRepository.parse();
        List<Hotels> hotels = hotelsRequestDataModel.getHotelsResponseList().stream().filter(hotel ->  cityName.equalsIgnoreCase(hotel.getCity())
        ).collect(Collectors.toList());

        HotelsResponseModel hotelsResponseModel = hotelsResponseDataModelMapper.map(hotels);
        sortHotels(hotelsResponseModel, sortingOption);
        return hotelsResponseModel;
    }

    /**
     * Gets Hotel Details sorted by SortOption
     * @param hotelsResponseModel HotelsResponseModel.
     * @param sortingOption SortingOptions.
     */
    private void sortHotels(final HotelsResponseModel hotelsResponseModel,
                                                 final SortingOptions sortingOption) {
        if (SortingOptions.ASC.equals(sortingOption)) {
            Collections.sort(hotelsResponseModel.getHotelsResponseList());
        } else if (SortingOptions.DESC.equals(sortingOption)) {
            Collections.sort(hotelsResponseModel.getHotelsResponseList(), Collections.reverseOrder());
        }
    }

}
