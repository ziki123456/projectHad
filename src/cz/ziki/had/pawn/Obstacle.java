package cz.ziki.had.pawn;

import cz.ziki.had.Constants;
import cz.ziki.had.Rect;
import cz.ziki.had.Snake;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * Represents an obstacle in the game that the snake can collide with.
 */
public class Obstacle extends CommonGameObject {

    /**
     * Constructs a new Obstacle with specified parameters.
     *
     * @param background The game field background.
     * @param snake      The snake object interacting with the obstacle.
     * @param x          The initial x-coordinate (tile-based).
     * @param y          The initial y-coordinate (tile-based).
     */
    public Obstacle(Rect background, Snake snake, int x, int y) {

        loadImage();

        this.gameField = background;
        this.snake = snake;
        this.myPhysicalShape = new Rect(  (x * Constants.TILE_WIDTH) + background.getX(), ((y  * Constants.TILE_WIDTH)+ background.getY()), Constants.TILE_WIDTH, Constants.TILE_WIDTH);

    }

    /**
     * Loads the image for the obstacle.
     */
    @Override
    public void loadImage() {
        try {

            BufferedImage foodImages = ImageIO.read(
                    Objects.
                            requireNonNull(
                                    this.getClass().
                                            getClassLoader().
                                            getResourceAsStream("obstacle.png")));
            foodImage =  foodImages;
            Graphics2D g2d = foodImages.createGraphics();
            g2d.drawImage(foodImages, 0, 0, null);
            g2d.dispose();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    /**
     * Draws the obstacle on the screen.
     *
     * @param g Graphics2D object used for rendering.
     */
    public void draw(Graphics2D g) {

        g.drawImage(this.foodImage, this.myPhysicalShape.getX() + xPadding, this.myPhysicalShape.getY(), null);
    }

    /**
     * Updates the obstacle's state. Checks for collision with the snake.
     *
     * @param dt Time delta for the update.
     */
    public void update (double dt) {
        if (intersectingWithSnake()) {
            snake.die();
        }
    }

    /**
     * Compares this obstacle to another object for equality.
     *
     * @param o The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Obstacle obstacle = (Obstacle) o;
        return myPhysicalShape.getX() == obstacle.myPhysicalShape.getX() && myPhysicalShape.getY() == obstacle.myPhysicalShape.getY();
    }

    /**
     * Returns a hash code value for the obstacle.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(myPhysicalShape.getX(), myPhysicalShape.getY());
    }
}