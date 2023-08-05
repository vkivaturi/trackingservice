package org.digit.tripmonitoring.service.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaProducer {

    KafkaTemplate<String, Event> kafkaTemplate;

    private static final String TOPIC = "notificationServiceTopic";

    @PostMapping("/event")
    public String publishMessage(@RequestBody Event event)
    {
        kafkaTemplate.send(TOPIC, event);
        return "SUCCESS";
    }
}
