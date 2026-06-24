package ch.studior2.buildingpermitmonitor.normalizer.address;

public class AddressComposer {
  public static String compose(String street, String streetNumber, String zip, String town) {
    if (street == null || street.trim().isEmpty()) {
      return "UNKNOWN_ADDRESS";
    }

    StringBuilder address = new StringBuilder();
    address.append(street.trim());

    if (streetNumber != null && !streetNumber.trim().isEmpty()) {
      address.append(" ").append(streetNumber.trim());
    }

    if (zip != null && !zip.trim().isEmpty() && town != null && !town.trim().isEmpty()) {
      address.append(", ").append(zip.trim()).append(" ").append(town.trim());
    } else if (town != null && !town.trim().isEmpty()) {
      address.append(", ").append(town.trim());
    }

    return address.toString().isEmpty() ? "UNKNOWN_ADDRESS" : address.toString();
  }
}
