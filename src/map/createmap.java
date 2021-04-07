package map;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.AbstractShapeMarker;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.MultiMarker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.AbstractMapProvider;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;
import markers.CityMarker;
import markers.EarthquakeMarker;
import markers.LandQuakeMarker;
import markers.OceanQuakeMarker;
import parser.dataParser;
import processing.core.PApplet;
import processing.core.PFont;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class createmap extends PApplet {

    private static final long serialVersionUID = 1L;
    private UnfoldingMap map;
    private AbstractMapProvider provider=new Microsoft.HybridProvider();

    private String data="https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";
    private String cityFile = "city-data.json";
    private String countryFile = "countries.geo.json";
    private List<Marker> cityMarkers;
    private List<Marker> countryMarkers;
    private List<Marker> earthquakeMarkers;


    public void setup(){
        size(800,500,P3D);
        setFrame();

        map=new UnfoldingMap(this,280,0,width-280,500,provider);
        MapUtils.createDefaultEventDispatcher(this,map);
        map.setZoomRange(1,10);

        List<Feature> countries= GeoJSONReader.loadData(this,countryFile);
        countryMarkers=MapUtils.createSimpleMarkers(countries);

        List<Feature> cities = GeoJSONReader.loadData(this, cityFile);
        cityMarkers = new ArrayList<>();
        for(Feature city : cities) {
            cityMarkers.add(new CityMarker(city));
        }

        List<PointFeature> features=dataParser.parseData(this,data);
        earthquakeMarkers=new ArrayList<>();

        for(PointFeature point : features){

            if(isLand(point)) {
                earthquakeMarkers.add(new LandQuakeMarker(point));
            }
            else {
                earthquakeMarkers.add(new OceanQuakeMarker(point));
            }
        }
        printQuakes();
        map.addMarkers(earthquakeMarkers);
        map.addMarkers(cityMarkers);
    }
    public void draw(){
        background(232, 227, 227);
        map.draw();
        addKey();
    }

    private boolean isLand(PointFeature earthquake) {

        for(Marker country : countryMarkers){
            if(isInCountry(earthquake,country)) return true;
        }
        return false;
    }

    private void addKey(){
        fill(139, 123, 123,150);
        stroke(255, 255, 255);

        rect(0,20,250,450,0,30,30,0);

        PFont font=createFont("Segoe UI Semilight.bold",20);
        textFont(font);
        fill(255,255,255);
        text("EARTHQUAKEMAP KEY",10,50);
        line(0,60,250,60);
        textSize(17);
        text("Magnitude",10,80);
        line(10,85,textWidth("Magnitude   "),85);

        fill(128, 0, 0,200);
        ellipse(40,110,30,30);
        fill(255, 111, 0,200);
        ellipse(40,150,30,30);
        fill(1255, 187, 0,200);
        ellipse(40,190,30,30);
        fill(255,255,255);
        textSize(15);
        text("Magnitude >5",80,115);
        text("Magnitude (4,5)",80,155);
        text("Magnitude <4",80,195);

        textSize(17);
        text("Type",10,230);
        line(10,235,textWidth("Type   "),235);
        fill(255, 255, 255,200);
        stroke(125, 103, 103);
        ellipse(40,270,30,30);
        rect(25,295,30,30);
        triangle(25,365,40,335,55,365);
        textSize(15);
        text("Land Earthquake",80,275);
        text("Ocean EarthQuake",80,315);
        text("City EarthQuake",80,355);

        textSize(18);
        stroke(255,255,255);
        text("Age",10,395);
        line(10,402,textWidth("Age   "),402);
        textSize(35);
        text("X",30,445);
        textSize(15);
        text("Past hour Earthquake",80,435);
    }

    private void setFrame(){
        Color bg=new Color(255, 250, 250);
        frame.setTitle("EarthquakeMap");
        frame.setBackground(bg);
        frame.setResizable(false);

    }

    private boolean isInCountry(PointFeature point, Marker country) {

        Location checkLoc = point.getLocation();

        if(country.getClass() == MultiMarker.class) {

            for(Marker marker : ((MultiMarker)country).getMarkers()) {

                if(((AbstractShapeMarker)marker).isInsideByLocation(checkLoc)) {
                    point.addProperty("country", country.getProperty("name"));
                    return true;
                }
            }
        }
        else if(((AbstractShapeMarker)country).isInsideByLocation(checkLoc)) {
            point.addProperty("country", country.getProperty("name"));
            return true;
        }
        return false;
    }

    private void printQuakes()
    {
        HashMap<String,Integer> quakes=new HashMap<>();
        int quakesOcean=0;

        for(Marker quakeMarker : earthquakeMarkers){
            EarthquakeMarker e=(EarthquakeMarker)quakeMarker;

            if(e.isOnLand()){
                String country=(String) e.getProperty("country");
                quakes.put(country,quakes.getOrDefault(country,0)+1);
            }
            else{
                quakesOcean++;
            }
        }
        for(Map.Entry<String,Integer> entry: quakes.entrySet()){
            if(entry.getValue()>0){
                System.out.println(entry.getKey()+": "+entry.getValue());
            }
        }
        System.out.println("Number of earthquakes in the Ocean: "+quakesOcean);

    }

}
