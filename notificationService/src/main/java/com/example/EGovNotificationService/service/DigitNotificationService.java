package com.example.EGovNotificationService.service;

import com.example.EGovNotificationService.data.EventData;
import com.example.EGovNotificationService.data.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

public class DigitNotificationService {


    @Autowired
    private WebClient webClient;

    public DigitNotificationService() {
    }

    public String notifyCall(String message) {
        return webClient.post().uri("/notify/{message}",message)
                .retrieve().bodyToMono(String.class).block();
    }
}
