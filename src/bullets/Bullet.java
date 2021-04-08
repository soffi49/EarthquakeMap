package bullets;

import de.fhpotsdam.unfolding.marker.Marker;

import java.awt.*;

public abstract class Bullet {
    protected Point center;
    protected int size;
    public boolean isactive;

    public Bullet(Point c,int s){
        center=c;
        size=s;
        isactive=false;
    }

    public abstract boolean doesBelong(Point p);
    public abstract void hideMarker(Marker m);
}
