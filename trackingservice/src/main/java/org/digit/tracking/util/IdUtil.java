package org.digit.tracking.util;

import java.util.UUID;

// Commmon util class to implement id (primary key) generation logic for each entity
public class IdUtil {
    //Basic implementation of id using UUID. entityName is supported for future use cases where id generation logic might vary based on the entity
   public static String getId(String ... entityName) {
       UUID uuid= UUID.randomUUID();
       return uuid.toString();
   }
}
