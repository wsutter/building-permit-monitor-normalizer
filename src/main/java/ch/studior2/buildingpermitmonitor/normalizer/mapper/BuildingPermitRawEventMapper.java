package ch.studior2.buildingpermitmonitor.normalizer.mapper;

import ch.studior2.buildingpermitmonitor.contracts.event.BuildingPermitNormalizedEvent;
import ch.studior2.buildingpermitmonitor.contracts.event.BuildingPermitRawEvent;
import ch.studior2.buildingpermitmonitor.normalizer.address.AddressComposer;
import ch.studior2.buildingpermitmonitor.normalizer.classification.BuildingPermitCategoryClassifier;
import ch.studior2.buildingpermitmonitor.normalizer.classification.BuildingPermitStatusNormalizer;
import org.springframework.stereotype.Component;

@Component
public class BuildingPermitRawEventMapper {

  private static final String SOURCE = "kt-zh";

  public BuildingPermitRawEventMapper(BuildingPermitCategoryClassifier classifier) {}

  public BuildingPermitNormalizedEvent map(BuildingPermitRawEvent rawEvent) {
    String description = rawEvent.projectDescription();
    String address = formatAddress(rawEvent);

    return new BuildingPermitNormalizedEvent(
        rawEvent.externalId(),
        SOURCE,
        rawEvent.externalId(),
        shorten(description, 200),
        description,
        BuildingPermitCategoryClassifier.classify(description).name(),
        BuildingPermitStatusNormalizer.normalize(null)
            .name(), // Status field not present in BuildingPermitRawEvent
        rawEvent.municipalityName(),
        rawEvent.publicationDate(),
        address);
  }

  private String formatAddress(BuildingPermitRawEvent rawEvent) {
    return AddressComposer.compose(
        rawEvent.projectLocationAddressStreet(),
        rawEvent.projectLocationAddressHouseNumber(),
        rawEvent.projectLocationAddressSwissZipCode() != null
            ? String.valueOf(rawEvent.projectLocationAddressSwissZipCode())
            : null,
        rawEvent.projectLocationAddressTown());
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
