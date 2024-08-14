package cz.ziki.had;

import java.awt.geom.Point2D;

public class ColisionChecker {


    public static boolean intersect(Point2D point2D, Rect rect) {

        return rect.getX() + rect.getWidth() > point2D.getX() &&
                point2D.getX() > rect.getX() &&
                rect.getY() + rect.getHeight() > point2D.getY() &&
                point2D.getY() > rect.getY();
    }

}
