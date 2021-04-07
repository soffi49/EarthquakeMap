package markers;

import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.utils.ScreenPosition;
import map.createmap;
import processing.core.PConstants;
import processing.core.PGraphics;

import java.util.List;

public class OceanQuakeMarker extends EarthquakeMarker {

    public OceanQuakeMarker(PointFeature quake) {
        super(quake);
        isOnLand = false;
    }


    @Override
    public void drawEarthquake(PGraphics pg, float x, float y) {
        pg.rectMode(PConstants.CENTER);
        pg.rect(x,y,radius,radius);

    }



}