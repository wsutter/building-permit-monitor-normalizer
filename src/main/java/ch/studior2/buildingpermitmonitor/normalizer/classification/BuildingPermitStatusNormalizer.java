package ch.studior2.buildingpermitmonitor.normalizer.classification;

import ch.studior2.buildingpermitmonitor.contracts.model.BuildingPermitStatus;

public class BuildingPermitStatusNormalizer {
  public static BuildingPermitStatus normalize(String status) {
    if (status == null || status.trim().isEmpty()) {
      return BuildingPermitStatus.UNKNOWN;
    }
    String normalizedStatus = status.toLowerCase().trim();

    return switch (normalizedStatus) {
      case String s when s.contains("eingereicht") || s.contains("beantragt") ->
          BuildingPermitStatus.SUBMITTED;
      case String s when s.contains("abgelehnt") -> BuildingPermitStatus.REJECTED;
      case String s when s.contains("zurückgezogen") -> BuildingPermitStatus.WITHDRAWN;
      case String s when s.contains("genehmigt") && s.startsWith("genehmigt") ->
          BuildingPermitStatus.APPROVED;
      default -> BuildingPermitStatus.UNKNOWN;
    };
  }
}
