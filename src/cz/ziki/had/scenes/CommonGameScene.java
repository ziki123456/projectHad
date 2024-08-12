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
public abstract class CommonGameScene {

    Rect background, foreground;

    KeyL keyListener;
    MouseL mouseListener;


    FoodFactory foodFactory = new FoodFactory();


    protected final Set<GameObject> gameObjects = Collections.synchronizedSet(new HashSet<>());
    protected final Set<Obstacle> obstacles = Collections.synchronizedSet(new HashSet<>());
    protected final Set<Food> foods = Collections.synchronizedSet(new HashSet<>());

    /**
     * Constructs a new GameScene with the specified key listener.
     *
     * @param keyListener   the key listener for handling user input
     */

    public CommonGameScene(KeyL keyListener) {

        background = new Rect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        foreground = new Rect(24, 48, (Constants.SCREEN_WIDTH - 48) / Constants.TILE_WIDTH * Constants.TILE_WIDTH, (Constants.SCREEN_HEIGHT - 172) / Constants.TILE_WIDTH * Constants.TILE_WIDTH);
        this.keyListener = keyListener;

    }


    protected int getNearestTile(double x) {
        int remainder = 0;
        remainder =  ((int)x) % Constants.TILE_WIDTH;
        return (int) (x - remainder) / Constants.TILE_WIDTH;
    }


    protected void addFood(Food clickedFood) {
        gameObjects.add(clickedFood);
        foods.add(clickedFood);
    }

    protected void removeFood(Food clickedFood) {
        gameObjects.remove(clickedFood);
        foods.remove(clickedFood);
    }


    protected void addObstacle(Obstacle clickedObstacle) {
        gameObjects.add(clickedObstacle);
        obstacles.add(clickedObstacle);
    }

    protected void removeObstacle(Obstacle clickedObstacle) {
        gameObjects.remove(clickedObstacle);
        obstacles.remove(clickedObstacle);
    }



    /**
     * Draws the game scene.
     *
     * @param g the Graphics object used for drawing
     */
    public void draw(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.fill(new Rectangle2D.Double(background.getX(), background.getY(), background.getWidth(), background.getHeight()));

        g2.setColor(new Color(131, 250, 101));
        g2.fill(new Rectangle2D.Double(foreground.getX(), foreground.getY(), foreground.getWidth(), foreground.getHeight()));

        gameObjects.forEach(gameObject -> gameObject.draw(g2));

        Font font = new Font("Arial", Font.BOLD, 40);
        FontMetrics metrics = g2.getFontMetrics(font);
        g2.setFont(font);
        g2.setColor(Color.WHITE);

    }
}