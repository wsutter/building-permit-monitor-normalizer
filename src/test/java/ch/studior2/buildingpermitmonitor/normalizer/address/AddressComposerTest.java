package ch.studior2.buildingpermitmonitor.normalizer.address;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import net.datafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests for AddressComposer utility. Ensures addresses are composed correctly from raw components.
 */
@DisplayName("AddressComposer")
class AddressComposerTest {
  private static final Faker faker = new Faker();

  @Nested
  @DisplayName("When all address components are valid")
  class ValidComponents {
    @ParameterizedTest
    @MethodSource(
        "ch.studior2.buildingpermitmonitor.normalizer.address.AddressComposerTest#validAddressCombinations")
    @DisplayName("composes full address from valid components")
    void compose_returnsExpectedFormat(
        String street, String streetNumber, String zip, String town, String expected) {
      // Act
      String result = AddressComposer.compose(street, streetNumber, zip, town);

      // Assert
      assertEquals(expected, result);
    }
  }

  @Nested
  @DisplayName("When address components are missing")
  class MissingComponents {
    @ParameterizedTest
    @MethodSource(
        "ch.studior2.buildingpermitmonitor.normalizer.address.AddressComposerTest#missingAddressCombinations")
    @DisplayName("returns UNKNOWN_ADDRESS for missing components")
    void compose_returnsUnknownForMissingComponents(
        String street, String streetNumber, String zip, String town, String expected) {
      // Act
      String result = AddressComposer.compose(street, streetNumber, zip, town);

      // Assert
      assertEquals(expected, result);
    }
  }

  @Nested
  @DisplayName("When address components are randomized")
  class RandomizedComponents {
    @ParameterizedTest
    @MethodSource(
        "ch.studior2.buildingpermitmonitor.normalizer.address.AddressComposerTest#randomizedAddressCombinations")
    @DisplayName("composes address correctly with randomized data")
    void compose_returnsExpectedFormatForRandomizedData(
        String street, String streetNumber, String zip, String town, String expected) {
      // Act
      String result = AddressComposer.compose(street, streetNumber, zip, town);

      // Assert
      assertEquals(expected, result);
    }
  }

  // Method sources for parameterized tests
  static Stream<Arguments> validAddressCombinations() {
    return Stream.of(
        Arguments.of("Hauptstrasse", "1", "8000", "Zürich", "Hauptstrasse 1, 8000 Zürich"),
        Arguments.of("Hauptstrasse", null, "8000", "Zürich", "Hauptstrasse, 8000 Zürich"),
        Arguments.of("Hauptstrasse", "1", null, "Zürich", "Hauptstrasse 1, Zürich"),
        Arguments.of("Hauptstrasse", "1", "8000", null, "Hauptstrasse 1"),
        Arguments.of("Hauptstrasse", null, null, null, "Hauptstrasse"));
  }

  static Stream<Arguments> missingAddressCombinations() {
    return Stream.of(
        Arguments.of(null, "1", "8000", "Zürich", "UNKNOWN_ADDRESS"),
        Arguments.of(null, null, "8000", "Zürich", "UNKNOWN_ADDRESS"),
        Arguments.of(" ", "1", "8000", "Zürich", "UNKNOWN_ADDRESS"),
        Arguments.of(null, null, null, null, "UNKNOWN_ADDRESS"));
  }

  static Stream<Arguments> randomizedAddressCombinations() {
    String street = faker.address().streetName();
    String streetNumber = faker.address().buildingNumber();
    String zip = faker.address().zipCode();
    String town = faker.address().city();
    String expected = AddressComposer.compose(street, streetNumber, zip, town);

    return Stream.of(
        Arguments.of(street, streetNumber, zip, town, expected),
        Arguments.of(street, null, zip, town, AddressComposer.compose(street, null, zip, town)),
        Arguments.of(
            street,
            streetNumber,
            null,
            town,
            AddressComposer.compose(street, streetNumber, null, town)));
  }
}
