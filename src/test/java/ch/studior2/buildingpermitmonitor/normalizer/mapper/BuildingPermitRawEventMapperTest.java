package ch.studior2.buildingpermitmonitor.normalizer.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import ch.studior2.buildingpermitmonitor.contracts.event.BuildingPermitNormalizedEvent;
import ch.studior2.buildingpermitmonitor.contracts.event.BuildingPermitRawEvent;
import ch.studior2.buildingpermitmonitor.normalizer.classification.BuildingPermitCategoryClassifier;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class BuildingPermitRawEventMapperTest {

  private final BuildingPermitRawEventMapper mapper =
      new BuildingPermitRawEventMapper(new BuildingPermitCategoryClassifier());

  @Test
  void map_setsAddressFieldCorrectly() {
    BuildingPermitRawEvent rawEvent =
        new BuildingPermitRawEvent(
            "ext-123",
            "pub-1",
            LocalDate.parse("2026-06-24"),
            null,
            null,
            null,
            "Zürich",
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            "Test Project",
            null,
            "Bahnhofstrasse",
            "10",
            8000,
            "Zürich",
            null,
            null,
            null,
            null,
            LocalDate.parse("2026-06-24"));

    BuildingPermitNormalizedEvent normalizedEvent = mapper.map(rawEvent);
    assertThat(normalizedEvent.address()).isEqualTo("Bahnhofstrasse 10, 8000 Zürich");
  }

  @Test
  void map_setsAddressToUnknown_whenStreetIsMissing() {
    BuildingPermitRawEvent rawEvent =
        new BuildingPermitRawEvent(
            "ext-123",
            "pub-1",
            LocalDate.parse("2026-06-24"),
            null,
            null,
            null,
            "Zürich",
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            "Test Project",
            null,
            null,
            "10",
            8000,
            "Zürich",
            null,
            null,
            null,
            null,
            LocalDate.parse("2026-06-24"));

    BuildingPermitNormalizedEvent normalizedEvent = mapper.map(rawEvent);
    assertThat(normalizedEvent.address()).isEqualTo("UNKNOWN_ADDRESS");
  }
}
