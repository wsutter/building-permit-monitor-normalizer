package ch.studior2.buildingpermitmonitor.normalizer.mapper;

import ch.studior2.buildingpermitmonitor.contracts.model.BuildingPermitCategory;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * Classifies building permit descriptions into canonical categories. Rules are case-insensitive and
 * match German keywords.
 */
@Component
public class BuildingPermitCategoryClassifier {

  private static final Map<String, BuildingPermitCategory> KEYWORD_TO_CATEGORY = new HashMap<>();

  static {
    // New construction
    KEYWORD_TO_CATEGORY.put("neubau", BuildingPermitCategory.NEW_BUILDING);

    // Renovation
    KEYWORD_TO_CATEGORY.put("umbau", BuildingPermitCategory.RENOVATION);

    // Demolition
    KEYWORD_TO_CATEGORY.put("abbruch", BuildingPermitCategory.DEMOLITION);
    KEYWORD_TO_CATEGORY.put("rückbau", BuildingPermitCategory.DEMOLITION);
    KEYWORD_TO_CATEGORY.put("rueckbau", BuildingPermitCategory.DEMOLITION);

    // Refurbishment
    KEYWORD_TO_CATEGORY.put("sanierung", BuildingPermitCategory.REFURBISHMENT);

    // Change of use
    KEYWORD_TO_CATEGORY.put("nutzungsänderung", BuildingPermitCategory.OTHER);
    KEYWORD_TO_CATEGORY.put("nutzungsaenderung", BuildingPermitCategory.OTHER);
  }

  public BuildingPermitCategory classify(String description) {
    if (description == null || description.isBlank()) {
      return BuildingPermitCategory.UNKNOWN;
    }

    String value = description.toLowerCase();

    return KEYWORD_TO_CATEGORY.entrySet().stream()
        .filter(entry -> value.contains(entry.getKey()))
        .findFirst()
        .map(Map.Entry::getValue)
        .orElse(BuildingPermitCategory.UNKNOWN);
  }
}
