package cz.ziki.had.scenes;

import cz.ziki.had.*;
import cz.ziki.had.pawn.GameObject;
import cz.ziki.had.pawn.Obstacle;
import cz.ziki.had.pawn.food.Food;
import cz.ziki.had.pawn.food.FoodFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Scene for editing game elements, including obstacles and food.
 */
public class EditScene extends CommonGameScene implements Scene {

    protected final Set<MenuItem> editItems = Collections.synchronizedSet(new HashSet<>());
    private static final int EDIT_ITEM_HEIGHT = 50;
    private static final int EDIT_ITEM_WIDTH = 179;

    /**
     * Constructs the EditScene and initializes menu items.
     *
     * @param keyListener  Listener for keyboard events.
     * @param mouseListener Listener for mouse events.
     */
    public EditScene(KeyL keyListener, MouseL mouseListener) {
        super(keyListener);
        this.mouseListener = mouseListener;
        mouseListener.registerOnClick(this::toggleOnClick);

        try {

            BufferedImage spritesheet = ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("SnakeEditScene.png")));

            //play
            editItems.add(
                    new MenuItemBuilder(spritesheet)
                            .setPassiveImage(0, 0, EDIT_ITEM_WIDTH, EDIT_ITEM_HEIGHT)
                            .setActiveImage(0, 50, EDIT_ITEM_WIDTH, EDIT_ITEM_HEIGHT)
                            .setMyPhysicalShape(new Rect(28, 600, EDIT_ITEM_WIDTH, EDIT_ITEM_HEIGHT))
                            .setAction(() -> cz.ziki.had.Window.getWindow().changeState(1, gameObjects, foods, obstacles))
                            .build()
            );

            //load
            editItems.add(
                    new MenuItemBuilder(spritesheet)
                            .setPassiveImage(0, 100, EDIT_ITEM_WIDTH, EDIT_ITEM_HEIGHT)
                            .setActiveImage(0, 150, EDIT_ITEM_WIDTH, EDIT_ITEM_HEIGHT)
                            .setMyPhysicalShape(new Rect(330, 600, EDIT_ITEM_WIDTH, EDIT_ITEM_HEIGHT))
                            .setAction(this::loadState)
                            .build()

            );

            //save
            editItems.add(
                    new MenuItemBuilder(spritesheet)
                            .setPassiveImage(0, 200, EDIT_ITEM_WIDTH, EDIT_ITEM_HEIGHT)
                            .setActiveImage(0, 250, EDIT_ITEM_WIDTH, EDIT_ITEM_HEIGHT)
                            .setMyPhysicalShape(new Rect(600, 600, EDIT_ITEM_WIDTH, EDIT_ITEM_HEIGHT))
                            .setAction(this::safeState)
                            .build()

            );

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Saves the current game state to a file.
     */
    public void safeState() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("editor.sav");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(gameObjects);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads the game state from a file.
     */
    public void loadState() {
        try {
            FileInputStream fileInputStream = new FileInputStream("editor.sav");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            obstacles.clear();
            gameObjects.clear();
            foods.clear();
            gameObjects.addAll((Set<GameObject>) objectInputStream.readObject());
            foods.addAll(
                    gameObjects.stream()
                            .filter((o) -> o instanceof Food)
                            .map(go -> (Food) go)
                            .collect(Collectors
                                    .toSet()));
            foods.forEach(Food::loadImage);

            obstacles.addAll(
                    gameObjects.stream()
                            .filter((o) -> o instanceof Obstacle)
                            .map(go -> (Obstacle) go)
                            .collect(Collectors
                                    .toSet()));
            obstacles.forEach(Obstacle::loadImage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the scene by checking menu item hover state.
     *
     * @param dt Time delta for the update.
     */
    @Override
    public void update(double dt) {
        editItems.forEach(item ->
                {
                    item.hover(mouseListener.getX(), mouseListener.getY());
                }
        );
    }

    /**
     * Draws the scene and menu items.
     *
     * @param g Graphics object for rendering.
     */
    @Override
    public void draw(Graphics g) {
        super.draw(g);
        editItems.forEach(item -> item.draw(g));
        String nicknameText = "Click to spawn an obstacle, hold F while clicking to spawn food";

        Font font = new Font("Tahoma", Font.BOLD, 20);
        g.setColor(Color.BLUE);
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);
        g.drawString(nicknameText, Constants.SCREEN_WIDTH / 2 - (metrics.stringWidth(nicknameText) / 2), Constants.SCREEN_HEIGHT - 15);
    }

    /**
     * Handles mouse click events to toggle food or obstacle placement.
     *
     * @param point2D Coordinates of the click.
     */
    private void toggleOnClick(Point2D point2D) {
        editItems.forEach(item ->
                {
                    item.click(point2D);
                }
        );
        if (!CollisionChecker.intersect(point2D, foreground)) return;
        if (keyListener.isKeyPressed(KeyEvent.VK_F)) {
            toggleFood(point2D);
        } else {
            toggleObstacle(point2D);
        }
    }

    /**
     * Toggles the placement of food at the specified position.
     *
     * @param point2D Coordinates of the click.
     */
    private void toggleFood(Point2D point2D) {
        Food clickedFood = foodFactory.getFood(FoodFactory.FoodType.APPLE,
                foreground,
                null,
                getNearestTile(point2D.getX() - foreground.getX()) * Constants.TILE_WIDTH + foreground.getX(),
                getNearestTile(point2D.getY() - foreground.getY()) * Constants.TILE_WIDTH + foreground.getY());

        if (foods.contains(clickedFood)) {
            removeFood(clickedFood);
        } else {
            addFood(clickedFood);
        }
    }

    /**
     * Toggles the placement of an obstacle at the specified position.
     *
     * @param point2D Coordinates of the click.
     */
    private void toggleObstacle(Point2D point2D) {
        Obstacle clickedObstacle = new Obstacle(foreground, null, getNearestTile(point2D.getX() - foreground.getX()), getNearestTile(point2D.getY() - foreground.getY()));

        if (obstacles.contains(clickedObstacle)) {
            removeObstacle(clickedObstacle);
        } else {
            addObstacle(clickedObstacle);
        }
    }
}
