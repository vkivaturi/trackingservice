package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.model.Location;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * POI
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-08-09T19:25:44.324498900+05:30[Asia/Calcutta]")
public class POI {

  private String id;

  private String locationName;

  /**
   * POI status to help to exclude or include this in route planning
   */
  public enum StatusEnum {
    ACTIVE("active"),
    
    INACTIVE("inactive");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static StatusEnum fromValue(String value) {
      for (StatusEnum b : StatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private StatusEnum status;

  /**
   * POI type helps identify the shape of the location
   */
  public enum TypeEnum {
    POINT("point"),
    
    LINE("line"),
    
    POLYGON("polygon");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TypeEnum fromValue(String value) {
      for (TypeEnum b : TypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private TypeEnum type;

  @Valid
  private List<@Valid Location> locationDetails;

  @Valid
  private List<String> alert;

  private String userId;

  public POI id(String id) {
    this.id = id;
    return this;
  }

  /**
   * GUID generated during POI creation
   * @return id
  */
  
  @Schema(name = "id", description = "GUID generated during POI creation", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public POI locationName(String locationName) {
    this.locationName = locationName;
    return this;
  }

  /**
   * Get locationName
   * @return locationName
  */
  
  @Schema(name = "locationName", example = "Any name assigned to the location", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("locationName")
  public String getLocationName() {
    return locationName;
  }

  public void setLocationName(String locationName) {
    this.locationName = locationName;
  }

  public POI status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * POI status to help to exclude or include this in route planning
   * @return status
  */
  
  @Schema(name = "status", example = "active", description = "POI status to help to exclude or include this in route planning", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("status")
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public POI type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * POI type helps identify the shape of the location
   * @return type
  */
  
  @Schema(name = "type", example = "point", description = "POI type helps identify the shape of the location", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("type")
  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public POI locationDetails(List<@Valid Location> locationDetails) {
    this.locationDetails = locationDetails;
    return this;
  }

  public POI addLocationDetailsItem(Location locationDetailsItem) {
    if (this.locationDetails == null) {
      this.locationDetails = new ArrayList<>();
    }
    this.locationDetails.add(locationDetailsItem);
    return this;
  }

  /**
   * A POI can comprise of on geocoordinate in case of single point and multiple geos in case of a polygon
   * @return locationDetails
  */
  @Valid 
  @Schema(name = "locationDetails", description = "A POI can comprise of on geocoordinate in case of single point and multiple geos in case of a polygon", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("locationDetails")
  public List<@Valid Location> getLocationDetails() {
    return locationDetails;
  }

  public void setLocationDetails(List<@Valid Location> locationDetails) {
    this.locationDetails = locationDetails;
  }

  public POI alert(List<String> alert) {
    this.alert = alert;
    return this;
  }

  public POI addAlertItem(String alertItem) {
    if (this.alert == null) {
      this.alert = new ArrayList<>();
    }
    this.alert.add(alertItem);
    return this;
  }

  /**
   * List of alert codes that may be applicable to the location. This is optional
   * @return alert
  */
  
  @Schema(name = "alert", description = "List of alert codes that may be applicable to the location. This is optional", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("alert")
  public List<String> getAlert() {
    return alert;
  }

  public void setAlert(List<String> alert) {
    this.alert = alert;
  }

  public POI userId(String userId) {
    this.userId = userId;
    return this;
  }

  /**
   * DIGIT Id of the user performing this action
   * @return userId
  */
  
  @Schema(name = "userId", example = "rajan123", description = "DIGIT Id of the user performing this action", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("userId")
  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    POI POI = (POI) o;
    return Objects.equals(this.id, POI.id) &&
        Objects.equals(this.locationName, POI.locationName) &&
        Objects.equals(this.status, POI.status) &&
        Objects.equals(this.type, POI.type) &&
        Objects.equals(this.locationDetails, POI.locationDetails) &&
        Objects.equals(this.alert, POI.alert) &&
        Objects.equals(this.userId, POI.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, locationName, status, type, locationDetails, alert, userId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class POI {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    locationName: ").append(toIndentedString(locationName)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    locationDetails: ").append(toIndentedString(locationDetails)).append("\n");
    sb.append("    alert: ").append(toIndentedString(alert)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

