package cz.ziki.had.pawn.food;

import cz.ziki.had.Constants;
import cz.ziki.had.Rect;
import cz.ziki.had.Snake;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Represents an Apple in the game, which is a type of food that the snake can eat.
 */
@SuppressWarnings("ALL")
public class Apple  extends CommonFood  implements Food{

    /**
     * Constructs an Apple object.
     *
     * @param background the game field where the apple will appear
     * @param snake the snake object that interacts with the apple
     * @param x the x-coordinate of the apple's initial position
     * @param y the y-coordinate of the apple's initial position
     */
    public Apple(Rect background, Snake snake, int x, int y) {

        loadImage();
        this.gameField = background;
        this.snake = snake;
        this.myPhysicalShape = new Rect(x, y, Constants.TILE_WIDTH, Constants.TILE_WIDTH);

        xPadding = (int) ((Constants.TILE_WIDTH - this.myPhysicalShape.getWidth()) / 2.0);
    }

    /**
     * Loads the image for the Apple object.
     * The image is extracted from a sprite sheet and scaled to fit the tile size.
     */
    @Override
    public void loadImage() {
        try {

            BufferedImage foodImages = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("snakehead.png"));
            Image tmp = foodImages.getSubimage(710, 0, 230, 190).getScaledInstance(Constants.TILE_WIDTH, Constants.TILE_WIDTH, Image.SCALE_SMOOTH);
            foodImage = new BufferedImage(Constants.TILE_WIDTH, Constants.TILE_WIDTH, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = foodImage.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    /**
     * Spawns the apple at a random position on the game field.
     */
    @Override
    public void spawn() {
        randomSpawn();
    }

    /**
     * Updates the apple's state. This method checks if the apple has been eaten by the snake.
     *
     * @param dt the time delta, used for time-based updates
     */
    public void update(double dt) {

        checkIfNotEaten();

    }

    /**
     * Draws the apple on the game field if it hasn't been eaten.
     */
    public void draw(Graphics2D g2) {
        drawIfNotEaten(g2);

    }
}
