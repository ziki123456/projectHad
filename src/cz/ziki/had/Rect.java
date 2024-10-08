package cz.ziki.had;

import java.io.Serializable;

/**
 * Represents a rectangle with a specified position (x, y) and size (width, height).
 */
public class Rect implements Serializable {

    private int x;
    private int y;
    private final int width;
    private final int height;

    /**
     * Constructs a new rectangle with the specified position and size.
     */
    public Rect(int x, int y, int width, int height) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}