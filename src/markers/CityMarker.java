package markers;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import processing.core.PGraphics;

import java.awt.*;

public class CityMarker extends SimplePointMarker {

    public static final int TRI_SIZE = 7;
    private static final Color trcolor=new Color(194, 55, 0);
    public CityMarker(Location location) {
        super(location);
    }
    public CityMarker(Feature city) {
        super(((PointFeature)city).getLocation(), city.getProperties());
    }

    public void draw(PGraphics pg, float x, float y) {

        pg.pushStyle();
        PointFloat p1=new PointFloat(x,(y-(float)(TRI_SIZE*2/3)));
        PointFloat p2=new PointFloat(x+(float)(TRI_SIZE/Math.sqrt(3)),
                y+(float)(TRI_SIZE/3));
        PointFloat p3=new PointFloat(x-(float)(TRI_SIZE/Math.sqrt(3)),
                y+(float)(TRI_SIZE/3));

        pg.fill(trcolor.getRGB());
        pg.triangle(p1.xCoord,p1.yCoord,p2.xCoord,p2.yCoord,p3.xCoord,p3.yCoord);
        pg.popStyle();
    }
    public String getCity()
    {
        return getStringProperty("name");
    }
    public String getCountry()
    {
        return getStringProperty("country");
    }
    public float getPopulation()
    {
        return Float.parseFloat(getStringProperty("population"));
    }

}
