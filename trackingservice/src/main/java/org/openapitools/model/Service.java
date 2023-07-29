package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.model.ServiceAlertsInner;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Citizen service provided by the government agency
 */

@Schema(name = "Service", description = "Citizen service provided by the government agency")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-07-29T10:39:40.023515100+05:30[Asia/Calcutta]")
public class Service {

  @Valid
  private List<@Valid ServiceAlertsInner> alerts;

  public Service alerts(List<@Valid ServiceAlertsInner> alerts) {
    this.alerts = alerts;
    return this;
  }

  public Service addAlertsItem(ServiceAlertsInner alertsItem) {
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
  @Valid 
  @Schema(name = "alerts", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("alerts")
  public List<@Valid ServiceAlertsInner> getAlerts() {
    return alerts;
  }

  public void setAlerts(List<@Valid ServiceAlertsInner> alerts) {
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
    Service service = (Service) o;
    return Objects.equals(this.alerts, service.alerts);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alerts);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Service {\n");
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

