package cz.ziki.had.scenes;
import cz.ziki.had.*;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * Represents a menu item in a user interface, which can be drawn, hovered over, and clicked.
 */
public class MenuItem {

    private final BufferedImage activeImage;
    private final BufferedImage passiveImage;
    private boolean isActive;
    private final Rect myPhysicalShape;
    private final Runnable action;

    /**
     * Executes the action associated with the menu item.
     */
    public void runAction(){
        action.run();
    }

    /**
     * Constructs a MenuItem with the specified parameters.
     *
     * @param activeImage   the image displayed when the menu item is active.
     * @param passiveImage  the image displayed when the menu item is inactive.
     * @param isActive      the initial state of the menu item (active or inactive).
     * @param myPhysicalShape the rectangular area of the menu item.
     * @param action        the action to be performed when the menu item is clicked.
     */
    public MenuItem(BufferedImage activeImage, BufferedImage passiveImage, boolean isActive, Rect myPhysicalShape, Runnable action) {
        this.activeImage = activeImage;
        this.passiveImage = passiveImage;
        this.isActive = isActive;
        this.myPhysicalShape = myPhysicalShape;
        this.action = action;
    }

    /**
     * Checks if the menu item is clicked based on the given point.
     *
     * @param point2D the point to check.
     * @return true if the point is within the menu item's rectangular area; otherwise, false.
     */
    public boolean isClicked(Point2D point2D) {
        return     point2D.getX() >= myPhysicalShape.getX()
                && point2D.getX() <= myPhysicalShape.getX() + myPhysicalShape.getWidth()
                && point2D.getY() >= myPhysicalShape.getY()
                && point2D.getY() <= myPhysicalShape.getY() + myPhysicalShape.getHeight();

    }

    /**
     * Handles a click event on the menu item.
     *
     * @param point2D the point of the click event.
     */
    public void click(Point2D point2D) {
        if (isClicked(point2D)){
            runAction();
        }
    }

    /**
     * Draws the menu item on the provided Graphics object.
     *
     * @param g the Graphics object used for drawing.
     */
    public void draw(Graphics g) {
        g.drawImage(
                isActive ? activeImage : passiveImage,
                myPhysicalShape.getX(),
                myPhysicalShape.getY(),
                myPhysicalShape.getWidth(),
                myPhysicalShape.getHeight(),
                null);
    }

    /**
     * Updates the state of the menu item based on whether the given coordinates are within its rectangular area.
     *
     * @param x the x-coordinate of the mouse or pointer.
     * @param y the y-coordinate of the mouse or pointer.
     */
    public void hover(double x, double y) {
        this.isActive = x >= myPhysicalShape.getX()
                && x <= myPhysicalShape.getX() + myPhysicalShape.getWidth()
                && y >= myPhysicalShape.getY()
                && y <= myPhysicalShape.getY() + myPhysicalShape.getHeight();
    }
}