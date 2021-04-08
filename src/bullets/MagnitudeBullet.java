package bullets;

import de.fhpotsdam.unfolding.marker.Marker;
import markers.EarthquakeMarker;

import java.awt.*;

public class MagnitudeBullet extends Bullet{

    private final int strength;

    public MagnitudeBullet(Point p,int s,int st){
        super(p,s);
        strength=st;
    }

    @Override
    public boolean doesBelong(Point p) {
        if(p.distance(this.center.x,this.center.y)<=this.size) return true;
        return false;
    }

    @Override
    public void hideMarker(Marker m) {
        switch (strength){
            case 3: if(((EarthquakeMarker)m).getMagnitude()>5) m.setHidden(isactive);
                break;
            case 2: if(((EarthquakeMarker)m).getMagnitude()<=5 &&
                    ((EarthquakeMarker)m).getMagnitude()>4) m.setHidden(isactive);
                break;
            case 1: if(((EarthquakeMarker)m).getMagnitude()<=4) m.setHidden(isactive);
                break;
            default: break;
        }
    }

}
