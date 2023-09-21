package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * AlertInfoResponseTripsInner
 */

@JsonTypeName("AlertInfoResponse_trips_inner")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-09-21T23:21:27.235117500+05:30[Asia/Calcutta]")
public class AlertInfoResponseTripsInner {

  private String tripId;

  @Valid
  private List<String> alerts;

  public AlertInfoResponseTripsInner tripId(String tripId) {
    this.tripId = tripId;
    return this;
  }

  /**
   * Get tripId
   * @return tripId
  */
  
  @Schema(name = "tripId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("tripId")
  public String getTripId() {
    return tripId;
  }

  public void setTripId(String tripId) {
    this.tripId = tripId;
  }

  public AlertInfoResponseTripsInner alerts(List<String> alerts) {
    this.alerts = alerts;
    return this;
  }

  public AlertInfoResponseTripsInner addAlertsItem(String alertsItem) {
    if (this.alerts == null) {
      this.alerts = new ArrayList<>();
    }
    this.alerts.add(alertsItem);
    return this;
  }

  /**
   * Get alerts
   * @return alerts
  */
  
  @Schema(name = "alerts", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("alerts")
  public List<String> getAlerts() {
    return alerts;
  }

  public void setAlerts(List<String> alerts) {
    this.alerts = alerts;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AlertInfoResponseTripsInner alertInfoResponseTripsInner = (AlertInfoResponseTripsInner) o;
    return Objects.equals(this.tripId, alertInfoResponseTripsInner.tripId) &&
        Objects.equals(this.alerts, alertInfoResponseTripsInner.alerts);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tripId, alerts);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AlertInfoResponseTripsInner {\n");
    sb.append("    tripId: ").append(toIndentedString(tripId)).append("\n");
    sb.append("    alerts: ").append(toIndentedString(alerts)).append("\n");
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

