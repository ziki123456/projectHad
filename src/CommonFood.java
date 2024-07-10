import java.awt.*;
import java.awt.image.BufferedImage;

public class CommonFood {
    public Rect background;
    public Snake snake;
    public int width, height;
    public Color color;
    public Rect rect;
    public BufferedImage foodImage;

    public int xPadding;

    private boolean isSpawned;

    public boolean isSpawned() {
        return isSpawned;
    }

    public void setSpawned(boolean spawned) {
        isSpawned = spawned;
    }

    /**
     * Spawns the food item at a random position on the game background.
     */
    public void randomSpawn() {

        do {

            double randX = (int) (Math.random() * (int) (background.width / Constants.TILE_WIDTH)) * Constants.TILE_WIDTH + background.x;
            double randY = (int) (Math.random() * (int) (background.height / Constants.TILE_WIDTH)) * Constants.TILE_WIDTH + background.y;
            this.rect.x = randX;
            this.rect.y = randY;
        } while (snake.intersectingWithRect(this.rect));

        this.isSpawned = true;

    }

    public void drawIfNotEaten(Graphics2D g2) {
        if (!snake.shouldGrow) g2.drawImage(this.foodImage, (int) this.rect.x + xPadding, (int) this.rect.y, null);
    }

    public void checkIfNotEaten() {

        if (snake.intersectingWithRect(this.rect)) {

            snake.grow();
            this.rect.x = -100;
            this.rect.y = -100;
            isSpawned = false;

        }
    }
}
