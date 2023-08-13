package com.example.EGovNotificationService.service;
import com.example.EGovNotificationService.dao.NotificationDAO;
import com.example.EGovNotificationService.data.Event;
import com.example.EGovNotificationService.data.UserDetails;
import com.example.EGovNotificationService.util.TemplateMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class KafkaConsumerForSMS {

    @Autowired
    NotificationDAO notificationDAO;

    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    DigitNotificationService digitNotificationService;
    @KafkaListener(topics = "Notification_Topic", groupId = "Notification_GroupId")
    public void consumeEvents(Event eventReceived) {
        /*
        Consume each event from kafka , the find user details and then hit notification api of digit
         */
        System.out.println("Message received is " + eventReceived.toString());
        handler(eventReceived);
    }

    private void handler(Event event) {

        //client call to fetch userDetails
        UserDetails data = userDetailsService.getData(event.getEventData());

        // create Notification Message
        String message = TemplateMessageUtil.getMessage(event);

        //notify call
        digitNotificationService.notifyCall(message);

        // async insert to db
        notificationDAO.insertData(event,message);

    }
}
