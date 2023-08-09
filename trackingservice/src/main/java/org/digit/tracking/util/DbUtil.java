package org.digit.tracking.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openapitools.model.Audit;
import org.openapitools.model.Location;
import org.openapitools.model.Operator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

// Commmon util class to implement id (primary key) generation logic for each entity
public class DbUtil {
    //Basic implementation of id using UUID. entityName is supported for future use cases where id generation logic might vary based on the entity
   public static String getId(String ... entityName) {
       UUID uuid= UUID.randomUUID();
       return uuid.toString();
   }

    public static Audit getAuditDetails(ResultSet rs) throws SQLException {
        Audit audit = new Audit();
        audit.setCreatedBy(rs.getString("createdBy"));
        audit.setCreatedDate(rs.getString("createdDate"));
        audit.setUpdatedBy(rs.getString("updatedBy"));
        audit.setUpdatedDate(rs.getString("updatedDate"));
        return audit;
    }
    //Common method to convert JSON in database to an object list
    public static List dbJsonToList(ResultSet rs, String dbColumn, Class<?> T) throws SQLException {
        //Fetch database field into a string json
        String jsonString = rs.getString(dbColumn);
        //Convert json string to array of string
        ObjectMapper mapper = new ObjectMapper();

        //Initialize a generic List object
        List dbJsonList = null;

        try {
            //Identify the class to which the output List should be converted to
            if (T == String.class) {
                dbJsonList = new ArrayList<String>();
                dbJsonList = Arrays.asList(mapper.readValue(jsonString, String[].class));
            } else if (T == Location.class) {
                dbJsonList = new ArrayList<Location>();
                dbJsonList = Arrays.asList(mapper.readValue(jsonString, Location[].class));
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return dbJsonList;
    }

    //Convert JSON of Operator entity to an object
    public static Operator dbJsonToOperator(ResultSet rs, String dbColumn, Class<?> T) throws SQLException {
        //Fetch database field into a string json
        String jsonString = rs.getString(dbColumn);
        //Convert json string to array of string
        ObjectMapper mapper = new ObjectMapper();

        Operator dbJson = new Operator();

        try {
            dbJson = mapper.readValue(jsonString, Operator.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return dbJson;
    }
}
