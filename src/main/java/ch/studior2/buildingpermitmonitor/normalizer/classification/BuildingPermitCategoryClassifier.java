package ch.studior2.buildingpermitmonitor.normalizer.classification;

import ch.studior2.buildingpermitmonitor.contracts.model.BuildingPermitCategory;

public class BuildingPermitCategoryClassifier {
  public static BuildingPermitCategory classify(String projectDescription) {
    if (projectDescription == null || projectDescription.trim().isEmpty()) {
      return BuildingPermitCategory.UNKNOWN;
    }
    String description = projectDescription.toLowerCase().trim();

    return switch (description) {
      case String d when d.contains("neubau") -> BuildingPermitCategory.NEW_BUILDING;
      case String d when d.contains("umbau") -> BuildingPermitCategory.RENOVATION;
      case String d when d.contains("rückbau") -> BuildingPermitCategory.DEMOLITION;
      case String d when d.contains("sanierung") -> BuildingPermitCategory.REFURBISHMENT;
      case String d when d.contains("nutzungsänderung") -> BuildingPermitCategory.OTHER;
      default -> BuildingPermitCategory.UNKNOWN;
    };
  }
}
