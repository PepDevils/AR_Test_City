package pt.pepdevils.virtualbraga.model;

import android.location.Location;

/**
 * Created by Pedro Fonseca on 10/10/2017.
 */

public class ARPoint {

    Location location;
    String name;

    public ARPoint(String name, double lat, double lon, double altitude) {
        this.name = name;
        location = new Location("ARPoint_" + name);
        location.setLatitude(lat);
        location.setLongitude(lon);
        location.setAltitude(altitude);
    }

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

}
