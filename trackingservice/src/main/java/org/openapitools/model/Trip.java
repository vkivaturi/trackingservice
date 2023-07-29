package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.OffsetDateTime;
import org.openapitools.model.Audit;
import org.openapitools.model.Operator;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Trip
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-07-29T10:39:40.023515100+05:30[Asia/Calcutta]")
public class Trip {

  private String routeId;

  private String serviceCode;

  /**
   * Trip status as progress is made
   */
  public enum StatusEnum {
    CREATED("created"),
    
    IN_PROGRESS("in progress"),
    
    COMPLETED("completed"),
    
    CANCELLED("cancelled");

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

  private Operator operator;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime plannedStartTime;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime plannedEndTime;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime actualStartTime;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime actualEndTime;

  private Audit audit;

  public Trip routeId(String routeId) {
    this.routeId = routeId;
    return this;
  }

  /**
   * Id of the assigned route
   * @return routeId
  */
  
  @Schema(name = "routeId", description = "Id of the assigned route", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("routeId")
  public String getRouteId() {
    return routeId;
  }

  public void setRouteId(String routeId) {
    this.routeId = routeId;
  }

  public Trip serviceCode(String serviceCode) {
    this.serviceCode = serviceCode;
    return this;
  }

  /**
   * Code of the service which will be rendered through this trip
   * @return serviceCode
  */
  
  @Schema(name = "serviceCode", description = "Code of the service which will be rendered through this trip", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("serviceCode")
  public String getServiceCode() {
    return serviceCode;
  }

  public void setServiceCode(String serviceCode) {
    this.serviceCode = serviceCode;
  }

  public Trip status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * Trip status as progress is made
   * @return status
  */
  
  @Schema(name = "status", example = "created", description = "Trip status as progress is made", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("status")
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public Trip operator(Operator operator) {
    this.operator = operator;
    return this;
  }

  /**
   * Get operator
   * @return operator
  */
  @Valid 
  @Schema(name = "operator", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("operator")
  public Operator getOperator() {
    return operator;
  }

  public void setOperator(Operator operator) {
    this.operator = operator;
  }

  public Trip plannedStartTime(OffsetDateTime plannedStartTime) {
    this.plannedStartTime = plannedStartTime;
    return this;
  }

  /**
   * Get plannedStartTime
   * @return plannedStartTime
  */
  @Valid 
  @Schema(name = "plannedStartTime", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("plannedStartTime")
  public OffsetDateTime getPlannedStartTime() {
    return plannedStartTime;
  }

  public void setPlannedStartTime(OffsetDateTime plannedStartTime) {
    this.plannedStartTime = plannedStartTime;
  }

  public Trip plannedEndTime(OffsetDateTime plannedEndTime) {
    this.plannedEndTime = plannedEndTime;
    return this;
  }

  /**
   * Get plannedEndTime
   * @return plannedEndTime
  */
  @Valid 
  @Schema(name = "plannedEndTime", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("plannedEndTime")
  public OffsetDateTime getPlannedEndTime() {
    return plannedEndTime;
  }

  public void setPlannedEndTime(OffsetDateTime plannedEndTime) {
    this.plannedEndTime = plannedEndTime;
  }

  public Trip actualStartTime(OffsetDateTime actualStartTime) {
    this.actualStartTime = actualStartTime;
    return this;
  }

  /**
   * Get actualStartTime
   * @return actualStartTime
  */
  @Valid 
  @Schema(name = "actualStartTime", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("actualStartTime")
  public OffsetDateTime getActualStartTime() {
    return actualStartTime;
  }

  public void setActualStartTime(OffsetDateTime actualStartTime) {
    this.actualStartTime = actualStartTime;
  }

  public Trip actualEndTime(OffsetDateTime actualEndTime) {
    this.actualEndTime = actualEndTime;
    return this;
  }

  /**
   * Get actualEndTime
   * @return actualEndTime
  */
  @Valid 
  @Schema(name = "actualEndTime", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("actualEndTime")
  public OffsetDateTime getActualEndTime() {
    return actualEndTime;
  }

  public void setActualEndTime(OffsetDateTime actualEndTime) {
    this.actualEndTime = actualEndTime;
  }

  public Trip audit(Audit audit) {
    this.audit = audit;
    return this;
  }

  /**
   * Get audit
   * @return audit
  */
  @Valid 
  @Schema(name = "audit", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("audit")
  public Audit getAudit() {
    return audit;
  }

  public void setAudit(Audit audit) {
    this.audit = audit;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Trip trip = (Trip) o;
    return Objects.equals(this.routeId, trip.routeId) &&
        Objects.equals(this.serviceCode, trip.serviceCode) &&
        Objects.equals(this.status, trip.status) &&
        Objects.equals(this.operator, trip.operator) &&
        Objects.equals(this.plannedStartTime, trip.plannedStartTime) &&
        Objects.equals(this.plannedEndTime, trip.plannedEndTime) &&
        Objects.equals(this.actualStartTime, trip.actualStartTime) &&
        Objects.equals(this.actualEndTime, trip.actualEndTime) &&
        Objects.equals(this.audit, trip.audit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(routeId, serviceCode, status, operator, plannedStartTime, plannedEndTime, actualStartTime, actualEndTime, audit);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Trip {\n");
    sb.append("    routeId: ").append(toIndentedString(routeId)).append("\n");
    sb.append("    serviceCode: ").append(toIndentedString(serviceCode)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    operator: ").append(toIndentedString(operator)).append("\n");
    sb.append("    plannedStartTime: ").append(toIndentedString(plannedStartTime)).append("\n");
    sb.append("    plannedEndTime: ").append(toIndentedString(plannedEndTime)).append("\n");
    sb.append("    actualStartTime: ").append(toIndentedString(actualStartTime)).append("\n");
    sb.append("    actualEndTime: ").append(toIndentedString(actualEndTime)).append("\n");
    sb.append("    audit: ").append(toIndentedString(audit)).append("\n");
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

