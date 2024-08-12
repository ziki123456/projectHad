package cz.ziki.had.scenes;

import cz.ziki.had.*;
import cz.ziki.had.pawn.GameObject;
import cz.ziki.had.pawn.Obstacle;
import cz.ziki.had.pawn.food.Food;
import cz.ziki.had.pawn.food.FoodFactory;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Represents the game scene where gameplay takes place.
 */
public class GameScene extends CommonGameScene implements Scene {

    Snake snake;

    /**
     * Constructs a new GameScene with the specified key listener.
     *
     * @param keyListener   the key listener for handling user input
     * @param mouseListener
     */

    public GameScene(KeyL keyListener, MouseL mouseListener) {

        super(keyListener);
        snake = new Snake(1, Constants.TILE_WIDTH * 3 + foreground.getX(), Constants.TILE_WIDTH * 3 + foreground.getY(), Constants.TILE_WIDTH, Constants.TILE_WIDTH, foreground);
        gameObjects.add(snake);
        newFood();

    }

    private FoodFactory.FoodType getNextFoodType() {
        Random random = new Random();
        int foodTypesNumber = random.nextInt(FoodFactory.FoodType.values().length);
        return FoodFactory.FoodType.values()[foodTypesNumber];
    }


    /**
     * Updates the game scene based on the elapsed time.
     *
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

        if (!isFoodRemaining(gameObjects)) {
            newFood();
        }

        for (GameObject gameObject : gameObjects) {
            gameObject.update(dt);
        }

    }


    private void newFood() {
        Food newFood = foodFactory.getFood(getNextFoodType(), foreground, snake, Constants.TILE_WIDTH, Constants.TILE_WIDTH);
        gameObjects.add(newFood);
        newFood.spawn();
    }

    private boolean isFoodRemaining(Set<GameObject> gameObjects) {
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Food) {
                return true;
            }
        }
        return false;
    }

    /**
     * Draws the game scene.
     *
     * @param g the Graphics object used for drawing
     */
    @Override
    public void draw(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.fill(new Rectangle2D.Double(background.getX(), background.getY(), background.getWidth(), background.getHeight()));

        g2.setColor(new Color(131, 250, 101));
        g2.fill(new Rectangle2D.Double(foreground.getX(), foreground.getY(), foreground.getWidth(), foreground.getHeight()));

        gameObjects.forEach(gameObject -> gameObject.draw(g2));

        Font font = new Font("Arial", Font.BOLD, 40);
        FontMetrics metrics = g2.getFontMetrics(font);
        String scoreText = "Score: " + snake.score;
        g2.setFont(font);
        g2.setColor(Color.WHITE);
        g2.drawString(scoreText, Constants.SCREEN_WIDTH / 2 - (metrics.stringWidth(scoreText) / 2), Constants.SCREEN_HEIGHT - 50);

    }
}