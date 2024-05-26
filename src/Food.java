import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Represents the food item in the game.
 */

public class Food {
    public Rect background;
    public Snake snake;
    public int width, height;
    public Color color;
    public Rect rect;
    public BufferedImage foodImage;

    public int xPadding;

    public boolean isSpawned = false;

/**
 * Constructs a new Food object with the specified parameters.
 */

    public Food(Rect background, Snake snake, int width, int height, Color color) {

        try {

            BufferedImage foodImages = ImageIO.read(new File("C:\\Users\\tadea\\Desktop\\projectHad\\snakehead.png"));
            Image tmp = foodImages.getSubimage(710, 0, 230, 190).getScaledInstance(Constants.TILE_WIDTH, Constants.TILE_WIDTH, Image.SCALE_SMOOTH);
            foodImage = new BufferedImage(Constants.TILE_WIDTH, Constants.TILE_WIDTH, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = foodImage.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();

        } catch (Exception e) {

            e.printStackTrace();

        }
        this.background = background;
        this.snake = snake;
        this.width = width;
        this.height = height;
        this.color = color;
        this.rect = new Rect(0, 0, width, height);

        xPadding = (int) ((Constants.TILE_WIDTH - this.width) / 2.0);
    }

    /**
     * Spawns the food item at a random position on the game background.
     */

    public void spawn() {
        do {
            double randX = (int) (Math.random() * (int) (background.width / Constants.TILE_WIDTH)) * Constants.TILE_WIDTH + background.x;
            double randY = (int) (Math.random() * (int) (background.height / Constants.TILE_WIDTH)) * Constants.TILE_WIDTH + background.y;
            this.rect.x = randX;
            this.rect.y = randY;
        } while (snake.intersectingWithRect(this.rect));
        this.isSpawned = true;
    }
    /**
     * Updates the state of the food item.
     * @param dt the time elapsed since the last update in seconds
     */

    public void update(double dt) {

        if (snake.intersectingWithRect(this.rect)) {

            snake.grow();
            this.rect.x = -100;
            this.rect.y = -100;
            isSpawned = false;
        }

    }

    /**
     * Draws the food item on the game screen.
     * @param g2 the Graphics2D object used for drawing
     */

    public void draw(Graphics2D g2) {

        g2.drawImage(this.foodImage, (int) this.rect.x + xPadding, (int) this.rect.y, null);

    }
}