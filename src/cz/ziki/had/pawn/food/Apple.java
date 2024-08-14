package cz.ziki.had.pawn.food;

import cz.ziki.had.Constants;
import cz.ziki.had.Rect;
import cz.ziki.had.Snake;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Apple  extends CommonFood  implements Food{




    /**
     * Constructs a new cz.ziki.had.FoodObjects.Food object with the specified parameters.
     */
    /**
     * Constructs a new cz.ziki.had.FoodObjects.Food object with the specified parameters.
     *
     * @param background
     * @param snake
     * @param x
     * @param y
     */
    public Apple(Rect background, Snake snake, int x, int y) {

        loadImage();
        this.gameField = background;
        this.snake = snake;
        this.myPhysicalShape = new Rect(x, y, Constants.TILE_WIDTH, Constants.TILE_WIDTH);

        xPadding = (int) ((Constants.TILE_WIDTH - this.myPhysicalShape.getWidth()) / 2.0);
    }

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


    @Override
    public void spawn() {
        randomSpawn();
    }

    /**
     * Updates the state of the food item.
     *
     * @param dt the time elapsed since the last update in seconds
     */

    public void update(double dt) {

        checkIfNotEaten();

    }

    /**
     * Draws the food item on the game screen.
     *
     * @param g2 the Graphics2D object used for drawing
     */

    public void draw(Graphics2D g2) {
        drawIfNotEaten(g2);

    }
}
