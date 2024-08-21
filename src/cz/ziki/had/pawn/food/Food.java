package cz.ziki.had.pawn.food;

import cz.ziki.had.pawn.GameObject;

import java.awt.*;

/**
 * Interface representing a food object in the game.
 */
public interface Food extends GameObject {

    /**
     * Loads the image associated with the food object.
     */
    void loadImage();

    /**
     * Spawns the food on the game field at a specific or random position.
     */
    void spawn();

    /**
     * Updates the state of the food object.
     * This method is typically called every game tick to allow the food to interact with the game world.
     *
     * @param dt the time delta, used for time-based updates
     */
    void update(double dt);

    /**
     * Draws the food object on the game field.
     *
     * @param g2 the Graphics2D object used for rendering the food image
     */
    void draw(Graphics2D g2);

    /**
     * Checks if the food object has been spawned on the game field.
     *
     * @return true if the food has been spawned, false otherwise
     */
    boolean isSpawned();
}
