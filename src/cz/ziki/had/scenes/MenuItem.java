package cz.ziki.had.scenes;
import cz.ziki.had.*;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class MenuItem {

    private final BufferedImage activeImage;
    private final BufferedImage passiveImage;
    private boolean isActive;
    private final Rect myPhysicalShape;
    private final Runnable action;

    public void runAction(){
        action.run();
    }

    public MenuItem(BufferedImage activeImage, BufferedImage passiveImage, boolean isActive, Rect myPhysicalShape, Runnable action) {
        this.activeImage = activeImage;
        this.passiveImage = passiveImage;
        this.isActive = isActive;
        this.myPhysicalShape = myPhysicalShape;
        this.action = action;
    }

    public boolean isClicked(Point2D point2D) {
        return     point2D.getX() >= myPhysicalShape.getX()
                && point2D.getX() <= myPhysicalShape.getX() + myPhysicalShape.getWidth()
                && point2D.getY() >= myPhysicalShape.getY()
                && point2D.getY() <= myPhysicalShape.getY() + myPhysicalShape.getHeight();

    }

    public void click(Point2D point2D) {
        if (isClicked(point2D)){
            runAction();
        }
    }

    public void draw(Graphics g) {
        g.drawImage(
                isActive ? activeImage : passiveImage,
                myPhysicalShape.getX(),
                myPhysicalShape.getY(),
                myPhysicalShape.getWidth(),
                myPhysicalShape.getHeight(),
                null);
    }

    public void hover(double x, double y) {
        this.isActive = x >= myPhysicalShape.getX()
                && x <= myPhysicalShape.getX() + myPhysicalShape.getWidth()
                && y >= myPhysicalShape.getY()
                && y <= myPhysicalShape.getY() + myPhysicalShape.getHeight();
    }
}