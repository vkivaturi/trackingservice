package org.digit.tripmonitoring.service.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    @KafkaListener(topics = "trackingServiceTopic", groupId = "group_id")
    public void consume(String message)
    {
        System.out.println("Message: " + message);
    }
}
