package org.digit.tracking.data.sao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

//TripSao is the service access object to manage trip data operations (search, update). This SAO interacts with DIGIT FSM APIs
public class TripSao {

    @Value("${DIGIT_URL}")
    private String digitUrl;

    @Value("${AUTH_TOKEN}")
    private String authToken;

    RestTemplate restTemplate;
    void searchTripForDriver() {

    }

    void updateTripStatus() {

    }

}
