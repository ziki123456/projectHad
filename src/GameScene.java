import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

/**
 * Represents the game scene where gameplay takes place.
 */

public class GameScene extends Scene {
    Rect background, foreground;
    Snake snake;
    KeyL keyListener;

    public Food food;

    /**
     * Constructs a new GameScene with the specified key listener.
     * @param keyListener the key listener for handling user input
     */

    public GameScene(KeyL keyListener) {
        background = new Rect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        foreground = new Rect(24, 48, (Constants.SCREEN_WIDTH - 48) / Constants.TILE_WIDTH * Constants.TILE_WIDTH , (Constants.SCREEN_HEIGHT - 172) / Constants.TILE_WIDTH * Constants.TILE_WIDTH );
        snake = new Snake(1, Constants.TILE_WIDTH * 3 + foreground.x, Constants.TILE_WIDTH * 3 + foreground.y, Constants.TILE_WIDTH, Constants.TILE_WIDTH, foreground);
        this.keyListener = keyListener;
        food = new Food(foreground, snake, Constants.TILE_WIDTH, Constants.TILE_WIDTH, Color.GREEN);
        food.spawn();

    }

    /**
     * Updates the game scene based on the elapsed time.
     * @param dt the time elapsed since the last update in seconds
     */

    @Override
    public void update(double dt) {
        if (keyListener.isKeyPressed(KeyEvent.VK_UP)) {
            snake.changeDirecton(Direction.UP);
        } else if (keyListener.isKeyPressed(KeyEvent.VK_DOWN)) {
            snake.changeDirecton(Direction.DOWN);
        } else if (keyListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            snake.changeDirecton(Direction.RIGHT);
        } else if (keyListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            snake.changeDirecton(Direction.LEFT);
        }

        if (!food.isSpawned) food.spawn();



        snake.update(dt);
        food.update(dt);
    }

    /**
     * Draws the game scene.
     * @param g the Graphics object used for drawing
     */

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.BLACK);
        g2.fill(new Rectangle2D.Double(background.x, background.y, background.width, background.height));

        g2.setColor(new Color(131,250,101));
        g2.fill(new Rectangle2D.Double(foreground.x, foreground.y, foreground.width, foreground.height));

        food.draw(g2);
        snake.draw(g2);

        Font font = new Font("Arial", Font.BOLD, 20);
        FontMetrics metrics = g2.getFontMetrics(font);
        String scoreText = "Score: " + snake.score;
        g2.setFont(font);
        g2.setColor(Color.WHITE);
        g2.drawString(scoreText, Constants.SCREEN_WIDTH / 2 - (metrics.stringWidth(scoreText) / 2), Constants.SCREEN_HEIGHT - 50);



    }
}