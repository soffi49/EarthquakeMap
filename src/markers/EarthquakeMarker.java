package markers;

import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import processing.core.PGraphics;

import java.awt.*;
import java.util.HashMap;

public abstract class EarthquakeMarker extends CommonMarker implements Comparable<EarthquakeMarker>{


    protected boolean isOnLand;
    protected float radius;
    protected static final float kmPerMile = 1.6f;

    public static final float THRESHOLD_MODERATE = 5;
    public static final float THRESHOLD_LIGHT = 4;

    public static final float THRESHOLD_INTERMEDIATE = 70;
    public static final float THRESHOLD_DEEP = 300;

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


    public void drawMarker(PGraphics pg, float x, float y) {

        pg.pushStyle();
        colorDetermine(pg);
        drawEarthquake(pg, x, y);

        if(getProperty("age").equals("Past Hour")){
            pg.fill(255,255,255);
            pg.textSize(10);
            pg.text("X",x-5,y+5,4);
        }

        pg.popStyle();

    }

    public void showTitle(PGraphics pg, float x, float y)
    {
        if(selected){
            String display=getTitle();
            pg.textSize(12);
            pg.fill(125, 103, 103);
            pg.rect(x,y-14,pg.textWidth(display),14);
            pg.fill(255,255,255);
            pg.text(display,x,y);
        }

    }

    public double threatCircle() {
        double miles = 20.0f * Math.pow(1.8, 2*getMagnitude()-5);
        return (miles * kmPerMile);
    }

    private void colorDetermine(PGraphics pg) {

        if(getMagnitude() >THRESHOLD_MODERATE){
            pg.fill(high.getRGB());
        }
        else if(getMagnitude() >THRESHOLD_LIGHT){
            pg.fill(medium.getRGB());
        }
        else{
            pg.fill(low.getRGB());
        }
    }

    public int compareTo(EarthquakeMarker marker){
        if(getMagnitude()>marker.getMagnitude()) return -1;
        if (getMagnitude()<marker.getMagnitude()) return 1;
        return 0;
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
    public boolean isThreatCircle(Marker m){
        if(m.getDistanceTo(this.getLocation())>threatCircle()) return false;
        return true;
    }

}
