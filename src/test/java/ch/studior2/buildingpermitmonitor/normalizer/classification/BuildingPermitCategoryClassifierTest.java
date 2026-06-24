package ch.studior2.buildingpermitmonitor.normalizer.classification;

import static org.assertj.core.api.Assertions.assertThat;

import ch.studior2.buildingpermitmonitor.contracts.model.BuildingPermitCategory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BuildingPermitCategoryClassifierTest {
  @ParameterizedTest
  @CsvSource({
    "Neubau eines Einfamilienhauses, NEW_BUILDING",
    "Umbau Bürogebäude, RENOVATION",
    "Rückbau alter Fabrik, DEMOLITION",
    "Sanierung Dach, REFURBISHMENT",
    "Nutzungsänderung Lager zu Büro, OTHER",
    "irgendwas unbekanntes, UNKNOWN",
    " , UNKNOWN",
    "null, UNKNOWN",
    "NeuBAU mit gemischter Schreibweise, NEW_BUILDING",
    "SANIERUNG in Großbuchstaben, REFURBISHMENT"
  })
  void classify_returnsExpectedCategory(String description, BuildingPermitCategory expected) {
    // Handle "null" string literal case
    String input = "null".equals(description) ? null : description;
    assertThat(BuildingPermitCategoryClassifier.classify(input)).isEqualTo(expected);
  }
}
