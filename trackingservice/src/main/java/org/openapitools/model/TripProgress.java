package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.model.TripProgressProgressDataInner;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Trip progress is shared by the client continuously as the operator is moving
 */

@Schema(name = "TripProgress", description = "Trip progress is shared by the client continuously as the operator is moving")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-07-30T17:09:16.737885200+05:30[Asia/Calcutta]")
public class TripProgress {

  private String progressReportedTime;

  @Valid
  private List<@Valid TripProgressProgressDataInner> progressData;

  public TripProgress progressReportedTime(String progressReportedTime) {
    this.progressReportedTime = progressReportedTime;
    return this;
  }

  /**
   * Date and time in ISO_DATE_TIME format. Time at which the client app is reporting this data. This can be different from the time when the geo data was recorded as the app might be offline at that time but reporting it later once the app is online.
   * @return progressReportedTime
  */
  
  @Schema(name = "progressReportedTime", example = "2023-07-30T10:24:10.547Z", description = "Date and time in ISO_DATE_TIME format. Time at which the client app is reporting this data. This can be different from the time when the geo data was recorded as the app might be offline at that time but reporting it later once the app is online.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("progressReportedTime")
  public String getProgressReportedTime() {
    return progressReportedTime;
  }

  public void setProgressReportedTime(String progressReportedTime) {
    this.progressReportedTime = progressReportedTime;
  }

  public TripProgress progressData(List<@Valid TripProgressProgressDataInner> progressData) {
    this.progressData = progressData;
    return this;
  }

  public TripProgress addProgressDataItem(TripProgressProgressDataInner progressDataItem) {
    if (this.progressData == null) {
      this.progressData = new ArrayList<>();
    }
    this.progressData.add(progressDataItem);
    return this;
  }

  /**
   * Get progressData
   * @return progressData
  */
  @Valid 
  @Schema(name = "progressData", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("progressData")
  public List<@Valid TripProgressProgressDataInner> getProgressData() {
    return progressData;
  }

  public void setProgressData(List<@Valid TripProgressProgressDataInner> progressData) {
    this.progressData = progressData;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TripProgress tripProgress = (TripProgress) o;
    return Objects.equals(this.progressReportedTime, tripProgress.progressReportedTime) &&
        Objects.equals(this.progressData, tripProgress.progressData);
  }

  @Override
  public int hashCode() {
    return Objects.hash(progressReportedTime, progressData);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TripProgress {\n");
    sb.append("    progressReportedTime: ").append(toIndentedString(progressReportedTime)).append("\n");
    sb.append("    progressData: ").append(toIndentedString(progressData)).append("\n");
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

