package ch.studior2.buildingpermitmonitor.normalizer.classification;

import ch.studior2.buildingpermitmonitor.contracts.model.BuildingPermitCategory;

public class BuildingPermitCategoryClassifier {
  public static BuildingPermitCategory classify(String projectDescription) {
    if (projectDescription == null || projectDescription.trim().isEmpty()) {
      return BuildingPermitCategory.UNKNOWN;
    }
    String desc = projectDescription.toLowerCase().trim();

    if (desc.contains("neubau")) {
      return BuildingPermitCategory.NEW_BUILDING;
    } else if (desc.contains("umbau")) {
      return BuildingPermitCategory.RENOVATION;
    } else if (desc.contains("rückbau")) {
      return BuildingPermitCategory.DEMOLITION;
    } else if (desc.contains("sanierung")) {
      return BuildingPermitCategory.REFURBISHMENT;
    } else if (desc.contains("nutzungsänderung")) {
      return BuildingPermitCategory.OTHER;
    } else {
      return BuildingPermitCategory.UNKNOWN;
    }
  }
}
