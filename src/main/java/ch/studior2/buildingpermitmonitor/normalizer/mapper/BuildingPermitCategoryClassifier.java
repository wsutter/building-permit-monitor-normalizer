package ch.studior2.buildingpermitmonitor.normalizer.mapper;

import ch.studior2.buildingpermitmonitor.contracts.model.BuildingPermitCategory;
import org.springframework.stereotype.Component;

@Component
public class BuildingPermitCategoryClassifier {

  public BuildingPermitCategory classify(String description) {
    if (description == null || description.isBlank()) {
      return BuildingPermitCategory.UNKNOWN;
    }

    String value = description.toLowerCase();

    if (value.contains("neubau")) {
      return BuildingPermitCategory.NEW_BUILDING;
    }
    if (value.contains("umbau")) {
      return BuildingPermitCategory.RENOVATION;
    }
    if (value.contains("abbruch") || value.contains("rückbau") || value.contains("rueckbau")) {
      return BuildingPermitCategory.DEMOLITION;
    }
    if (value.contains("sanierung")) {
      return BuildingPermitCategory.REFURBISHMENT;
    }

    return BuildingPermitCategory.OTHER;
  }
}
