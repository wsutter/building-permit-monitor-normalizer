package ch.studior2.buildingpermitmonitor.normalizer.mapper;

import ch.studior2.buildingpermitmonitor.contracts.event.BuildingPermitNormalizedEvent;
import ch.studior2.buildingpermitmonitor.contracts.event.BuildingPermitRawEvent;
import ch.studior2.buildingpermitmonitor.contracts.model.BuildingPermitStatus;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class BuildingPermitRawEventMapper {

  private final BuildingPermitCategoryClassifier classifier;

  public BuildingPermitRawEventMapper(BuildingPermitCategoryClassifier classifier) {
    this.classifier = classifier;
  }

  public BuildingPermitNormalizedEvent map(BuildingPermitRawEvent rawEvent) {
    Map<String, String> payload = rawEvent.payload();

    String municipality =
        firstNonBlank(
            payload.get("gemeinde"), payload.get("Gemeinde"), payload.get("municipality"));
    String description =
        firstNonBlank(
            payload.get("bauvorhaben"), payload.get("Bauvorhaben"), payload.get("description"));
    String address =
        firstNonBlank(payload.get("adresse"), payload.get("Adresse"), payload.get("address"));
    String permitId = rawEvent.source() + ":" + rawEvent.externalId();

    return new BuildingPermitNormalizedEvent(
        permitId,
        rawEvent.source(),
        rawEvent.externalId(),
        shorten(description, 120),
        description,
        classifier.classify(description).name(),
        BuildingPermitStatus.SUBMITTED.name(),
        municipality,
        null,
        address);
  }

  private String firstNonBlank(String... values) {
    for (String value : values) {
      if (value != null && !value.isBlank()) {
        return value.trim();
      }
    }
    return null;
  }

  private String shorten(String value, int maxLength) {
    if (value == null || value.length() <= maxLength) {
      return value;
    }
    return value.substring(0, maxLength - 3) + "...";
  }
}
