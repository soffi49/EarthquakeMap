package bullets;

import de.fhpotsdam.unfolding.marker.Marker;

import java.awt.*;

public class AgeBullet extends Bullet{

    public AgeBullet(Point p,int s){
        super(p,s);
    }

    @Override
    public boolean doesBelong(Point p) {
        if((p.x<=(center.x+size/2))&&(p.x>=(center.x-size/2))&&(p.y>=(center.y-size/2))&&(p.y<=(center.y+size/2)))
            return true;
        return false;
    }

    @Override
    public void hideMarker(Marker m) {
        if(m.getProperty("age").equals("Past Hour")) m.setHidden(isactive);
    }
}
