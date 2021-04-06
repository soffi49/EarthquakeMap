package parser;

import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import processing.core.PApplet;
import processing.data.XML;
import java.util.ArrayList;
import java.util.List;

public class dataParser {

    public static List<PointFeature> parseData(PApplet papplet,String data){
        List<PointFeature> features=new ArrayList<PointFeature>();
        XML earthquakedata= papplet.loadXML(data);
        XML[] items=earthquakedata.getChildren("entry");

        PointFeature point;

        for(int i=0;i<items.length;i++){

            Location location=getLocation(items[i]);

            if(location==null) continue;

            point=new PointFeature(location);
            features.add(point);

            String title=getString(items[i],"title");
            if(title!= null){
                point.putProperty("title",title);
                point.putProperty("magnitude",Float.parseFloat(title.substring(2,5)));
            }

            float depth=Float.parseFloat(getString(items[i],"georss:elev"));
            int interVal = (int)(depth/100);
            depth = (float) interVal/10;
            point.putProperty("depth", Math.abs((depth)));

            XML[] categories = items[i].getChildren("category");
            for (XML category : categories) {
                String label = category.getString("label");
                if ("Age".equals(label)) {
                    String age = category.getString("term");
                    point.putProperty("age", age);
                }
            }
        }

        return features;
    }

    private static Location getLocation(XML item){
        Location location=null;
        XML itemLoc=item.getChild("georss:point");

        if(itemLoc!=null && itemLoc.getContent()!=null){
            String pointData=itemLoc.getContent();
            String[] latlon=pointData.split(" ");
            float lat = Float.valueOf(latlon[0]);
            float lon = Float.valueOf(latlon[1]);
            location=new Location(lat,lon);
        }
        return  location;
    }

    private static String getString(XML item,String tag){
        String str=null;
        XML itemStr=item.getChild(tag);

        if(itemStr!=null && itemStr.getContent()!=null){
           str=itemStr.getContent();
        }
        return str;
    }
}
