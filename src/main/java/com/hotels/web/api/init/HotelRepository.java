package com.hotels.web.api.init;

import com.hotels.web.api.model.Hotels;
import com.hotels.web.api.model.HotelsResponseDataModel;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


/**
 * HotelRepository Class.
 */
@Getter
@Setter
@Component
public class HotelRepository {

    private static final String HOTEL_DB_PATH = "src/main/resources/static/hoteldb.csv";

    /**
     * Setup Csv Parser.
     * @return hotelsResponseDataModel HotelsResponseDataModel
     */
    @Bean
    public HotelsResponseDataModel parse() throws IOException {

        CSVReader reader =
                new CSVReaderBuilder(new FileReader(HOTEL_DB_PATH)).
                        withSkipLines(1). // Skiping firstline as it is header
                        build();
        List<Hotels> hotels = reader.readAll().stream().map(data -> {
            Hotels csvObject = new Hotels();
            csvObject.setCity(data[0]);
            csvObject.setHotelId(Integer.valueOf(data[1]));
            csvObject.setRoom(data[2]);
            csvObject.setPrice(Integer.valueOf(data[3]));
            return csvObject;
        }).collect(Collectors.toList());

        HotelsResponseDataModel hotelsResponseDataModel = new HotelsResponseDataModel();
        hotelsResponseDataModel.setHotelsResponseList(hotels);

        return hotelsResponseDataModel;
    }
}

