package bullets;

import de.fhpotsdam.unfolding.marker.Marker;
import markers.CityMarker;
import markers.LandQuakeMarker;
import markers.OceanQuakeMarker;

import java.awt.*;

public class TypeBullet extends Bullet{

    public enum type {circle,square,triangle};
    private type bulletType;

    public TypeBullet(Point p,int s,type t){
        super(p,s);
        bulletType=t;
    }

    @Override
    public boolean doesBelong(Point p) {
        switch (bulletType){
            case circle:
                if(p.distance(this.center.x,this.center.y)<=this.size) return true;
                return false;
            case square:
                if((p.x<=(center.x+size/2))&&(p.x>=(center.x-size/2))&&(p.y>=(center.y-size/2))&&(p.y<=(center.y+size/2)))
                    return true;
                return false;
            case triangle:
                Point p1=new Point(center.x, center.y-size/2);
                Point p2=new Point(center.x-size/2, center.y+size/2);
                Point p3=new Point(center.x+size/2, center.y+size/2);
                float A=Math.abs((float) (p1.x*(p2.y-p3.y)+p2.x*(p3.y-p1.y)+p3.x*(p1.y-p2.y))/2);
                float A1=Math.abs((float) (p.x*(p2.y-p3.y)+p2.x*(p3.y-p.y)+p3.x*(p.y-p2.y))/2);
                float A2=Math.abs((float) (p1.x*(p.y-p3.y)+p.x*(p3.y-p1.y)+p3.x*(p1.y-p.y))/2);
                float A3=Math.abs((float) (p1.x*(p2.y-p.y)+p2.x*(p.y-p1.y)+p.x*(p1.y-p2.y))/2);
                if(A1+A2+A3==A) return true;
                return false;
            default: return false;
        }
    }

    @Override
    public void hideMarker(Marker m) {
        switch (bulletType){
            case square: if(m instanceof OceanQuakeMarker) m.setHidden(isactive);
                break;
            case circle: if(m instanceof LandQuakeMarker) m.setHidden(isactive);
                break;
            case triangle: if(m instanceof CityMarker) m.setHidden(isactive);
                break;

        }
    }
}
