package cz.ziki.had;

import java.awt.geom.Point2D;

/**
 * Provides methods for collision detection between geometric shapes.
 */
public class ColisionChecker {

    /**
     * Checks if a given point intersects with a specified rectangle.
     *
     * @param point2D the point to check for intersection
     * @param rect the rectangle to check against
     * @return {@code true} if the point intersects with the rectangle, {@code false} otherwise
     */
    public static boolean intersect(Point2D point2D, Rect rect) {

        return rect.getX() + rect.getWidth() > point2D.getX() &&
                point2D.getX() > rect.getX() &&
                rect.getY() + rect.getHeight() > point2D.getY() &&
                point2D.getY() > rect.getY();
    }
}
