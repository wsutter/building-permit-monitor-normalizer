package ch.studior2.buildingpermitmonitor.normalizer.mapper;

import ch.studior2.buildingpermitmonitor.contracts.event.BuildingPermitNormalizedEvent;
import ch.studior2.buildingpermitmonitor.contracts.event.BuildingPermitRawEvent;
import ch.studior2.buildingpermitmonitor.contracts.model.BuildingPermitStatus;
import org.springframework.stereotype.Component;

@Component
public class BuildingPermitRawEventMapper {

  private static final String SOURCE = "kt-zh";

  private final BuildingPermitCategoryClassifier classifier;

  public BuildingPermitRawEventMapper(BuildingPermitCategoryClassifier classifier) {
    this.classifier = classifier;
  }

  public BuildingPermitNormalizedEvent map(BuildingPermitRawEvent rawEvent) {
    String description = rawEvent.projectDescription();
    String address = formatAddress(rawEvent);

    return new BuildingPermitNormalizedEvent(
        rawEvent.externalId(),
        SOURCE,
        rawEvent.externalId(),
        shorten(description, 120),
        description,
        classifier.classify(description).name(),
        BuildingPermitStatus.SUBMITTED.name(),
        rawEvent.municipalityName(),
        rawEvent.publicationDate(),
        address);
  }

  private String formatAddress(BuildingPermitRawEvent rawEvent) {
    return firstNonBlank(
        joinAddressParts(
            rawEvent.projectLocationAddressStreet(),
            rawEvent.projectLocationAddressHouseNumber(),
            rawEvent.projectLocationAddressSwissZipCode(),
            rawEvent.projectLocationAddressTown()));
  }

  private String joinAddressParts(String street, String houseNumber, Integer zipCode, String town) {
    String streetAndHouseNumber = joinNonBlank(" ", street, houseNumber);

    String zipCodeAndTown =
        joinNonBlank(" ", zipCode == null ? null : String.valueOf(zipCode), town);

    return joinNonBlank(", ", streetAndHouseNumber, zipCodeAndTown);
  }

  private String firstNonBlank(String... values) {
    for (String value : values) {
      if (value != null && !value.isBlank()) {
        return value.trim();
      }
    }
    return null;
  }

  private String joinNonBlank(String delimiter, String... values) {
    StringBuilder result = new StringBuilder();

    for (String value : values) {
      if (value != null && !value.isBlank()) {
        if (!result.isEmpty()) {
          result.append(delimiter);
        }
        result.append(value.trim());
      }
    }

    return result.isEmpty() ? null : result.toString();
  }

  private String shorten(String value, int maxLength) {
    if (value == null || value.length() <= maxLength) {
      return value;
    }
    return value.substring(0, maxLength - 3) + "...";
  }
}
