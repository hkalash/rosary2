package com.smartsoftwaresolutions.rosary2;

public class shared_Menu {
// it is the path that will be shared

  public String flavor(String Flavor) {
      String path = "";

      if (Flavor.equals("Free")) {
          path = "Check it out.\n\n" + " https://play.google.com/store/apps/details?id=com.smartsoftwaresolutions.rosary2.free";

      } else if (Flavor.equals("NM")) {
          path = "Check it out\n\n" + " https://play.google.com/store/apps/details?id=com.smartsoftwaresolutions.rosary2.nm";
      }









      return path;
}
}
