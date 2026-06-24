package ch.studior2.buildingpermitmonitor.normalizer.classification;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ch.studior2.buildingpermitmonitor.contracts.model.BuildingPermitStatus;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BuildingPermitStatusNormalizerTest {
  @ParameterizedTest
  @CsvSource({
    "Eingereicht, SUBMITTED",
    "Beantragt, SUBMITTED",
    "Genehmigt, APPROVED",
    "Abgelehnt, REJECTED",
    "Zurückgezogen, WITHDRAWN",
    " , UNKNOWN",
    "null, UNKNOWN",
    "EINGEREICHT, SUBMITTED",
    "abgelehnt wegen Formfehler, REJECTED",
    "teilweise genehmigt, UNKNOWN",
    "abc123, UNKNOWN"
  })
  void normalize_returnsExpectedStatus(String status, BuildingPermitStatus expected) {
    String input = "null".equals(status) ? null : status;
    assertEquals(expected, BuildingPermitStatusNormalizer.normalize(input));
  }
}
