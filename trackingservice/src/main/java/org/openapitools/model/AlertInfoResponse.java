package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.model.AlertInfoResponseTripsInner;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * AlertInfoResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-09-24T22:54:39.333730900+05:30[Asia/Calcutta]")
public class AlertInfoResponse {

  private String applicationNo;

  private String tenantId;

  @Valid
  private List<@Valid AlertInfoResponseTripsInner> trips;

  public AlertInfoResponse applicationNo(String applicationNo) {
    this.applicationNo = applicationNo;
    return this;
  }

  /**
   * Get applicationNo
   * @return applicationNo
  */
  
  @Schema(name = "applicationNo", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("applicationNo")
  public String getApplicationNo() {
    return applicationNo;
  }

  public void setApplicationNo(String applicationNo) {
    this.applicationNo = applicationNo;
  }

  public AlertInfoResponse tenantId(String tenantId) {
    this.tenantId = tenantId;
    return this;
  }

  /**
   * Get tenantId
   * @return tenantId
  */
  
  @Schema(name = "tenantId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("tenantId")
  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }

  public AlertInfoResponse trips(List<@Valid AlertInfoResponseTripsInner> trips) {
    this.trips = trips;
    return this;
  }

  public AlertInfoResponse addTripsItem(AlertInfoResponseTripsInner tripsItem) {
    if (this.trips == null) {
      this.trips = new ArrayList<>();
    }
    this.trips.add(tripsItem);
    return this;
  }

  /**
   * Get trips
   * @return trips
  */
  @Valid 
  @Schema(name = "trips", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("trips")
  public List<@Valid AlertInfoResponseTripsInner> getTrips() {
    return trips;
  }

  public void setTrips(List<@Valid AlertInfoResponseTripsInner> trips) {
    this.trips = trips;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AlertInfoResponse alertInfoResponse = (AlertInfoResponse) o;
    return Objects.equals(this.applicationNo, alertInfoResponse.applicationNo) &&
        Objects.equals(this.tenantId, alertInfoResponse.tenantId) &&
        Objects.equals(this.trips, alertInfoResponse.trips);
  }

  @Override
  public int hashCode() {
    return Objects.hash(applicationNo, tenantId, trips);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AlertInfoResponse {\n");
    sb.append("    applicationNo: ").append(toIndentedString(applicationNo)).append("\n");
    sb.append("    tenantId: ").append(toIndentedString(tenantId)).append("\n");
    sb.append("    trips: ").append(toIndentedString(trips)).append("\n");
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

