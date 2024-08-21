package cz.ziki.had.pawn;

import cz.ziki.had.Rect;
import cz.ziki.had.Snake;

import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * Abstract class representing a common game object with basic properties and behavior.
 */
public abstract class CommonGameObject implements GameObject, Serializable {

    public Rect gameField;
    public Snake snake;
    public int xPadding;

    public Rect myPhysicalShape;
    public transient BufferedImage foodImage;

    /**
     * Checks if this game object intersects with the snake.
     *
     * @return True if the object intersects with the snake, false otherwise.
     */
    public boolean intersectingWithSnake() {
        return snake.intersectingWithRect(myPhysicalShape);
    }

    /**
     * Sets the snake associated with this game object.
     *
     * @param snake The snake to associate with this object.
     */
    @Override
    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    /**
     * Loads the image for the game object.
     * (To be implemented by subclasses).
     */
    void loadImage() {
    }
}