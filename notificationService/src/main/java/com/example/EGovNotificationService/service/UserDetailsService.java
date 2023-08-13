package com.example.EGovNotificationService.service;

import com.example.EGovNotificationService.data.EventData;
import com.example.EGovNotificationService.data.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class UserDetailsService {

    @Autowired
    private WebClient webClient;

    public UserDetailsService() {
    }

    public UserDetails getData(EventData eventData) {
        return webClient.get().uri("/{tripId}/details",eventData.getTrip_id())
                .retrieve().bodyToMono(UserDetails.class).block();
    }
}
