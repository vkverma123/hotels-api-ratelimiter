package com.hotels.web.api.integration;

import com.hotels.web.api.HotelsServiceApplication;
import com.hotels.web.api.init.HotelRepository;
import com.hotels.web.api.model.HotelsResponseModel;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {HotelsServiceApplication.class, HotelRepository.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HotelsControllerDevIntegrationTest {

    private final static String CITY_API_ENDPOINT = "/city/Bangkok";
    private final static String ROOM_API_ENDPOINT = "/room/Superior";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate;
    private HttpHeaders httpHeaders;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        restTemplate = new TestRestTemplate();
        httpHeaders = new HttpHeaders();
    }

    @Test
    public void givenHotelDataWhenRequestsContainsCityBangkokWithinRateLimitThenAccepted() throws Exception {

        RequestBuilder request = get(CITY_API_ENDPOINT).contentType(MediaType.APPLICATION_JSON);


        for (int i = 1; i <= 2; i++) {
            mockMvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.hotelsResponseList[?(@.city == \"Bangkok\" && @.room == \"Superior\" && @.hotelId == 8 && @.price == 2400)]").exists())
                    .andExpect(jsonPath("$.hotelsResponseList[?(@.city == \"Bangkok\" && @.room == \"Superior\" && @.hotelId == 6 && @.price == 2000)]").exists())
                    .andExpect(jsonPath("$.hotelsResponseList[?(@.city == \"Bangkok\" && @.room == \"Deluxe\" && @.hotelId == 11 && @.price == 60)]").exists());
        }
    }

    @Test
    public void givenHotelDataWhenRequestsContainsRoomSuperiorWithinRateLimitThenAccepted() throws Exception {

        RequestBuilder request = get(ROOM_API_ENDPOINT).contentType(MediaType.APPLICATION_JSON);

        for (int i = 1; i <= 4; i++) {
            mockMvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.hotelsResponseList[?(@.city == \"Bangkok\" && @.room == \"Superior\" && @.hotelId == 8 && @.price == 2400)]").exists())
                    .andExpect(jsonPath("$.hotelsResponseList[?(@.city == \"Ashburn\" && @.room == \"Superior\" && @.hotelId == 20 && @.price == 4444)]").exists())
                    .andExpect(jsonPath("$.hotelsResponseList[?(@.city == \"Amsterdam\" && @.room == \"Superior\" && @.hotelId == 2 && @.price == 2000)]").exists())
                    .andExpect(jsonPath("$.hotelsResponseList[?(@.city == \"Ashburn\" && @.room == \"Superior\" && @.hotelId == 16 && @.price == 800)]").exists());
        }
    }

    @Test
    public void testRetrieveHotelsForDeluxeRoom() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<String>(null, httpHeaders);
        ResponseEntity<HotelsResponseModel> response = restTemplate.exchange(createURLWithPort("/room/deluxe"), HttpMethod.GET,
                entity, HotelsResponseModel.class);
        assertEquals(200, response.getBody().getStatusCode());
    }

    @Test
    public void testRetrieveHotelsForBangkok() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<String>(null, httpHeaders);
        ResponseEntity<HotelsResponseModel> response = restTemplate.exchange(createURLWithPort("/city/bangkok"),
                HttpMethod.GET, entity, HotelsResponseModel.class);
        HotelsResponseModel responseString = response.getBody();
        assertEquals(200, responseString.getStatusCode());
    }

    @Test
    public void testRetrieveHotelsForBangkokUsesUpTheLimit() throws JSONException, InterruptedException {
        HttpEntity<String> entity = new HttpEntity<String>(null, httpHeaders);
        String url = createURLWithPort("/city/bangkok");
        for (int i = 0; i < 100; i++) {
            restTemplate.exchange(url, HttpMethod.GET, entity, HotelsResponseModel.class);
        }
        ResponseEntity<HotelsResponseModel> response = restTemplate.exchange(url, HttpMethod.GET, entity, HotelsResponseModel.class);
        assertEquals(429, response.getBody().getStatusCode());
        Thread.sleep(10000);
        response = restTemplate.exchange(url, HttpMethod.GET, entity, HotelsResponseModel.class);
        assertEquals(200, response.getBody().getStatusCode());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}