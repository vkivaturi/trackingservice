package org.digit.tripmonitoring.service.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    @KafkaListener(topics = "trackingServiceTopic", groupId = "group_id")
    public void consume(String message)
    {
        /* TODO: Fetch everything from message queue at once and use TripMonitoringUtil.
         *  Then, call the producer to make entry into message queue if anomaly detected
         */
        System.out.println("Message: " + message);
    }
}
