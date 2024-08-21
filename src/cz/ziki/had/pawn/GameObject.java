package cz.ziki.had.pawn;

import cz.ziki.had.Snake;

import java.awt.*;

/**
 * Interface for game objects that can be updated and drawn on the screen.
 */
public interface GameObject {

    /**
     * Updates the state of the game object.
     *
     * @param dt Time delta for the update.
     */
    void update(double dt);

    /**
     * Draws the game object on the screen.
     *
     * @param g2 Graphics2D object used for rendering.
     */
    void draw(Graphics2D g2);

    /**
     * Sets the snake associated with this game object.
     *
     * @param snake The snake to associate with this object.
     */
    void setSnake(Snake snake);

}
