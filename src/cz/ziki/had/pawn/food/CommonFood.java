package cz.ziki.had.pawn.food;


import cz.ziki.had.pawn.CommonGameObject;
import cz.ziki.had.Constants;

import java.awt.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Abstract class representing common functionality for all types of food in the game.
 */
public abstract class CommonFood extends CommonGameObject implements Serializable {

    /**
     * Padding for positioning the food image correctly within the tile.
     * @deprecated
     */
    protected int xPadding;

    /**
     * Indicates whether the food has been spawned on the game field.
     */
    private boolean isSpawned;

    /**
     * Spawns the food item at a random position on the game background.
     */
    public void randomSpawn() {

        do {

            int tileCountX = gameField.getWidth() / Constants.TILE_WIDTH;
            int randomTileX = (int) (Math.random() * (tileCountX -1));

            int tileCountY = gameField.getHeight() / Constants.TILE_WIDTH;
            int randomTileY = (int) (Math.random() * (tileCountY -1));

            this.myPhysicalShape.setX(randomTileX * Constants.TILE_WIDTH + gameField.getX());
            this.myPhysicalShape.setY(randomTileY * Constants.TILE_WIDTH + gameField.getY());
        } while (snake.intersectingWithRect(this.myPhysicalShape));

        this.isSpawned = true;

    }

    /**
     * Draws the food on the game field if it hasn't been eaten by the snake.
     *
     * @param g2 the Graphics2D object used for drawing the food image
     */
    public void drawIfNotEaten(Graphics2D g2) {
        if (snake == null || !snake.shouldGrow) g2.drawImage(this.foodImage, this.myPhysicalShape.getX() + xPadding, this.myPhysicalShape.getY(), null);
    }

    /**
     * Checks if the food has been eaten by the snake. If so, triggers the snake to grow
     * and removes the food from the game field.
     */
    public void checkIfNotEaten() {

        if (intersectingWithSnake()) {

            snake.grow();
            this.myPhysicalShape.setX(-100);
            this.myPhysicalShape.setY(-100);
            isSpawned = false;

        }
    }

    /**
     * Compares this food object to another object for equality. Two food objects are
     * considered equal if they have the same position on the game field.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommonFood food = (CommonFood) o;
        return myPhysicalShape.getX() == food.myPhysicalShape.getX() && myPhysicalShape.getY() == food.myPhysicalShape.getY();
    }

    /**
     * Generates a hash code based on the food's position on the game field.
     *
     * @return the hash code for the food object
     */
    @Override
    public int hashCode() {
        return Objects.hash(myPhysicalShape.getX(), myPhysicalShape.getY());
    }

    /**
     * Checks if the food has been spawned on the game field.
     *
     * @return true if the food is spawned, false otherwise
     */
    public boolean isSpawned() {
        return isSpawned;
    }

}
