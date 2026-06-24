package ch.studior2.buildingpermitmonitor.normalizer.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import ch.studior2.buildingpermitmonitor.contracts.event.BuildingPermitNormalizedEvent;
import ch.studior2.buildingpermitmonitor.contracts.event.BuildingPermitRawEvent;
import ch.studior2.buildingpermitmonitor.normalizer.classification.BuildingPermitCategoryClassifier;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("BuildingPermitRawEventMapper")
class BuildingPermitRawEventMapperTest {

  private final BuildingPermitRawEventMapper mapper =
      new BuildingPermitRawEventMapper(new BuildingPermitCategoryClassifier());

  @Test
  @DisplayName("should produce a non-null address and municipality from populated source fields")
  void shouldProduceAddressAndMunicipality() {
    BuildingPermitNormalizedEvent normalized = mapper.map(rawEventWithAddress("Eingereicht"));

    assertThat(normalized.municipality()).isEqualTo("Thalwil");
    assertThat(normalized.address()).isEqualTo("Eisenbahnstrasse 27, 8800 Thalwil");
    assertThat(normalized.permitId()).isEqualTo("00002982:00006183");
    assertThat(normalized.source()).isEqualTo("kt-zh");
    assertThat(normalized.category()).isEqualTo("RENOVATION");
    assertThat(normalized.publishedDate()).isEqualTo(LocalDate.of(2026, 5, 17));
    assertThat(normalized.status()).isEqualTo("UNKNOWN");
  }

  @Test
  @DisplayName("should default status field to UNKNOWN")
  void shouldDefaultStatusField() {
    BuildingPermitNormalizedEvent normalized = mapper.map(rawEventWithAddress(null));
    assertThat(normalized.status()).isEqualTo("UNKNOWN");
  }

  // Raw event with the fields the mapper consumes populated (as the fixed binding now delivers),
  // everything else null. Positions follow the BuildingPermitRawEvent record component order.
  private static BuildingPermitRawEvent rawEventWithAddress(String status) {
    return new BuildingPermitRawEvent(
        "00002982", // id
        "00006183", // publicationNumber
        LocalDate.of(2026, 5, 17), // publicationDate
        null, // entryDeadline
        null, // expirationDate
        141, // bfsNr
        "Thalwil", // municipalityName
        null, // buildingContractorLegalEntitySelectType
        null, // buildingContractorNoUid
        null, // buildingContractorIndex
        null, // buildingContractorCompanyLegalForm
        null, // buildingContractorCompanyLegalFormDe
        null, // buildingContractorCompanyAddressSwissZipCode
        null, // buildingContractorCompanyAddressTown
        null, // projectFramerSelectType
        null, // projectFramerLegalEntitySelectType
        null, // projectFramerNoUid
        null, // projectFramerIndex
        null, // projectFramerCompanyLegalForm
        null, // projectFramerCompanyLegalFormDe
        null, // projectFramerCompanyAddressSwissZipCode
        null, // projectFramerCompanyAddressTown
        null, // delegationSelectType
        null, // delegationBuildingContractorLegalEntitySelectType
        null, // delegationBuildingContractorNoUid
        null, // delegationBuildingContractorIndex
        null, // delegationBuildingContractorCompanyLegalForm
        null, // delegationBuildingContractorCompanyLegalFormDe
        null, // delegationBuildingContractorCompanyAddressSwissZipCode
        null, // delegationBuildingContractorCompanyAddressTown
        "Umbau Wohnung", // projectDescription
        null, // projectLocationAddressIndex
        "Eisenbahnstrasse", // projectLocationAddressStreet
        "27", // projectLocationAddressHouseNumber
        8800, // projectLocationAddressSwissZipCode
        "Thalwil", // projectLocationAddressTown
        null, // districtCadastreRelationCadastre
        null, // districtCadastreRelationCadastreRaw
        null, // districtCadastreRelationBuildingZone
        null, // districtCadastreRelationDistrict
        null); // lastUpdated
  }
}
