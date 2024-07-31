package cz.ziki.had;

import cz.ziki.had.pawn.GameObject;
import cz.ziki.had.pawn.Obstacle;
import cz.ziki.had.pawn.food.Apple;
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
import java.util.function.Consumer;

/**
 * Represents the game scene where gameplay takes place.
 */
public class GameScene extends Scene {

    Rect background, foreground;
    Snake snake;
    KeyL keyListener;
    public MouseL mouseListener;


    public FoodFactory foodFactory = new FoodFactory();


    private final Set<GameObject> gameObjects = Collections.synchronizedSet(new HashSet<>());
    private final Set<Obstacle> obstacles = Collections.synchronizedSet(new HashSet<>());
    private final Set<Food> foods = Collections.synchronizedSet(new HashSet<>());

    /**
     * Constructs a new GameScene with the specified key listener.
     *
     * @param keyListener   the key listener for handling user input
     * @param mouseListener
     */

    public GameScene(KeyL keyListener, MouseL mouseListener) {

        background = new Rect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        foreground = new Rect(24, 48, (Constants.SCREEN_WIDTH - 48) / Constants.TILE_WIDTH * Constants.TILE_WIDTH, (Constants.SCREEN_HEIGHT - 172) / Constants.TILE_WIDTH * Constants.TILE_WIDTH);
        snake = new Snake(1, Constants.TILE_WIDTH * 3 + foreground.getX(), Constants.TILE_WIDTH * 3 + foreground.getY(), Constants.TILE_WIDTH, Constants.TILE_WIDTH, foreground);
        gameObjects.add(snake);
        this.keyListener = keyListener;
        this.mouseListener = mouseListener;
        mouseListener.registerOnClick(this::toggleOnClick);
        newFood();
        addObstacle(new Obstacle(foreground,snake,2,2));
    }

    private FoodFactory.FoodType getNextFoodType() {
        Random random = new Random();
        int foodTypesNumber = random.nextInt(FoodFactory.FoodType.values().length);
        return FoodFactory.FoodType.values()[foodTypesNumber];
    }

    private int getNearestTile(double x) {
        int remainder = 0;
        remainder =  ((int)x) % Constants.TILE_WIDTH;
        return (int) (x - remainder) / Constants.TILE_WIDTH;
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

/*
        if (mouseListener.getX() >= foreground.getX() && mouseListener.getX() <= foreground.getX() + foreground.getWidth() &&
                mouseListener.getY() >= foreground.getY() && mouseListener.getY() <= foreground.getY() + foreground.getHeight()) {
            if (mouseListener.isPressed()) {


                toggleObstacle(point2D);


            }
        }
  */
    }

    private void toggleOnClick(Point2D point2D) {
        if(keyListener.isKeyPressed(KeyEvent.VK_F)) {
            System.out.println("asdf");
            toggleFood(point2D);
        } else {
            toggleObstacle(point2D);
            System.out.println("fdas");
        }

    }

    private void toggleFood(Point2D point2D) {
        Food clickedFood = foodFactory.getFood(FoodFactory.FoodType.APPLE,
                foreground,
                snake,
                getNearestTile(point2D.getX()-foreground.getX()) * Constants.TILE_WIDTH + foreground.getX(),
                getNearestTile(point2D.getY()-foreground.getY()) * Constants.TILE_WIDTH + foreground.getY());

        if (foods.contains(clickedFood)) {

            removeFood(clickedFood);
        } else {

            addFood(clickedFood);
        }
    }

    private void addFood(Food clickedFood) {
        gameObjects.add(clickedFood);
        foods.add(clickedFood);
    }

    private void removeFood(Food clickedFood) {
        gameObjects.remove(clickedFood);
        foods.remove(clickedFood);
    }

    private void toggleObstacle(Point2D point2D) {
        Obstacle clickedObstacle = new Obstacle(foreground,snake, getNearestTile(point2D.getX()-foreground.getX()) , getNearestTile(point2D.getY()-foreground.getY()));

        if (obstacles.contains(clickedObstacle)) {

            removeObstacle(clickedObstacle);
        } else {

            addObstacle(clickedObstacle);
        }
    }

    private void addObstacle(Obstacle clickedObstacle) {
        gameObjects.add(clickedObstacle);
        obstacles.add(clickedObstacle);
    }

    private void removeObstacle(Obstacle clickedObstacle) {
        gameObjects.remove(clickedObstacle);
        obstacles.remove(clickedObstacle);
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