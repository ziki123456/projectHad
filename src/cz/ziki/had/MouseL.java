package cz.ziki.had;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.util.function.Consumer;


/**
 * Handles mouse inputs by implementing the MouseAdapter and MouseMotionListener interfaces.
 */
public class MouseL extends MouseAdapter implements MouseMotionListener {

    public boolean isPressed = false;
    public double x = 0.0, y = 0.0;
    private Consumer<Point2D> onClickListener;

    @Override
    public void mousePressed(MouseEvent e) {
        isPressed = true;
        int clickX = e.getX();
        int clickY = e.getY();

        System.out.println("Kliknuto na: X = " + clickX + ", Y = " + clickY);
        onClickListener.accept(new Point2D.Double(clickX, clickY));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isPressed = false;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.x = e.getX();
        this.y = e.getY();
    }



    public void registerOnClick(Consumer<Point2D> consumer){
        this.onClickListener = consumer;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public boolean isPressed() {
        return this.isPressed;
    }
}