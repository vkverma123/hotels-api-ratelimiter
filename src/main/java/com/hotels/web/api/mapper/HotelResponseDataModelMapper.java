package com.hotels.web.api.mapper;

import com.hotels.web.api.model.Hotels;
import com.hotels.web.api.model.HotelsResponseDataModel;
import com.hotels.web.api.model.HotelsResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * HotelResponseDataModelMapper Class.
 */
@Component
public class HotelResponseDataModelMapper implements IMapper<List<Hotels>, HotelsResponseModel> {

    private static final String SUCCESS = "success";
    /**
     * Maps a list of {@link Hotels} to a list of {@link HotelsResponseModel}.
     */
    @Override
    public HotelsResponseModel map(final List<Hotels> hotelList) {

        final List<Hotels> hotelsResponseDataList = new ArrayList<>();
        for (final Hotels hotelRequestData: hotelList) {
            final Hotels hotel = new Hotels();
            hotel.setHotelId(hotelRequestData.getHotelId());
            hotel.setCity(hotelRequestData.getCity());
            hotel.setRoom(hotelRequestData.getRoom());
            hotel.setPrice(hotelRequestData.getPrice());

            hotelsResponseDataList.add(hotel);
        }

        final HotelsResponseModel responseModel = new HotelsResponseModel();
        responseModel.setHotelsResponseList(hotelsResponseDataList);
        responseModel.setStatusCode(HttpStatus.OK.value());
        responseModel.setMessage(SUCCESS);
        return responseModel;
    }
}
