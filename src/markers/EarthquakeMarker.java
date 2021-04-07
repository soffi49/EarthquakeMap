package markers;

import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import processing.core.PConstants;
import processing.core.PGraphics;

import java.awt.*;
import java.util.HashMap;

public abstract class EarthquakeMarker extends SimplePointMarker{


    protected boolean isOnLand;
    protected float radius;

    /** Greater than or equal to this threshold is a moderate earthquake */
    public static final float THRESHOLD_MODERATE = 5;
    /** Greater than or equal to this threshold is a light earthquake */
    public static final float THRESHOLD_LIGHT = 4;

    /** Greater than or equal to this threshold is an intermediate depth */
    public static final float THRESHOLD_INTERMEDIATE = 70;
    /** Greater than or equal to this threshold is a deep depth */
    public static final float THRESHOLD_DEEP = 300;

    // ADD constants for colors
    private static final Color high=new Color(128, 0, 0,200);
    private static final Color medium=new Color(255, 111, 0,200);
    private static final Color low=new Color(255, 187, 0,200);

    public abstract void drawEarthquake(PGraphics pg, float x, float y);

    public EarthquakeMarker (PointFeature feature)
    {
        super(feature.getLocation());
        HashMap<String, Object> properties = feature.getProperties();
        float magnitude = Float.parseFloat(properties.get("magnitude").toString());
        properties.put("radius", 2*magnitude );
        setProperties(properties);
        this.radius = 1.75f*getMagnitude();
    }


    public void draw(PGraphics pg, float x, float y) {

        pg.pushStyle();
        colorDetermine(pg);
        drawEarthquake(pg, x, y);

        if(getProperty("age").equals("Past Hour")){
            pg.fill(255,255,255);
            pg.textSize(20);
            pg.text("X",x,y,20);
        }

        pg.popStyle();

    }

    private void colorDetermine(PGraphics pg) {

        if(getMagnitude() >5){
            pg.fill(high.getRGB());
        }
        else if(getMagnitude() >4){
            pg.fill(medium.getRGB());
        }
        else{
            pg.fill(low.getRGB());
        }
    }

    public float getMagnitude() {
        return Float.parseFloat(getProperty("magnitude").toString());
    }
    public float getDepth() {
        return Float.parseFloat(getProperty("depth").toString());
    }
    public String getTitle() {
        return (String) getProperty("title");

    }
    public float getRadius() {
        return Float.parseFloat(getProperty("radius").toString());
    }
    public boolean isOnLand()
    {
        return isOnLand;
    }

}