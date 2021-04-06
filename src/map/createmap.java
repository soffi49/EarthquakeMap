package map;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.AbstractMapProvider;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parser.dataParser;
import processing.core.PApplet;
import processing.core.PFont;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class createmap extends PApplet {

    UnfoldingMap map;
    AbstractMapProvider provider=new Microsoft.HybridProvider();
    String data="https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

    public void setup(){
        size(800,500,OPENGL);
        setFrame();

        map=new UnfoldingMap(this,280,0,width-280,500,provider);
        MapUtils.createDefaultEventDispatcher(this,map);
        map.zoomToLevel(2);
        map.setZoomRange(1,10);

        List<PointFeature> features=dataParser.parseData(this,data);
        List<Marker> markers=new ArrayList<Marker>();
        for(PointFeature point : features){
            markers.add(styleMarker(point));
        }
        map.addMarkers(markers);
    }
    public void draw(){
        background(232, 227, 227);
        map.draw();
        addKey();
    }

    private SimplePointMarker styleMarker(PointFeature feature){
        SimplePointMarker marker= new SimplePointMarker(feature.getLocation(),feature.getProperties());
        Object magnitude=feature.getProperty("magnitude");

        if((float)magnitude>5){
            marker.setRadius(15);
            marker.setColor(color(128, 0, 0,200));
        }
        else if((float)magnitude>4){
            marker.setRadius(10);
            marker.setColor(color(255, 111, 0,200));
        }
        else{
            marker.setRadius(6);
            marker.setColor(color(255, 187, 0,200));
        }
        return marker;
    }

    private void addKey(){
        fill(159, 147, 147,150);
        stroke(255, 255, 255);

        rectMode(CENTER);
        rect(0,200,500,220,30);

        PFont font=createFont("Segoe UI Semilight.bold",20);
        textFont(font);
        fill(255,255,255);
        text("EARTHQUAKEMAP KEY",10,130);

        fill(128, 0, 0,200);
        ellipse(40,180,40,40);
        fill(255, 111, 0,200);
        ellipse(40,230,30,30);
        fill(1255, 187, 0,200);
        ellipse(40,280,20,20);
        fill(255,255,255);
        textSize(15);
        text("Magnitude >5",80,185);
        text("Magnitude (4,5)",80,235);
        text("Magnitude <4",80,285);

    }

    private void setFrame(){
        Color bg=new Color(255, 250, 250);
        frame.setTitle("EarthquakeMap");
        frame.setBackground(bg);
        frame.setResizable(false);

    }
}
