package ch.studior2.buildingpermitmonitor.normalizer.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Named.named;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import ch.studior2.buildingpermitmonitor.contracts.model.BuildingPermitCategory;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("BuildingPermitCategoryClassifier")
class BuildingPermitCategoryClassifierTest {

  private final BuildingPermitCategoryClassifier classifier =
      new BuildingPermitCategoryClassifier();

  @Nested
  @DisplayName("classify")
  class Classify {

    @ParameterizedTest(name = "{0}")
    @MethodSource("descriptions")
    @DisplayName("should classify descriptions")
    void shouldClassifyDescriptions(String description, BuildingPermitCategory expected) {
      assertThat(classifier.classify(description)).isEqualTo(expected);
    }

    static Stream<Arguments> descriptions() {
      return Stream.of(
          arguments(
              named("neubau", "Neubau Mehrfamilienhaus"), BuildingPermitCategory.NEW_BUILDING),
          arguments(
              named("neubau (lowercase)", "neubau einfamilienhaus"),
              BuildingPermitCategory.NEW_BUILDING),
          arguments(named("umbau", "Umbau bestehende Wohnung"), BuildingPermitCategory.RENOVATION),
          arguments(
              named("umbau (lowercase)", "umbau bürogebäude"), BuildingPermitCategory.RENOVATION),
          arguments(named("rückbau", "Rückbau Garage"), BuildingPermitCategory.DEMOLITION),
          arguments(
              named("rückbau (lowercase)", "rückbau lagerhalle"),
              BuildingPermitCategory.DEMOLITION),
          arguments(named("sanierung", "Sanierung Dach"), BuildingPermitCategory.REFURBISHMENT),
          arguments(
              named("sanierung (lowercase)", "sanierung fassade"),
              BuildingPermitCategory.REFURBISHMENT),
          arguments(
              named("nutzungsänderung", "Nutzungsänderung Büro zu Wohnen"),
              BuildingPermitCategory.OTHER),
          arguments(
              named("nutzungsänderung (lowercase)", "nutzungsänderung lager zu wohnung"),
              BuildingPermitCategory.OTHER),
          arguments(named("blank", " "), BuildingPermitCategory.UNKNOWN),
          arguments(named("unmatched", "Bauzaun-Bewilligung"), BuildingPermitCategory.UNKNOWN));
    }
  }
}
